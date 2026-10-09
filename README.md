#  TaskManager – Sistema Gerenciador de Tarefas

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![SQLite](https://img.shields.io/badge/SQLite-JDBC-blue?logo=sqlite)
![NetBeans](https://img.shields.io/badge/IDE-Apache%20NetBeans-1B6AC6?logo=apachenetbeans)
![Status](https://img.shields.io/badge/status-em%20evolu%C3%A7%C3%A3o-green)

Sistema de gestão de tarefas via console, desenvolvido em **Java**, que aplica
conceitos de **Orientação a Objetos**, **Generics**, **Polimorfismo** e
**persistência relacional com JDBC + SQLite**, usando o padrão **DAO**.

---

##  Sumário
- [Sobre o projeto](#-sobre-o-projeto)
- [Funcionalidades](#-funcionalidades)
- [Tecnologias](#-tecnologias)
- [Arquitetura](#-arquitetura)
- [Conceitos aplicados](#-conceitos-aplicados)
- [Modelo do banco de dados](#-modelo-do-banco-de-dados)
- [Como executar](#-como-executar)
- [Exemplo de uso](#-exemplo-de-uso)
- [Melhorias futuras](#-melhorias-futuras)
- [Autora](#-autora)

---

##  Sobre o projeto
O TaskManager permite cadastrar, listar, atualizar e remover tarefas, com
suporte a **tarefas comuns** e **tarefas prioritárias** (BAIXA, MÉDIA, ALTA).
Os dados são salvos em um banco **SQLite**, então nada se perde ao fechar
o programa.

O projeto nasceu como estudo prático de POO e foi evoluindo:
migrou de **MySQL para SQLite** (sem precisar instalar servidor de banco)
e passou a ter [ descreva aqui as melhorias novas que você implementou ].

##  Funcionalidades
- ✅ Cadastro de tarefas comuns e prioritárias
- ✅ CRUD completo (criar, listar, atualizar, excluir) via DAO
- ✅ Criação automática das tabelas na primeira execução
- ✅ Consultas seguras com `PreparedStatement` (previne SQL Injection)
- ✅ Gestor genérico de tarefas com Generics
- ✅ Tratamento de erros com exceções personalizadas
- ✅ [ nova funcionalidade 1 ]
- ✅ [ nova funcionalidade 2 ]

##  Tecnologias
| Categoria | Tecnologia |
|---|---|
| Linguagem | Java (JDK 21) |
| Banco de dados | SQLite |
| Acesso a dados | JDBC (sqlite-jdbc) + padrão DAO |
| IDE | Apache NetBeans |
| Controle de versão | Git / GitHub |

##  Arquitetura
Projeto organizado em camadas, cada uma com uma responsabilidade:

```
TaskManager/
├── src/
│   ├── app/          # Classe principal (Main) e menu interativo
│   ├── controller/   # TaskManager: gestor genérico de tarefas
│   ├── dao/          # TarefaDAO: acesso ao banco de dados
│   ├── database/     # ConexaoBD: configuração da conexão SQLite
│   ├── exceptions/   # Exceções personalizadas
│   └── model/        # Tarefa, TarefaPrioritaria, Prioridade (enum)
└── meus_bancos/      # Arquivo .db do SQLite
```

Fluxo de uma operação:

```
Menu (app) → TaskManager (controller) → TarefaDAO (dao) → ConexaoBD → SQLite
```

##  Conceitos aplicados
- **Herança e polimorfismo:** `TarefaPrioritaria` estende `Tarefa`
- **Enum:** `Prioridade` (BAIXA, MEDIA, ALTA)
- **Generics:** `TaskManager<T extends Tarefa>` manipula listas com segurança de tipos
- **Padrão DAO:** isola o SQL do restante da aplicação
- **Exceções personalizadas:** erros de entrada e de conexão tratados de forma clara
- **Separação em camadas:** facilita manutenção e testes



##  Como executar

### Pré-requisitos
- JDK 21 instalado
- Apache NetBeans (ou outra IDE Java)
- Driver [sqlite-jdbc](https://github.com/xerial/sqlite-jdbc/releases) (arquivo `.jar`)

### Passo a passo
1. Clone o repositório:
```bash
   git clone https://github.com/laralcf-netizen/TaskManager.git
```
2. Abra o projeto no NetBeans (**File > Open Project**).
3. Adicione o `.jar` do sqlite-jdbc em **Libraries > Add JAR/Folder**.
4. Confirme que a pasta `meus_bancos` existe na raiz do projeto
   (o banco `.db` é criado nela).
5. Execute a classe `app.Main` (**Run > Run Project**).

### Problemas comuns
- **`No suitable driver found`:** o `.jar` do sqlite-jdbc não está nas Libraries.
- **`unable to open database file`:** a pasta `meus_bancos` não existe.

##  Exemplo de uso
```
===== TASK MANAGER =====
1 - Cadastrar tarefa
2 - Cadastrar tarefa prioritária
3 - Listar tarefas
4 - Atualizar tarefa
5 - Remover tarefa
0 - Sair
Escolha: _
```
> Troque pelo menu real do seu programa. Um print da tela fica ótimo aqui!


## 👩‍💻 Autora
**Lara Lívia Cardozo Freitas**
[GitHub](https://github.com/laralcf-netizen)
