package br.ufal.ic.p2.myfood.models;

import  br.ufal.ic.p2.myfood.exceptions.*;

public class Entrega {

    private static int cnt = 1;
    private int id;
    private String destino;
    private Pedido pedido;
    private Entregador entregador;

    public Entrega() {
    }

    public Entrega(Pedido pedido, Entregador entregador, String destino) {
        this.id = cnt++;
        this.pedido = pedido;
        this.entregador = entregador;
        this.destino = destino;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Entregador getEntregador() {
        return entregador;
    }

    public void setEntregador(Entregador entregador) {
        this.entregador = entregador;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public String getAtributo(String atributo) throws InvalidAtributeException {

        return switch (atributo) {
            case "cliente" -> pedido.getCliente().getNome();
            case "empresa" -> pedido.getEmpresa().getNome();
            case "pedido" -> String.valueOf(pedido.getNumero());
            case "entregador" -> entregador.getNome();
            case "destino" -> getDestino();
            case "produtos" -> pedido.getProd_list().toString();
            default -> throw new InvalidAtributeException("Atributo nao existe");
        };
    }

    @Override
    public String toString() {
        return "Entrega{" + "id=" + id +", destino='" + destino + '\'' +", pedido=" + pedido.getNumero() + ", entregador=" + entregador.getNome() +'}';
    }


}