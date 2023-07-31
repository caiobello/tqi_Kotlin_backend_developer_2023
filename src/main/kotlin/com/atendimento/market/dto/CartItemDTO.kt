package com.atendimento.market.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

/**
 *  Classe que representa o objeto de transferência de dados (DTO) para um item de carrinho.
 *  * É usada para receber e enviar dados relacionados a um item de carrinho entre a camada de controle (controller)
 *  * e a camada de serviço (service) da aplicação.
 *
 * "productId" ID do produto que será adicionado ao carrinho.
 * "quantity" Quantidade do produto que será adicionada ao carrinho (deve ser maior ou igual a 1).
 * "salePrice" Preço de venda do produto que será utilizado no carrinho (não pode ser nulo).
 */
data class CartItemDTO(
        @NotNull(message = "O ID do produto não pode ser nulo")
        val productId: Long,

        @Min(value = 1, message = "A quantidade mínima deve ser 1")
        val quantity: Int?,

        @NotNull(message = "O preço de venda não pode ser nulo")
        val salePrice: Double
)
