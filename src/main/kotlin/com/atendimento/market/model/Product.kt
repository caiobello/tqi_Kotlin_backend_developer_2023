package com.atendimento.market.model

import com.atendimento.market.enum.PaymentMethod
import jakarta.persistence.*

/**
 * Classe que representa um produto no sistema de atendimento ao mercado.
 *
 * "id" - O ID do produto (gerado automaticamente pelo sistema).
 * "name" - O nome do produto. Deve ser único no sistema.
 * "unit" A - unidade de medida do produto (por exemplo, "kg", "un", "litro", etc.).
 * "price" - O preço do produto.
 * "category" - A categoria à qual o produto pertence. Pode ser nulo caso o produto não esteja associado a uma categoria.
 */

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["name"])])
data class Product(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        @Column(unique = true)
        var name: String,
        var unit: String,
        var price: Double,


        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "category_id")
        var category: Category? = null
)
