package com.kotlin.deliveryapi.domain.order

import com.kotlin.deliveryapi.domain.orderItem.OrderItemMenu
import com.kotlin.deliveryapi.repository.orderDiscount.OrderDiscountItem

data class OrderDetail(
    val orderId: Long,
    val customerId: Long,
    val storeId: Long,
    val orderItems: List<OrderItemMenu>,
    val orderDiscountItem: OrderDiscountItem?,
)
