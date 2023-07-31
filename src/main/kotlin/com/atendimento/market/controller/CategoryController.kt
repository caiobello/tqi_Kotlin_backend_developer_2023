package com.atendimento.market.controller

import com.atendimento.market.dto.CategoryDTO
import com.atendimento.market.model.Category
import com.atendimento.market.service.CategoryService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Controlador responsável pelas requisições relacionadas às categorias de produtos.
 */

@RestController
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RequestMapping("/categories")
class CategoryController(private val categoryService: CategoryService) {

    // Endpoint para criar uma nova categoria
    @PostMapping("/post")
    fun createCategory(@Valid @RequestBody categoryDTO: CategoryDTO): ResponseEntity<Category> {
        val createdCategory = categoryService.createCategory(categoryDTO)
        return ResponseEntity(createdCategory, HttpStatus.CREATED)
    }

    // Endpoint para obter informações de uma categoria específica com base no categoryId
    @GetMapping("/get/{categoryId}")
    fun getCategory(@PathVariable categoryId: Long): ResponseEntity<Category> {
        val category = categoryService.getCategoryById(categoryId)
        return ResponseEntity.ok(category)
    }

    // Endpoint para listar todas as categorias existentes
    @GetMapping("/get/all")
    fun getAllCategories(): ResponseEntity<List<Category>> {
        val categories = categoryService.getAllCategories()
        return ResponseEntity.ok(categories)
    }

    // Endpoint para atualizar informações de uma categoria com base no categoryId
    @PutMapping("/put/{categoryId}")
    fun updateCategory(
            @PathVariable categoryId: Long,
            @Valid @RequestBody categoryDTO: CategoryDTO
    ): ResponseEntity<Category> {
        val updatedCategory = categoryService.updateCategory(categoryId, categoryDTO)
        return ResponseEntity.ok(updatedCategory)
    }

    // Endpoint para excluir uma categoria com base no categoryId
    @DeleteMapping("/delete/{categoryId}")
    fun deleteCategory(@PathVariable categoryId: Long): ResponseEntity<String> {
        categoryService.deleteCategory(categoryId)
        return ResponseEntity.ok("Categoria excluída com sucesso.")
    }
}
