package com.kotlin.deliveryapi.service.menu

import com.kotlin.deliveryapi.repository.menu.Menu
import com.kotlin.deliveryapi.repository.menu.MenuRepository
import org.springframework.stereotype.Service

@Service
class MenuService(
    private val menuRepository: MenuRepository,
) {
    fun findByStoreId(storeId: Long): List<Menu> = menuRepository.findAllByStoreId(storeId)
}
