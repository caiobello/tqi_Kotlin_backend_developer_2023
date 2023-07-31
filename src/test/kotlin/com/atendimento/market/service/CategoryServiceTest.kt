package com.atendimento.market.service

import com.atendimento.market.dto.CategoryDTO
import com.atendimento.market.model.Category
import com.atendimento.market.repository.CategoryRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.*

/**
 * Classe de teste para [CategoryService].
 *
 * Esta classe contém os testes unitários para a classe CategoryService. A classe CategoryService é responsável por
 * lidar com a lógica de negócio relacionada às categorias de produtos.
 *
 * Os testes são escritos usando o framework de testes JUnit Jupiter e a biblioteca Mockito para simular as
 * dependências da classe CategoryService.
 *
 *
 */
class CategoryServiceTest {

    @InjectMocks
    private lateinit var categoryService: CategoryService

    @Mock
    private lateinit var categoryRepository: CategoryRepository

    /**
     * Configuração pré-teste que é executada antes de cada teste.
     */
    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    /**
     * Teste para verificar se a criação de categoria funciona corretamente.
     */
    @Test
    fun testCreateCategory() {
        // Dado
        val categoryName = "Produtos de Limpeza"
        val categoryDTO = CategoryDTO(name = categoryName)

        // Simula o método save do categoryRepository para retornar uma categoria com um ID gerado.
        val savedCategory = Category(id = 1L, name = categoryName)
        Mockito.`when`(categoryRepository.save(Mockito.any(Category::class.java))).thenReturn(savedCategory)

        // Quando
        val resultCategory = categoryService.createCategory(categoryDTO)

        // Então
        assertEquals(categoryName, resultCategory.name)
    }

    /**
     * Teste para verificar se a obtenção de uma categoria por ID funciona corretamente quando a categoria existe.
     */
    @Test
    fun testGetCategoryById_CategoryExists() {
        // Dado
        val categoryId = 1L
        val categoryName = "Produtos de Limpeza"

        // Simula o método findById do categoryRepository para retornar uma categoria.
        val category = Category(id = categoryId, name = categoryName)
        Mockito.`when`(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category))

        // Quando
        val resultCategory = categoryService.getCategoryById(categoryId)

        // Então
        assertEquals(categoryName, resultCategory.name)
    }

    /**
     * Teste para verificar se a obtenção de uma categoria por ID funciona corretamente quando a categoria não existe.
     */
    @Test
    fun testGetCategoryById_CategoryNotExists() {
        // Dado
        val categoryId = 1L

        // Simula o método findById do categoryRepository para retornar um Optional vazio.
        Mockito.`when`(categoryRepository.findById(categoryId)).thenReturn(Optional.empty())

        // Quando e Então
        assertThrows(NoSuchElementException::class.java) {
            categoryService.getCategoryById(categoryId)
        }
    }

    /**
     * Teste para verificar se a atualização de categoria funciona corretamente quando a categoria existe.
     */
    @Test
    fun testUpdateCategory_CategoryExists() {
        // Dado
        val categoryId = 1L
        val categoryName = "Produtos de Limpeza"
        val updatedCategoryName = "Produtos de Limpeza Atualizados"
        val categoryDTO = CategoryDTO(name = updatedCategoryName)

        // Simula o método findById do categoryRepository para retornar uma categoria.
        val category = Category(id = categoryId, name = categoryName)
        Mockito.`when`(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category))

        // Simula o método save do categoryRepository para retornar a categoria atualizada.
        val updatedCategory = Category(id = categoryId, name = updatedCategoryName)
        Mockito.`when`(categoryRepository.save(Mockito.any(Category::class.java))).thenReturn(updatedCategory)

        // Quando
        val resultCategory = categoryService.updateCategory(categoryId, categoryDTO)

        // Então
        assertEquals(updatedCategoryName, resultCategory.name)
    }

    /**
     * Teste para verificar se a atualização de categoria funciona corretamente quando a categoria não existe.
     */
    @Test
    fun testUpdateCategory_CategoryNotExists() {
        // Dado
        val categoryId = 1L
        val updatedCategoryName = "Produtos de Limpeza Atualizados"
        val categoryDTO = CategoryDTO(name = updatedCategoryName)

        // Simula o método findById do categoryRepository para retornar um Optional vazio.
        Mockito.`when`(categoryRepository.findById(categoryId)).thenReturn(Optional.empty())

        // Quando e Então
        assertThrows(NoSuchElementException::class.java) {
            categoryService.updateCategory(categoryId, categoryDTO)
        }
    }

    /**
     * Teste para verificar se a exclusão de categoria funciona corretamente quando a categoria existe.
     */
    @Test
    fun testDeleteCategory_CategoryExists() {
        // Dado
        val categoryId = 1L

        // Simula o método findById do categoryRepository para retornar uma categoria.
        val category = Category(id = categoryId, name = "Produtos de Limpeza")
        Mockito.`when`(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category))

        // Quando
        categoryService.deleteCategory(categoryId)

        // Então
        // Verifica se o método deleteById do categoryRepository é chamado uma vez com o categoryId correto.
        Mockito.verify(categoryRepository, Mockito.times(1)).deleteById(categoryId)
    }

    /**
     * Teste para verificar se a exclusão de categoria funciona corretamente quando a categoria não existe.
     */
    @Test
    fun testDeleteCategory_CategoryNotExists() {
        // Dado
        val categoryId = 1L

        // Simula o método findById do categoryRepository para retornar um Optional vazio.
        Mockito.`when`(categoryRepository.findById(categoryId)).thenReturn(Optional.empty())

        // Quando
        categoryService.deleteCategory(categoryId)

        // Então
        // Você pode adicionar verificações adicionais aqui para verificar se o método deleteById do categoryRepository não foi chamado.
        Mockito.verify(categoryRepository, Mockito.times(0)).deleteById(categoryId)
    }

    /**
     * Teste para verificar se a obtenção de todas as categorias funciona corretamente.
     */
    @Test
    fun testGetAllCategories() {
        // Dado
        val categories = listOf(
                Category(id = 1L, name = "Categoria 1"),
                Category(id = 2L, name = "Categoria 2"),
                Category(id = 3L, name = "Categoria 3")
        )

        // Simula o método findAll do categoryRepository para retornar a lista de categorias.
        Mockito.`when`(categoryRepository.findAll()).thenReturn(categories)

        // Quando
        val resultCategories = categoryService.getAllCategories()

        // Então
        assertEquals(categories, resultCategories)
    }
}
