package org.example;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.UUID;

public class Cliente {
    private static final int PORTA_SERVIDOR = 5000;
    private static final String IP_SERVIDOR = "localhost";

    public void enviarRequisicao(String mensagem) {
        int tentativas = 3;
        boolean recebeuResposta = false;

        try {
            DatagramSocket socket = new DatagramSocket();

            InetAddress enderecoServidor =
                    InetAddress.getByName(IP_SERVIDOR);

            String idRequisicao = UUID.randomUUID().toString();

            String mensagemCompleta = idRequisicao + ";" + mensagem;

            byte[] dadosEnvio = mensagemCompleta.getBytes();

            DatagramPacket pacoteEnvio = new DatagramPacket(
                    dadosEnvio,
                    dadosEnvio.length,
                    enderecoServidor,
                    PORTA_SERVIDOR
            );

            socket.setSoTimeout(3000);

            for (int i = 1; i <= tentativas; i++) {
                socket.send(pacoteEnvio);

                System.out.println("Requisicao enviada. Tentativa: " + i);

                try {
                    byte[] dadosRecebidos = new byte[1024];

                    DatagramPacket pacoteRecebido = new DatagramPacket(
                            dadosRecebidos,
                            dadosRecebidos.length
                    );

                    socket.receive(pacoteRecebido);

                    String resposta = new String(
                            pacoteRecebido.getData(),
                            0,
                            pacoteRecebido.getLength()
                    );

                    System.out.println("Resposta do servidor: " + resposta);

                    recebeuResposta = true;
                    break;

                } catch (java.net.SocketTimeoutException e) {
                    System.out.println("Tempo esgotado! Tentando novamente...");
                }
            }

            if (!recebeuResposta) {
                System.out.println("Servidor nao respondeu apos 3 tentativas.");
            }

            socket.close();

        } catch (Exception e) {
            System.out.println("Erro no cliente: " + e.getMessage());
        }
    }
    public void testarRequisicaoDuplicada() {
        enviarRequisicaoComId("123", "SOMAR;10;20");
        enviarRequisicaoComId("123", "SOMAR;10;20");
    }

    private void enviarRequisicaoComId(String id, String mensagem) {
        try {
            DatagramSocket socket = new DatagramSocket();

            InetAddress enderecoServidor =
                    InetAddress.getByName(IP_SERVIDOR);

            String mensagemCompleta = id + ";" + mensagem;

            byte[] dadosEnvio = mensagemCompleta.getBytes();

            DatagramPacket pacoteEnvio = new DatagramPacket(
                    dadosEnvio,
                    dadosEnvio.length,
                    enderecoServidor,
                    PORTA_SERVIDOR
            );

            socket.send(pacoteEnvio);

            byte[] dadosRecebidos = new byte[1024];

            DatagramPacket pacoteRecebido = new DatagramPacket(
                    dadosRecebidos,
                    dadosRecebidos.length
            );

            socket.receive(pacoteRecebido);

            String resposta = new String(
                    pacoteRecebido.getData(),
                    0,
                    pacoteRecebido.getLength()
            );

            System.out.println("Resposta recebida: " + resposta);

            socket.close();

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
