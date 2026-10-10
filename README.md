# 📋 TaskManager – Sistema Gerenciador de Tarefas

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![JavaFX](https://img.shields.io/badge/JavaFX-21-blue)
![SQLite](https://img.shields.io/badge/SQLite-JDBC-003B57?logo=sqlite)
![JUnit](https://img.shields.io/badge/JUnit-4.13.2-25A162)
![NetBeans](https://img.shields.io/badge/IDE-Apache%20NetBeans-1B6AC6?logo=apachenetbeans)

Sistema de gestão de tarefas desenvolvido em **Java**, com **versão console** e **interface gráfica (JavaFX)**.
O projeto aplica **Orientação a Objetos**, **Generics**, **Polimorfismo**, **exceções personalizadas** e
**persistência relacional com JDBC + SQLite**, usando o padrão **DAO**, além de **testes unitários com JUnit**.

---

## 📑 Sumário

- [Sobre o projeto](#-sobre-o-projeto)
- [Funcionalidades](#-funcionalidades)
- [Tecnologias](#-tecnologias)
- [Arquitetura](#-arquitetura)
- [Conceitos aplicados](#-conceitos-aplicados)
- [Banco de dados](#-banco-de-dados)
- [Como executar](#-como-executar)
- [Interface gráfica (JavaFX)](#-interface-gráfica-javafx)
- [Testes unitários](#-testes-unitários)
- [Melhorias futuras](#-melhorias-futuras)
- [Autora](#-autora)

---

## 💡 Sobre o projeto

O TaskManager permite **cadastrar, listar, concluir e remover tarefas**, com suporte a tarefas com
**prioridade** (BAIXA, MEDIA e ALTA). Os dados ficam salvos em um banco **SQLite**, então nada se perde ao
fechar o programa.

O projeto evoluiu em etapas:

1. Aplicação de console com POO e regras de negócio.
2. Persistência em banco de dados com JDBC e padrão DAO (migração de **MySQL para SQLite**, sem precisar de servidor).
3. **Testes unitários** com JUnit para validar as regras das classes de domínio.
4. **Interface gráfica** com JavaFX, reaproveitando o mesmo DAO da versão console.

## ✨ Funcionalidades

- ✅ Cadastro de tarefas com título, descrição e prioridade (BAIXA, MEDIA, ALTA)
- ✅ Listagem, conclusão e remoção de tarefas
- ✅ Criação automática da tabela na primeira execução
- ✅ Consultas seguras com `PreparedStatement` (previne SQL Injection)
- ✅ Gestor de tarefas na camada de controle (`TaskManager`), com uso de Generics
- ✅ Validações com exceções personalizadas (`TarefaException`): título vazio e prioridade nula
- ✅ **Interface gráfica JavaFX** com tabela, formulário, confirmação de remoção e mensagens de erro
- ✅ **Testes unitários JUnit** para as classes de domínio

## 🛠 Tecnologias

| Categoria | Tecnologia |
|---|---|
| Linguagem | Java (JDK 21) |
| Interface gráfica | JavaFX 21 |
| Banco de dados | SQLite |
| Acesso a dados | JDBC (sqlite-jdbc) + padrão DAO |
| Testes | JUnit 4.13.2 |
| IDE / build | Apache NetBeans (projeto Ant) |
| Controle de versão | Git / GitHub |

## 🏗 Arquitetura

O projeto é organizado em camadas, cada uma com uma responsabilidade:

```
TaskManager/
├── src/
│   ├── app/           # Main (console), TaskManagerFX (tela JavaFX) e Launcher (inicia o JavaFX)
│   ├── controller/    # TaskManager: gestor de tarefas
│   ├── dao/           # TarefaDAO: acesso ao banco de dados
│   ├── database/      # ConexaoBD: conexão com o SQLite
│   ├── exceptions/    # TarefaException: exceção personalizada
│   └── model/         # Tarefa, TarefaPrioritaria e Prioridade (enum)
└── test/
    └── model/         # TarefaTest e TarefaPrioritariaTest
```

Fluxo de uma operação (console ou interface gráfica):

```
Main / TaskManagerFX  →  TarefaDAO  →  ConexaoBD  →  SQLite
```

## 🎓 Conceitos aplicados

- **Herança e polimorfismo:** `TarefaPrioritaria` estende `Tarefa`
- **Enum:** `Prioridade` (BAIXA, MEDIA, ALTA)
- **Generics:** manipulação segura de listas de tarefas no gestor
- **Padrão DAO:** isola todo o SQL do restante da aplicação
- **Exceções personalizadas:** erros de validação tratados de forma clara, inclusive na interface gráfica
- **Separação em camadas:** a versão console e a versão gráfica compartilham o mesmo DAO
- **Testes automatizados:** regras de negócio validadas com JUnit

## 🗄 Banco de dados

Tabela `tarefas`, criada automaticamente pelo `TarefaDAO`:

| Coluna | Tipo | Descrição |
|---|---|---|
| `id` | INTEGER (PK, autoincrement) | Identificador |
| `titulo` | TEXT (obrigatório) | Título da tarefa |
| `descricao` | TEXT | Descrição |
| `concluida` | INTEGER (padrão 0) | 0 = pendente, 1 = concluída |
| `prioridade` | TEXT (padrão `MEDIA`) | BAIXA, MEDIA ou ALTA |

## 🚀 Como executar

### Pré-requisitos

- JDK 21
- Apache NetBeans (ou outra IDE Java)
- Driver [sqlite-jdbc](https://github.com/xerial/sqlite-jdbc/releases) (arquivo `.jar`)
- Para a interface gráfica: [JavaFX SDK 21](https://gluonhq.com/products/javafx/)

### Versão console

1. Clone o repositório:
   ```bash
   git clone https://github.com/laralcf-netizen/TaskManager.git
   ```
2. Abra o projeto no NetBeans (**File > Open Project**).
3. Adicione o `.jar` do sqlite-jdbc em **Properties > Libraries > Classpath**.
4. Execute a classe `app.Main` (clique direito em `Main.java` > **Run File**).

Menu da versão console:

```
== Gerenciador de Tarefas ==
1. Criar nova tarefa
2. Listar tarefas
3. Concluir tarefa
4. Remover tarefa
5. Sair
```

## 🖥 Interface gráfica (JavaFX)

A tela possui formulário de cadastro (título, descrição e prioridade), tabela de tarefas e botões para
**concluir**, **remover** e **atualizar a lista**. Erros de validação aparecem em janelas de alerta.

<!-- Para exibir um print, salve a imagem em docs/tela.png e remova os símbolos de comentário da linha abaixo -->
<!-- ![Tela do TaskManager](docs/tela.png) -->

### Configuração no NetBeans

1. Baixe e extraia o **JavaFX SDK 21**.
2. Em **Properties > Libraries > Classpath > Add JAR/Folder**, adicione todos os `.jar` da pasta `lib` do SDK.
3. Em **Properties > Run > VM Options**, informe o caminho do SDK **no seu computador**:
   ```
   --module-path "C:\caminho\para\javafx-sdk-21\lib" --add-modules javafx.controls,javafx.fxml
   ```
4. Em **Properties > Run > Main Class**, escolha `app.Launcher`.
5. Execute o projeto (**Run > Run Project**) ou clique direito em `Launcher.java` > **Run File**.

> O `Launcher` existe porque a classe com `main` não pode estender `Application` diretamente quando o JavaFX
> é carregado como módulo. Ele apenas chama o `TaskManagerFX`.

### Problemas comuns

| Mensagem | O que fazer |
|---|---|
| `Module javafx.controls not found` | Confira o caminho do `--module-path` nas VM Options e se a Main Class é `app.Launcher` |
| `no suitable pipeline found` | Instale o *Microsoft Visual C++ Redistributable* (x64) e atualize o driver de vídeo; como teste, acrescente `-Dprism.order=sw` às VM Options |
| `No suitable driver found` | O `.jar` do sqlite-jdbc não está nas Libraries do projeto |

## 🧪 Testes unitários

Os testes cobrem as classes de domínio, **incluindo os caminhos de erro** (exceções):

| Classe de teste | Testes | O que valida |
|---|---|---|
| `TarefaTest` | 8 | Criação, trim do título, descrição nula, status inicial pendente, `setConcluida`, título vazio/nulo e validação no setter |
| `TarefaPrioritariaTest` | 5 | Prioridade guardada, polimorfismo, prioridade nula, validação herdada de `Tarefa` e troca de prioridade |

### Como rodar

1. Em **Test Libraries**, mantenha o **JUnit 4.13.2** e o **Hamcrest**.
2. No NetBeans, use **Run > Test Project** (`Alt + F6`).
3. Confira a aba **Test Results**: a barra verde indica que todos os testes passaram.


## 👩‍💻 Autora

**Lara**
[GitHub](https://github.com/laralcf-netizen)
