
package org.example;

import java.util.HashMap;
import java.util.Map;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Servidor {

    private static final int PORTA = 5000;

    private final Map<String, String> respostasArmazenadas = new HashMap<>();

    public void iniciarServidor() {
        try {
            DatagramSocket socket = new DatagramSocket(PORTA);
            Calculadora calculadora = new Calculadora();

            System.out.println("Servidor iniciado!");
            System.out.println("Aguardando requisicoes na porta " + PORTA);

            while (true) {
                byte[] dadosRecebidos = new byte[1024];

                DatagramPacket pacoteRecebido =
                        new DatagramPacket(dadosRecebidos, dadosRecebidos.length);

                socket.receive(pacoteRecebido);

                String mensagem = new String(
                        pacoteRecebido.getData(),
                        0,
                        pacoteRecebido.getLength()
                );

                System.out.println("Requisicao recebida: " + mensagem);

                InetAddress enderecoCliente = pacoteRecebido.getAddress();
                int portaCliente = pacoteRecebido.getPort();

                String resposta;

                try {
                    String[] partes = mensagem.split(";");

                    if (partes.length != 4) {
                        throw new IllegalArgumentException("Formato de requisicao invalido!");
                    }

                    String idRequisicao = partes[0];

                    if (respostasArmazenadas.containsKey(idRequisicao)) {

                        resposta = respostasArmazenadas.get(idRequisicao);

                        System.out.println("Requisicao duplicada identificada!");

                    } else {

                        String operacao = partes[1];
                        double numero1 = Double.parseDouble(partes[2]);
                        double numero2 = Double.parseDouble(partes[3]);

                        double resultado;

                        switch (operacao) {
                            case "SOMAR":
                                resultado = calculadora.somar(numero1, numero2);
                                break;

                            case "SUBTRAIR":
                                resultado = calculadora.subtrair(numero1, numero2);
                                break;

                            case "MULTIPLICAR":
                                resultado = calculadora.multiplicar(numero1, numero2);
                                break;

                            case "DIVIDIR":
                                resultado = calculadora.dividir(numero1, numero2);
                                break;

                            default:
                                throw new IllegalArgumentException("Operacao invalida!");
                        }

                        resposta = String.valueOf(resultado);

                        respostasArmazenadas.put(idRequisicao, resposta);
                    }

                } catch (Exception e) {
                    resposta = "ERRO: " + e.getMessage();
                }

                byte[] dadosResposta = resposta.getBytes();

                DatagramPacket pacoteResposta = new DatagramPacket(
                        dadosResposta,
                        dadosResposta.length,
                        enderecoCliente,
                        portaCliente
                );

                socket.send(pacoteResposta);

                System.out.println("Resposta enviada: " + resposta + "\n");
            }

        } catch (Exception e) {
            System.out.println("Erro no servidor: " + e.getMessage());
        }
    }
}
