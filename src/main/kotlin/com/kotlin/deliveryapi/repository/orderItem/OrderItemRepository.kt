package com.kotlin.deliveryapi.repository.orderItem

import com.kotlin.deliveryapi.domain.orderItem.OrderItemMenu
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface OrderItemRepository : JpaRepository<OrderItem, Long> {
    @Query(
        value = """
            SELECT new com.kotlin.deliveryapi.domain.orderItem.OrderItemMenu(oi.orderItemId, oi.orderId, oi.menuId, m.menuName, oi.menuPrice, m.menuMainImageUrl)
            FROM OrderItem as oi
            JOIN Menu as m ON oi.menuId = m.menuId
            WHERE oi.orderId = :orderId
        """,
    )
    fun findAllByOrderId(
        @Param("orderId") orderId: Long,
    ): List<OrderItemMenu>
}
