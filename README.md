# Movie Challenge

## Arquitetura
![Arquitetura](/src/main/resources/docs/movie-challenge.png)

### Stack
- [Java 11](https://www.java.com/pt_BR/)
- [Spring Boot 2.2.4](http://spring.io/projects/spring-boot)
- [MongoDB](https://www.mongodb.com/)
- [Spring Cloud Vault](https://cloud.spring.io/spring-cloud-vault/reference/html/)
- [Lombok](https://projectlombok.org/)
- [Orika](https://orika-mapper.github.io/orika-docs/)
- [SpringFox](http://springfox.github.io/springfox/)
- [Maven](https://maven.apache.org/)
- [Docker](https://docs.docker.com/)
- [JUnit5](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito](https://site.mockito.org/)
- [Fixture Factory](https://github.com/six2six/fixture-factory)
- [Jacoco](https://www.eclemma.org/jacoco/trunk/doc/maven.html)

### Estrutura do Projeto
- movie-challenge
  - config
  - controller
    - mapppers
    - request
    - response
  - exception
  - model
  - repository
    - impl
  - service
    - impl

## Build do Projeto
Execute a linha de comando maven:
```sh
mvn clean package
```

## Coverage
A cobertura de teste foi de 100% de Linhas, Métodos e Branches.  
Algumas classes não foram testadas como POJO, exception, etc.  
No ```pom.xml``` existe a configuração de todas as classes que não foram testada e excluída do coverage.  

Execute a linha de comando maven:
```sh
mvn clean verify
```

## Teste Unitário
Execute a linha de comando maven:
```sh
mvn test
```

## Swagger
- http://localhost:8080/swagger-ui.html

## Logger
Para alterar o level do log na aplicação ```OFF```, ```ERROR```, ```WARN```, ```INFO```, ```DEBUG``` e ```TRACE``` basta executar a requisição abaixo: 
	
```POST``` - http://localhost:8080/actuator/loggers/br.com.abbarros.moviechallenge
```java
{
	"configuredLevel": "DEBUG",
	"effectiveLevel": "DEBUG"
}
```
## CorrelationId
Para rastrear melhor a execução da aplicação, incluí um Header chamado ```correlationId```. Quando a aplicação recebe uma requisição, existe um filter ```MDCConfig``` que verifica se o correlationId foi enviado, e realiza um put desse cara no MDC, caso contrário, a aplicação gera um UUID randômico. Utilizei também um appender do logback para formatar as mensagens de log com a informação do correlationId - o logback pode ser usado também com o appender do Gelf. 

![CorrelationId](/src/main/resources/docs/correlationId.png)

## Spring Cloud Vault
O Spring Cloud Vault acessa diferentes recursos e configurações, por padrão nos contextos:
```sh
/secret/{application}/{profile}
/secret/{application}
/secret/{defaultContext}/{profile}
/secret/{defaultContext}
```
porém, na nossa aplicação só nos importa o contexto ```/secret/{application}``` portanto configurei a inicialização, onde applicationName é o movie-challenge:

```java
public class VaultConfig implements VaultConfigurer {

    @Value("${spring.cloud.vault.generic.application-name}")
    private String applicationName;

    @Override
    public void addSecretBackends(SecretBackendConfigurer configurer) {
        configurer.add("secret/" + applicationName);
    }

}
```
resources/META-INF
```sh
org.springframework.cloud.bootstrap.BootstrapConfiguration=br.com.abbarros.moviechallenge.config.vault.VaultConfig
```

## Rodar a Aplicação
Execute a linha de comando maven e docker compose:
```sh
mvn clean package
```
```sh
docker-compose build
```
```sh
docker-compose up -d
```
ou
```sh
make run
```

