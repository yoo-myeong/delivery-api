package com.kotlin.deliveryapi.service.order

import com.kotlin.deliveryapi.controller.order.dto.OrderRequest
import com.kotlin.deliveryapi.domain.order.OrderDetail
import com.kotlin.deliveryapi.domain.order.OrderUUIDGenerator
import com.kotlin.deliveryapi.exception.DuplicateOrderException
import com.kotlin.deliveryapi.exception.NotFoundException
import com.kotlin.deliveryapi.repository.checkout.Checkout
import com.kotlin.deliveryapi.repository.checkout.CheckoutRepository
import com.kotlin.deliveryapi.repository.checkoutItem.CheckoutItem
import com.kotlin.deliveryapi.repository.checkoutItem.CheckoutItemRepository
import com.kotlin.deliveryapi.repository.order.Order
import com.kotlin.deliveryapi.repository.order.OrderRepository
import com.kotlin.deliveryapi.repository.orderDiscount.OrderDiscountItem
import com.kotlin.deliveryapi.repository.orderDiscount.OrderDiscountItemRepository
import com.kotlin.deliveryapi.repository.orderItem.OrderItem
import com.kotlin.deliveryapi.repository.orderItem.OrderItemRepository
import com.kotlin.deliveryapi.service.CartItemService
import com.kotlin.deliveryapi.service.cart.CartService
import com.kotlin.deliveryapi.service.discount.DiscountService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
@Transactional
class OrderService(
    private val checkoutRepository: CheckoutRepository,
    private val checkoutItemRepository: CheckoutItemRepository,
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
    private val discountService: DiscountService,
    private val cartService: CartService,
    private val cartItemService: CartItemService,
    private val orderDiscountItemRepository: OrderDiscountItemRepository,
) {
    @Value("\${server.role-name}")
    lateinit var roleName: String

    fun order(orderRequest: OrderRequest): Order {
        val checkoutOptional = checkoutRepository.findById(orderRequest.checkoutId)
        if (checkoutOptional.isEmpty) {
            throw NotFoundException("체크아웃 정보를 찾을 수 없습니다. ${orderRequest.checkoutId}")
        }
        val checkout = checkoutOptional.get()
        validateDuplicatedOrder(orderRequest.checkoutId)
        val checkoutItems = checkoutItemRepository.findAllByCheckoutId(checkout.checkoutId)
        val menuPrices = checkoutItems.map { it.menuPrice.multiply(BigDecimal(it.menuQuantity)) }
        val orderAmount = menuPrices.sumOf { it }
        val maxDiscount = discountService.findDiscountBy(checkoutId = checkout.checkoutId)
        val discountValue = maxDiscount?.discountValue?.let { BigDecimal(it) } ?: BigDecimal(0)
        val totalAmount = orderAmount.minus(discountValue)

        // 주문 생성
        val createdOrder = createOrder(checkout, orderAmount, discountValue, totalAmount)

        // 주문 아이템 생성
        val orderItems = createOrderItems(checkoutItems, createdOrder)

        // 장바구니 아이템 삭제
        removeCartItems(orderRequest, orderItems)

        return createdOrder
    }

    private fun createOrder(
        checkout: Checkout,
        orderAmount: BigDecimal,
        discountValue: BigDecimal,
        totalAmount: BigDecimal,
    ): Order {
        val orderUUID = OrderUUIDGenerator.gen()
        val order =
            Order(
                orderUUID = orderUUID.id,
                orderShortenId = orderUUID.shortenId,
                checkoutId = checkout.checkoutId,
                orderAmount = orderAmount,
                discountAmount = discountValue,
                deliveryFee = BigDecimal.ZERO,
                totalAmount = totalAmount,
                storeId = checkout.storeId,
                customerId = checkout.customerId,
            )
        order.createdBy = roleName
        order.updatedBy = roleName
        val createdOrder = orderRepository.save(order)
        return createdOrder
    }

    private fun createOrderItems(
        checkoutItems: List<CheckoutItem>,
        createdOrder: Order,
    ): List<OrderItem> {
        val orderItems =
            checkoutItems.map {
                val orderItem =
                    OrderItem(
                        orderId = createdOrder.orderId,
                        menuId = it.menuId,
                        menuPrice = it.menuPrice,
                        menuQuantity = it.menuQuantity,
                    )
                orderItem.createdBy = roleName
                orderItem.updatedBy = roleName
                orderItem
            }
        orderItemRepository.saveAll(orderItems)
        return orderItems
    }

    private fun removeCartItems(
        orderRequest: OrderRequest,
        orderItems: List<OrderItem>,
    ) {
        val cart = cartService.findByCustomerId(customerId = orderRequest.customerId)
        val orderedMenuIds = orderItems.map { it.menuId }.toList()
        cartItemService.remove(cartId = cart.cartId, orderedMenuIds = orderedMenuIds)
    }

    private fun validateDuplicatedOrder(checkoutId: Long) {
        val existsByCheckoutId = orderRepository.existsByCheckoutId(checkoutId)
        if (existsByCheckoutId) {
            throw DuplicateOrderException("이미 처리된 주문입니다. checkoutId: $checkoutId")
        }
    }

    fun detail(orderId: Long): OrderDetail {
        val orderOptional = orderRepository.findById(orderId)
        if (orderOptional.isEmpty) {
            throw NotFoundException("요청한 주문서($orderId) 정보를 찾을 수 없습니다.")
        }

        val order = orderOptional.get()
        val orderItemMenus = orderItemRepository.findAllByOrderId(orderId = orderId)
        val orderDiscountItems = orderDiscountItemRepository.findAllByOrderId(orderId = orderId)
        val orderDiscountItem: OrderDiscountItem? = if (orderDiscountItems.isNotEmpty()) orderDiscountItems.first() else null

        return OrderDetail(
            orderId = orderId,
            customerId = order.customerId,
            storeId = order.storeId,
            orderItems = orderItemMenus,
            orderDiscountItem = orderDiscountItem,
        )
    }
}
