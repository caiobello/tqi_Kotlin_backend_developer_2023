package com.atendimento.market.enum

/**
 * Enumeração que representa os diferentes métodos de pagamento disponíveis na aplicação
 *
 * Os métodos de pagamento disponíveis são:
 * - CARTAO_CREDITO: Representa o pagamento por cartão de crédito.
 * - CARTAO_DEBITO: Representa o pagamento por cartão de débito.
 * - DINHEIRO: Representa o pagamento em dinheiro.
 * - PIX: Representa o pagamento por PIX, que é uma forma de transferência instantânea no Brasil.
 */

enum class PaymentMethod {
    CARTAO_CREDITO,
    CARTAO_DEBITO,
    DINHEIRO,
    PIX
}
