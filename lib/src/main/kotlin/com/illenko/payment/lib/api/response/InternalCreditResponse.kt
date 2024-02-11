package com.illenko.payment.lib.api.response

import com.illenko.payment.lib.enums.Status
import java.util.UUID

data class InternalCreditResponse(
    val paymentId: UUID,
    val status: Status,
)