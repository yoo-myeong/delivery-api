package com.kotlin.deliveryapi.domain.orderItem

import java.math.BigDecimal

data class OrderItemMenu(
    val orderId: Long,
    val orderItemId: Long,
    val menuId: Long,
    val menuName: String,
    val menuPrice: BigDecimal,
    val menuMainImageUrl: String,
)
