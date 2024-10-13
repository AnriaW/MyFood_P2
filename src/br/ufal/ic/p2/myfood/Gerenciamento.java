package br.ufal.ic.p2.myfood;

import br.ufal.ic.p2.myfood.exceptions.*;
import br.ufal.ic.p2.myfood.utils.*;
import br.ufal.ic.p2.myfood.models.*;
import br.ufal.ic.p2.myfood.persistence.*;

import java.util.*;
import java.util.stream.Collectors;

public class Gerenciamento {

    public static Persistencia<Usuario> persistenciaUsuario = new PersistenciaUsuario();
    public static Persistencia<Empresa> persistenciaEmpresa = new PersistenciaEmpresa();
    public static Persistencia<Produto> persistenciaProduto = new PersistenciaProduto();
    public static Persistencia<Pedido> persistenciaPedido = new PersistenciaPedido();
    public static Persistencia<Entrega> persistenciaEntrega = new PersistenciaEntrega();

    public Gerenciamento() {
        persistenciaUsuario.iniciar();
        persistenciaEmpresa.iniciar();
        persistenciaProduto.iniciar();
        persistenciaPedido.iniciar();
        persistenciaEntrega.iniciar();

        for (Usuario usuario : persistenciaUsuario.listar()) {
            if (usuario instanceof Dono dono) {
                List<Empresa> empresasDoUsuario = persistenciaEmpresa.listar()
                        .stream()
                        .filter(company -> company.getDono().getId() == dono.getId())
                        .toList();

                dono.setComp_list(empresasDoUsuario);
            }
        }

        for (Empresa empresa : persistenciaEmpresa.listar()) {
            List<Produto> produtosEmpresa = persistenciaProduto.listar()
                    .stream()
                    .filter(produto -> produto.getId_dono() == empresa.getId())
                    .toList();

            empresa.setProd_list(produtosEmpresa);
        }
    }

    public void zerarSistema() {
        persistenciaUsuario.limpar();
        persistenciaEmpresa.limpar();
        persistenciaProduto.limpar();
        persistenciaPedido.limpar();
    }


    public void encerrarSistema() {
    }


    public List<Pedido> pedidosClienteEmpresa(int idCliente, int idEmpresa) {
        String nomeCliente = persistenciaUsuario.buscar(idCliente).getNome();
        String nomeEmpresa = persistenciaEmpresa.buscar(idEmpresa).getNome();

        return persistenciaPedido.listar()
                .stream()
                .filter(pedido -> pedido.getCliente().getNome().equals(nomeCliente) && pedido.getEmpresa().getNome().equals(nomeEmpresa) && pedido.getEstado().equals("aberto"))
                .toList();
    }


    private void testUserInvalid(String nome, String email, String senha, String endereco) throws UserCreationException {
        if (nome == null || nome.isEmpty()) {
            throw new UserCreationException("Nome invalido");
        }

        if (email == null || !(email.contains("@"))) {
            throw new UserCreationException("Email invalido");
        }

        if (senha == null || senha.isEmpty()) {
            throw new UserCreationException("Senha invalido");
        }

        if (endereco == null || endereco.isEmpty()) {
            throw new UserCreationException("Endereco invalido");
        }

    }

    public void criarUsuario(String nome, String email, String senha, String endereco) throws UserCreationException {

        testUserInvalid(nome, email, senha, endereco);

        for (Usuario user : persistenciaUsuario.listar()) {
            if (user.getEmail().equals(email)) {
                throw new UserCreationException("Conta com esse email ja existe");
            }
        }

        Usuario cliente = new Usuario(nome, email, senha, endereco);
        persistenciaUsuario.salvar(cliente);
    }


    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws UserCreationException {

        testUserInvalid(nome, email, senha, endereco);

        if (cpf == null || cpf.length() != 14) {
            throw new UserCreationException("CPF invalido");
        }

        for (Usuario user : persistenciaUsuario.listar()) {
            if (user.getEmail().equals(email)) {
                throw new UserCreationException("Conta com ese email ja existe");
            }
        }

        Dono dono = new Dono(nome, email, senha, endereco, cpf);
        persistenciaUsuario.salvar(dono);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String veiculo, String placa) throws UserCreationException {

        testUserInvalid(nome, email, senha, endereco);

        if (veiculo == null || veiculo.isEmpty()) {
            throw new UserCreationException("Veiculo invalido");
        }

        if (placa == null || placa.isEmpty()) {
            throw new UserCreationException("Placa invalido");
        }

        for (Usuario user : persistenciaUsuario.listar()) {
            if (user.getEmail().equals(email)) {
                throw new UserCreationException("Conta com ese email ja existe");
            }
        }

        Entregador entregador = new Entregador(nome, email, senha, endereco, veiculo, placa);
        persistenciaUsuario.salvar(entregador);
    }


