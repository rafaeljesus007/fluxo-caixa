# Fluxo de Caixa - Microservices

Este projeto consiste em uma aplicação de fluxo de caixa distribuída em microserviços, seguindo princípios de Clean Architecture. Cada microserviço é responsável por uma função específica (gerenciamento de contas, lançamentos e categorias) e possui seu próprio banco de dados para garantir autonomia e segurança de dados.

---

## Índice
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Arquitetura do Projeto](#arquitetura-do-projeto)
- [Estrutura de Pastas](#estrutura-de-pastas)
- [Configuração dos Bancos de Dados](#configuração-dos-bancos-de-dados)
- [Execução e Configuração do Projeto](#execução-e-configuração-do-projeto)
- [Testes](#testes)
- [Swagger](#swagger)
- [Licença](#licença)

---

## Tecnologias Utilizadas

- **Linguagem:** Java 17
- **Framework:** Spring Boot
- **Injeção de Dependência:** Spring IoC
- **Banco de Dados:** MySQL, com uma instância dedicada para cada microserviço
- **Gerenciamento de Dependências:** Maven
- **Testes Unitários:** JUnit 5 e Mockito
- **Documentação da API:** Swagger
- **Containerização:** Docker e Docker Compose
- **Padrão Arquitetural:** Microservices com Clean Architecture (sem camada de adapters)

## Arquitetura do Projeto

O projeto adota uma arquitetura baseada em **Clean Architecture**, na qual os componentes de lógica de negócio estão desacoplados dos detalhes de implementação, como bancos de dados e interfaces de usuário. Cada microserviço possui camadas organizadas de acordo com as responsabilidades:

1. **Domain**: contém as entidades e contratos das interfaces de repositórios e serviços.
2. **Application**: camada de lógica de negócio, onde ficam os casos de uso, chamados de `services`.
3. **Infrastructure**: implementações de repositórios, configuração e integração com frameworks.

Cada microserviço possui seu próprio banco de dados, evitando dependências diretas entre eles, e são expostos via endpoints REST para permitir comunicação interna ou externa.

## Estrutura de Pastas

Abaixo está a estrutura geral dos diretórios do projeto:

```
fluxo-caixa/
├── backend/
│   ├── services/
│   │   ├── service-category/
│   │   │   ├── src/
│   │   │   │   ├── main/
│   │   │   │   │   ├── java/org/act/category/
│   │   │   │   │   │   ├── domain/           # Entidades e interfaces de repositório
│   │   │   │   │   │   ├── application/      # Casos de uso (serviços)
│   │   │   │   │   │   ├── infrastructure/   # Implementações dos repositórios e Controllers REST
│   │   │   │   ├── resources/
│   │   │   │   │   ├── application.yml
│   │   ├── service-account/
│   │   ├── service-launch/
├── docker-compose.yml
```
´´´

# Bancos de Dados

Cada microserviço possui um banco de dados MySQL dedicado, configurado de acordo com os princípios de segurança e independência de dados. Isso permite que cada serviço seja escalável e possa ser mantido de forma independente. Abaixo estão as configurações detalhadas para cada banco de dados:

Banco de Dados - Category Service

Tabela: categories

    id (PK) - Identificador único da categoria
    name - Nome da categoria (único)
    description - Descrição da categoria

Banco de Dados - Account Service

Tabela: accounts

    id (PK) - Identificador único da conta
    name - Nome da conta
    balance - Saldo da conta (BigDecimal)
    created_at - Data de criação

Banco de Dados - Launch Service

Tabela: launches

    id (PK) - Identificador único do lançamento
    account_id (FK) - Referência ao id da conta no banco de contas
    category_id (FK) - Referência ao id da categoria no banco de categorias
    amount - Valor do lançamento (BigDecimal)
    date - Data do lançamento
    description - Descrição do lançamento

# Configuração dos Bancos de Dados, e serviços no Docker Compose

No arquivo docker-compose.yml, cada serviço possui um container MySQL independente configurado com seu respectivo banco de dados, usuário e senha. Exemplo de configuração no Docker Compose:

```
version: '3.8'

services:
  mysql-category:
    image: mysql:8.0.33
    container_name: mysql_category_service
    environment:
      MYSQL_ROOT_PASSWORD: rootpassword
      MYSQL_DATABASE: category_db
      MYSQL_USER: category_user
      MYSQL_PASSWORD: category_password
    ports:
      - "3306:3306"
    volumes:
      - category_db_data:/var/lib/mysql
    networks:
      - category_network

  service-category:
    build:
      context: ./services/service-category
      dockerfile: Dockerfile
    container_name: category_service
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql-category:3306/category_db
      SPRING_DATASOURCE_USERNAME: category_user
      SPRING_DATASOURCE_PASSWORD: category_password
      SPRING_JPA_HIBERNATE_DDL_AUTO: update
    ports:
      - "8080:8080"
    depends_on:
      - mysql-category
    networks:
      - category_network

  mysql-account:
    image: mysql:8.0.33
    container_name: mysql_account_service
    environment:
      MYSQL_ROOT_PASSWORD: rootpassword
      MYSQL_DATABASE: account_db
      MYSQL_USER: account_user
      MYSQL_PASSWORD: account_password
    ports:
      - "3307:3306"
    volumes:
      - account_db_data:/var/lib/mysql
    networks:
      - account_network

  service-account:
    build:
      context: ./services/service-account
      dockerfile: Dockerfile
    container_name: account_service
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql-account:3306/account_db
      SPRING_DATASOURCE_USERNAME: account_user
      SPRING_DATASOURCE_PASSWORD: account_password
      SPRING_JPA_HIBERNATE_DDL_AUTO: update
    ports:
      - "8081:8080"
    depends_on:
      - mysql-account
    networks:
      - account_network
      - launch_network  # Rede compartilhada com o serviço de lançamentos para comunicação interna

  mysql-launch:
    image: mysql:8.0.33
    container_name: mysql_launch_service
    environment:
      MYSQL_ROOT_PASSWORD: rootpassword
      MYSQL_DATABASE: launch_db
      MYSQL_USER: launch_user
      MYSQL_PASSWORD: launch_password
    ports:
      - "3308:3306"
    volumes:
      - launch_db_data:/var/lib/mysql
    networks:
      - launch_network

  service-launch:
    build:
      context: ./services/service-launch
      dockerfile: Dockerfile
    container_name: launch_service
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql-launch:3306/launch_db
      SPRING_DATASOURCE_USERNAME: launch_user
      SPRING_DATASOURCE_PASSWORD: launch_password
      SPRING_JPA_HIBERNATE_DDL_AUTO: update
    ports:
      - "8082:8080"
    depends_on:
      - mysql-launch
    networks:
      - launch_network

volumes:
  category_db_data:
  account_db_data:
  launch_db_data:

networks:
  category_network:
    driver: bridge
  account_network:
    driver: bridge
  launch_network:
    driver: bridge

```

# Geração Automática das Tabelas

As tabelas de cada banco de dados são geradas automaticamente através do Hibernate ao iniciar cada microserviço. Nos arquivos application.yml de cada serviço, a propriedade spring.jpa.hibernate.ddl-auto está definida como update, garantindo que as tabelas e suas alterações sejam aplicadas ao banco de dados com base nas entidades Java.
Execução e Configuração do Projeto
Pré-requisitos

Docker e Docker Compose instalados.
Java 17 e Maven instalados para desenvolvimento local.

# Instruções para executar localmente

    Clone o repositório:

    bash
    git clone https://github.com/seu-usuario/fluxo-caixa.git
    

Compile o todos os serviços usando Maven:

    bash
    cd fluxo-caixa/backend/services/service-launch
    mvn clean install

Suba os containers com Docker Compose:

    bash

    docker-compose up --build

    Acesso aos serviços:
        Category Service: http://localhost:8080
        Account Service: http://localhost:8082
        Launch Service: http://localhost:8081

Cada serviço possui um container de banco de dados independente e configurações de rede dedicadas.
# Testes

O projeto possui testes unitários utilizando JUnit 5 e Mockito para garantir a funcionalidade das classes de serviço (camada de aplicação). Para executar os testes, utilize o comando:

    bash

    mvn test

Os testes cobrem métodos essenciais de criação, atualização, deleção e listagem de cada microserviço, além de cálculos específicos, como o saldo consolidado diário para o Account Service.
# Swagger

Cada microserviço possui uma documentação Swagger acessível nos endpoints:

    Category Service: http://localhost:8080/swagger-ui.html
    Account Service: http://localhost:8082/swagger-ui.html
    Launch Service: http://localhost:8081/swagger-ui.html

Esses endpoints permitem explorar e testar as funcionalidades expostas de cada microserviço de forma interativa.
Licença
