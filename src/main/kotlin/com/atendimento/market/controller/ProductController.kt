package com.atendimento.market.controller

import com.atendimento.market.dto.ProductDTO
import com.atendimento.market.model.Product
import com.atendimento.market.service.ProductService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Controlador responsável pelas requisições relacionadas aos produtos.
 */

@RestController
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RequestMapping("/products")
class ProductController(private val productService: ProductService) {

    // Endpoint para criar um novo produto
    @PostMapping("/post")
    fun createProduct(@Valid @RequestBody productDTO: ProductDTO): ResponseEntity<Product> {
        val createdProduct = productService.createProduct(productDTO)
        return ResponseEntity(createdProduct, HttpStatus.CREATED)
    }

    // Endpoint para obter um produto pelo seu ID
    @GetMapping("/get/{productId}")
    fun getProduct(@PathVariable productId: Long): ResponseEntity<Product> {
        val product = productService.getProductById(productId)
        return ResponseEntity.ok(product)
    }

    // Endpoint para listar todos os produtos existentes
    @GetMapping("/get/all")
    fun getAllProducts(): ResponseEntity<List<Product>> {
        val products = productService.getAllProducts()
        return ResponseEntity.ok(products)
    }

    // Endpoint para atualizar um produto pelo seu ID
    @PutMapping("/put/{productId}")
    fun updateProduct(
            @PathVariable productId: Long,
            @Valid @RequestBody productDTO: ProductDTO
    ): ResponseEntity<Product> {
        val updatedProduct = productService.updateProduct(productId, productDTO)
        return ResponseEntity.ok(updatedProduct)
    }

    // Endpoint para excluir um produto pelo seu ID
    @DeleteMapping("/delete/{productId}")
    fun deleteProduct(@PathVariable productId: Long): ResponseEntity<String> {
        productService.deleteProduct(productId)
        return ResponseEntity.ok("Produto excluído com sucesso.")
    }
}
