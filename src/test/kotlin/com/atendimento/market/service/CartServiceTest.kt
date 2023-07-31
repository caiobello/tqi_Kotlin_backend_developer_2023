import com.atendimento.market.dto.CartItemDTO
import com.atendimento.market.model.CartItem
import com.atendimento.market.model.Product
import com.atendimento.market.repository.CartRepository
import com.atendimento.market.service.CartService
import com.atendimento.market.service.ProductService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.*
/**
 * Este arquivo contém os testes unitários para a classe CartService.
 * A classe CartService é responsável por lidar com a lógica de negócio relacionada ao carrinho de compras.
 * Os testes são escritos usando o framework de testes JUnit Jupiter e a biblioteca Mockito.
 * A anotação @InjectMocks é usada para injetar automaticamente as dependências na classe sendo testada.
 * As anotações @Mock são usadas para criar objetos simulados (mocks) das dependências da classe sendo testada.
 * Cada teste é documentado com uma breve descrição do cenário sendo testado e os resultados esperados.
 */
class CartServiceTest {

    @InjectMocks
    private lateinit var cartService: CartService

    @Mock
    private lateinit var cartItemRepository: CartRepository

    @Mock
    private lateinit var productService: ProductService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    // Teste para adicionar um item ao carrinho quando ele já existe no carrinho
    @Test
    fun testAddToCart_ExistingCartItem() {
        // Dado
        val productId = 1L
        val existingQuantity = 7
        val quantityToAdd = 2
        val salePrice = 10.0
        val cartItemDTO = CartItemDTO(productId = productId, quantity = quantityToAdd, salePrice = salePrice)

        // Simulando o método getProductById do productService para retornar um produto
        val product = Product(id = productId, name = "Produto 1", unit = "unidade", price = 10.0)
        Mockito.`when`(productService.getProductById(productId)).thenReturn(product)

        // Simulando o método findByProductId do cartItemRepository para retornar um item de carrinho existente
        val existingCartItem = CartItem(id = productId, productId = productId, quantity = existingQuantity, salePrice = 10.0)
        Mockito.`when`(cartItemRepository.findByProductId(productId)).thenReturn(existingCartItem)

        // Simulando o método save do cartItemRepository para retornar o item de carrinho atualizado
        Mockito.`when`(cartItemRepository.save(existingCartItem)).thenReturn(existingCartItem)

        // Quando
        val resultCartItem = cartService.addToCart(cartItemDTO)

        // Então
        assertEquals(productId, resultCartItem.productId)
        assertEquals(existingQuantity + quantityToAdd, resultCartItem.quantity)
        assertEquals(salePrice, resultCartItem.salePrice)
    }

    // Teste para adicionar um item ao carrinho quando ele não existe no carrinho
    @Test
    fun testAddToCart_NewCartItem() {
        // Dado
        val productId = 2L
        val quantity = 3
        val salePrice = 15.0
        val cartItemDTO = CartItemDTO(productId = productId, quantity = quantity, salePrice = salePrice)

        // Simulando o método getProductById do productService para retornar um produto
        val product = Product(id = productId, name = "Produto 2", unit = "unidade", price = 15.0)
        Mockito.`when`(productService.getProductById(productId)).thenReturn(product)

        // Simulando o método findByProductId do cartItemRepository para retornar null (item de carrinho não existe)
        Mockito.`when`(cartItemRepository.findByProductId(productId)).thenReturn(null)

        // Simulando o método save do cartItemRepository para retornar o item de carrinho com um ID gerado
        val savedCartItem = CartItem(id = 3L, productId = productId, quantity = quantity, salePrice = salePrice)
        Mockito.`when`(cartItemRepository.save(Mockito.any(CartItem::class.java))).thenReturn(savedCartItem)

        // Quando
        val resultCartItem = cartService.addToCart(cartItemDTO)

        // Então
        assertEquals(productId, resultCartItem.productId)
        assertEquals(quantity, resultCartItem.quantity)
        assertEquals(salePrice, resultCartItem.salePrice)
    }

    // Teste para remover um item do carrinho quando ele está presente
    @Test
    fun testRemoveFromCart_ItemPresent() {
        // Dado
        val productId = 1L

        // Simulando o método findById do cartItemRepository para retornar um Optional com um item de carrinho
        val cartItem = CartItem(id = productId, productId = productId, quantity = 10, salePrice = 10.0)
        Mockito.`when`(cartItemRepository.findById(productId)).thenReturn(Optional.of(cartItem))

        // Quando
        val result = cartService.removeFromCart(productId)

        // Então
        assertTrue(result)
        // Você também pode verificar se o método delete do cartItemRepository é chamado, mas é opcional para este teste.
    }

    // Teste para remover um item do carrinho quando ele não está presente
    @Test
    fun testRemoveFromCart_ItemNotPresent() {
        // Dado
        val productId = 1L

        // Simulando o método findById do cartItemRepository para retornar um Optional vazio
        Mockito.`when`(cartItemRepository.findById(productId)).thenReturn(Optional.empty())

        // Quando
        val result = cartService.removeFromCart(productId)

        // Então
        assertFalse(result)
    }

    // Teste para obter um item do carrinho por ID quando ele existe
    @Test
    fun testGetCartItemById_ItemExists() {
        // Dado
        val productId = 1L
        val cartItem = CartItem(id = productId, productId = productId, quantity = 5, salePrice = 10.0)

        // Simulando o método findById do cartItemRepository para retornar o item de carrinho
        Mockito.`when`(cartItemRepository.findById(productId)).thenReturn(Optional.of(cartItem))

        // Quando
        val resultCartItem = cartService.getCartItemById(productId)

        // Então
        assertEquals(productId, resultCartItem.productId)
        assertEquals(5, resultCartItem.quantity)
        assertEquals(10.0, resultCartItem.salePrice)
    }

    // Teste para obter um item do carrinho por ID quando ele não existe
    @Test
    fun testGetCartItemById_ItemNotExists() {
        // Dado
        val productId = 1L

        // Simulando o método findById do cartItemRepository para retornar um Optional vazio
        Mockito.`when`(cartItemRepository.findById(productId)).thenReturn(Optional.empty())

        // Quando e Então
        assertThrows<NoSuchElementException> {
            cartService.getCartItemById(productId)
        }
    }

    // Teste para obter todos os itens do carrinho
    @Test
    fun testGetAllCartItems() {
        // Dado
        val cartItems = listOf(
            CartItem(id = 1L, productId = 1L, quantity = 5, salePrice = 10.0),
            CartItem(id = 2L, productId = 2L, quantity = 3, salePrice = 5.0)
        )

        // Simulando o método findAll do cartItemRepository para retornar os itens de carrinho
        Mockito.`when`(cartItemRepository.findAll()).thenReturn(cartItems)

        // Quando
        val resultCartItems = cartService.getAllCartItems()

        // Então
        assertEquals(cartItems.size, resultCartItems.size)
        assertEquals(cartItems[0].id, resultCartItems[0].id)
        assertEquals(cartItems[1].id, resultCartItems[1].id)
        // Adicione mais verificações para checar o conteúdo dos itens de carrinho, se necessário.
    }

    // Escreva mais métodos de teste para cobrir outros cenários da classe CartService

}
