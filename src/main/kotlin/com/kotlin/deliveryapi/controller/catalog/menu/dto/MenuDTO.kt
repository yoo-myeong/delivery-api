package com.kotlin.deliveryapi.controller.catalog.menu.dto

import com.kotlin.deliveryapi.repository.menu.Menu
import java.math.BigDecimal

data class MenuDTO(
    val menuId: Long,
    val menuName: String,
    val storeId: Long,
    val price: BigDecimal,
    val description: String,
    val menuImageUrl: String,
) {
    companion object {
        fun from(menu: Menu): MenuDTO =
            MenuDTO(
                menuId = menu.menuId,
                menuName = menu.menuName,
                storeId = menu.storeId,
                price = menu.price,
                description = menu.description,
                menuImageUrl = menu.menuMainImageUrl,
            )
    }
}