    public String getAtributoUsuario(int id, String atributo) throws UnregisteredException {
        Usuario usuario = persistenciaUsuario.buscar(id);

        if (usuario == null)
            throw new UnregisteredException("Usuario nao cadastrado.");

        return usuario.getAtributo(atributo);
    }


    public int login(String email, String senha) throws InvalidCredentialsException {
        for (Usuario user : persistenciaUsuario.listar()) {
            if (user.getEmail().equals(email) && user.getSenha().equals(senha)) {
                return user.getId();
            }
        }
        throw new InvalidCredentialsException();
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha) throws CompanyCreationException, WrongTypeUserException {

        if (nome == null || nome.isEmpty()) {
            throw new CompanyCreationException("Nome invalido");
        }
        if (endereco == null || endereco.isEmpty()) {
            throw new CompanyCreationException("Endereco invalido");
        }

        for (Empresa empresa : persistenciaEmpresa.listar()) {
            if (empresa.getNome().equals(nome) && empresa.getDono().getId() != dono) {
                throw new CompanyCreationException("Empresa com esse nome ja existe");
            }
            if (empresa.getNome().equals(nome) && empresa.getEndereco().equals(endereco)) {
                throw new CompanyCreationException("Proibido cadastrar duas empresas com o mesmo nome e local");
            }
        }

        if (!(persistenciaUsuario.buscar(dono).getClass().getSimpleName().equals("Dono"))) {
            throw new WrongTypeUserException();
        }

        if (tipoEmpresa.equals("restaurante")) {
            Dono tempDono = (Dono) persistenciaUsuario.buscar(dono);
            Restaurante restaurante = new Restaurante(nome, endereco, tempDono, tipoCozinha);
            persistenciaEmpresa.salvar(restaurante);
            tempDono.addComp_list(restaurante);
            return restaurante.getId();
        }

        return -1;
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String abre, String fecha, String tipoMercado) throws CompanyCreationException, WrongTypeUserException {

        if (nome == null || nome.isEmpty()) {
            throw new CompanyCreationException("Nome invalido");
        }
        if (endereco == null || endereco.isEmpty()) {
            throw new CompanyCreationException("Endereco da empresa invalido");
        }
        if (tipoEmpresa == null || tipoEmpresa.isEmpty()) {
            throw new CompanyCreationException("Tipo de empresa invalido");
        }
        if (abre == null) {
            throw new CompanyCreationException("Horarios invalido");
        }
        if (fecha == null) {
            throw new CompanyCreationException("Horarios invalido");
        }

        if (!abre.matches("^\\d{2}:\\d{2}$") || !fecha.matches("^\\d{2}:\\d{2}$")) {
            throw new CompanyCreationException("Formato de hora invalido");
        }

        if (tipoMercado == null || tipoMercado.isEmpty()) {
            throw new CompanyCreationException("Tipo de mercado invalido");
        }

        String[] abreParts = abre.split(":");
        String[] fechaParts = fecha.split(":");

        int abreHora = Integer.parseInt(abreParts[0]);
        int abreMinuto = Integer.parseInt(abreParts[1]);

        int fechaHora = Integer.parseInt(fechaParts[0]);
        int fechaMinuto = Integer.parseInt(fechaParts[1]);

        if (abreHora > 23 || fechaHora > 23 || abreMinuto > 59 || fechaMinuto > 59) {
            throw new CompanyCreationException("Horarios invalidos");
        }

        if (fechaHora < abreHora || (fechaHora == abreHora && fechaMinuto < abreMinuto)) {
            throw new CompanyCreationException("Horarios invalidos");
        }

        for (Empresa empresa : persistenciaEmpresa.listar()) {
            if (empresa.getNome().equals(nome) && empresa.getDono().getId() != dono) {
                throw new CompanyCreationException("Empresa com esse nome ja existe");
            }
            if (empresa.getNome().equals(nome) && empresa.getEndereco().equals(endereco)) {
                throw new CompanyCreationException("Proibido cadastrar duas empresas com o mesmo nome e local");
            }
        }

        if (!(persistenciaUsuario.buscar(dono).getClass().getSimpleName().equals("Dono"))) {
            throw new WrongTypeUserException();
        }

        if (tipoEmpresa.equals("mercado")) {
            Dono tempDono = (Dono) persistenciaUsuario.buscar(dono);
            Mercado mercado = new Mercado(nome, endereco, tempDono, abre, fecha, tipoMercado);
            persistenciaEmpresa.salvar(mercado);
            tempDono.addComp_list(mercado);
            return mercado.getId();
        }

        return -1;
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, boolean aberto24Horas, int numeroFuncionarios) throws CompanyCreationException, WrongTypeUserException {

        if (nome == null || nome.isEmpty()) {
            throw new CompanyCreationException("Nome invalido");
        }
        if (endereco == null || endereco.isEmpty()) {
            throw new CompanyCreationException("Endereco da empresa invalido");
        }
        if (tipoEmpresa == null || tipoEmpresa.isEmpty()) {
            throw new CompanyCreationException("Tipo de empresa invalido");
        }


        for (Empresa empresa : persistenciaEmpresa.listar()) {
            if (empresa.getNome().equals(nome) && empresa.getDono().getId() != dono) {
                throw new CompanyCreationException("Empresa com esse nome ja existe");
            }
            if (empresa.getNome().equals(nome) && empresa.getEndereco().equals(endereco)) {
                throw new CompanyCreationException("Proibido cadastrar duas empresas com o mesmo nome e local");
            }
        }

        if (!(persistenciaUsuario.buscar(dono).getClass().getSimpleName().equals("Dono"))) {
            throw new WrongTypeUserException();
        }

        if (tipoEmpresa.equals("farmacia")) {
            Dono tempDono = (Dono) persistenciaUsuario.buscar(dono);
            Farmacia farmacia = new Farmacia(nome, endereco, tempDono, aberto24Horas, numeroFuncionarios);
            persistenciaEmpresa.salvar(farmacia);
            tempDono.addComp_list(farmacia);
            return farmacia.getId();
        }

        return -1;
    }


