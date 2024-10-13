# MyFood - Sistema de Gerenciamento de Pedidos e Produtos
# Antônio Wagner e Taísa Lima

## Visão Geral do Sistema

MyFood é uma plataforma de gerenciamento de pedidos e produtos inspirada em modelos de delivery. O sistema permite que usuários criem e gerenciem contas, empresas e produtos, além de realizar e gerenciar pedidos. O objetivo principal é facilitar a interação entre clientes e empresas em um ambiente de delivery, centralizando a criação de pedidos e o controle de produtos.

### Componentes Principais:

- **Usuários**: Divididos em clientes, proprietários de empresas e entregadores.
- **Empresas**: Criadas pelos donos e associadas aos produtos e pedidos.
- **Produtos**: Oferecidos pelas empresas e gerenciados pelos donos.
- **Pedidos**: Realizados pelos clientes e gerenciados pelas empresas.
- **Entregas**: Gerenciadas pelos entregadores.

## Arquitetura do Sistema

A arquitetura do sistema foi projetada para ser simples e escalável, utilizando um padrão de projeto Facade para organizar a comunicação entre diferentes subsistemas.

### Estrutura do Projeto:

```
src/br.ufal.ic.p2.myfood/
├── models/
├── persistence/
├── exception/
├── utils/
├── gerenciamento/
└── facade/
```

- **models**: Classes modelo que definem Usuário, Empresa, Produto, Pedido e Entrega.
- **persistence**: Responsável pela persistência dos dados utilizando arquivos XML.
- **exception**: Conjunto de classes para tratamento de exceções.
- **utils**: Contém interfaces e utilitários de persistência para tratar arquivos XML.
- **gerenciamento**: Classe que interliga a lógica de negócios, fazendo a comunicação entre a Facade e os modelos.
- **facade**: Interface de fachada que simplifica o acesso às funcionalidades do sistema.

## Funcionalidades Principais

1. Gerenciamento de Usuários (clientes, donos de empresas, entregadores)
2. Gerenciamento de Empresas (restaurantes, mercados, farmácias)
3. Gerenciamento de Produtos
4. Criação e Gerenciamento de Pedidos
5. Sistema de Entrega

# Gerenciamento Class Methods Overview

The Gerenciamento class is a central component of the MyFood system, handling the business logic and coordinating interactions between different parts of the system. Here's an overview of each method:

## System Management

1. `zerarSistema()`: Resets the entire system by clearing all persisted data.

2. `encerrarSistema()`: Saves the current state of the system and finalizes operations.

## User Management

3. `criarUsuario(String nome, String email, String senha, String endereco)`: Creates a new regular user (cliente).

4. `criarUsuario(String nome, String email, String senha, String endereco, String cpf)`: Creates a new business owner (dono) user.

5. `criarUsuario(String nome, String email, String senha, String endereco, String veiculo, String placa)`: Creates a new delivery person (entregador) user.

6. `getAtributoUsuario(int id, String atributo)`: Retrieves a specific attribute of a user.

7. `login(String email, String senha)`: Authenticates a user and returns their ID.

## Company Management

8. `criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha)`: Creates a new restaurant.

9. `criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String abre, String fecha, String tipoMercado)`: Creates a new market.

10. `criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, boolean aberto24Horas, int numeroFuncionarios)`: Creates a new pharmacy.

11. `getEmpresasDoUsuario(int idDono)`: Retrieves all companies owned by a specific user.

12. `getAtributoEmpresa(int idEmpresa, String atributo)`: Retrieves a specific attribute of a company.

13. `getIdEmpresa(int idDono, String nome, int indice)`: Retrieves the ID of a company based on owner ID, name, and index.

14. `alterarFuncionamento(int mercadoId, String abre, String fecha)`: Updates the opening and closing times of a market.

## Product Management

15. `criarProduto(int idEmpresa, String nome, float valor, String categoria)`: Creates a new product for a company.

16. `editarProduto(int idProduto, String nome, float valor, String categoria)`: Edits an existing product.

17. `getProduto(String nome, int idEmpresa, String atributo)`: Retrieves a specific attribute of a product.

18. `listarProdutos(int idEmpresa)`: Lists all products of a company.

## Order Management

19. `criarPedido(int idCliente, int idEmpresa)`: Creates a new order.

20. `adicionarProduto(int idPedido, int idProduto)`: Adds a product to an order.

21. `getNumeroPedido(int idCliente, int idEmpresa, int indice)`: Retrieves the order number based on client ID, company ID, and index.

22. `getPedidos(int idPedido, String atributo)`: Retrieves a specific attribute of an order.

23. `fecharPedido(int idPedido)`: Closes an order.

24. `liberarPedido(int idPedido)`: Releases an order for preparation.

25. `removerProduto(int idPedido, String produto)`: Removes a product from an order.

## Delivery Management

26. `cadastrarEntregador(int idEmpresa, int idEntregador)`: Registers a delivery person with a company.

27. `getEntregadores(int idEmpresa)`: Retrieves all delivery persons associated with a company.

28. `getEmpresas(int idEntregador)`: Retrieves all companies a delivery person is associated with.

29. `obterPedido(int idEntregador)`: Assigns an order to a delivery person.

30. `criarEntrega(int idPedido, int idEntregador, String destino)`: Creates a new delivery for an order.

31. `getEntrega(int idEntrega, String atributo)`: Retrieves a specific attribute of a delivery.

32. `getIdEntrega(int pedidoId)`: Retrieves the delivery ID associated with an order.

33. `entregar(int entregaId)`: Marks a delivery as completed.

## Helper Methods

34. `testUserInvalid(String nome, String email, String senha, String endereco)`: Validates user input for creating a new user.

35. `testProductInvalid(String nome, float valor, String categoria)`: Validates product input for creating or editing a product.

36. `pedidosClienteEmpresa(int idCliente, int idEmpresa)`: Retrieves all open orders for a specific client and company.

37. `getIndexByNome(List<Empresa> empresas, String nome)`: Helper method to find the index of a company in a list by its name.

These methods collectively manage the core functionalities of the MyFood system, handling user accounts, company operations, product management, order processing, and delivery logistics.

## Instalação e Configuração

1. Clone o repositório.
2. Abra o projeto em seu ambiente de desenvolvimento Java.
3. Certifique-se de que o Java está instalado em sua máquina.
4. Compile e execute o projeto usando o arquivo `Main.java`.

## Testes e Validação

Os testes foram escritos utilizando o EasyAccept e estão localizados no diretório `tests`. Eles cobrem as principais funcionalidades do sistema, como criação de usuários, empresas, produtos e pedidos, além de operações de edição e remoção.

Para rodar os testes:
1. Compile o projeto.
2. Execute o arquivo `Main.java`, que irá rodar automaticamente os testes.

## Padrões de Projeto Utilizados

1. **Facade**: Fornece uma interface simplificada para interagir com o sistema.
2. **MVC (Model-View-Controller)**: Estrutura o código separando a lógica de negócios, a persistência de dados e o controle do sistema.
3. **Singleton**: Utilizado em algumas partes do sistema, especialmente na persistência de dados.

## Conclusão

O MyFood oferece uma base sólida para um sistema de delivery, com potencial para expansão e melhorias futuras. O projeto demonstra a aplicação prática de conceitos de design de software, persistência de dados e padrões de projeto em um contexto real de aplicação.