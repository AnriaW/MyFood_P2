package br.ufal.ic.p2.myfood.persistence;

import br.ufal.ic.p2.myfood.utils.Persistencia;
import br.ufal.ic.p2.myfood.models.Entrega;

import java.util.ArrayList;
import java.util.List;

public class PersistenciaEntrega implements Persistencia<Entrega> {

    private List<Entrega> entrega_list = new ArrayList<>();
    private TratarXML controle = new TratarXML();
    private final String arquivo = "xml/entregas.xml";

    @Override
    public void iniciar() {
        entrega_list = controle.DesserializarXML(entrega_list, arquivo);
    }

    @Override
    public void salvar(Entrega modelo) {
        entrega_list.add(modelo);
        controle.SerializarXML(entrega_list, arquivo);
    }

    @Override
    public void remover(int id) {
        if (entrega_list != null) {
            entrega_list.removeIf(entrega -> entrega.getId() == id);
            controle.SerializarXML(entrega_list, arquivo);
        }
    }

    @Override
    public void limpar() {
        if (entrega_list != null) {
            entrega_list.clear();
        }
        controle.ApagarDadosXML(arquivo);
    }

    @Override
    public void editar(Entrega nova_entrega) {
        for (int i = 0; i < entrega_list.size(); i++) {
            if (entrega_list.get(i).getId() == nova_entrega.getId()) {
                entrega_list.set(i, nova_entrega);
                break;
            }
        }
        controle.SerializarXML(entrega_list, arquivo);
    }
    @Override
    public Entrega buscar(int id) {
        for (Entrega entrega : entrega_list) {
            if (entrega.getId() == id) {
                return entrega;
            }
        }
        return null;
    }

    @Override
    public List<Entrega> listar() {
        if (entrega_list == null) {
            entrega_list = new ArrayList<>();
        }
        return entrega_list;
    }
    @Override
    public void atualizar() {
        controle.SerializarXML(entrega_list, arquivo);
    }
}