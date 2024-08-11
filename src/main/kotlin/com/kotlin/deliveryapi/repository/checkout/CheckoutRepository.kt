package com.kotlin.deliveryapi.repository.checkout

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface CheckoutRepository : JpaRepository<Checkout, Long> {
    fun findByCustomerIdAndIsDeletedIsFalse(customerId: Long): Optional<Checkout>

    fun findAllByCheckoutIdIsNotAndCustomerIdIs(
        checkoutId: Long,
        customerId: Long,
    ): List<Checkout>
}
