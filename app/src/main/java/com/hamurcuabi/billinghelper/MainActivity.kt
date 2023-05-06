package com.hamurcuabi.billinghelper

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData

class MainActivity : AppCompatActivity() {

    private lateinit var iapConnector: IapConnector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val isBillingClientConnected: MutableLiveData<Boolean> = MutableLiveData()

        isBillingClientConnected.value = false

        val nonConsumablesList = listOf("lifetime")
        val consumablesList = listOf("base", "moderate", "quite", "plenty", "yearly")
        val subsList = listOf("subscription")

        iapConnector = IapConnector(
            context = this,
            nonConsumableKeys = nonConsumablesList,
            consumableKeys = consumablesList,
            subscriptionKeys = subsList,
            key = "LICENSE KEY",
            enableLogging = true,
            billingErrorListener = object : BillingErrorListener {
                override fun onBillingError(result: BillingResponseCode) {
                    TODO("Not yet implemented")
                }

            }
        )

        iapConnector.addBillingClientConnectionListener(object : BillingClientConnectionListener {

            override fun onConnected(status: Boolean, billingResponseCode: Int) {
                Log.d(
                    "KSA",
                    "This is the status: $status and response code is: $billingResponseCode"
                )
                isBillingClientConnected.value = status

            }

        })

        iapConnector.addPurchaseListener(object : PurchaseServiceListener {
            override fun onPricesUpdated(iapKeyPrices: Map<String, DataWrappers.ProductDetails>) {
                // list of available products will be received here, so you can update UI with prices if needed
            }

            override fun onProductPurchased(purchaseInfo: DataWrappers.PurchaseInfo) {
                when (purchaseInfo.sku) {
                    "base" -> {
                        purchaseInfo.orderId
                    }

                    "moderate" -> {

                    }

                    "quite" -> {

                    }

                    "plenty" -> {

                    }

                    "yearly" -> {

                    }
                }
            }

            override fun onProductRestored(purchaseInfo: DataWrappers.PurchaseInfo) {
                // will be triggered fetching owned products using IapConnector;
            }
        })

        iapConnector.addSubscriptionListener(object : SubscriptionServiceListener {
            override fun onSubscriptionRestored(purchaseInfo: DataWrappers.PurchaseInfo) {
                // will be triggered upon fetching owned subscription upon initialization
            }

            override fun onSubscriptionPurchased(purchaseInfo: DataWrappers.PurchaseInfo) {
                // will be triggered whenever subscription succeeded
                when (purchaseInfo.sku) {
                    "subscription" -> {

                    }
                }
            }

            override fun onPricesUpdated(iapKeyPrices: Map<String, DataWrappers.ProductDetails>) {
                // list of available products will be received here, so you can update UI with prices if needed
            }
        })

        isBillingClientConnected.observe(this) {
            Log.d("KSA", "This is the new billing client status $it")
            when {
                it -> {
                    iapConnector.purchase(this, "base")
                    iapConnector.subscribe(this, "subscription")
                }
            }
        }
    }
}