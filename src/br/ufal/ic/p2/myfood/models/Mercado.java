package br.ufal.ic.p2.myfood.models;

public class Mercado extends Empresa {
    private String abre;
    private String fecha;
    private String tipoMercado;

    public Mercado() {
    }

    public Mercado(String nome, String endereco, Dono dono, String abre, String fecha, String tipoMercado) {
        super(nome, endereco, dono);
        this.abre = abre;
        this.fecha = fecha;
        this.tipoMercado = tipoMercado;
    }


    public String getTipoMercado() {
        return tipoMercado;
    }

    public void setTipoMercado(String tipoMercado) {
        this.tipoMercado = tipoMercado;
    }

    @Override
    public String getAtributo(String atributo) {
        if (atributo.equals("tipoMercado")) {
            return tipoMercado;
        } else if (atributo.equals("abre")) {
            return abre;
        } else if (atributo.equals("fecha")) {
            return fecha;
        }
        return super.getAtributo(atributo);
    }
}
