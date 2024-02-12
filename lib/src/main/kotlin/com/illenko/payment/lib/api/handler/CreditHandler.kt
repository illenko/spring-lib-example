package com.illenko.payment.lib.api.handler

import com.illenko.payment.lib.api.request.InternalCreditRequest
import com.illenko.payment.lib.api.response.InternalCreditResponse
import com.illenko.payment.lib.service.CreditService
import mu.KotlinLogging
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.Mono

@Component
@ConditionalOnBean(CreditService::class)
@RegisterReflectionForBinding(
    InternalCreditRequest::class,
    InternalCreditResponse::class,
)
class CreditHandler(private val creditService: CreditService) {

    private val log = KotlinLogging.logger {}

    fun credit(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono<InternalCreditRequest>()
            .doOnNext { log.info { "Processing credit request: $it" } }
            .flatMap { creditService.credit(it) }
            .flatMap { ServerResponse.ok().bodyValue(it) }
}