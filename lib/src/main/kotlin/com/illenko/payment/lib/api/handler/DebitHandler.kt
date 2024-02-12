package com.illenko.payment.lib.api.handler

import com.illenko.payment.lib.api.request.InternalDebitRequest
import com.illenko.payment.lib.api.response.InternalDebitResponse
import com.illenko.payment.lib.service.DebitService
import mu.KotlinLogging
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.Mono

@Component
@ConditionalOnBean(DebitService::class)
@RegisterReflectionForBinding(
    InternalDebitRequest::class,
    InternalDebitResponse::class,
)
class DebitHandler(private val debitService: DebitService) {

    private val log = KotlinLogging.logger {}

    fun debit(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono<InternalDebitRequest>()
            .doOnNext { log.info { "Processing debit request: $it" } }
            .flatMap { debitService.debit(it) }
            .flatMap { ServerResponse.ok().bodyValue(it) }

}