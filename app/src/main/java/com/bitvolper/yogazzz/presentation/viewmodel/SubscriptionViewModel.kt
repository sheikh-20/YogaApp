package com.bitvolper.yogazzz.presentation.viewmodel

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import com.bitvolper.yogazzz.billing.SUBSCRIPTION_ID
import com.limurse.iap.BillingClientConnectionListener
import com.limurse.iap.DataWrappers
import com.limurse.iap.IapConnector
import com.limurse.iap.SubscriptionServiceListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber
import javax.inject.Inject

data class SubscriptionUIState(val isBillingClientConnected: Boolean = false)
@HiltViewModel
class SubscriptionViewModel @Inject constructor(private val connector: IapConnector): ViewModel() {

    companion object {
        const val TAG = "SubscriptionViewModel"
    }

    private var _purchaseUIState = MutableStateFlow(SubscriptionUIState())
    val purchaseUIState: StateFlow<SubscriptionUIState> = _purchaseUIState.asStateFlow()

    private fun clientInitialize() {
        connector.addBillingClientConnectionListener(object : BillingClientConnectionListener {
            override fun onConnected(status: Boolean, billingResponseCode: Int) {
                Timber.tag(TAG).d("This is the status: " + status + " and response code is: " + billingResponseCode)

                _purchaseUIState.update {
                    it.copy(isBillingClientConnected = status)
                }
            }
        })
    }

    fun launchBilling(context: Context) {
        connector.purchase(context as Activity, SUBSCRIPTION_ID)
    }

    init {
        clientInitialize()
    }
}