package com.atendimento.market.service

import com.atendimento.market.dto.ProductDTO
import com.atendimento.market.model.Product
import com.atendimento.market.model.Category // Importe a entidade Category
import com.atendimento.market.repository.ProductRepository
import com.atendimento.market.repository.CategoryRepository // Importe o CategoryRepository
import org.springframework.stereotype.Service
import java.util.*
/**
 * O ProductService é uma classe de serviço que lida com a lógica de negócio relacionada aos produtos no sistema de atendimento ao mercado.
 */
@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository // Injete o CategoryRepository
) {

    // Cria um novo produto com base nas informações fornecidas no ProductDTO
    fun createProduct(productDTO: ProductDTO): Product {
        // Recupera a categoria pelo seu ID do banco de dados, se estiver presente no ProductDTO
        val category = productDTO.categoryId?.let {
            categoryRepository.findById(it).orElseThrow {
                NoSuchElementException("Category not found with id: ${productDTO.categoryId}")
            }
        }

        // Cria uma nova instância de Product com as informações do ProductDTO e a categoria associada
        val product = Product(
            name = productDTO.name,
            unit = productDTO.unit,
            price = productDTO.price,
            category = category // Define a categoria associada ao produto
        )

        return productRepository.save(product) // Salva o produto no banco de dados e o retorna
    }

    // Atualiza um produto com base no seu ID e nas informações fornecidas no ProductDTO
    fun updateProduct(productId: Long, productDTO: ProductDTO): Product {
        val product = getProductById(productId) // Obtém o produto existente pelo seu ID
        product.name = productDTO.name // Atualiza o nome do produto
        product.unit = productDTO.unit // Atualiza a unidade do produto
        product.price = productDTO.price // Atualiza o preço do produto

        // Se houver um categoryId no ProductDTO, recupere a categoria correspondente pelo seu ID e associe-a ao produto
        if (productDTO.categoryId != null) {
            val category = getCategoryById(productDTO.categoryId)
            product.category = category
        }

        return productRepository.save(product) // Salva as alterações no banco de dados e retorna o produto atualizado
    }

    // Deleta um produto com base no seu ID
    fun deleteProduct(productId: Long) {
        productRepository.deleteById(productId) // Deleta o produto do banco de dados
    }

    // Obtém um produto pelo seu ID
    fun getProductById(productId: Long): Product {
        return productRepository.findById(productId)
            .orElseThrow { NoSuchElementException("Product not found with id: $productId") }
    }

    // Método auxiliar para recuperar a categoria pelo seu ID
    fun getCategoryById(categoryId: Long): Category {
        return categoryRepository.findById(categoryId)
            .orElseThrow { NoSuchElementException("Category not found with id: $categoryId") }
    }

    // Obtém todos os produtos existentes
    fun getAllProducts(): List<Product> {
        return productRepository.findAll()
    }

    // Outros métodos relacionados à lógica de negócio dos produtos podem ser implementados aqui
}
