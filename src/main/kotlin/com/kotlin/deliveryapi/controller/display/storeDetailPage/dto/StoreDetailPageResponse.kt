package com.kotlin.deliveryapi.controller.display.storeDetailPage.dto

import com.kotlin.deliveryapi.controller.catalog.menu.dto.MenuDTO
import java.math.BigDecimal

data class StoreDetailPageResponse(
    val storeId: Long,
    val storeName: String,
    val phone: String,
    val address: String,
    val deliveryFee: BigDecimal,
    val deliveryTime: String,
    val minimumOrderPrice: BigDecimal,
    val reviewGrade: Int,
    val storeMainImageUrl: String,
    val description: String,
    val menus: List<MenuDTO>,
)
