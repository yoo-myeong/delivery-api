package com.kotlin.deliveryapi.external.pg

import com.kotlin.deliveryapi.external.pg.dto.PgPayRequest
import com.kotlin.deliveryapi.external.pg.dto.PgPayResponse
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class PgAdapter {
    fun pay(pgPayRequest: PgPayRequest): PgPayResponse =
        PgPayResponse(
            payAmount = pgPayRequest.payAmount,
            pgPaymentId = UUID.randomUUID().toString(),
            customerId = pgPayRequest.customerId,
        )
}
