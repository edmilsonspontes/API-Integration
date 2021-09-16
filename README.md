# Integração de APIs

## Estes projetos foram construídos com Java 8 e Spring Boot 2.4.5

## Objetivos específicos

   * Criação de API cliente para realizar a busca de todas a universidades do Brasil na API pública `University Domains and Names API` (`https://github.com/Hipo/university-domains-list-api`);
   * Criação de aplicação REST em Java para realizar as operações de CRUD sobre os dados de universidades;
   * Criação de aplicação client em Angular framework para consumir os dados da aplicação REST e realizar as operações.


## university-domains-api-client
Realiza a busca das universidades na API `University Domains and Names API`

## university-domains-service
Consome os dados `university-domains-api-client`, alimenta o banco de dados local e permite as operações de CRUD. 
   * Expoe os recursos através da url `http://localhost:8090/university`
   * Documentação em swagger `http://localhost:8090/swagger-ui.html`

## university-domains-client
Interface Gráfica do Usuário para realização das operações em `university-domains-service`
