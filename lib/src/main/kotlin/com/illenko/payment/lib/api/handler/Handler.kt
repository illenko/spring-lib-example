package com.illenko.payment.lib.api.handler

import com.illenko.payment.lib.api.request.InternalCreditRequest
import com.illenko.payment.lib.api.request.InternalDebitRequest
import com.illenko.payment.lib.api.response.InternalCreditResponse
import com.illenko.payment.lib.api.response.InternalDebitResponse
import com.illenko.payment.lib.enums.Errors
import com.illenko.payment.lib.service.CreditService
import com.illenko.payment.lib.service.DebitService
import mu.KotlinLogging
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.Mono


@Component
@RegisterReflectionForBinding(
    InternalCreditRequest::class,
    InternalDebitRequest::class,
    InternalCreditResponse::class,
    InternalDebitResponse::class
)
class Handler {

    private val log = KotlinLogging.logger {}

    @Autowired(required = false)
    private lateinit var creditService: CreditService

    @Autowired(required = false)
    private lateinit var debitService: DebitService

    fun credit(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono<InternalCreditRequest>()
            .doOnNext { log.info { "Processing credit request: $it" } }
            .flatMap { creditService.credit(it) }
            .flatMap { ServerResponse.ok().bodyValue(it) }
            .doOnError { log.error { "Error when processing credit request: $it" } }
            .onErrorResume { ServerResponse.badRequest().bodyValue(Errors.CREDIT_FAILED) }

    fun debit(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono<InternalDebitRequest>()
            .doOnNext { log.info { "Processing debit request: $it" } }
            .flatMap { debitService.debit(it) }
            .flatMap { ServerResponse.ok().bodyValue(it) }
            .doOnError { log.error { "Error when processing debit request: $it" } }
            .onErrorResume { ServerResponse.badRequest().bodyValue(Errors.DEBIT_FAILED) }

}