    public String getEmpresasDoUsuario(int idDono) throws WrongTypeUserException {
        if (!(persistenciaUsuario.buscar(idDono).getClass().getSimpleName().equals("Dono"))) {
            throw new WrongTypeUserException();
        }

        Dono tempDono = (Dono) persistenciaUsuario.buscar(idDono);
        return "{" + tempDono.getComp_list().toString() + "}";
    }


    public String getAtributoEmpresa(int idEmpresa, String atributo) throws InvalidAtributeException, UnregisteredException {
        Empresa tempEmpresa = persistenciaEmpresa.buscar(idEmpresa);
        if (tempEmpresa == null) {
            throw new UnregisteredException("Empresa nao cadastrada");
        }

        if (atributo == null || atributo.isEmpty()) {
            throw new InvalidAtributeException();
        }

        if (Objects.equals(atributo, "dono")) {
            Usuario tempUsuario = persistenciaUsuario.buscar(tempEmpresa.getDono().getId());
            return tempUsuario.getNome();
        }

        String result = tempEmpresa.getAtributo(atributo);
        if (result == null) {
            throw new InvalidAtributeException();
        }

        return result;
    }

    public int getIdEmpresa(int idDono, String nome, int indice) throws OutofBoundsException, WrongTypeUserException, UnregisteredException, CompanyCreationException {
        // Verificar se o nome é inválido
        if (nome == null || nome.isEmpty()) {
            throw new CompanyCreationException("Nome invalido");
        }

        // Verificar se o usuario é do tipo Dono
        if (!(persistenciaUsuario.buscar(idDono).getClass().getSimpleName().equals("Dono"))) {
            throw new WrongTypeUserException();
        }

        // Buscar todas as empresas do dono com o nome fornecido
        List<Empresa> empresasDoUsuario = persistenciaEmpresa.listar()
                .stream()
                .filter(company -> company.getDono().getId() == idDono && company.getNome().equals(nome))
                .toList();

        // Verificar se o índice é negativo antes de verificar se a lista está vazia
        if (indice < 0) {
            throw new OutofBoundsException("Indice invalido");
        }

        // Verificar se existem empresas com o nome fornecido
        if (empresasDoUsuario.isEmpty()) {
            throw new UnregisteredException("Nao existe empresa com esse nome");
        }

        // Verificar se o índice é maior que o tamanho da lista de empresas
        if (indice >= empresasDoUsuario.size()) {
            throw new OutofBoundsException("Indice maior que o esperado");
        }

        // Retornar o ID da empresa correspondente ao índice
        return empresasDoUsuario.get(indice).getId();
    }


