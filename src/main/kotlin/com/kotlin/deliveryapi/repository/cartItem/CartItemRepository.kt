package com.kotlin.deliveryapi.repository.cartItem

import com.kotlin.deliveryapi.domain.cart.CartMenu
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CartItemRepository : JpaRepository<CartItem, Long> {
    @Query(
        value = """
            SELECT new com.kotlin.deliveryapi.domain.cart.CartMenu(
                ci.cartId, 
                ci.cartItemId, 
                ci.menuId, 
                m.menuName, 
                m.menuMainImageUrl, 
                m.price, 
            ci.quantity) 
            FROM CartItem ci
            JOIN Menu m ON m.menuId = ci.menuId
            WHERE ci.cartId = :cartId AND ci.isDeleted = false
        """,
    )
    fun findAllByCartId(
        @Param("cartId") cartId: Long,
    ): List<CartMenu>

    fun findAllByCartIdAndMenuIdIn(
        cartId: Long,
        menuIds: List<Long>,
    ): List<CartItem>
}
