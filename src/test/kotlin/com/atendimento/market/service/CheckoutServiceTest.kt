package com.atendimento.market.service

import com.atendimento.market.dto.CheckoutRequestDTO
import com.atendimento.market.enum.PaymentMethod
import com.atendimento.market.model.CartItem
import com.atendimento.market.model.Checkout
import com.atendimento.market.repository.CheckoutRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Esta classe contém os testes unitários para a classe CheckoutService. A classe CheckoutService é responsável por
 * lidar com a lógica de negócio relacionada ao processo de finalização de um checkout.
 *
 * Os testes são escritos usando o framework de testes JUnit Jupiter e a biblioteca Mockito para simular as
 * dependências da classe CheckoutService.
 */
class CheckoutServiceTest {

    @InjectMocks
    private lateinit var checkoutService: CheckoutService

    @Mock
    private lateinit var cartService: CartService

    @Mock
    private lateinit var checkoutRepository: CheckoutRepository

    /**
     * Configuração pré-teste que é executada antes de cada teste.
     */
    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    /**
     * Teste para verificar se o processo de finalização de checkout funciona corretamente.
     */
    @Test
    fun testFinalizeCheckout() {
        // Dado
        val cartItems = listOf(
                CartItem(id = 1L, productId = 1L, quantity = 2, salePrice = 10.0),
                CartItem(id = 2L, productId = 2L, quantity = 1, salePrice = 5.0)
        )
        val totalSales = 25.0
        val roundedTotal = 25.0

        val checkoutRequestDTO = CheckoutRequestDTO(total = totalSales, paymentMethod = PaymentMethod.CARTAO_CREDITO)

        // Simula o serviço do carrinho para retornar os itens do carrinho
        Mockito.`when`(cartService.getAllCartItems()).thenReturn(cartItems)

        // Simula o repositório de checkout para retornar o checkout salvo
        val savedCheckout = Checkout(id = 1L, total = roundedTotal, paymentMethod = PaymentMethod.CARTAO_CREDITO)
        Mockito.`when`(checkoutRepository.save(Mockito.any(Checkout::class.java))).thenReturn(savedCheckout)

        // Quando
        val resultCheckout = checkoutService.finalizeCheckout(checkoutRequestDTO)

        // Então
        assertEquals(roundedTotal, resultCheckout.total)
        assertEquals(PaymentMethod.CARTAO_CREDITO, resultCheckout.paymentMethod)
    }

    /**
     * Teste para verificar se a atualização do método de pagamento em um checkout existente funciona corretamente.
     */
    @Test
    fun testUpdatePaymentMethod_CheckoutExists() {
        // Dado
        val checkoutId = 1L
        val existingCheckout = Checkout(id = checkoutId, total = 50.0, paymentMethod = PaymentMethod.DINHEIRO)

        val checkoutRequestDTO = CheckoutRequestDTO(total = 50.0, paymentMethod = PaymentMethod.CARTAO_DEBITO)

        // Simula o repositório de checkout para retornar o checkout existente
        Mockito.`when`(checkoutRepository.findById(checkoutId)).thenReturn(java.util.Optional.of(existingCheckout))

        // Simula o repositório de checkout para retornar o checkout atualizado
        val updatedCheckout = Checkout(id = checkoutId, total = 50.0, paymentMethod = PaymentMethod.CARTAO_DEBITO)
        Mockito.`when`(checkoutRepository.save(Mockito.any(Checkout::class.java))).thenReturn(updatedCheckout)

        // Quando
        val resultCheckout = checkoutService.updatePaymentMethod(checkoutId, checkoutRequestDTO)

        // Então
        assertEquals(PaymentMethod.CARTAO_DEBITO, resultCheckout?.paymentMethod)
    }

