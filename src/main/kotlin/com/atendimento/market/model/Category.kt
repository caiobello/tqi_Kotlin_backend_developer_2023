package com.atendimento.market.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

/**
 * Classe que representa uma categoria no sistema de atendimento ao mercado.
 *
 * id O ID da categoria (gerado automaticamente pelo sistema).
 * name O nome da categoria.
 */


@Entity
data class Category(
        @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
        var name: String
)
