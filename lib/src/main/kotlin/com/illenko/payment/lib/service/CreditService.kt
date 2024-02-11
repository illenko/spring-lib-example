package com.illenko.payment.lib.service

import com.illenko.payment.lib.api.request.InternalCreditRequest
import com.illenko.payment.lib.api.response.InternalCreditResponse
import reactor.core.publisher.Mono

fun interface CreditService {
    fun credit(request: InternalCreditRequest): Mono<InternalCreditResponse>
}