package com.kotlin.deliveryapi.controller.orderHistories.dto

import com.kotlin.deliveryapi.domain.order.OrderStatus

data class OrderHistoryRequest(
    val customerId: Long,
    val orderStatus: OrderStatus,
)
