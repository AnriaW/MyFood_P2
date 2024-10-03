package br.ufal.ic.p2.myfood.models;

import br.ufal.ic.p2.myfood.exceptions.CompanyCreationException;

public class Mercado extends Empresa {
    private String abre;
    private String fecha;
    private String tipoMercado;

    public Mercado() {
    }

    public Mercado(String nome, String endereco, Dono dono, String abre, String fecha, String tipoMercado) throws CompanyCreationException {
        if (!abre.matches("^\\d{2}:\\d{2}$") || !fecha.matches("^\\d{2}:\\d{2}$")){
            try {
                throw new CompanyCreationException("Formato de hora invalido");
            } catch (CompanyCreationException e) {
                throw new RuntimeException(e);
            }
        }
        super(nome, endereco, dono);
        this.abre = abre;
        this.fecha = fecha;
        this.tipoMercado = tipoMercado;
    }

    public String getAbre(){
        return abre;
    }

    public void setAbre(String abre){
        this.abre = abre;
    }

    public String getFecha(){
        return abre;
    }

    public void setFecha(String fecha){
        this.fecha = fecha;
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
