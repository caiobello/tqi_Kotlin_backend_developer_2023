package com.atendimento.market.controller

import com.atendimento.market.dto.CheckoutRequestDTO
import com.atendimento.market.model.Checkout
import com.atendimento.market.service.CheckoutService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Controlador responsável pelas requisições relacionadas aos checkouts de compras.
 */


@RestController
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RequestMapping("/checkout")
class CheckoutController(private val checkoutService: CheckoutService) {

    // Endpoint para finalizar um checkout
    @PostMapping("/post")
    fun finalizeCheckout(@Valid @RequestBody checkoutRequestDTO: CheckoutRequestDTO): ResponseEntity<Checkout> {
        val finalizedCheckout = checkoutService.finalizeCheckout(checkoutRequestDTO)
        return ResponseEntity(finalizedCheckout, HttpStatus.CREATED)
    }

    // Endpoint para atualizar o método de pagamento de um checkout específico com base no checkoutId
    @PutMapping("/put/{checkoutId}")
    fun updatePaymentMethod(
            @PathVariable checkoutId: Long,
            @Valid @RequestBody checkoutRequestDTO: CheckoutRequestDTO
    ): ResponseEntity<Checkout> {
        val updatedCheckout = checkoutService.updatePaymentMethod(checkoutId, checkoutRequestDTO)
        return updatedCheckout?.let {
            ResponseEntity(it, HttpStatus.OK)
        } ?: ResponseEntity(HttpStatus.NOT_FOUND)
    }

    // Endpoint para obter informações de um checkout específico com base no checkoutId
    @GetMapping("/get/{checkoutId}")
    fun getCheckoutById(@PathVariable checkoutId: Long): ResponseEntity<Checkout> {
        val checkout = checkoutService.getCheckoutById(checkoutId)
        return checkout?.let {
            ResponseEntity(it, HttpStatus.OK)
        } ?: ResponseEntity(HttpStatus.NOT_FOUND)
    }

    // Endpoint para listar todos os checkouts existentes
    @GetMapping("/get/all")
    fun getAllCheckouts(): ResponseEntity<List<Checkout>> {
        val checkouts = checkoutService.getAllCheckouts()
        return ResponseEntity(checkouts, HttpStatus.OK)
    }
}
