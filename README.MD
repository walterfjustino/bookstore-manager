
<h2>Bookstore API Manager</h2>

O objetivo do projeto Bookstore API Manager é disponibilizar uma API para cadastro dos livros de uma livraria através de uma API REST.

Durante o projeto, são abordados os seguintes tópicos:

* Setup inicial de projeto com o Spring Boot Initializr.
* Criação de modelo de dados para o mapeamento de entidades em bancos de dados.
* Desenvolvimento de operações de gerenciamento de usuários, livros, autores e editora.
* Desenvolvimento de autenticaçao e autorização de usuários através do Spring Security, e com suporte a JWT.
* Relação de cada uma das operações acima com o padrão arquitetural REST, e a explicação de cada um dos conceitos REST envolvidos durante o desenvolvimento do projeto.
* Desenvolvimento de testes unitários para validação das funcionalidades.
* Apresentação do TDD (Test Driven Development), e como desenvolver funcionalidade na prática com o TDD.
* Desenvolvimento de testes de integração com o Postman.

Para executar o projeto no terminal, digite o seguinte comando:

```shell script
mvn spring-boot:run 
```

Após executar o comando acima, basta apenas abrir o seguinte endereço e visualizar a execução do projeto:

```
http://localhost:8080/api/v1/books
```

Para abrir a documentação (disponibilizada através do Swagger 2) de todas as operações implementadas com o padrão arquitetural REST, acesse o seguinte link abaixo:

```
http://localhost:8080/swagger-ui.html
```

Após executar o comando acima, basta apenas abrir o seguinte endereço e visualizar a execução do projeto:

As seguintes ferramentas abaixo são utilizadas como parte do desenvolvimento do projeto prático do curso:
q
* Java 17 ou versões superiores.
* Spring-boot versão 3.0.1
* Maven 3.6.3 ou versões superiores.
* SDKMan! para o gerenciamento de múltiplcas versões de Java, Maven e Spring Boot.
* Banco de dados H2 como SGBD do nosso projeto.
* Intellj IDEA Community Edition ou sua IDE favorita.
* Controle de versão GIT instalado na sua máquina.
* Springdoc OPENAPI 3 para a documentação de todos os endpoints desenvolvidos dentro do projeto.
* Conta no GitHub para o armazenamento do seu projeto na nuvem.
* Postman para execução de testes de integração para a validação de ponta a ponta da API.

