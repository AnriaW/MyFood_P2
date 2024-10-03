package br.ufal.ic.p2.myfood.models;

import br.ufal.ic.p2.myfood.models.*;

public class Entrega {

    private int cnt = 1;
    private String destino;

    public Entrega(){
    }
     public Entrega(Pedido pedido, Entregador entregador, String destino){
        this.pedido = pedido;
        this.entregador = entregador;
        this.destino = destino;
     }

     


}
