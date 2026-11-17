package org.example;

public class MainCliente {
    public static void main(String[] args) {
        Cliente cliente = new Cliente();

        cliente.enviarRequisicao("SOMAR;10;20");
        cliente.enviarRequisicao("SUBTRAIR;20;10");
        cliente.enviarRequisicao("MULTIPLICAR;10;5");
        cliente.enviarRequisicao("DIVIDIR;20;4");

        cliente.enviarRequisicao("DIVIDIR;10;0");
        cliente.enviarRequisicao("POTENCIA;2;3");
    }
}