/*
  Criação da tabela "cart_item".

  Descrição: Esta tabela armazena informações dos itens do carrinho de compras.
  Cada item de carrinho tem um identificador único (id), uma referência ao produto (product_id),
  uma quantidade (quantity) e um preço de venda (sale_price).
*/

CREATE TABLE cart_item (
    id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    sale_price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product(id)
);
