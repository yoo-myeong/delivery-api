package com.kotlin.deliveryapi.controller.cart

import com.kotlin.deliveryapi.controller.cart.dto.CartMenuDTO
import com.kotlin.deliveryapi.controller.cart.dto.CartQueryRequest
import com.kotlin.deliveryapi.controller.cart.dto.CartQueryResponse
import com.kotlin.deliveryapi.service.CartItemService
import com.kotlin.deliveryapi.service.cart.CartService
import io.github.oshai.kotlinlogging.KotlinLogging
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "CartController", description = "장바구니 컨트롤러")
@RestController
class CartController(
    private val cartService: CartService,
    private val cartItemService: CartItemService,
) {
    companion object {
        private val logger = KotlinLogging.logger(this::class.java.name)
    }

    @GetMapping("/cart/items")
    @Operation(
        summary = "장바구니 목록 요청",
        description = "현재 장바구니에 담긴 음식 메뉴 목록을 조회한다",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "장바구니 요청에 대한 응답",
                useReturnTypeSchema = true,
            ),
        ],
    )
    fun list(
        @Parameter(
            schema = Schema(implementation = CartQueryRequest::class),
            name = "cartQueryRequest",
            description = "장바구니 조회 요청",
            required = true,
            `in` = ParameterIn.QUERY,
        ) cartQueryRequest: CartQueryRequest,
    ): ResponseEntity<CartQueryResponse> {
        logger.info { ">>> 장바구니 조회 요청" }
        val cart = cartService.findByCustomerId(cartQueryRequest.customerId)
        val cartMenus = cartItemService.findAllByCartId(cart.cartId)
        val cartMenuDTOs = cartMenus.map { CartMenuDTO.from(it) }

        return ResponseEntity.ok(
            CartQueryResponse(
                customerId = cartQueryRequest.customerId,
                cartItems = cartMenuDTOs,
            ),
        )
    }
}
