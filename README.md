# Calculadora Remota – Java UDP

## Descrição
Projeto desenvolvido para implementar o padrão de comunicação **Request-Reply** utilizando sockets UDP em Java. O sistema permite que um cliente envie operações matemáticas para um servidor, que processa a requisição e retorna o resultado.

## Funcionalidades
- Soma, subtração, multiplicação e divisão.
- Tratamento de divisão por zero e operações inválidas.
- Identificação de requisições por ID.
- Identificação de requisições duplicadas.
- Timeout e tentativas de reenvio em caso de ausência de resposta.

## Tecnologias
- Java 27
- IntelliJ IDEA
- Protocolo UDP (DatagramSocket e DatagramPacket)

## Como executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/rraquelof/sistemas-distribuidos-atividade4.git
   ```

2. Abra o projeto no IntelliJ IDEA.

3. Execute a classe `MainServidor.java` para iniciar o servidor.

4. Mantenha o servidor em execução e abra outra configuração de execução.

5. Execute a classe `MainCliente.java` para enviar as requisições.

O servidor utiliza a porta **5000** para receber as mensagens do cliente.

## Como testar

A classe `MainCliente.java` contém chamadas para testar as operações:

- `SOMAR;10;20` – Resultado: 30
- `SUBTRAIR;20;10` – Resultado: 10
- `MULTIPLICAR;10;5` – Resultado: 50
- `DIVIDIR;20;4` – Resultado: 5
- `DIVIDIR;10;0` – Testa o tratamento de divisão por zero.
- `POTENCIA;2;3` – Testa o tratamento de operação inválida.

Também é possível testar o timeout desligando o servidor antes de executar o cliente e testar a identificação de requisições duplicadas utilizando o método `testarRequisicaoDuplicada()` da classe `Cliente`.

## Estrutura do projeto

- `Calculadora.java`: implementa as operações matemáticas.
- `Cliente.java`: envia requisições e recebe respostas.
- `Servidor.java`: recebe, processa e responde às requisições.
- `MainCliente.java`: inicia os testes do cliente.
- `MainServidor.java`: inicia o servidor.

## Observação
O servidor deve estar em execução antes de iniciar os testes do cliente. Cliente e servidor devem utilizar a mesma porta de comunicação configurada no projeto.