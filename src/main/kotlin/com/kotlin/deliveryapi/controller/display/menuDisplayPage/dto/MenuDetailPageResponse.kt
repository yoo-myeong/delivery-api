package com.kotlin.deliveryapi.controller.display.menuDisplayPage.dto

import com.kotlin.deliveryapi.domain.catalog.menu.MenuStatus
import java.math.BigDecimal

data class MenuDetailPageResponse(
    val menuId: Long,
    val menuName: String,
    val storeId: Long,
    val description: String,
    val menuMainImageUrl: String,
    val price: BigDecimal,
    val menuStatue: MenuStatus,
)
