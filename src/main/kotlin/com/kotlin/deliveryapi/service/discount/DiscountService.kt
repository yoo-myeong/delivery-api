package com.kotlin.deliveryapi.service.discount

import com.kotlin.deliveryapi.domain.discount.DiscountMethod
import com.kotlin.deliveryapi.repository.checkoutDiscount.CheckoutDiscountItemRepository
import com.kotlin.deliveryapi.repository.discount.Discount
import com.kotlin.deliveryapi.repository.discount.DiscountRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime

@Service
@Transactional
class DiscountService(
    private val discountRepository: DiscountRepository,
    private val checkoutDiscountItemRepository: CheckoutDiscountItemRepository,
) {
    fun findDiscountBy(checkoutId: Long): Discount? {
        val checkoutDiscountItems = checkoutDiscountItemRepository.findAllByCheckoutId(checkoutId)
        val discountIds = checkoutDiscountItems.map { it.discountId }
        val discounts = find(discountIds)
        return discounts.filter { it.discountMethod == DiscountMethod.FIXED_AMOUNT }.maxByOrNull { it.discountValue }
    }

    fun findAvailableDiscount(currentDateTime: OffsetDateTime): List<Discount> =
        this.discountRepository.findAllAvailableDiscount(currentDateTime)

    fun find(discountIds: List<Long>): List<Discount> = this.discountRepository.findAllByDiscountIdIn(discountIds)
}
