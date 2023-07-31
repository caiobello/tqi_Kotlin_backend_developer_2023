# Testes para a camada Service referente as classes CategoryService, ProductService, CartService e CheckoutService;

Estes são os testes unitários para as classes de serviço referentes ao projeto de autoatendimento de um mercado. As classes de serviço são responsáveis por fornecer a lógica de negócios para a manipulação de diferentes entidades, como categorias de produtos, produtos, carrinho de compras e o processo de checkout. Os testes foram escritos em Kotlin e utilizam as bibliotecas JUnit 5 e Mockito para simular comportamentos de objetos e verificar os resultados esperados.

As classes de teste abrangem cenários diversos e críticos para garantir que as funcionalidades essenciais das classes de serviço sejam executadas corretamente. Cada classe de teste se concentra em testar os métodos relevantes de sua respectiva classe de serviço e garante que os resultados estejam em conformidade com as expectativas.

### Tecnologias utilizadas:
- Linguagem de programação: Kotlin
- Framework de teste: JUnit 5
- Biblioteca de mock: Mockito

### Estrutura do projeto
- com.atendimento.market.service: Contém a implementação dos serviços de manipulação do carrinho.
- com.atendimento.market.dto: Contém os DTOs utilizados nos testes.
- com.atendimento.market.model: Contém as entidades de modelo, como "CartItem" e "Product".
- com.atendimento.market.repository: Contém as interfaces de repositório.
- com.atendimento.market.controller: Contém o controlador da aplicação.

### Descrição dos Testes Category Service

1. testCreateCategory: Testa a criação de uma categoria. Verifica se a categoria criada possui o nome correto.
2. testGetCategoryById_CategoryExists: Testa a obtenção de uma categoria por ID quando a categoria existe. Verifica se a categoria retornada possui o nome correto.
3. testGetCategoryById_CategoryNotExists: Testa a obtenção de uma categoria por ID quando a categoria não existe. Garante que o método lança uma exceção "NoSuchElementException".
4. testUpdateCategory_CategoryExists: Testa a atualização de uma categoria quando ela existe. Verifica se o nome da categoria é atualizado corretamente.
5. testUpdateCategory_CategoryNotExists: Testa a atualização de uma categoria quando ela não existe. Garante que o método lança uma exceção "NoSuchElementException".
6. testDeleteCategory_CategoryExists: Testa a exclusão de uma categoria quando ela existe. Verifica se o método "deleteById" do repositório é chamado uma vez com o ID correto.
7. testDeleteCategory_CategoryNotExists: Testa a exclusão de uma categoria quando ela não existe. Verifica se o método "deleteById" do repositório não é chamado.
8. testGetAllCategories: Testa a obtenção de todas as categorias. Verifica se a lista retornada possui a quantidade correta de categorias e se as categorias contêm os valores esperados.


### Descrição dos Testes Product Service

1. testCreateProduct: Testa a criação de um produto. Verifica se o produto criado possui as informações corretas, incluindo o nome, unidade, preço e categoria associada.
2. testUpdateProduct_ProductExists: Testa a atualização de um produto quando ele existe. Verifica se as informações do produto são atualizadas corretamente.
3. testUpdateProduct_ProductNotExists: Testa a atualização de um produto quando ele não existe. Garante que o método lança uma exceção "NoSuchElementException".
4. testDeleteProduct: Testa a exclusão de um produto. Verifica se a exclusão é realizada corretamente.
5. testGetProductById_ProductExists: Testa a obtenção de um produto por ID quando o produto existe. Verifica se o produto retornado possui as informações corretas, incluindo o nome, unidade, preço e categoria associada.
6. testGetProductById_ProductNotExists: Testa a obtenção de um produto por ID quando o produto não existe. Garante que o método lança uma exceção "NoSuchElementException".
7. testGetAllProducts: Testa a obtenção de todos os produtos. Verifica se a lista retornada possui a quantidade correta de produtos e se os produtos contêm as informações esperadas.
8. testGetCategoryById_CategoryExists: Testa a obtenção de uma categoria por ID quando a categoria existe. Verifica se a categoria retornada possui o nome correto.
9. testGetCategoryById_CategoryNotExists: Testa a obtenção de uma categoria por ID quando a categoria não existe. Garante que o método lança uma exceção "NoSuchElementException".

### Descrição dos Testes Cart Service

1. testAddToCart_ExistingCartItem: Testa a adição de um item ao carrinho quando ele já existe no carrinho. Garante que a quantidade e o preço de venda sejam atualizados corretamente.
2. testAddToCart_NewCartItem: Testa a adição de um item ao carrinho quando ele não existe no carrinho. Certifica-se de que um novo item é criado corretamente no carrinho.
3. testRemoveFromCart_ItemPresent: Testa a remoção de um item do carrinho quando ele está presente. Verifica se o método retorna true e, opcionalmente, se o método "delete" do repositório foi chamado.
4. testRemoveFromCart_ItemNotPresent: Testa a remoção de um item do carrinho quando ele não está presente. Garante que o método retorne false.
5. testGetCartItemById_ItemExists: Testa a obtenção de um item do carrinho por ID quando ele existe. Verifica se o item retornado possui os valores esperados.
6. testGetCartItemById_ItemNotExists: Testa a obtenção de um item do carrinho por ID quando ele não existe. Verifica se o método lança uma exceção "NoSuchElementException".
7. testGetAllCartItems: Testa a obtenção de todos os itens do carrinho. Verifica se a lista retornada possui a quantidade correta de itens e se os itens contêm os valores esperados.


### Descrição dos Testes Checkout Service

1. testFinalizeCheckout: Testa a finalização do checkout, garantindo que o total de vendas seja arredondado corretamente para duas casas decimais e que o método retorne o checkout finalizado com sucesso.
2. testUpdatePaymentMethod_CheckoutExists: Testa a atualização do método de pagamento do checkout quando o checkout existe. Verifica se o método retorna o checkout atualizado corretamente com o novo método de pagamento.
3. testUpdatePaymentMethod_CheckoutNotExists: Testa a atualização do método de pagamento do checkout quando o checkout não existe. Verifica se o método retorna null quando o checkout não é encontrado.
4. testGetCheckoutById_CheckoutExists: Testa a obtenção do checkout por ID quando o checkout existe. Verifica se o checkout retornado possui os valores esperados.
5. testGetCheckoutById_CheckoutNotExists: Testa a obtenção do checkout por ID quando o checkout não existe. Verifica se o método retorna null quando o checkout não é encontrado.
6. testGetAllCheckouts_NoCheckouts: Testa a obtenção de todos os checkouts quando não há nenhum checkout na lista. Verifica se a lista retornada está vazia.
7. testGetAllCheckouts_CheckoutsExist: Testa a obtenção de todos os checkouts quando existem checkouts na lista. Verifica se a lista retornada possui a quantidade correta de checkouts e se os checkouts contêm os valores esperados.
8. testRoundToTwoDecimalPlaces: Testa o arredondamento de um valor para duas casas decimais. Verifica se o valor arredondado é o esperado.


### Considerações sobre os testes
Os testes escritos para as classes de serviço garantem que todas as principais operações do projeto estejam funcionando corretamente e que os comportamentos esperados sejam atendidos. Os testes contribuem para a robustez do projeto, identificando e prevenindo falhas e problemas potenciais.
