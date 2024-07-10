# Projeto quarkus

Este é um projeto de exemplo usando Quarkus Java Framework.

Se quiser saber mais sobre o Quarkus, visite o site: https://quarkus.io/.


## Executando o aplicativo em desenvolvimento (dev)

Você pode executar seu aplicativo no modo dev, que permite a codificação ao vivo (reativa) usando:
```
./mvn quarkus:dev
```     
## Empacotando e executando o aplicativo

A aplicação é empacotável usando `./mvnw package`.
Ele produz o arquivo executável `pr-quarkus-exemplo-1.0.0-runner.jar` no diretório` /target`.
Esteja ciente de que não é um _über-jar_, pois as dependências são copiadas no diretório `target/lib`.

O aplicativo agora pode ser executado usando `java -jar target/pr-quarkus-exemplo-1.0.0-runner.jar`

## Criando um executável nativo

Você pode criar um executável nativo usando: `./mvnw package -Pnative`.

Ou você pode usar o Docker para criar o executável nativo usando: `./mvnw package -Pnative -Dquarkus.native.container-build = true`.

Você pode então executar o seu binário: `. /target/pr-quarkus-exemplo-1.0.0-runner`

Se você quiser saber mais sobre a criação de executáveis ​​nativos, consulte https://quarkus.io/guides/building-native-image-guide.

## API Swagger

Você pode visualiar a API através da interface gráfica do swagger acessando:

http://localhost:8080/swagger-ui

Para fazer download do arquivo (swagger) acesse:

http://localhost:8080/openapi

Você pode alterar as configurações no arquivo `application.properties`