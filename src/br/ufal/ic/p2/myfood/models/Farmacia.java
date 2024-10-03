package br.ufal.ic.p2.myfood.models;

import br.ufal.ic.p2.myfood.exceptions.CompanyCreationException;

public class Farmacia extends Empresa {
    private boolean aberto24Horas;

    public Farmacia(){}

    public Farmacia(String nome, String endereco, Dono dono, boolean aberto24Horas){

        super(nome, endereco, dono);
        this.aberto24Horas = aberto24Horas;
    }

    public Boolean getAberto24Horas() {
        return aberto24Horas;
    }

    public void setAberto24Horas(Boolean aberto24Horas) {
        this.aberto24Horas = aberto24Horas;
    }

    @Override
    public String getAtributo(String atributo) {
        if (atributo.equals("aberto24Horas")) {
            return String.valueOf(aberto24Horas);
        }
        return super.getAtributo(atributo);
    }


}
