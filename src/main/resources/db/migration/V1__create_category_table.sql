/*
  Criação da tabela "category".

  Descrição: Esta tabela armazena as informações das categorias de produtos.
  Cada categoria tem um identificador único (id) e um nome (name).
*/

CREATE TABLE category (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);
