## Sistema Bancário

### Descrição
<p> Este projeto é um sistema bancário desenvolvido em Java utilizando Spring Boot e PostgreSQL. 
O sistema permite a criação e gerenciamento de contas bancárias e transações financeiras para 
clientes, com funcionalidades baseadas na renda salarial do cliente.</p>

### Tecnologias Utilizadas
- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Insomnia (para testes de API)

### Configuração do Projeto
Requisitos
- Java 11+
- Maven 3.6+
- PostgreSQL 12+

### Passos para Configuração

1.Clone o repositório

```
git clone https://github.com/DiegoMouraOficial/sistema-bancario.git

cd sistema-bancario
cd bancodigital
```
2.Configure o banco de dados PostgreSQL e atualize o arquivo application.properties:

```
# application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/bancodigital
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

```
3.Compile e execute a aplicação:

```bash
mvn clean install
```
```bash
mvn spring-boot:run
```
### Rotas da API
#### Clientes
->>> Cadastro de Cliente <<<-
- URL: /clientes/cadastrar
- Método: POST
- Descrição: Cadastra um novo cliente no sistema.
- Corpo da Requisição (JSON)
```
{
  "nome": "Diego Moura",
  "cpf": "3584141411",
  "email": "diego@example.com",
  "senha": "senha1234",
  "endereco": "Rua Exemplo, 1243",
  "rendaSalarial": 4000.0
}
```
#### Resposta de Sucesso:
- Status: 201 Created
```
{
	"id": "9d90cb72-1d01-46e0-bf20-83eb168f4e5d",
	"nome": "Diego Moura",
	"cpf": "3584141411",
	"email": "diego@example.com",
	"senha": "senha1234",
	"endereco": "Rua Exemplo, 1243",
	"contas": [],
	"rendaSalarial": 4000.0
}
```

    

