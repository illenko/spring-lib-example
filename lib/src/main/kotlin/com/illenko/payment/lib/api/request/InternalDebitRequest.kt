package com.illenko.payment.lib.api.request

import java.util.UUID

data class InternalDebitRequest(
    val paymentId: UUID,
    val from: String,
    val amount: Long,
    val fee: Long,
)