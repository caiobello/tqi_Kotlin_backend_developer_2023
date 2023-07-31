package com.atendimento.market.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import com.atendimento.market.enum.PaymentMethod

/**
 * Classe que representa um checkout no sistema de atendimento ao mercado.
 *
 * "id" é o ID do checkout (gerado automaticamente pelo sistema).
 * "total" é o valor total do checkout.
 * "paymentMethod" é o método de pagamento do checkout.
 */

@Entity
data class Checkout(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        var total: Double,
        var paymentMethod: PaymentMethod
)
