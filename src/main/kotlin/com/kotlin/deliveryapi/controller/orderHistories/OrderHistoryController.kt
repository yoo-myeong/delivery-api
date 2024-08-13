package com.kotlin.deliveryapi.controller.orderHistories

import com.kotlin.deliveryapi.controller.orderHistories.dto.OrderHistoryDTO
import com.kotlin.deliveryapi.controller.orderHistories.dto.OrderHistoryRequest
import com.kotlin.deliveryapi.controller.orderHistories.dto.OrderHistoryResponse
import com.kotlin.deliveryapi.service.orderHistory.OrderHistoryService
import io.github.oshai.kotlinlogging.KotlinLogging
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RestController

@Tag(name = "OrderHistoryController", description = "주문 이력 컨트롤러")
@RestController
class OrderHistoryController(
    private val orderHistoryService: OrderHistoryService,
) {
    companion object {
        private val logger = KotlinLogging.logger(this::class.java.name)
    }

    @GetMapping("/apis/order-histories")
    fun list(
        @ModelAttribute orderHistoryRequest: OrderHistoryRequest,
    ): ResponseEntity<OrderHistoryResponse> {
        logger.info { ">>> 주문 이력 조회, $orderHistoryRequest" }
        val orderDetails = orderHistoryService.findAll(orderHistoryRequest.customerId, orderHistoryRequest.orderStatus)
        val orderHistoryDTOs = orderDetails.map { OrderHistoryDTO.from(it) }
        val orderHistoryResponse =
            OrderHistoryResponse(
                orderHistories = orderHistoryDTOs,
            )
        return ResponseEntity.ok(orderHistoryResponse)
    }
}
