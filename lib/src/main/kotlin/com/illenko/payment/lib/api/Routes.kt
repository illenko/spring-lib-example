package com.illenko.payment.lib.api

import com.illenko.payment.lib.api.handler.Handler
import com.illenko.payment.lib.service.CreditService
import com.illenko.payment.lib.service.DebitService
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class Routes {

    @Bean
    @ConditionalOnBean(CreditService::class)
    fun creditRouter(handler: Handler) = router {
        POST("/credit", handler::credit)
    }

    @Bean
    @ConditionalOnBean(DebitService::class)
    fun debitRouter(handler: Handler) = router {
        POST("/debit", handler::debit)
    }
}
