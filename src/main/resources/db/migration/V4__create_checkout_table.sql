/*
  Criação da tabela "checkout".

  Descrição: Esta tabela armazena informações sobre os checkouts (finalizações de compras).
  Cada checkout tem um identificador único (id), um total (total) representando o valor total da compra,
  e um método de pagamento (payment_method) que indica o método utilizado para efetuar o pagamento.
*/
CREATE TABLE checkout (
    id INT PRIMARY KEY AUTO_INCREMENT,
    total DECIMAL(10, 2) NOT NULL,
    payment_method VARCHAR(100) NOT NULL
);
