package com.illenko.payment.lib.service

import com.illenko.payment.lib.api.request.InternalDebitRequest
import com.illenko.payment.lib.api.response.InternalDebitResponse
import reactor.core.publisher.Mono

fun interface DebitService {
    fun debit(request: InternalDebitRequest): Mono<InternalDebitResponse>
}