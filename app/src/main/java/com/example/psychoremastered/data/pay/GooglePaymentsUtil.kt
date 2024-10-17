package com.example.psychoremastered.data.pay

import com.example.psychoremastered.core.util.Constants
import com.google.android.gms.tasks.Task
import com.google.android.gms.wallet.IsReadyToPayRequest
import com.google.android.gms.wallet.PaymentData
import com.google.android.gms.wallet.PaymentDataRequest
import com.google.android.gms.wallet.PaymentsClient
import kotlinx.coroutines.tasks.await
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

class GooglePaymentsUtil @Inject constructor(
    private val paymentsClient: PaymentsClient
) : PaymentsUtil {
    private val baseRequest = JSONObject()
        .put("apiVersion", 2)
        .put("apiVersionMinor", 0)

    private val gatewayTokenizationSpecification: JSONObject =
        JSONObject()
            .put("type", "PAYMENT_GATEWAY")
            .put("parameters", JSONObject(Constants.PAYMENT_GATEWAY_TOKENIZATION_PARAMETERS))

    private val allowedCardNetworks = JSONArray(Constants.SUPPORTED_NETWORKS)

    private val allowedCardAuthMethods = JSONArray(Constants.SUPPORTED_METHODS)

    private val merchantInfo: JSONObject =
        JSONObject().put("merchantName", "Example Merchant")

    private val cardPaymentMethod: JSONObject = baseCardPaymentMethod()
        .put("tokenizationSpecification", gatewayTokenizationSpecification)

    override fun getAllowedPaymentMethods(): JSONArray = JSONArray().put(cardPaymentMethod)

    override suspend fun isReadyToPayRequest(): Boolean =
        try {
            val isReady = baseRequest
                .put("allowedPaymentMethods", JSONArray().put(baseCardPaymentMethod()))
            val request = IsReadyToPayRequest.fromJson(isReady.toString())
            paymentsClient.isReadyToPay(request).await()
        } catch (e: JSONException) {
            false
        }

    private fun baseCardPaymentMethod(): JSONObject =
        JSONObject()
            .put("type", "CARD")
            .put(
                "parameters", JSONObject()
                    .put("allowedAuthMethods", allowedCardAuthMethods)
                    .put("allowedCardNetworks", allowedCardNetworks)
                    .put("billingAddressRequired", true)
                    .put(
                        "billingAddressParameters", JSONObject()
                            .put("format", "FULL")
                    )
            )

    override fun getLoadPaymentDataTask(price: String): Task<PaymentData> {
        val paymentDataRequestJson = getPaymentDataRequest(price)
        val request = PaymentDataRequest.fromJson(paymentDataRequestJson.toString())
        return paymentsClient.loadPaymentData(request)
    }

    private fun getPaymentDataRequest(price: String): JSONObject =
        baseRequest
            .put("allowedPaymentMethods", getAllowedPaymentMethods())
            .put("transactionInfo", getTransactionInfo(price))
            .put("merchantInfo", merchantInfo)
            .put("shippingAddressRequired", true)
            .put(
                "shippingAddressParameters", JSONObject()
                    .put("phoneNumberRequired", false)
                    .put("allowedCountryCodes", JSONArray(Constants.SHIPPING_SUPPORTED_COUNTRIES))
            )

    private fun getTransactionInfo(price: String): JSONObject =
        JSONObject()
            .put("totalPrice", price)
            .put("totalPriceStatus", "FINAL")
            .put("countryCode", Constants.COUNTRY_CODE)
            .put("currencyCode", Constants.CURRENCY_CODE)
}