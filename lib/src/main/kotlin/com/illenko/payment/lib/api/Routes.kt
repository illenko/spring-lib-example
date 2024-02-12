package com.illenko.payment.lib.api

import com.illenko.payment.lib.api.handler.CreditHandler
import com.illenko.payment.lib.api.handler.DebitHandler
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class Routes {

    @Bean
    @ConditionalOnBean(CreditHandler::class)
    fun creditRouter(handler: CreditHandler) = router {
        POST("/credit", handler::credit)
    }

    @Bean
    @ConditionalOnBean(DebitHandler::class)
    fun debitRouter(handler: DebitHandler) = router {
        POST("/debit", handler::debit)
    }
}
