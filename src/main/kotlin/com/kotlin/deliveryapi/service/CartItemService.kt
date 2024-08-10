package com.kotlin.deliveryapi.service

import com.kotlin.deliveryapi.domain.cart.CartMenu
import com.kotlin.deliveryapi.repository.cartItem.CartItemRepository
import org.springframework.stereotype.Service

@Service
class CartItemService(
    private val cartItemRepository: CartItemRepository,
) {
    fun findAllByCartId(cartId: Long): List<CartMenu> = cartItemRepository.findAllByCartId(cartId)
}
