package com.kotlin.deliveryapi.service.orderHistory

import com.kotlin.deliveryapi.domain.order.OrderStatus
import com.kotlin.deliveryapi.domain.orderHistory.OrderHistory
import com.kotlin.deliveryapi.repository.order.OrderRepository
import com.kotlin.deliveryapi.repository.orderItem.OrderItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class OrderHistoryService(
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
) {
    fun findAll(
        customerId: Long,
        orderStatus: OrderStatus,
    ): List<OrderHistory> {
        val orders = orderRepository.findAllByCustomerId(customerId, orderStatus)
        return orders.map { orderStore ->
            val orderItemMenus = orderItemRepository.findAllByOrderId(orderId = orderStore.orderId)
            val menuRepresentativeImageUrl = orderItemMenus.first().menuMainImageUrl
            val menuNames = orderItemMenus.map { it.menuName }
            OrderHistory(
                orderId = orderStore.orderId,
                orderStatus = orderStore.orderStatus,
                storeId = orderStore.storeId,
                storeName = orderStore.storeName,
                menuCount = orderItemMenus.size,
                menuNames = menuNames,
                menuRepresentativeImageUrl = menuRepresentativeImageUrl,
                totalOrderAmount = orderStore.orderTotalAmount,
            )
        }
    }
}