    public static int getIndexByNome(List<Empresa> empresas, String nome) {
        for (int i = 0; i < empresas.size(); i++) {
            if (empresas.get(i).getNome().equals(nome)) {
                return i;
            }
        }
        return -1;
    }


    private void testProductInvalid(String nome, float valor, String categoria) throws ProductCreationException {
        if (nome == null || nome.isEmpty()) {
            throw new ProductCreationException("Nome invalido");
        }

        if (valor < 0) {
            throw new ProductCreationException("Valor invalido");
        }

        if (categoria == null || categoria.isEmpty()) {
            throw new ProductCreationException("Categoria invalido");
        }

    }


    public int criarProduto(int idEmpresa, String nome, float valor, String categoria) throws ProductCreationException {
        Empresa empresa = persistenciaEmpresa.buscar(idEmpresa);

        testProductInvalid(nome, valor, categoria);

        for (Produto produto : empresa.getProd_list()) {
            if (produto.getNome().equals(nome)) {
                throw new ProductCreationException("Ja existe um produto com esse nome para essa empresa");
            }
        }

        Produto produto = new Produto(nome, valor, categoria, empresa.getId());
        persistenciaProduto.salvar(produto);
        empresa.addProd_list(produto);
        return produto.getId();
    }


    public void editarProduto(int idProduto, String nome, float valor, String categoria) throws ProductCreationException {
        testProductInvalid(nome, valor, categoria);

        Produto prod = persistenciaProduto.buscar(idProduto);
        if (prod == null) {
            throw new ProductCreationException("Produto nao cadastrado");
        }

        prod.setNome(nome);
        prod.setValor(valor);
        prod.setCategoria(categoria);

        persistenciaProduto.editar(prod);
    }


    public String getProduto(String nome, int idEmpresa, String atributo) throws InvalidAtributeException, UnregisteredException {
        Empresa empresa = persistenciaEmpresa.buscar(idEmpresa);
        List<Produto> list = empresa.getProd_list();

        if (atributo == null || atributo.isEmpty()) {
            throw new InvalidAtributeException();
        }

        for (Produto prod : list) {
            if (prod.getNome().equals(nome)) {
                return switch (atributo) {
                    case "nome" -> prod.getNome();
                    case "valor" -> String.format(Locale.US, "%.2f", prod.getValor());
                    case "categoria" -> prod.getCategoria();
                    case "empresa" -> String.valueOf(empresa.getNome());
                    default -> throw new InvalidAtributeException("Atributo nao existe");
                };
            }
        }
        throw new UnregisteredException("Produto nao encontrado");
    }


    public String listarProdutos(int idEmpresa) throws UnregisteredException {
        Empresa empresa = persistenciaEmpresa.buscar(idEmpresa);

        if (empresa == null) {
            throw new UnregisteredException("Empresa nao encontrada");
        }

        return "{" + empresa.getProd_list() + "}";
    }


