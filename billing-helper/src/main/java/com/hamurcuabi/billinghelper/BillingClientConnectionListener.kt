package com.hamurcuabi.billinghelper

interface BillingClientConnectionListener {
    fun onConnected(status: Boolean, billingResponseCode: Int)
}