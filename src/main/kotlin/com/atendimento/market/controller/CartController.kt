package com.atendimento.market.controller

import com.atendimento.market.dto.CartItemDTO
import com.atendimento.market.model.CartItem
import com.atendimento.market.service.CartService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Controlador responsável pelas requisições relacionadas ao carrinho de compras.
 */

@RestController
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RequestMapping("/cart")
class CartController(private val cartService: CartService) {

    // Endpoint para adicionar um item ao carrinho
    @PostMapping("/post")
    fun addToCart(@Valid @RequestBody cartItemDTO: CartItemDTO): ResponseEntity<CartItem> {
        val addedCartItem = cartService.addToCart(cartItemDTO)
        return ResponseEntity(addedCartItem, HttpStatus.CREATED)
    }

    // Endpoint para remover um item do carrinho com base no productId
    @DeleteMapping("/delete/{productId}")
    fun removeFromCart(@PathVariable productId: Long): ResponseEntity<String> {
        val removed = cartService.removeFromCart(productId)
        return if (removed) {
            ResponseEntity("Produto removido do carrinho.", HttpStatus.OK)
        } else {
            ResponseEntity("Produto não encontrado no carrinho.", HttpStatus.NOT_FOUND)
        }
    }

    // Endpoint para obter informações de um item específico no carrinho com base no productId
    @GetMapping("/get/{productId}")
    fun getCartItem(@PathVariable productId: Long): ResponseEntity<CartItem> {
        val cartItem = cartService.getCartItemById(productId)
        return ResponseEntity.ok(cartItem)
    }

    // Endpoint para listar todos os itens presentes no carrinho
    @GetMapping("/get/all")
    fun getAllCartItems(): ResponseEntity<List<CartItem>> {
        val cartItems = cartService.getAllCartItems()
        return ResponseEntity.ok(cartItems)
    }
}
