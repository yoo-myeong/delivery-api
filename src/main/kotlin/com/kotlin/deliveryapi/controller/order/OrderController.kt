package com.kotlin.deliveryapi.controller.order

import com.kotlin.deliveryapi.controller.order.dto.OrderDetailDTO
import com.kotlin.deliveryapi.controller.order.dto.OrderDetailResponse
import com.kotlin.deliveryapi.domain.order.OrderDetail
import com.kotlin.deliveryapi.exception.NotFoundException
import com.kotlin.deliveryapi.service.order.OrderService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "orderController", description = "주문 정보 커트롤러")
@RestController
class OrderController(
    private val orderService: OrderService,
) {
    @GetMapping("/orders/{orderId}")
    fun detail(
        @PathVariable orderId: Long,
        @RequestParam customerId: Long,
    ): ResponseEntity<OrderDetailResponse> {
        val orderDetail = orderService.detail(orderId = orderId)
        validateOrder(customerId, orderDetail)
        val orderDetailDTO = OrderDetailDTO.from(orderDetail)
        return ResponseEntity.ok(OrderDetailResponse(orderDetailDTO = orderDetailDTO))
    }

    private fun validateOrder(
        customerId: Long,
        orderDetail: OrderDetail,
    ) {
        if (orderDetail.customerId != customerId) {
            throw NotFoundException("고객의 주문 정보를 찾을 수 없습니다. $customerId")
        }
    }
}
