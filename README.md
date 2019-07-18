## **Client Services REST API**

**Nome:** Yuri Stefano Scripnic

**Data:** 17/07/2019


####Como usar os serviços
Os serviços podem ser acessados por um browser ou outra ferramenta 
que faça requisições HTTP
Assim que o servidor seja executado, é possivel receber uma lista de
 clientes pré-cadastrados digitando-se em um browser o seguinte:
http://localhost:8080/client

Para informações de um cliente especifico, adicionar o id à URL
http://localhost:8080/client/1

Para receber as informações de clima recolhidas pela aplicação:
http://localhost:8080/client

Os demais serviços precisam ser chamado usando ferramentas como
 curl ou Postman
 
##Quais ferramentas foram usadas (e porque essas foram as escolhidas)
Foram utilizadas as seguintes ferramentas:
* Spring Boot
* H2 Database
* Lombok

Estas ferramentas foram escolhidas devido a grande facilidade de uso,
 e o poder de gerar aplicações rapidamente.
* Spring Boot é altamente escalavel e pode ser utilizado em produção.
* O banco de Dados H2 foi utilizado com um database embbed, 
pela rapidez de implementação. Este pode ser trocado por um banco
 mais robusto, trocando-se as configurações de jdbc
* A library Lombok foi incluida para facilitar a costrução de
 objeto Java, criando um código mais conciso

##Qualquer infraestrutura adicional necessária para executar, testar, empacotar e entregar seu projeto
O projeto usa o empacotador marven , a plataforma Spring Boot e
 o Banco de Dados embbed H2, nenhuma infraestrutura adicional é
  necessária para testes

## Como executar, testar, empacotar e entregar o seu projeto
O projeto pode ser executado com o seguinte comando chamado do 
diretório raiz do projeto: ./mvnw spring-boot:run

Ele pode ser testado com:
./mvnw test

E empacotado com:
./mvnw package

##Instruções para como montar o ambiente de produção onde seus serviços devem ser executados (preferencialmente citando ferramentas e processos)
O Spring Boot pode ser utilizado em produção, mas não é escalavel
 da maneira atual. Uma melhor performace pode ser esperada utilizando-se
  um Application Server (fazer o deploy do jar) 
ou criando-se uma estrutura de container e servidores virtuais
 como Dockers em Kubernetes
O Banco de dados deve ser trocado por outro, como o MYSQL
Se utilizado uma ferramenta de CI/CD como Jenkins,
 o empacotamento e o deploy em produção pode ser automatizados