    /**
     * Teste para verificar se a atualização do método de pagamento em um checkout não existente funciona corretamente.
     */
    @Test
    fun testUpdatePaymentMethod_CheckoutNotExists() {
        // Dado
        val checkoutId = 1L

        val checkoutRequestDTO = CheckoutRequestDTO(total = 50.0, paymentMethod = PaymentMethod.CARTAO_DEBITO)

        // Simula o repositório de checkout para retornar um Optional vazio
        Mockito.`when`(checkoutRepository.findById(checkoutId)).thenReturn(java.util.Optional.empty())

        // Quando
        val resultCheckout = checkoutService.updatePaymentMethod(checkoutId, checkoutRequestDTO)

        // Então
        assertEquals(null, resultCheckout)
    }

    /**
     * Teste para verificar se a obtenção de um checkout por ID, quando o checkout existe, funciona corretamente.
     */
    @Test
    fun testGetCheckoutById_CheckoutExists() {
        // Dado
        val checkoutId = 1L
        val existingCheckout = Checkout(id = checkoutId, total = 50.0, paymentMethod = PaymentMethod.DINHEIRO)

        // Simula o repositório de checkout para retornar o checkout existente
        Mockito.`when`(checkoutRepository.findById(checkoutId)).thenReturn(java.util.Optional.of(existingCheckout))

        // Quando
        val resultCheckout = checkoutService.getCheckoutById(checkoutId)

        // Então
        assertEquals(checkoutId, resultCheckout?.id)
        assertEquals(50.0, resultCheckout?.total)
        assertEquals(PaymentMethod.DINHEIRO, resultCheckout?.paymentMethod)
    }

    /**
     * Teste para verificar se a obtenção de um checkout por ID, quando o checkout não existe, funciona corretamente.
     */
    @Test
    fun testGetCheckoutById_CheckoutNotExists() {
        // Dado
        val checkoutId = 1L

        // Simula o repositório de checkout para retornar um Optional vazio
        Mockito.`when`(checkoutRepository.findById(checkoutId)).thenReturn(java.util.Optional.empty())

        // Quando
        val resultCheckout = checkoutService.getCheckoutById(checkoutId)

        // Então
        assertEquals(null, resultCheckout)
    }

    /**
     * Teste para verificar se a obtenção de todos os checkouts, quando não existem checkouts, funciona corretamente.
     */
    @Test
    fun testGetAllCheckouts_NoCheckouts() {
        // Dado
        Mockito.`when`(checkoutRepository.findAll()).thenReturn(emptyList())

        // Quando
        val resultCheckouts = checkoutService.getAllCheckouts()

        // Então
        assertEquals(0, resultCheckouts.size)
    }

    /**
     * Teste para verificar se a obtenção de todos os checkouts, quando existem checkouts, funciona corretamente.
     */
    @Test
    fun testGetAllCheckouts_CheckoutsExist() {
        // Dado
        val checkouts = listOf(
                Checkout(id = 1L, total = 50.0, paymentMethod = PaymentMethod.DINHEIRO),
                Checkout(id = 2L, total = 30.0, paymentMethod = PaymentMethod.CARTAO_CREDITO)
        )
        Mockito.`when`(checkoutRepository.findAll()).thenReturn(checkouts)

        // Quando
        val resultCheckouts = checkoutService.getAllCheckouts()

        // Então
        assertEquals(2, resultCheckouts.size)
        assertEquals(50.0, resultCheckouts[0].total)
        assertEquals(PaymentMethod.DINHEIRO, resultCheckouts[0].paymentMethod)
        assertEquals(30.0, resultCheckouts[1].total)
        assertEquals(PaymentMethod.CARTAO_CREDITO, resultCheckouts[1].paymentMethod)
    }

    /**
     * Teste para verificar se o arredondamento para duas casas decimais funciona corretamente.
     */
    @Test
    fun testRoundToTwoDecimalPlaces() {
        // Dado
        val value = 25.4586

        // Quando
        val roundedValue = checkoutService.roundToTwoDecimalPlaces(value)

        // Então
        assertEquals(25.46, roundedValue)
    }

    // Você pode escrever métodos de teste adicionais para outros cenários, se necessário
}
