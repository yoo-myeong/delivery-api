package com.kotlin.deliveryapi.service.cart

import com.kotlin.deliveryapi.exception.NotFoundException
import com.kotlin.deliveryapi.repository.cart.Cart
import com.kotlin.deliveryapi.repository.cart.CartRepository
import com.kotlin.deliveryapi.repository.cartItem.CartItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CartService(
    private val cartRepository: CartRepository,
    private val cartItemRepository: CartItemRepository,
) {
    fun findByCustomerId(customerId: Long): Cart {
        val cartOptional = cartRepository.findAllByCustomerIdAndIsDeleted(customerId, false)
        if (cartOptional.isEmpty) {
            throw NotFoundException("고객님의 장바구니 정보를 찾을 수 없습니다.")
        }
        return cartOptional.get()
    }
}
