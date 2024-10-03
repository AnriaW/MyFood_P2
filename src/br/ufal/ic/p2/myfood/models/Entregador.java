package br.ufal.ic.p2.myfood.models;

import java.util.ArrayList;
import java.util.List;

public class Entregador extends Usuario{
    private String veiculo;
    private String placa;

    public Entregador(){}

    public Entregador(String nome, String email, String senha, String endereco, String veiculo, String placa){
        super(nome, email, senha, endereco);
        this.veiculo = veiculo;
        this.placa = placa;
    }

    public String getVeiculo() {

        return veiculo;
    }

    public void setVeiculo(String veiculo) {

        this.veiculo = veiculo;
    }

    public String getPlaca(){
        return placa;
    }

    public void setPlaca(String Placa){
        this.placa = placa;
    }

    @Override
    public String getAtributo(String atributo) {
        if (atributo.equals("veiculo")) {
            return veiculo;
        }
        else if(atributo.equals("placa")) {
            return placa;
        }
        return super.getAtributo(atributo);
    }
}
