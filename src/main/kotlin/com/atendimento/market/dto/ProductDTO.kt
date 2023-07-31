package com.atendimento.market.dto
/**
 * Classe que representa o objeto de transferência de dados (DTO) para um produto.
 * É usada para receber e enviar dados relacionados a um produto entre a camada de controle (controller)
 * e a camada de serviço (service) da aplicação.
 *
 * "id" Identificador único do produto (opcional).
 * "name" Nome do produto.
 * "unit" Unidade do produto (ex: kg, unidade, litro).
 * "price" Preço do produto.
 * "categoryId" Identificador único da categoria do produto (opcional).
 */
data class ProductDTO(
        val id: Long?,
        val name: String,
        val unit: String,
        val price: Double,
        val categoryId: Long?
)
