package com.atendimento.market.repository

import com.atendimento.market.model.CartItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
/**
 * O CartRepository é uma interface que atua como um repositório de dados para a entidade CartItem no sistema de atendimento ao mercado.
 * Essa interface estende a interface JpaRepository, fornecida pelo Spring Data JPA, para aproveitar os métodos padrão de acesso a dados (CRUD) e outros recursos relacionados à persistência.
 */
@Repository
interface CartRepository : JpaRepository<CartItem, Long> {
    fun findByProductId(productId: Long): CartItem?
}
