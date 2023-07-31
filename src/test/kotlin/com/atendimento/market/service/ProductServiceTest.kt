package com.atendimento.market.service

import com.atendimento.market.dto.ProductDTO
import com.atendimento.market.model.Product
import com.atendimento.market.model.Category
import com.atendimento.market.repository.ProductRepository
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
 * Esta classe contém os testes unitários para a classe ProductService. A classe ProductService é responsável por
 * lidar com a lógica de negócio relacionada aos produtos e categorias do mercado de atendimento.
 *
 * Os testes são escritos usando o framework de testes JUnit Jupiter e a biblioteca Mockito para simular as
 * dependências da classe ProductService.
 */
class ProductServiceTest {

    @InjectMocks
    private lateinit var productService: ProductService

    @Mock
    private lateinit var productRepository: ProductRepository

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
     * Teste para verificar se a criação de um produto funciona corretamente.
     */
    @Test
    fun testCreateProduct() {
        // Dado
        val categoryId = 1L
        val categoryName = "Produtos de Limpeza"
        val productDTO = ProductDTO(id = null, name = "Detergente", unit = "110ml", price = 1000.0, categoryId = categoryId)

        // Simula o findById do categoryRepository para retornar uma categoria
        val category = Category(id = categoryId, name = categoryName)
        Mockito.`when`(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category))

        // Simula o save do productRepository para retornar um produto com um ID gerado
        val savedProduct = Product(id = 1L, name = "Detergente", unit = "110ml", price = 1000.0, category = category)
        Mockito.`when`(productRepository.save(Mockito.any(Product::class.java))).thenReturn(savedProduct)

        // Quando
        val resultProduct = productService.createProduct(productDTO)