    public int criarPedido(int idCliente, int idEmpresa) throws OrderCreationException, WrongTypeUserException {
        Usuario temp_cliente = persistenciaUsuario.buscar(idCliente);
        List<Pedido> pedidosClienteEmpresa = pedidosClienteEmpresa(idCliente, idEmpresa);

        if (temp_cliente.getClass().getSimpleName().equals("Dono")) {
            throw new WrongTypeUserException("Dono de empresa nao pode fazer um pedido");
        } else if (!pedidosClienteEmpresa.isEmpty()) {
            throw new OrderCreationException();
        } else {
            Empresa temp_comp = persistenciaEmpresa.buscar(idEmpresa);
            Pedido ped = new Pedido(temp_cliente, temp_comp);
            persistenciaPedido.salvar(ped);
            return ped.getNumero();
        }
    }


    public void adicionarProduto(int idPedido, int idProduto) throws UnregisteredException, StatusException {
        Pedido tempPedido = persistenciaPedido.buscar(idPedido);

        if (tempPedido == null) {
            throw new UnregisteredException("Nao existe pedido em aberto");
        }

        if (!(tempPedido.getEstado().equals("aberto"))) {
            throw new StatusException("Nao e possivel adcionar produtos a um pedido fechado");
        }

        List<Produto> prodList = tempPedido.getEmpresa().getProd_list();
        for (Produto prod : prodList) {
            if (prod.getId() == idProduto) {
                tempPedido.addProductToList(prod);
                return;
            }
        }

        throw new UnregisteredException("O produto nao pertence a essa empresa");
    }


    public int getNumeroPedido(int idCliente, int idEmpresa, int indice) {
        String nomeCliente = persistenciaUsuario.buscar(idCliente).getNome();
        String nomeEmpresa = persistenciaEmpresa.buscar(idEmpresa).getNome();

        List<Pedido> pedidosClienteEmpresa = persistenciaPedido.listar()
                .stream()
                .filter(pedido -> pedido.getCliente().getNome().equals(nomeCliente) && pedido.getEmpresa().getNome().equals(nomeEmpresa))
                .toList();

        return pedidosClienteEmpresa.get(indice).getNumero();
    }


    public String getPedidos(int idPedido, String atributo) throws UnregisteredException, InvalidAtributeException {
        if (atributo == null || atributo.isEmpty()) {
            throw new InvalidAtributeException();
        }

        Pedido tempPedido = persistenciaPedido.buscar(idPedido);

        if (tempPedido.getNumero() == idPedido) {
            return switch (atributo) {
                case "cliente" -> tempPedido.getCliente().getNome();
                case "empresa" -> tempPedido.getEmpresa().getNome();
                case "estado" -> tempPedido.getEstado();
                case "produtos" -> "{" + tempPedido.getProd_list() + "}";
                case "valor" -> String.format(Locale.US, "%.2f", tempPedido.getValor_total());
                default -> throw new InvalidAtributeException("Atributo nao existe");
            };
        }
        throw new UnregisteredException("Produto nao encontrado");
    }


    public void fecharPedido(int idPedido) throws UnregisteredException {
        Pedido tempPedido = persistenciaPedido.buscar(idPedido);
        if (tempPedido == null) {
            throw new UnregisteredException("Pedido nao encontrado");
        }

        tempPedido.mudarEstado();

    }

    public void liberarPedido(int idPedido) throws UnregisteredException, StatusException {
        if (String.valueOf(idPedido) == null || String.valueOf(idPedido).isEmpty()) {
            throw new UnregisteredException("Nao e possivel liberar um produto que nao esta sendo preparado");
        }

        Pedido tempPedido = persistenciaPedido.buscar(idPedido);
        if (tempPedido == null) {
            throw new UnregisteredException("Pedido nao encontrado");
        }
        tempPedido.mudarEstadoNovamente();
    }

    public void removerProduto(int idPedido, String produto) throws InvalidAtributeException, UnregisteredException, StatusException {
        if (produto == null || produto.isEmpty()) {
            throw new InvalidAtributeException("Produto invalido");
        }

        Pedido ped = persistenciaPedido.buscar(idPedido);
        if (ped.getEstado().equals("preparando")) {
            throw new StatusException("Nao e possivel remover produtos de um pedido fechado");
        }

        List<Produto> listProd = ped.getProd_list();
        for (Produto prod : listProd) {
            if (prod.getNome().equals(produto)) {
                ped.removeProductFromList(prod);
                persistenciaPedido.atualizar();
                return;
            }
        }

        throw new UnregisteredException("Produto nao encontrado");
    }

