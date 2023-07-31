package com.atendimento.market.service

import com.atendimento.market.dto.CategoryDTO
import com.atendimento.market.model.Category
import com.atendimento.market.repository.CategoryRepository
import org.springframework.stereotype.Service
import java.util.*
/**
 * O CategoryService é uma classe de serviço que lida com a lógica de negócio relacionada às categorias no sistema de atendimento ao mercado.
 */

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {

    // Cria uma nova categoria com base nas informações fornecidas no CategoryDTO
    fun createCategory(categoryDTO: CategoryDTO): Category {
        val category = Category(name = categoryDTO.name) // Cria uma nova instância de Category
        return categoryRepository.save(category) // Salva a categoria no banco de dados e a retorna
    }

    // Obtém uma categoria pelo seu ID
    fun getCategoryById(categoryId: Long): Category {
        return categoryRepository.findById(categoryId)
            .orElseThrow { NoSuchElementException("Category not found with id: $categoryId") }
    }

    // Obtém todas as categorias existentes
    fun getAllCategories(): List<Category> {
        return categoryRepository.findAll()
    }

    // Atualiza os dados de uma categoria com base no ID e nas informações fornecidas no CategoryDTO
    fun updateCategory(categoryId: Long, categoryDTO: CategoryDTO): Category {
        val category = getCategoryById(categoryId) // Obtém a categoria existente pelo ID
        category.name = categoryDTO.name // Atualiza o nome da categoria com o novo nome fornecido
        return categoryRepository.save(category) // Salva as alterações no banco de dados e retorna a categoria atualizada
    }

    // Deleta uma categoria com base no seu ID
    fun deleteCategory(categoryId: Long) {
        val category = categoryRepository.findById(categoryId)
        if (category.isPresent) {
            categoryRepository.deleteById(categoryId) // Deleta a categoria do banco de dados
        }
        // Caso a categoria não exista, não faz nada (não lança exceção)
    }

    // Outros métodos relacionados à lógica de negócio das categorias podem ser implementados aqui
}
