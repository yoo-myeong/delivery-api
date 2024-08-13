package com.kotlin.deliveryapi.domain.orderHistory

import com.kotlin.deliveryapi.domain.order.OrderStatus
import java.math.BigDecimal

data class OrderHistory(
    val orderId: Long,
    val storeId: Long,
    val storeName: String,
    val menuCount: Int,
    val menuNames: List<String>,
    val menuRepresentativeImageUrl: String,
    val totalOrderAmount: BigDecimal,
    val orderStatus: OrderStatus,
)
