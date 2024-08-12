package com.kotlin.deliveryapi.controller.order.dto

import com.kotlin.deliveryapi.domain.orderItem.OrderItemMenu
import java.math.BigDecimal

data class OrderItemDTO(
    val orderItemId: Long,
    val menuId: Long,
    val menuName: String,
    val menuPrice: BigDecimal,
) {
    companion object {
        fun from(orderItemMenu: OrderItemMenu): OrderItemDTO =
            OrderItemDTO(
                orderItemId = orderItemMenu.orderItemId,
                menuId = orderItemMenu.menuId,
                menuPrice = orderItemMenu.menuPrice,
                menuName = orderItemMenu.menuName,
            )
    }
}
