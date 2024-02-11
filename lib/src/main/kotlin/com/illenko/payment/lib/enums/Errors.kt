package com.illenko.payment.lib.enums

enum class Errors(
    val code: String,
    val text: String,
) {
    CREDIT_FAILED(code = "ERR-001", text = "Credit operation failed"),
    DEBIT_FAILED(code = "ERR-002", text = "Debit operation failed"),
}