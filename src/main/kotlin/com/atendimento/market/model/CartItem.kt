package com.atendimento.market.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

/**
 * Classe que representa um item do carrinho de compras no sistema.
 *
 * "id" O ID do item do carrinho (gerado automaticamente pelo sistema).
 * "productId" O ID do produto associado ao item do carrinho.
 * "quantity" A quantidade do produto presente no carrinho.
 * "salePrice" O preço de venda do produto associado ao item do carrinho.
 */

@Entity
data class CartItem(
        @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
        val productId: Long,
        var quantity: Int,
        var salePrice: Double
)
