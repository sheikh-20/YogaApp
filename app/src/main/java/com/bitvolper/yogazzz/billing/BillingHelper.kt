package com.bitvolper.yogazzz.billing

import android.app.Activity
import android.content.Context
import android.util.Log
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.SkuDetails
import com.android.billingclient.api.SkuDetailsParams
import com.android.billingclient.api.acknowledgePurchase
import com.android.billingclient.api.queryProductDetails
import com.android.billingclient.api.queryPurchasesAsync
import com.android.billingclient.api.querySkuDetails
import com.google.common.collect.ImmutableList
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class BillingHelper @Inject constructor(
    @ApplicationContext context: Context,
    private val coroutineScope: CoroutineScope,
    knownInAppSKUs: Array<String>?,
    knowConsumableInAppKUSs: Array<String>?): PurchasesUpdatedListener, BillingClientStateListener {

    private val billingClient: BillingClient

    private val knownInAppSKUs: List<String>?
    private val knowConsumableInAppKUSs: List<String>?

    private val skuStateMap: MutableMap<String, MutableStateFlow<SkuState>> = HashMap()
    private val skuDetailsMap: MutableMap<String, MutableStateFlow<SkuDetails?>> = HashMap()

    private enum class SkuState {
        SKU_STATE_UNPURCHASED, SKU_STATE_PENDING, SKU_STATE_PURCHASED, SKU_STATE_PURCHASED_AND_ACKNOWLEDGED
    }

    override fun onBillingSetupFinished(billingResult: BillingResult) {
        val responseCode = billingResult.responseCode
        val debugMessage = billingResult.debugMessage
        Log.d(TAG, "onBillingSetupFinished: $responseCode $debugMessage")
        when (responseCode) {
            BillingClient.BillingResponseCode.OK -> {

                if (billingClient.isReady) {
                    Timber.tag(TAG).d("Billing Client is ready")
                    coroutineScope.launch {
                        querySkuDetailsAsync()
                        restorePurchases()
                    }

                } else {
                    Timber.tag(TAG).e("Billing client error!")
                }
            }
        }
    }

    /**
     * Called by initializeFlows to create the various Flow objects we're planning to emit.
     * @param skuList a List<String> of SKUs representing purchases.
     */
    private fun addSkuFlows(skuList: List<String>?) {

        Timber.tag(TAG).d(skuList.toString())

        if (null == skuList) {
            Log.e(
                TAG,
                "addSkuFlows: " +
                        "SkuList is either null or empty."
            )
        }
        for (sku in skuList!!) {
            val skuState = MutableStateFlow(SkuState.SKU_STATE_UNPURCHASED)
            val details = MutableStateFlow<SkuDetails?>(null)
            // this initialization calls querySkuDetailsAsync() when the first subscriber appears
            details.subscriptionCount.map { count ->
                count > 0
            } // map count into active/inactive flag
                .distinctUntilChanged() // only react to true<->false changes
                .onEach { isActive -> // configure an action
                    if (isActive) {
                        querySkuDetailsAsync()
                    }
                }
                .launchIn(coroutineScope) // launch it inside defaultScope

            skuStateMap[sku] = skuState
            skuDetailsMap[sku] = details
        }
    }

    /**
     * Calls the billing client functions to query sku details for the in-app SKUs.
     * SKU details are useful for displaying item names and price lists to the user, and are
     * required to make a purchase.
     */
//    private suspend fun querySkuDetailsAsync() {
//        if (!knownInAppSKUs.isNullOrEmpty()) {
//            val skuDetailsResult = billingClient.querySkuDetails(
//                SkuDetailsParams.newBuilder()
//                    .setType(BillingClient.SkuType.SUBS)
//                    .setSkusList(knownInAppSKUs.toMutableList())
//                    .build()
//            )
//            // Process the result
//            onSkuDetailsResponse(skuDetailsResult.billingResult, skuDetailsResult.skuDetailsList)
//        }
//    }

    /**
     * Calls the billing client functions to query sku details for the subscription SKUs.
     * SKU details are useful for displaying item names and price lists to the user, and are
     * required to make a purchase.
     */
    private suspend fun querySkuDetailsAsync() {
        if (!knowConsumableInAppKUSs.isNullOrEmpty()) {

            Timber.tag(TAG).d("Query details called!")

//            val skuDetailsResult = billingClient.querySkuDetails(
//                SkuDetailsParams.newBuilder()
//                    .setType(BillingClient.SkuType.SUBS)
//                    .setSkusList(knowConsumableInAppKUSs.toMutableList())
//                    .build()
//            )


            // Process the result
//            onSkuDetailsResponse(queryProductDetailsParams., skuDetailsResult.skuDetailsList)
            val productList = listOf(
                QueryProductDetailsParams.Product.newBuilder()
                    .setProductId(SUBSCRIPTION_ID)
                    .setProductType(BillingClient.ProductType.SUBS)
                    .build()
            )
            val params = QueryProductDetailsParams.newBuilder()
            params.setProductList(productList)

            // leverage queryProductDetails Kotlin extension function
            val productDetailsResult = billingClient.queryProductDetails(params.build())

            when (productDetailsResult.billingResult.responseCode) {
                BillingClient.BillingResponseCode.OK -> {

                    val result = productDetailsResult.productDetailsList?.map { it.productId }
                    Timber.tag(TAG).d(result.toString())
                }
            }
        }
    }

    /**
     * This calls all the skus available and checks if they have been purchased.
     * You should call it every time the activity starts
     */
    private suspend fun restorePurchases() {
        val purchasesResult = billingClient.queryPurchasesAsync(BillingClient.SkuType.SUBS)
        val billingResult = purchasesResult.billingResult
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
            handlePurchase(purchasesResult.purchasesList)
        }
    }

    /**
     * This is called right after [querySkuDetailsAsync]. It gets all the skus available
     * and get the details for all of them.
     */
    private fun onSkuDetailsResponse(billingResult: BillingResult, skuDetailsList: List<SkuDetails>?) {
        val responseCode = billingResult.responseCode
        val debugMessage = billingResult.debugMessage
        when (responseCode) {
            BillingClient.BillingResponseCode.OK -> {
                Timber.tag(TAG).i("onSkuDetailsResponse: " + responseCode + " " + debugMessage)

                val result = skuDetailsList?.map { it.sku }
                Timber.tag(TAG).d(result.toString())

                if (skuDetailsList == null || skuDetailsList.isEmpty()) {
                    Timber.tag(TAG)
                        .e("onSkuDetailsResponse: " + "Found null or empty SkuDetails. " + "Check to see if the SKUs you requested are correctly published " + "in the Google Play Console.")
                } else {
                    for (skuDetails in skuDetailsList) {
                        val sku = skuDetails.sku
                        val detailsMutableFlow = skuDetailsMap[sku]
                        detailsMutableFlow?.tryEmit(skuDetails) ?: Log.e(TAG, "Unknown sku: $sku")
                    }
                }
            }
        }
    }

    /**
     * Shows a   for an in-app purchase
     */
    fun launchBillingFlow(activity: Activity, sku: String) {
        val skuDetails = skuDetailsMap[sku]?.value
        if (null != skuDetails) {
            val flowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(skuDetails)
                .build()
            billingClient.launchBillingFlow(activity, flowParams)
        }
        Timber.tag(TAG).e("SkuDetails not found for: " + sku)
    }

    /**
     * This method is called right after [launchBillingFlow] which helps you complete the
     * purchase of a product
     */
    override fun onPurchasesUpdated(billingResult: BillingResult, list: MutableList<Purchase>?) {
        when (billingResult.responseCode) {
            BillingClient.BillingResponseCode.OK -> if (null != list) {
                handlePurchase(list)
                return
            } else Timber.tag(TAG).d("Null Purchase List Returned from OK response!")
            BillingClient.BillingResponseCode.USER_CANCELED -> Timber.tag(TAG)
                .i("onPurchasesUpdated: User canceled the purchase")
            BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED -> Timber.tag(TAG)
                .i("onPurchasesUpdated: The user already owns this item")
            BillingClient.BillingResponseCode.DEVELOPER_ERROR -> Timber.tag(TAG)
                .e("onPurchasesUpdated: Developer error means that Google Play " + "does not recognize the configuration. If you are just getting started, " + "make sure you have configured the application correctly in the " + "Google Play Console. The SKU product ID must match and the APK you " + "are using must be signed with release keys.")
            else -> Timber.tag(TAG)
                .d("BillingResult [" + billingResult.responseCode + "]: " + billingResult.debugMessage)
        }
    }

    /**
     * This checks the purchase of a product and acknowledge it if the valid.
     * Notice that you should implement your own server to check the validity of
     * a purchase.
     */
    private fun handlePurchase(purchases: List<Purchase>?) {
        if (null != purchases) {
            for (purchase in purchases) {
                // Global check to make sure all purchases are signed correctly.
                // This check is best performed on your server.
                val purchaseState = purchase.purchaseState
                if (purchaseState == Purchase.PurchaseState.PURCHASED){
                    if (!isSignatureValid(purchase)) {
                        Log.e(
                            TAG,
                            "Invalid signature. Check to make sure your " +
                                    "public key is correct."
                        )
                        continue
                    }
                    // only set the purchased state after we've validated the signature.
                    setSkuStateFromPurchase(purchase)

                    if (!purchase.isAcknowledged) {
                        coroutineScope.launch {
                            for (sku in purchase.skus) {
                                if (knowConsumableInAppKUSs?.contains(sku)!!) {
                                    // Consume item
                                    // You should save it inside your own server
                                }
                                // Acknowledge item and change its state
                                val billingResult = billingClient.acknowledgePurchase(
                                    AcknowledgePurchaseParams.newBuilder()
                                        .setPurchaseToken(purchase.purchaseToken)
                                        .build()
                                )
                                if (billingResult.responseCode != BillingClient.BillingResponseCode.OK) {
                                    Log.e(TAG, "Error acknowledging purchase: ${purchase.skus}")
                                } else {
                                    // purchase acknowledged
                                    val skuStateFlow = skuStateMap[sku]
                                    skuStateFlow?.tryEmit(SkuState.SKU_STATE_PURCHASED_AND_ACKNOWLEDGED)
                                }
                            }
                        }
                    }
                }  else {
                    // purchase not purchased
                    setSkuStateFromPurchase(purchase)
                }
            }
        } else {
            Log.d(TAG, "Empty purchase list.")
        }
    }

    /**
     * This sets the state of every sku inside [skuStateMap]
     */
    private fun setSkuStateFromPurchase(purchase: Purchase) {
        if (purchase.skus.isNullOrEmpty()) {
            Timber.tag(TAG).e("Empty list of skus")
            return
        }

        for (sku in purchase.skus) {
            val skuState = skuStateMap[sku]
            if (null == skuState) {
                Timber.tag(TAG)
                    .e("Unknown SKU " + sku + ". Check to make " + "sure SKU matches SKUS in the Play developer console.")
                continue
            }

            when (purchase.purchaseState) {
                Purchase.PurchaseState.PENDING -> skuState.tryEmit(SkuState.SKU_STATE_PENDING)
                Purchase.PurchaseState.UNSPECIFIED_STATE -> skuState.tryEmit(SkuState.SKU_STATE_UNPURCHASED)
                Purchase.PurchaseState.PURCHASED -> if (purchase.isAcknowledged) {
                    skuState.tryEmit(SkuState.SKU_STATE_PURCHASED_AND_ACKNOWLEDGED)
                } else {
                    skuState.tryEmit(SkuState.SKU_STATE_PURCHASED)
                }
                else -> Log.e(TAG, "Purchase in unknown state: " + purchase.purchaseState)
            }
        }
    }

    /**
     * @return Flow which says whether a purchase has been purchased and acknowledge
     */
    fun isPurchased(sku: String): Flow<Boolean> {
        val skuStateFLow = skuStateMap[sku]!!
        return skuStateFLow.map { skuState -> skuState == SkuState.SKU_STATE_PURCHASED_AND_ACKNOWLEDGED }
    }

    /**
     * This should check the validity of your purchase with a secure server
     * making this function unnecessary
     */
    private fun isSignatureValid(purchase: Purchase): Boolean {
        return Security.verifyPurchase(purchase.originalJson, purchase.signature)
    }

    /**
     * The title of our SKU from SkuDetails.
     * @param sku to get the title from
     * @return title of the requested SKU as an observable
     * */
    fun getSkuTitle(sku: String): Flow<String> {
        val skuDetailsFlow = skuDetailsMap[sku]!!
        return skuDetailsFlow.mapNotNull { skuDetails ->
            skuDetails?.title
        }
    }

    fun getSkuPrice(sku: String): Flow<String> {
        val skuDetailsFlow = skuDetailsMap[sku]!!
        return skuDetailsFlow.mapNotNull { skuDetails ->
            skuDetails?.price
        }
    }

    fun getSkuDescription(sku: String): Flow<String> {
        val skuDetailsFlow = skuDetailsMap[sku]!!
        return skuDetailsFlow.mapNotNull { skuDetails ->
            skuDetails?.description
        }
    }

    override fun onBillingServiceDisconnected() {
        Timber.tag(TAG).i("Service disconnected")
        // reconnect the service
    }

    init {
        this.knownInAppSKUs = if (knownInAppSKUs == null) {
            ArrayList()
        } else {
            listOf(*knownInAppSKUs)
        }

        this.knowConsumableInAppKUSs = if (knowConsumableInAppKUSs == null) {
            ArrayList()
        } else {
            listOf(*knowConsumableInAppKUSs)
        }

        //Add flow for in app purchases
//        addSkuFlows(this.knownInAppSKUs)

        //Add flow for subscription
        addSkuFlows(this.knowConsumableInAppKUSs)

        //Connecting the billing client
        billingClient = BillingClient.newBuilder(context)
            .setListener(this)
            .enablePendingPurchases()
            .build()
        billingClient.startConnection(this)

    }

    companion object {
        private const val TAG = "BillingHelper"
//        private val TAG = "Monsters:" + BillingHelper::class.java.simpleName
//
//        @Volatile
//        private var sInstance: BillingHelper? = null
//
//        // Standard boilerplate double check locking pattern for thread-safe singletons.
//        @JvmStatic
//        fun getInstance(
//            context: Context,
//            defaultScope: CoroutineScope,
//            knownInAppSKUs: Array<String>,
//            knowConsumableInAppKUSs: Array<String>
//        ) = sInstance ?: synchronized(this) {
//            sInstance ?: BillingHelper(
//                application,
//                defaultScope,
//                knownInAppSKUs,
//                knowConsumableInAppKUSs
//            ).also { sInstance = it }
//        }
    }
}