    public int alterarFuncionamento(int mercadoId, String abre, String fecha) throws UnregisteredException {
        if (abre == null) {
            throw new UnregisteredException("Horarios invalidos");
        }
        if (fecha == null) {
            throw new UnregisteredException("Horarios invalidos");
        }

        if (!abre.matches("^\\d{2}:\\d{2}$") || !fecha.matches("^\\d{2}:\\d{2}$")) {
            throw new UnregisteredException("Formato de hora invalido");
        }
        String[] abreParts = abre.split(":");
        String[] fechaParts = fecha.split(":");

        int abreHora = Integer.parseInt(abreParts[0]);
        int abreMinuto = Integer.parseInt(abreParts[1]);

        int fechaHora = Integer.parseInt(fechaParts[0]);
        int fechaMinuto = Integer.parseInt(fechaParts[1]);

        if (abreHora > 23 || fechaHora > 23 || abreMinuto > 59 || fechaMinuto > 59) {
            throw new UnregisteredException("Horarios invalidos");
        }

        if (fechaHora < abreHora || (fechaHora == abreHora && fechaMinuto < abreMinuto)) {
            throw new UnregisteredException("Horarios invalidos");
        }

        Empresa empresa = persistenciaEmpresa.buscar(mercadoId);

        try {
            Mercado mercado = (Mercado) empresa;
            mercado.getTipoMercado();
        } catch (ClassCastException e) {
            throw new UnregisteredException("Nao e um mercado valido");
        }
        Mercado mercado = (Mercado) empresa;

        mercado.setAbre(abre);
        mercado.setFecha(fecha);

        persistenciaEmpresa.atualizar();
        return -1;
    }

    public void cadastrarEntregador(int idEmpresa, int idEntregador) throws WrongTypeUserException, UnregisteredException {
        Empresa empresa = persistenciaEmpresa.buscar(idEmpresa);
        if (empresa == null) {
            throw new UnregisteredException("Empresa nao encontrada");
        }
        if (idEntregador <= 0 || idEmpresa <= 0) {
            throw new UnregisteredException("ID de empresa ou entregador inválido");
        }

        Usuario usuario = persistenciaUsuario.buscar(idEntregador);
        if (usuario == null || !usuario.isEntregador()) {
            throw new WrongTypeUserException("Usuario nao e um entregador");
        }

        if (empresa.getListaEntregadores().contains(usuario)) {
            throw new UnregisteredException("Entregador ja cadastrado nesta empresa");
        }

        empresa.addEntregador((Entregador) usuario);

    }

    public String getEntregadores(int idEmpresa) throws UnregisteredException {
        Empresa empresa = persistenciaEmpresa.buscar(idEmpresa);
        if (empresa == null) {
            throw new UnregisteredException("Empresa nao encontrada");
        }

        List<String> emails = empresa.getListaEntregadores()
                .stream()
                .map(Entregador::getEmail)
                .collect(Collectors.toList());


        return "{[" + String.join(", ", emails) + "]}";
    }

    public String getEmpresas(int idEntregador) throws WrongTypeUserException {
        Usuario entregador = persistenciaUsuario.buscar(idEntregador);

        if (entregador == null || !entregador.isEntregador()) {
            throw new WrongTypeUserException("Usuario nao e um entregador");
        }

        List<Empresa> empresasDoEntregador = persistenciaEmpresa.listar()
                .stream()
                .filter(empresa -> empresa.getListaEntregadores().contains(entregador))
                .collect(Collectors.toList());

        String empresasFormatadas = empresasDoEntregador.stream()
                .map(empresa -> "[" + empresa.getNome() + ", " + empresa.getEndereco() + "]")
                .collect(Collectors.joining(", "));

        return "{[" + empresasFormatadas + "]}";
    }

