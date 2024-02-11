package com.illenko.payment.paypalintegration

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("com.illenko.payment")
class PaypalIntegrationApplication

fun main(args: Array<String>) {
    runApplication<PaypalIntegrationApplication>(*args)
}
