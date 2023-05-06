package com.hamurcuabi.billinghelper

interface BillingErrorListener {

    fun onBillingError(result: BillingResponseCode)
}

enum class BillingResponseCode(val code: Int) {

    SERVICE_TIMEOUT(-3),
    FEATURE_NOT_SUPPORTED(-2),
    SERVICE_DISCONNECTED(-1),
    OK(0),
    USER_CANCELED(1),
    SERVICE_UNAVAILABLE(2),
    BILLING_UNAVAILABLE(3),
    ITEM_UNAVAILABLE(4),
    DEVELOPER_ERROR(5),
    ERROR(6),
    ITEM_ALREADY_OWNED(7),
    ITEM_NOT_OWNED(8),
    NETWORK_ERROR(12);

    companion object {

        fun fromCode(code: Int) = values().firstOrNull { it.code == code } ?: NETWORK_ERROR
    }
}