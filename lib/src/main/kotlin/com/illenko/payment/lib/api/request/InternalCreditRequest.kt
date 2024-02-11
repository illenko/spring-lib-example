package com.illenko.payment.lib.api.request

import java.util.UUID

data class InternalCreditRequest(
    val paymentId: UUID,
    val to: String,
    val amount: Long,
)