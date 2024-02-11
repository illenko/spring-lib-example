package com.illenko.payment.paypalintegration.service

import com.illenko.payment.lib.api.request.InternalCreditRequest
import com.illenko.payment.lib.api.response.InternalCreditResponse
import com.illenko.payment.lib.enums.Status
import com.illenko.payment.lib.service.CreditService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import mu.KotlinLogging
import reactor.kotlin.core.publisher.toMono

@Service
class CreditServiceImpl : CreditService {

    private val log = KotlinLogging.logger {}

    override fun credit(request: InternalCreditRequest): Mono<InternalCreditResponse> {
        log.info { "Processing credit request $request" }
        log.info { "Converting $request to paypal request..." }
        val response = InternalCreditResponse(paymentId = request.paymentId, status = Status.SUCCESS)
        log.info { "Converting paypal response to internal response: $response" }
        return response.toMono()
    }
}