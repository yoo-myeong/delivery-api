package com.kotlin.deliveryapi.domain.order

import java.util.UUID

class OrderUUIDGenerator {
    companion object {
        fun gen(): OrderUUID {
            val orderUUID = UUID.randomUUID().toString()
            val orderShortenId = orderUUID.substring(0, 7)
            return OrderUUID(
                id = orderUUID,
                shortenId = orderShortenId,
            )
        }
    }
}
