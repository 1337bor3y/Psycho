package com.example.psychoremastered.core.util

import com.google.android.gms.wallet.WalletConstants

object Constants {
    const val ROOM_THERAPIST_DB_NAME = "therapist.db"
    const val PASSWORD_LENGTH = 7
    const val FIREBASE_DB_CLIENT_PATH = "clients"
    const val FIREBASE_DB_THERAPIST_PATH = "therapists"
    const val FIREBASE_STORAGE_IMAGE_PATH = "chat_images"
    const val PREFERENCE_NAME = "preferences"
    const val PREFERENCE_KEY_IS_CLIENT = "is_client"
    const val PAGE_SIZE = 4
    const val FIREBASE_DB_UNAVAILABLE_TIME_PATH = "unavailableTimes"
    const val BOOKING_FEES_PERCENTAGE = 0.1
    const val PAYMENTS_ENVIRONMENT = WalletConstants.ENVIRONMENT_TEST
    val SUPPORTED_NETWORKS = listOf("MASTERCARD", "VISA")
    val SUPPORTED_METHODS = listOf("PAN_ONLY", "CRYPTOGRAM_3DS")
    const val COUNTRY_CODE = "UA"
    const val CURRENCY_CODE = "USD"
    private const val PAYMENT_GATEWAY_TOKENIZATION_NAME = "example"
    val PAYMENT_GATEWAY_TOKENIZATION_PARAMETERS = mapOf(
        "gateway" to PAYMENT_GATEWAY_TOKENIZATION_NAME,
        "gatewayMerchantId" to "exampleGatewayMerchantId"
    )
    const val MERCHANT_NAME = "Psycho"
}