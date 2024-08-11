package com.kotlin.deliveryapi.controller.payment.dto

import com.kotlin.deliveryapi.domain.payment.PaymentMethod

data class PayRequest(
    val checkoutId: Long,
    val customerId: Long,
    val paymentMethod: PaymentMethod,
)
