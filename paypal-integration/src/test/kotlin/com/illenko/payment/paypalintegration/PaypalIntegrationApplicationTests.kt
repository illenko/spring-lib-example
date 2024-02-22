package com.illenko.payment.paypalintegration

import com.illenko.payment.lib.api.request.InternalCreditRequest
import com.illenko.payment.lib.api.request.InternalDebitRequest
import com.illenko.payment.lib.api.response.InternalCreditResponse
import com.illenko.payment.lib.enums.Status
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import java.util.UUID

@SpringBootTest
@AutoConfigureWebTestClient(timeout = "PT10M")
class PaypalIntegrationApplicationTests {

    @Autowired
    lateinit var client: WebTestClient

    @Test
    fun `test credit endpoint`() {
        val request = InternalCreditRequest(
            paymentId = UUID.randomUUID(),
            to = "user@user.com",
            amount = 10000,
        )

        val expected = InternalCreditResponse(
            paymentId = request.paymentId,
            status = Status.SUCCESS,
        )

        val actual = client.post()
            .uri("/credit")
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .returnResult(InternalCreditResponse::class.java)
            .responseBody
            .blockFirst()

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `test debit endpoint`() {
        val request = InternalDebitRequest(
            paymentId = UUID.randomUUID(),
            from = "user@user.com",
            amount = 10000,
            fee = 100,
        )

        client.post()
            .uri("/debit")
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus()
            .isNotFound
    }
}
