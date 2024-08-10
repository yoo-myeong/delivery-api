package com.kotlin.deliveryapi.exception

class NotFoundException(
    message: String? = "Not Found",
) : RuntimeException(message)
