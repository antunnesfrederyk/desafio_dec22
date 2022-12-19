# DESAFIO - Votação

    - Cadastrar uma nova pauta
    - Abrir uma sessão de votação em uma pauta(a sessão de votação deve ficar aberta por um tempo determinado na chamada de abertura ou 1 minuto por default)
    - Receber votos dos assosiados em pauta (Os votos são apenas Sim/Não. Cada associado é identificado por um id unico e pode votar apenas uma vez por pauta)
    - Contabilizar os votos e dar resulado da votação em pauta

## Regras de Negócio
Ao utilizar API, deve-se usar a ordem lógica de operações:
1. Cadastrar nova pauta
2. Iniciar sessão de votação em pauta
3. Registrar voto
4. Validar votos de sessão encerrada em tópico kafka

## Tecnologias

* Java
* SpringBoot
* Docker
* Postgres
* Kafka
* Liquibase
* Swagger

## Licença de software
Como este projeto é dedicado teste de conhecimento e não é um projeto com objetivo comercial, não há cobrança, direitos ou restrições em seu uso. (MIT License).

## Running Locally

### Subir Container Database e Kafka
Para subir os containers necessários para executar aplicação é necessário ter o docker instalado e executar o comando abaixo:

    docker-compose up    

Quatro conainers serão iniciados:
* Database Postegres
* Zookeeper
* Broker
* Control Center Kafka

### Executar Build de Projeto
    ./gradlew build

### Executar Liquibase (Criar Banco e Tableas)
    ./gradlew liquibaseUpdate

* Para validar a sincrolização do liquibase execute:

        ./gradlew liquibaseHistory

### Subir aplicacao
    ./gradlew bootRun

# Testes

### Postman Collection
Caminho do arquivo:

    /postman/requests.json

### Swagger
* Acessando Swagger


    http://localhost:8080/swagger-ui/#/
* Open API URL


    http://localhost:8080/v2/api-docs

### Control Center
Para acompanhar mensagens publicadas em tópico kafka utilize o control center no host abaixo:

    http://localhost:9021/clusters

1. Selecione o Cluster Disponível
2. Clique em __Topics__
3. Clique no tópico __${ENV}.votes__
4. Clique em __Messages__
5. Clique no botão __Play__ para iniciar consumer do tócpico

* O Serviço conta com schedule que fica validando a cada 30s (configurano no .yml) tempos de sessões e contalizando votos de sessões encerradas.

## Observação:
A api de consulta de cpf está offline, não deixando seguir fluxo caso habilitada, então foi criada uma flag para ativar/desativar a consulta de cpf na api.

* Para habilitar e desabilitar, é necessário utilizar de variável de ambiente ou mudar o valor default no application.yml:


    integrations:
      validate-document:
        url: "https://user-info.herokuapp.com"
        enabled: ${DOCUMENT_VALIDATION_ACTIVE:false}