package com.kotlin.deliveryapi.controller.display.storeDetailPage

import com.kotlin.deliveryapi.controller.catalog.menu.dto.MenuDTO
import com.kotlin.deliveryapi.controller.display.storeDetailPage.dto.StoreDetailPageResponse
import com.kotlin.deliveryapi.exception.NotFoundStoreException
import com.kotlin.deliveryapi.service.menu.MenuService
import com.kotlin.deliveryapi.service.store.StoreService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@Tag(name = "storeDetailPageController", description = "상점 상세 페이지 컨트롤러")
@RestController
class StoreDetailPageController(
    val storeService: StoreService,
    val menuService: MenuService,
) {
    @GetMapping("/display/storeDetail/{storeId}")
    fun list(
        @PathVariable storeId: Long,
    ): StoreDetailPageResponse {
        val storeOptional = storeService.findByStoreId(storeId)
        if (storeOptional.isEmpty) {
            throw NotFoundStoreException("상점 정보를 찾을 수 없습니다. $storeId")
        }
        val store = storeOptional.get()

        val menus = menuService.findByStoreId(storeId)
        val menuDtos = menus.map { MenuDTO.from(it) }

        return StoreDetailPageResponse(
            storeId = storeId,
            storeName = store.storeName,
            phone = store.storePhone,
            address = store.address,
            storeMainImageUrl = store.storeMainImageUrl,
            description = store.description,
            deliveryFee = store.deliveryFee,
            deliveryTime = store.deliveryTime,
            reviewGrade = store.reviewGrade,
            minimumOrderPrice = store.minimumOrderPrice,
            menus = menuDtos,
        )
    }
}