    public int obterPedido(int idEntregador) throws WrongTypeUserException, UnregisteredException, StatusException {
        Usuario entregador = persistenciaUsuario.buscar(idEntregador);

        if (entregador == null || !entregador.getClass().getSimpleName().equals("Entregador")) {
            throw new WrongTypeUserException("Usuario nao e um entregador.");
        }

        List<Empresa> empresasDoEntregador = persistenciaEmpresa.listar()
                .stream()
                .filter(empresa -> empresa.getListaEntregadores().contains(entregador))
                .collect(Collectors.toList());

        if (empresasDoEntregador.isEmpty()) {
            throw new UnregisteredException("Entregador nao esta em nenhuma empresa.");
        }

        List<Pedido> pedidosProntos = persistenciaPedido.listar()
                .stream()
                .filter(pedido -> empresasDoEntregador.contains(pedido.getEmpresa()) && pedido.getEstado().equals("pronto"))
                .collect(Collectors.toList());

        if (pedidosProntos.isEmpty()) {
            throw new StatusException("Nao existe pedido para entrega");
        }

        Pedido pedidoSelecionado = pedidosProntos.stream()
                .filter(p -> p.getEmpresa().getClass().getSimpleName().equals("Farmacia"))
                .min(Comparator.comparingInt(Pedido::getNumero))  // Selecionar o pedido de farmácia mais antigo
                .orElse(null);

        if (pedidoSelecionado == null) {
            pedidoSelecionado = pedidosProntos.stream()
                    .min(Comparator.comparingInt(Pedido::getNumero))  // Selecionar o pedido mais antigo no geral
                    .orElseThrow(() -> new StatusException("Nao foi possivel selecionar um pedido"));
        }


        return pedidoSelecionado.getNumero();
    }

    public int criarEntrega(int idPedido, int idEntregador, String destino) throws UnregisteredException, StatusException {
        Pedido pedido = persistenciaPedido.buscar(idPedido);
        if (pedido == null) {
            throw new UnregisteredException("Pedido nao encontrado");
        }

        Usuario usuario = persistenciaUsuario.buscar(idEntregador);
        if (usuario == null || !usuario.getClass().getSimpleName().equals("Entregador")) {
            throw new UnregisteredException("Nao e um entregador valido");
        }

        Entregador entregador = (Entregador) usuario;

        if (entregador.isEmEntrega()) {
            throw new StatusException("Entregador ainda em entrega");
        }
        if (!pedido.getEstado().equals("pronto")) {
            throw new StatusException("Pedido nao esta pronto para entrega");
        }

        if (destino == null) {
            destino = pedido.getCliente().getEndereco();
        }

        entregador.setEmEntrega(true);
        pedido.mudarParaEntregando();

        Entrega novaEntrega = new Entrega(pedido, entregador, destino);
        persistenciaEntrega.salvar(novaEntrega);
        persistenciaPedido.atualizar();
        persistenciaUsuario.atualizar();

        return novaEntrega.getId();
    }

    public String getEntrega(int idEntrega, String atributo) throws UnregisteredException, InvalidAtributeException {
        Entrega entrega = persistenciaEntrega.buscar(idEntrega);
        if (entrega == null) {
            throw new UnregisteredException("Entrega não encontrada");
        }

        if (atributo == null || atributo.trim().isEmpty()) {
            throw new UnregisteredException("Atributo invalido");
        }
        return entrega.getAtributo(atributo);
    }

    public int getIdEntrega(int pedidoId) throws UnregisteredException {
        for (Entrega entrega : persistenciaEntrega.listar()) {
            if (entrega.getPedido().getNumero() == pedidoId) {
                return entrega.getId();
            }
        }

        throw new UnregisteredException("Nao existe entrega com esse id");
    }

    public void entregar(int entregaId) throws UnregisteredException {

        Entrega entrega = persistenciaEntrega.buscar(entregaId);

        if (entrega == null) {
            throw new UnregisteredException("Nao existe nada para ser entregue com esse id");
        }

        Pedido pedido = entrega.getPedido();
        pedido.getEntregar();

        Entregador entregador = entrega.getEntregador();
        entregador.setEmEntrega(false);

        persistenciaPedido.atualizar();
        persistenciaEntrega.atualizar();
        persistenciaUsuario.atualizar();
    }
}