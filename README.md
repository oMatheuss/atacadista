# Atacadista

Aplicação em java para
gerenciar produtos, pedidos e clientes em um sistema atacadista.

## Como rodar

Para executar a aplicação execute o comando na pasta do projeto:

```
./mvnw spring-boot:run
```

A aplicação deve começar a rodar na porta 8080: http://localhost:8080.

Para executar os teste da aplicação execute:

```console
./mvnw test
```

## Documentação

Foi adicionado o **[swagger-ui](https://swagger.io/tools/swagger-ui/)** usando o **[springdoc](https://springdoc.org/)**, após iniciar a aplicação, é possivel acessar ele no link: http://localhost:8080/swagger-ui/index.html

## Ferramentas

- Spring Initializr: https://start.spring.io/
- H2 Database: https://www.h2database.com/html/main.html
- Mockito: https://site.mockito.org/
- JUnit: https://junit.org/junit5/
- Hibernate Validator: https://hibernate.org/validator/
- SpringDoc: https://springdoc.org/