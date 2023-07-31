package com.atendimento.market.dto
/**
 * Classe que representa o objeto de transferência de dados (DTO) para uma categoria.
 * É usada para receber e enviar dados relacionados a uma categoria entre a camada de controle (controller)
 * e a camada de serviço (service) da aplicação.
 *
 * "name" Nome da categoria.
 */

data class CategoryDTO(
    val name: String
)
