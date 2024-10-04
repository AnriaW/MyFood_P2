package br.ufal.ic.p2.myfood.models;

import br.ufal.ic.p2.myfood.models.*;

import java.util.ArrayList;
import java.util.List;

public class Entrega {

    private int cnt = 1;
    private String destino;
    private Pedido pedido;
    private Entregador entregador;

    public Entrega(){
    }
     public Entrega(Pedido pedido, Entregador entregador, String destino){
        this.pedido = pedido;
        this.entregador = entregador;
        this.destino = destino;

     }

     public String getDestino(){
        return destino;
     }

     public void setDestino(String destino){
        this.destino = destino;
     }

}
