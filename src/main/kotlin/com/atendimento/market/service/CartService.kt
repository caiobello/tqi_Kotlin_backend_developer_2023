package com.atendimento.market.service

import com.atendimento.market.dto.CartItemDTO
import com.atendimento.market.model.CartItem
import com.atendimento.market.repository.CartRepository
import org.springframework.stereotype.Service
import java.util.*
/**
 * O CartService é uma classe de serviço que lida com a lógica de negócio relacionada ao carrinho de compras no sistema de atendimento ao mercado.
 */

@Service
class CartService(private val cartItemRepository: CartRepository, private val productService: ProductService) {

    // Adiciona um item ao carrinho com base no CartItemDTO recebido
    fun addToCart(cartItemDTO: CartItemDTO): CartItem {
        // Verifica se já existe um item de carrinho com o mesmo productId
        val existingCartItem = cartItemRepository.findByProductId(cartItemDTO.productId)

        // Obtém as informações do produto com base no productId
        val product = productService.getProductById(cartItemDTO.productId)

        // Define o preço de venda do item com base no preço do produto
        val salePrice = product.price

        // Se já existir um item de carrinho com o mesmo productId, atualiza a quantidade e o preço de venda
        return if (existingCartItem != null) {
            val updatedCartItem = existingCartItem.apply {
                // Incrementa a quantidade do item de carrinho com a quantidade fornecida no DTO ou 1 por padrão
                quantity += (cartItemDTO.quantity ?: 1)
                this.salePrice = salePrice
            }
            cartItemRepository.save(updatedCartItem) // Salva o item de carrinho atualizado no banco de dados
            updatedCartItem // Retorna o item de carrinho atualizado
        } else {
            // Se não existir um item de carrinho com o mesmo productId, cria um novo item de carrinho
            val cartItem = CartItem(
                productId = cartItemDTO.productId,
                quantity = cartItemDTO.quantity ?: 1, // Define a quantidade com a quantidade fornecida no DTO ou 1 por padrão
                salePrice = salePrice // Define o preço de venda do item com base no preço do produto
            )
            cartItemRepository.save(cartItem) // Salva o novo item de carrinho no banco de dados
            cartItem // Retorna o novo item de carrinho
        }
    }

    // Remove um item do carrinho com base no productId fornecido
    fun removeFromCart(productId: Long): Boolean {
        // Verifica se existe um item de carrinho com o productId fornecido
        val existingItem = cartItemRepository.findById(productId)
        if (existingItem.isPresent) {
            cartItemRepository.delete(existingItem.get()) // Remove o item de carrinho do banco de dados
            return true
        }
        return false
    }

    // Obtém um item de carrinho com base no productId fornecido
    fun getCartItemById(productId: Long): CartItem {
        return cartItemRepository.findById(productId)
            .orElseThrow { NoSuchElementException("CartItem not found with productId: $productId") }
    }

    // Obtém todos os itens de carrinho existentes
    fun getAllCartItems(): List<CartItem> {
        return cartItemRepository.findAll()
    }

    // Outros métodos relacionados à lógica de negócio do carrinho de compras podem ser implementados aqui
}
