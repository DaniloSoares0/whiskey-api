<h2>Desenvolvimento de testes unitários para validar uma API REST de gerenciamento de estoques de whiskey.</h2>

Durante o exercício, forma abordados os seguintes tópicos:
 
* Apresentação conceitual sobre testes: a pirâmide dos tipos de testes, e também a importância de cada tipo de teste durante o ciclo de desenvolvimento.
* Foco nos testes unitários: mostrar o porque é importante o desenvolvimento destes tipos de testes como parte do ciclo de desenvolvimento de software.
* Principais frameworks para testes unitários em Java: JUnit, Mockito e Hamcrest. 
* Desenvolvimento de testes unitários para validação de funcionalides básicas: criação, listagem, consulta por nome e exclusão de whiskey.
* TDD: apresentação e exemplo prático em 2 funcionaliades importantes: incremento e decremento do número de whiskey no estoque.

Para executar o projeto no terminal, digite o seguinte comando:

```shell script
mvn spring-boot:run 
```

Para executar a suíte de testes, basta executar o seguinte comando:

```shell script
mvn clean test
```

Após executar o comando acima, basta apenas abrir o seguinte endereço e visualizar a execução do projeto:

```
http://localhost:8080/api/v1/whiskey
```

São necessários os seguintes pré-requisitos para a execução do projeto desenvolvido:

* Java 14 ou versões superiores.
* Maven 3.6.3 ou versões superiores.
* Intellj IDEA Community Edition ou sua IDE favorita.
* Controle de versão GIT instalado na sua máquina.

Abaixo, seguem links bem bacanas, sobre tópicos mencionados durante a aula:


