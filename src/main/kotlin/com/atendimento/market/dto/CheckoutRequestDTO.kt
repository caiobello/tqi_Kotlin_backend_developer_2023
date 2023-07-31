package com.atendimento.market.dto

import com.atendimento.market.enum.PaymentMethod
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull


/**
 * Classe que representa o objeto de transferência de dados (DTO) para uma solicitação de checkout.
 * É usada para receber e enviar dados relacionados a uma solicitação de checkout entre a camada de controle (controller)
 * e a camada de serviço (service) da aplicação.
 *
 * "total" Valor total da compra.
 * "paymentMethod" Método de pagamento escolhido para a compra.
 */

data class CheckoutRequestDTO(
    @Min(value = 0, message = "O total não pode ser negativo")
    val total: Double,

    @NotNull(message = "O método de pagamento não pode ser nulo")
    val paymentMethod: PaymentMethod
)
