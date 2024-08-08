package com.kotlin.deliveryapi.service.store

import com.kotlin.deliveryapi.repository.store.Store
import com.kotlin.deliveryapi.repository.store.StoreRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

@Service
@Transactional
class StoreService(
    private val storeRepository: StoreRepository,
) {
    fun findByStoreId(storeId: Long): Optional<Store> = storeRepository.findById(storeId)
}
