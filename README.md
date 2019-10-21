Teste Símios - Mercado Livre
============================

## Overview
API Rest para validação/cadastro de dnas solicitados.

## Tecnologia utilizada

Linguagem:
* Java 8 

Frameworks:
* Spring Boot - utilizado para injeção de dependências e inversão de controle, camada rest, acesso ao banco de dados e Log de aplicação.
* Lombok - declaração de Get/Set, construtores e builders para as classes Java.
* Swaggwer - documentação dos endpoints rest.
* Mockito - integrado com o Spring-boot-test para Mock e Verify de teste unitários.

Banco de dados:
* MySql (Amazon RDS) em ambiente produtivo.
* H2 em memória para ambiente local.

Build:
* Maven - foi utilizado na estrutura de projeto e empacotamento do artefato de deploy (jar)

Cobertura de código:
* Jacoco - integrado ao build do maven provê um relatório de percentual de cobertura de teste do projeto

Log:
* AwsLogAppender - blibliote para envio de logs da aplicação para o AWS Cloudwatch. 

## Camadas da aplicação
A aplicação segue uma divisão por pacotes conforme abaixo:
* ``com.mercadolivre.simios`` - pacote raíz da aplicação, classes:
[Application](https://github.com/gilmaslima/simios/blob/master/src/main/java/com/mercadolivre/simios/Application.java) - Responsável por iniciar a aplição e o contexto do Spring.

* ``com.mercadolivre.simios.controller`` - nesse pacote esão as classes Rest Controller:
[DnaController](https://github.com/gilmaslima/simios/blob/master/src/main/java/com/mercadolivre/simios/controller/DnaController.java)
- método isSimian 
  > disponibiliza o endpoint rest POST /simian
  > requisição de exemplo: 
  > { "dna": [ "ATGCGT", "CAGTTG", "ATATGT", "AACAAT", "CCCATA", "TCACTG"] }
  >
  > possíveis respostas:
  > Código HTTP 200 no caso de um DNA de simio
  > Código HTTP 403 no caso de um DNA humano
  > Código HTTP 500 quando houver um erro inesperado na aplicação

- método stats
  > disponibilizao o endpoint GET /stats
  > não há parâmetros de entrada
  > exemplo de resposta:
  > {"count_mutant_dna": 40, "count_human_dna": 100: "ratio": 0.4}
  > Código HTTP 200 ao retornar o json
  > Código HTTP 500 quando houver um erro inesperado na aplicação na requisição

* ``com.mercadolivre.simios.helper`` - nesse pacote está a classe [DnaHelper](https://github.com/gilmaslima/simios/blob/master/src/main/java/com/mercadolivre/simios/helper/DnaHelper.java) responsável pela regra de pesquisa de DNA.

* ``com.mercadolivre.simios.model`` - nesse pacote estão as classes que reperesentam os Objetos de requess, reponse e entidades de banco de dados:
[Dna](https://github.com/gilmaslima/simios/blob/master/src/main/java/com/mercadolivre/simios/model/Dna.java) - Mapeamento da tabela DNA
[DnaRequest](https://github.com/gilmaslima/simios/blob/master/src/main/java/com/mercadolivre/simios/model/DnaRequest.java) - representa o objeto/json usado na requisição do endpoint POST /simian.
[Stats](https://github.com/gilmaslima/simios/blob/master/src/main/java/com/mercadolivre/simios/model/Stats.java) - representa o objeto usado no retorno da consulta sumarizada do banco de dados.
[StatsResponse](https://github.com/gilmaslima/simios/blob/master/src/main/java/com/mercadolivre/simios/model/StatsResponse.java) - representa o objeto/json usado na resposta do endpoint GET /stats.

* ``com.mercadolivre.simios.repository`` - contém os repositórios de acesso ao banco de dados da aplicação:
[DnaRepository](https://github.com/gilmaslima/simios/blob/master/src/main/java/com/mercadolivre/simios/repository/DnaRepository.java) - Interface para manipulação da tabela DNA.
[StatsCustomRepository](https://github.com/gilmaslima/simios/blob/master/src/main/java/com/mercadolivre/simios/repository/StatsCustomRepository.java) - Disponibiliza uma consulta via native query do JPA para retornar os dados da rota de stats.

* ``com.mercadolivre.simios.service`` - Camada de serviço da aplicação.
[DnaService](https://github.com/gilmaslima/simios/blob/master/src/main/java/com/mercadolivre/simios/service/DnaService.java) - Faz a orquestração das chamadas de banco de dados, monta os objetos de retorno e contém a regra de negócio da aplicação.

* ``com.mercadolivre.simios.validation`` - Pacote com os validadores customizados do Json de request.
[DnaConstraint](https://github.com/gilmaslima/simios/blob/master/src/main/java/com/mercadolivre/simios/validation/DnaConstraint.java) - Interface annotation usada na validação do campo ``dna`` durante o request da rota /simian.
[DnaArrayValidator](https://github.com/gilmaslima/simios/blob/master/src/main/java/com/mercadolivre/simios/validation/DnaArrayValidator.java) - Implementação de ConstraintValidator que é acionada para executar a validação do campo dna.

## Configuração da aplicação
``src/main/resources``
[application-local.properties](https://github.com/gilmaslima/simios/blob/master/src/main/resources/application-local.properties) - Contém as configurações da aplicação para ser executada localmente:
    
    spring.jpa.hibernate.ddl-auto=create
    spring.datasource.url=jdbc:h2:mem:testdb
    spring.datasource.driverClassName=org.h2.Driver
    spring.datasource.username=sa
    spring.datasource.password=
    spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
    nitrogen.base = A,T,C,G
    logging.level.root=INFO
    logging.level.org.springframework.web=INFO
    logging.level.org.hibernate=INFO

[application.properties](https://github.com/gilmaslima/simios/blob/master/src/main/resources/application.properties) - Contém as configurações da aplicação para ser executada em ambiente produtivo:
    
    spring.jpa.hibernate.ddl-auto=validate
    spring.datasource.url=jdbc:mysql://database-1.c5f6ijx73fsq.us-east-1.rds.amazonaws.com:3306/simiosdb
    spring.datasource.username=root
    spring.datasource.password=12345678
    nitrogen.base = A,T,C,G
    logging.level.root=INFO
    logging.level.org.springframework.web=INFO
    logging.level.org.hibernate=INFO

[logback-spring.xml](https://github.com/gilmaslima/simios/blob/master/src/main/resources/logback-spring.xml) - Configuração de log da aplicação, utiliza o ``appender`` STDOUT (saída no console da máquina) quando executado no profile local. Caso contrário utiliza o ``appender`` cloud-watch enviando so logs para o AWS Cloudwatch.
