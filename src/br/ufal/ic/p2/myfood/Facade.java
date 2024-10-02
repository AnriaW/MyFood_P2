package br.ufal.ic.p2.myfood;

import br.ufal.ic.p2.myfood.exceptions.*;

public class Facade {
    private final Gerenciamento gerenciamento = new Gerenciamento();

    public void zerarSistema() {
        gerenciamento.zerarSistema();
    }

    public void encerrarSistema() {
        gerenciamento.encerrarSistema();
    }

    // User Story 1

    public String getAtributoUsuario(int id, String atributo) throws UnregisteredException {
        return gerenciamento.getAtributoUsuario(id, atributo);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco) throws UserCreationException {
        gerenciamento.criarUsuario(nome, email, senha, endereco);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws UserCreationException {
        gerenciamento.criarUsuario(nome, email, senha, endereco, cpf);
    }

    public int login(String email, String senha) throws InvalidCredentialsException {
        return gerenciamento.login(email, senha);
    }

    // User Story 2

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha) throws CompanyCreationException, WrongTypeUserException {
        return gerenciamento.criarEmpresa(tipoEmpresa, dono, nome, endereco, tipoCozinha);
    }

    public String getEmpresasDoUsuario(int dono) throws WrongTypeUserException {
        return gerenciamento.getEmpresasDoUsuario(dono);
    }

    public String getAtributoEmpresa(int empresa, String atributo) throws InvalidAtributeException, UnregisteredException {
        return gerenciamento.getAtributoEmpresa(empresa, atributo);
    }

    public int getIdEmpresa(int idDono, String nome, int indice) throws OutofBoundsException, WrongTypeUserException, UnregisteredException, CompanyCreationException {
        return gerenciamento.getIdEmpresa(idDono, nome, indice);
    }

    // User Story 3

    public int criarProduto(int id_empresa, String nome, float valor, String categoria) throws ProductCreationException {
        return gerenciamento.criarProduto(id_empresa, nome, valor, categoria);
    }

    public void editarProduto(int produto, String nome, float valor, String categoria) throws ProductCreationException {
        gerenciamento.editarProduto(produto, nome, valor, categoria);
    }

    public String getProduto(String nome, int empresa, String atributo) throws InvalidAtributeException, UnregisteredException {
        return gerenciamento.getProduto(nome, empresa, atributo);
    }

    public String listarProdutos(int empresa) throws UnregisteredException {
        return gerenciamento.listarProdutos(empresa);
    }

    // User Story 4

    public int criarPedido(int cliente, int empresa) throws Exception, WrongTypeUserException {
        return gerenciamento.criarPedido(cliente, empresa);
    }

    public int getNumeroPedido(int cleinte, int empresa, int indice) {
        return gerenciamento.getNumeroPedido(cleinte, empresa, indice);
    }

    public void adicionarProduto(int pedido, int produto) throws UnregisteredException, StatusException {
        gerenciamento.adicionarProduto(pedido, produto);
    }

    public String getPedidos(int pedido, String atributo) throws InvalidAtributeException, UnregisteredException {
        return gerenciamento.getPedidos(pedido, atributo);
    }

    public void fecharPedido(int pedido) throws UnregisteredException {
        gerenciamento.fecharPedido(pedido);
    }

    public void removerProduto(int pedido, String produto) throws StatusException, UnregisteredException, InvalidAtributeException {
        gerenciamento.removerProduto(pedido, produto);
    }

}