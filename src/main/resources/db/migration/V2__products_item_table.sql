/*
  Criação da tabela "product".

  Descrição: Esta tabela armazena as informações dos produtos.
  Cada produto tem um identificador único (id), um nome (name), uma unidade (unit),
  um preço (price) e uma referência à categoria a qual pertence (category_id).
*/

CREATE TABLE product (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    unit VARCHAR(50) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    category_id INT NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category(id)
);
