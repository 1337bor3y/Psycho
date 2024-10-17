package com.example.psychoremastered.presentation.google_pay

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.psychoremastered.domain.use_case.GetAllowedPaymentMethodsUseCase
import com.example.psychoremastered.domain.use_case.GetLoadPaymentDataTaskUseCase
import com.example.psychoremastered.domain.use_case.IsReadyToPayUseCase
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.wallet.PaymentData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class GooglePaymentViewModel @Inject constructor(
    getAllowedPaymentMethodsUseCase: GetAllowedPaymentMethodsUseCase,
    private val getLoadPaymentDataTaskUseCase: GetLoadPaymentDataTaskUseCase,
    private val isReadyToPayUseCase: IsReadyToPayUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(
        GooglePaymentState().copy(
            allowedPaymentMethods = getAllowedPaymentMethodsUseCase()
        )
    )
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            verifyGooglePayReadiness()
        }
    }

    fun onEvent(event: GooglePaymentEvent) {
        when (event) {
            is GooglePaymentEvent.LoadPaymentData -> loadPaymentData(
                price = event.price,
                onCompleteListener = event.onCompleteListener
            )

            is GooglePaymentEvent.SetPaymentData -> setPaymentData(event.paymentData)
        }
    }

    private suspend fun verifyGooglePayReadiness() {
        try {
            if (isReadyToPayUseCase()) {
                _state.update {
                    it.copy(
                        available = true,
                        error = null
                    )
                }
            } else {
                _state.update {
                    it.copy(
                        error = CommonStatusCodes.ERROR.toString()
                    )
                }
            }
        } catch (exception: ApiException) {
            _state.update {
                it.copy(
                    error = exception.message
                )
            }
        }
    }

    private fun loadPaymentData(
        price: String,
        onCompleteListener: OnCompleteListener<PaymentData>
    ) {
        getLoadPaymentDataTaskUseCase(price).addOnCompleteListener(
            onCompleteListener
        )
    }

    private fun setPaymentData(paymentData: PaymentData) {
        extractPaymentBillingName(paymentData)?.let {
            _state.update {
                it.copy(
                    paymentCompleted = true,
                    error = null
                )
            }
        } ?: _state.update {
            it.copy(
                paymentCompleted = false,
                error = CommonStatusCodes.INTERNAL_ERROR.toString()
            )
        }
    }

    private fun extractPaymentBillingName(paymentData: PaymentData): String? {
        val paymentInformation = paymentData.toJson()

        try {
            val paymentMethodData =
                JSONObject(paymentInformation).getJSONObject("paymentMethodData")
            val billingName = paymentMethodData.getJSONObject("info")
                .getJSONObject("billingAddress").getString("name")

            return billingName
        } catch (error: JSONException) {
            _state.update {
                it.copy(
                    paymentCompleted = false,
                    error = error.message
                )
            }
        }

        return null
    }
}
