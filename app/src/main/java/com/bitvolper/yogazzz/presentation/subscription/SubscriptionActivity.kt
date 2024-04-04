package com.bitvolper.yogazzz.presentation.subscription

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.coroutineScope
import com.bitvolper.yogazzz.base.BaseActivity
import com.bitvolper.yogazzz.billing.BillingHelper
import com.bitvolper.yogazzz.billing.SUBSCRIPTION_ID
import com.bitvolper.yogazzz.presentation.home.recommendation.RecommendationApp
import com.bitvolper.yogazzz.presentation.serenitydetail.yogaexercise.YogaExerciseApp
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.presentation.viewmodel.AccountViewModel
import com.bitvolper.yogazzz.presentation.viewmodel.SubscriptionViewModel
import com.limurse.iap.BillingClientConnectionListener
import com.limurse.iap.DataWrappers
import com.limurse.iap.IapConnector
import com.limurse.iap.SubscriptionServiceListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SubscriptionActivity: BaseActivity() {

    companion object {
        fun startActivity(activity: Activity?) {
            val intent = Intent(activity, SubscriptionActivity::class.java)
            activity?.startActivity(intent)
        }
    }

    private val accountViewModel: AccountViewModel by viewModels()

    @Inject
    lateinit var connector: IapConnector


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        accountViewModel.getSubscription()
        clientInitialize()

        lifecycle.coroutineScope.launch {
            accountViewModel.appThemeIndex.collect {
                setTransparentStatusBar(it.themeIndex)

                setContent {
                    YogaAppTheme(darkTheme = when (it.themeIndex) {
                        0 -> isSystemInDarkTheme()
                        1 -> false
                        else -> true
                    } ) {
                        // A surface container using the 'background' color from the theme
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            SubscriptionApp(purchaseSubscription = { connector.purchase(this@SubscriptionActivity, SUBSCRIPTION_ID) })
                        }
                    }
                }
            }
        }




        connector.addSubscriptionListener(object : SubscriptionServiceListener {
            override fun onSubscriptionRestored(purchaseInfo: DataWrappers.PurchaseInfo) {
                // will be triggered upon fetching owned subscription upon initialization
            }

            override fun onSubscriptionPurchased(purchaseInfo: DataWrappers.PurchaseInfo) {
                // will be triggered whenever subscription succeeded
                when (purchaseInfo.sku) {
                    SUBSCRIPTION_ID -> {
                        Timber.tag(SubscriptionViewModel.TAG).d("Succeeded")
                    }
                }
            }

            override fun onPricesUpdated(iapKeyPrices: Map<String, List<DataWrappers.ProductDetails>>) {
                // list of available products will be received here, so you can update UI with prices if needed
                Timber.tag(SubscriptionViewModel.TAG).d("Price -> ${iapKeyPrices.values.isEmpty()}")
            }

            override fun onPurchaseFailed(purchaseInfo: DataWrappers.PurchaseInfo?, billingResponseCode: Int?) {
                // will be triggered whenever subscription purchase is failed
                Timber.tag(SubscriptionViewModel.TAG).d("Purchase Info: ${purchaseInfo?.sku} \n response: $billingResponseCode" )
            }
        })
    }


    fun clientInitialize() {
        connector.addBillingClientConnectionListener(object : BillingClientConnectionListener {
            override fun onConnected(status: Boolean, billingResponseCode: Int) {
                Timber.tag(SubscriptionViewModel.TAG)
                    .d("This is the status: " + status + " and response code is: " + billingResponseCode)

//                _purchaseUIState.update {
//                    it.copy(isBillingClientConnected = status)
//                }
            }
        })
    }
}