        // Então
        assertEquals("Detergente", resultProduct.name)
        assertEquals("110ml", resultProduct.unit)
        assertEquals(1000.0, resultProduct.price)
        assertEquals(category, resultProduct.category)
    }

    /**
     * Teste para verificar se a atualização de um produto existente funciona corretamente.
     */
    @Test
    fun testUpdateProduct_ProductExists() {
        // Dado
        val productId = 1L
        val categoryName = "Produtos de Limpeza"
        val updatedProductName = "Updated Detergente"
        val productDTO = ProductDTO(id = productId, name = updatedProductName, unit = "110ml", price = 1200.0, categoryId = 1L)

        // Simula o findById do productRepository para retornar um produto
        val product = Product(id = productId, name = "Detergente", unit = "110ml", price = 1000.0, category = Category(id = 1L, name = categoryName))
        Mockito.`when`(productRepository.findById(productId)).thenReturn(Optional.of(product))

        // Simula o findById do categoryRepository para retornar uma categoria
        val updatedCategory = Category(id = 1L, name = categoryName)
        Mockito.`when`(categoryRepository.findById(1L)).thenReturn(Optional.of(updatedCategory))

        // Simula o save do productRepository para retornar o produto atualizado
        val updatedProduct = Product(id = productId, name = updatedProductName, unit = "110ml", price = 1200.0, category = updatedCategory)
        Mockito.`when`(productRepository.save(Mockito.any(Product::class.java))).thenReturn(updatedProduct)

        // Quando
        val resultProduct = productService.updateProduct(productId, productDTO)

        // Então
        assertEquals(updatedProductName, resultProduct.name)
        assertEquals("110ml", resultProduct.unit)
        assertEquals(1200.0, resultProduct.price)
        assertEquals(updatedCategory, resultProduct.category)
    }

    /**
     * Teste para verificar se a atualização de um produto não existente lança a exceção correta.
     */
    @Test
    fun testUpdateProduct_ProductNotExists() {
        // Dado
        val productId = 1L
        val productDTO = ProductDTO(id = productId, name = "Updated Detergente", unit = "110ml", price = 1200.0, categoryId = 1L)

        // Simula o findById do productRepository para retornar um Optional vazio
        Mockito.`when`(productRepository.findById(productId)).thenReturn(Optional.empty())

        // Quando e Então
        assertThrows(NoSuchElementException::class.java) {
            productService.updateProduct(productId, productDTO)
        }
    }

    /**
     * Teste para verificar se a exclusão de um produto funciona corretamente.
     */
    @Test
    fun testDeleteProduct() {
        // Dado
        val productId = 1L

        // Quando
        productService.deleteProduct(productId)

        // Então
        // Você pode adicionar verificações adicionais aqui para verificar se o método deleteById do productRepository foi chamado.
    }

    /**
     * Teste para verificar se a obtenção de um produto por ID, quando o produto existe, funciona corretamente.
     */
    @Test
    fun testGetProductById_ProductExists() {
        // Dado
        val productId = 1L
        val categoryName = "Produtos de Limpeza"

        // Simula o findById do productRepository para retornar um produto
        val product = Product(id = productId, name = "Detergente", unit = "110ml", price = 1000.0, category = Category(id = 1L, name = categoryName))
        Mockito.`when`(productRepository.findById(productId)).thenReturn(Optional.of(product))

        // Quando
        val resultProduct = productService.getProductById(productId)

        // Então
        assertEquals("Detergente", resultProduct.name)
        assertEquals("110ml", resultProduct.unit)
        assertEquals(1000.0, resultProduct.price)
        assertEquals(categoryName, resultProduct.category?.name)
    }

    /**
     * Teste para verificar se a obtenção de um produto por ID, quando o produto não existe, lança a exceção correta.
     */
    @Test
    fun testGetProductById_ProductNotExists() {
        // Dado
        val productId = 1L

        // Simula o findById do productRepository para retornar um Optional vazio
        Mockito.`when`(productRepository.findById(productId)).thenReturn(Optional.empty())

        // Quando e Então
        assertThrows(NoSuchElementException::class.java) {
            productService.getProductById(productId)
        }
    }

    /**
     * Teste para verificar se a obtenção de todos os produtos funciona corretamente.
     */
    @Test
    fun testGetAllProducts() {
        // Dado
        val products = listOf(
                Product(id = 1L, name = "Detergente", unit = "110ml", price = 1000.0, category = Category(id = 1L, name = "Produtos de Limpeza")),
                Product(id = 2L, name = "Sabão", unit = "100g", price = 800.0, category = Category(id = 2L, name = "Produtos de Limpeza"))
        )
        Mockito.`when`(productRepository.findAll()).thenReturn(products)

        // Quando
        val resultProducts = productService.getAllProducts()

        // Então
        assertEquals(2, resultProducts.size)
        assertEquals("Detergente", resultProducts[0].name)
        assertEquals("110ml", resultProducts[0].unit)
        assertEquals(1000.0, resultProducts[0].price)
        assertEquals("Sabão", resultProducts[1].name)
        assertEquals("100g", resultProducts[1].unit)
        assertEquals(800.0, resultProducts[1].price)
    }

    /**
     * Teste para verificar se a obtenção de uma categoria por ID, quando a categoria existe, funciona corretamente.
     */
    @Test
    fun testGetCategoryById_CategoryExists() {
        // Dado
        val categoryId = 1L
        val categoryName = "Produtos de Limpeza"

        // Simula o findById do categoryRepository para retornar uma categoria
        val category = Category(id = categoryId, name = categoryName)
        Mockito.`when`(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category))

        // Quando
        val resultCategory = productService.getCategoryById(categoryId)

        // Então
        assertEquals(categoryName, resultCategory.name)
    }

    /**
     * Teste para verificar se a obtenção de uma categoria por ID, quando a categoria não existe, lança a exceção correta.
     */
    @Test
    fun testGetCategoryById_CategoryNotExists() {
        // Dado
        val categoryId = 1L

        // Simula o findById do categoryRepository para retornar um Optional vazio
        Mockito.`when`(categoryRepository.findById(categoryId)).thenReturn(Optional.empty())

        // Quando e Então
        assertThrows(NoSuchElementException::class.java) {
            productService.getCategoryById(categoryId)
        }
    }

    // Escreva métodos de teste similares para outros métodos da classe ProductService

}
