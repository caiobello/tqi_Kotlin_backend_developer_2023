package com.atendimento.market.service

import com.atendimento.market.dto.CheckoutRequestDTO
import com.atendimento.market.model.Checkout
import com.atendimento.market.repository.CheckoutRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode
/**
 * O CheckoutService é uma classe de serviço que lida com a lógica de negócio relacionada ao processo de checkout no sistema de atendimento ao mercado.
 */

@Service
class CheckoutService(private val cartService: CartService, private val checkoutRepository: CheckoutRepository) {

    // Finaliza o checkout com base nas informações fornecidas no CheckoutRequestDTO
    fun finalizeCheckout(checkoutRequestDTO: CheckoutRequestDTO): Checkout {
        // Obter todos os itens do carrinho
        val cartItems = cartService.getAllCartItems()

        // Calcular o total de vendas somando o preço de cada produto no carrinho
        val totalSales = cartItems.sumOf { it.salePrice * it.quantity }

        // Arredonda o total para duas casas decimais
        val totalRounded = roundToTwoDecimalPlaces(totalSales)

        // Cria uma nova instância de Checkout com o total e o método de pagamento fornecidos
        val checkout = Checkout(
            total = totalRounded,
            paymentMethod = checkoutRequestDTO.paymentMethod
        )

        return checkoutRepository.save(checkout) // Salva o checkout no banco de dados e o retorna
    }

    // Atualiza o método de pagamento de um checkout com base no seu ID e nas informações fornecidas no CheckoutRequestDTO
    fun updatePaymentMethod(checkoutId: Long, checkoutRequestDTO: CheckoutRequestDTO): Checkout? {
        val existingCheckout = checkoutRepository.findById(checkoutId)
        if (existingCheckout.isPresent) {
            val checkout = existingCheckout.get()
            checkout.paymentMethod = checkoutRequestDTO.paymentMethod // Atualiza o método de pagamento
            return checkoutRepository.save(checkout) // Salva as alterações no banco de dados e retorna o checkout atualizado
        }
        return null
    }

    // Obtém um checkout pelo seu ID, retorna null se não for encontrado
    fun getCheckoutById(checkoutId: Long): Checkout? {
        return checkoutRepository.findById(checkoutId).orElse(null)
    }

    // Método auxiliar para arredondar o valor para duas casas decimais
    fun roundToTwoDecimalPlaces(value: Double): Double {
        return BigDecimal(value).setScale(2, RoundingMode.HALF_EVEN).toDouble()
    }

    // Obtém todos os checkouts existentes
    fun getAllCheckouts(): List<Checkout> {
        return checkoutRepository.findAll()
    }
}
