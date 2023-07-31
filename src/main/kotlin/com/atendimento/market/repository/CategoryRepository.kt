package com.atendimento.market.repository

import com.atendimento.market.model.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
/**
 * O CategoryRepository é uma interface que atua como um repositório de dados para a entidade Category no sistema de atendimento ao mercado.
 * Essa interface estende a interface JpaRepository, fornecida pelo Spring Data JPA, para aproveitar os métodos padrão de acesso a dados (CRUD) e outros recursos relacionados à persistência.
 */
@Repository
interface CategoryRepository : JpaRepository<Category, Long>
