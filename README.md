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

# Visão Geral dos Métodos da Classe Gerenciamento

A classe Gerenciamento é um componente central do sistema MyFood, responsável pela lógica de negócios e coordenação das interações entre diferentes partes do sistema. Aqui está uma visão geral de cada método:

## Gerenciamento do Sistema

1. `zerarSistema()`: Restaura o sistema inteiro, limpando todos os dados persistidos.

2. `encerrarSistema()`: Salva o estado atual do sistema e finaliza as operações.

## Gerenciamento de Usuários

3. `criarUsuario(String nome, String email, String senha, String endereco)`: Cria um novo usuário comum (cliente).

4. `criarUsuario(String nome, String email, String senha, String endereco, String cpf)`: Cria um novo usuário proprietário de empresa (dono).

5. `criarUsuario(String nome, String email, String senha, String endereco, String veiculo, String placa)`: Cria um novo usuário entregador.

6. `getAtributoUsuario(int id, String atributo)`: Recupera um atributo específico de um usuário.

7. `login(String email, String senha)`: Autentica um usuário e retorna seu ID.

## Gerenciamento de Empresas

8. `criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha)`: Cria um novo restaurante.

9. `criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String abre, String fecha, String tipoMercado)`: Cria um novo mercado.

10. `criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, boolean aberto24Horas, int numeroFuncionarios)`: Cria uma nova farmácia.

11. `getEmpresasDoUsuario(int idDono)`: Recupera todas as empresas de um usuário específico.

12. `getAtributoEmpresa(int idEmpresa, String atributo)`: Recupera um atributo específico de uma empresa.

13. `getIdEmpresa(int idDono, String nome, int indice)`: Recupera o ID de uma empresa com base no ID do dono, nome e índice.

14. `alterarFuncionamento(int mercadoId, String abre, String fecha)`: Atualiza os horários de funcionamento de um mercado.

## Gerenciamento de Produtos

15. `criarProduto(int idEmpresa, String nome, float valor, String categoria)`: Cria um novo produto para uma empresa.

16. `editarProduto(int idProduto, String nome, float valor, String categoria)`: Edita um produto existente.

17. `getProduto(String nome, int idEmpresa, String atributo)`: Recupera um atributo específico de um produto.

18. `listarProdutos(int idEmpresa)`: Lista todos os produtos de uma empresa.

## Gerenciamento de Pedidos

19. `criarPedido(int idCliente, int idEmpresa)`: Cria um novo pedido.

20. `adicionarProduto(int idPedido, int idProduto)`: Adiciona um produto a um pedido.

21. `getNumeroPedido(int idCliente, int idEmpresa, int indice)`: Recupera o número do pedido com base no ID do cliente, ID da empresa e índice.

22. `getPedidos(int idPedido, String atributo)`: Recupera um atributo específico de um pedido.

23. `fecharPedido(int idPedido)`: Fecha um pedido.

24. `liberarPedido(int idPedido)`: Libera um pedido para preparação.

25. `removerProduto(int idPedido, String produto)`: Remove um produto de um pedido.

## Gerenciamento de Entregas

26. `cadastrarEntregador(int idEmpresa, int idEntregador)`: Cadastra um entregador em uma empresa.

27. `getEntregadores(int idEmpresa)`: Recupera todos os entregadores associados a uma empresa.

28. `getEmpresas(int idEntregador)`: Recupera todas as empresas associadas a um entregador.

29. `obterPedido(int idEntregador)`: Atribui um pedido a um entregador.

30. `criarEntrega(int idPedido, int idEntregador, String destino)`: Cria uma nova entrega para um pedido.

31. `getEntrega(int idEntrega, String atributo)`: Recupera um atributo específico de uma entrega.

32. `getIdEntrega(int pedidoId)`: Recupera o ID da entrega associada a um pedido.

33. `entregar(int entregaId)`: Marca uma entrega como concluída.

## Métodos Auxiliares

34. `testUserInvalid(String nome, String email, String senha, String endereco)`: Valida a entrada de dados para criação de um novo usuário.

35. `testProductInvalid(String nome, float valor, String categoria)`: Valida a entrada de dados para criação ou edição de um produto.

36. `pedidosClienteEmpresa(int idCliente, int idEmpresa)`: Recupera todos os pedidos abertos de um cliente e empresa específicos.

37. `getIndexByNome(List<Empresa> empresas, String nome)`: Método auxiliar para encontrar o índice de uma empresa em uma lista pelo seu nome.

Esses métodos gerenciam coletivamente as funcionalidades principais do sistema MyFood, lidando com contas de usuários, operações de empresas, gerenciamento de produtos, processamento de pedidos e logística de entregas.
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