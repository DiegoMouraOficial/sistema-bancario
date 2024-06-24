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
git clone https://github.com/seu-usuario/banking-system.git

cd banking-system
```
2.Configure o banco de dados PostgreSQL e atualize o arquivo application.properties:

```
# application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/banking_system
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
#### Cliente
->>> Cadastro de Cliente <<<-
- URL: /clientes/cadastrar
- Método: POST
- Descrição: Cadastra um novo cliente no sistema.
- Corpo da Requisição (JSON)
```
{
  "nome": "João Silva",
  "cpf": "12345678900",
  "email": "joao.silva@example.com",
  "endereco": "Rua Exemplo, 123",
  "rendaSalarial": 5000,
  "senha": "senha123"
}
```
#### Resposta de Sucesso:
- Status: 201 Created
```
  {
  "id": "c0bfbe97-4285-46fc-9a67-b88b0c9d859f",
  "nome": "João Silva",
  "cpf": "12345678900",
  "email": "joao.silva@example.com",
  "endereco": "Rua Exemplo, 123",
  "rendaSalarial": 5000,
  "senha": "senha123"
  }
```

    

