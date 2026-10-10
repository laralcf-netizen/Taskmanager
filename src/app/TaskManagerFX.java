package app; // mesmo pacote do Main e do Launcher

import dao.TarefaDAO; // seu DAO que fala com o SQLite
import exceptions.TarefaException; // sua exceção personalizada
import java.sql.SQLException; // erros do banco de dados
import javafx.application.Application; // classe base de todo app JavaFX
import javafx.beans.property.SimpleStringProperty; // texto "observável" para as células da tabela
import javafx.collections.FXCollections; // fábrica de listas observáveis
import javafx.collections.ObservableList; // lista que avisa a tabela quando muda
import javafx.geometry.Insets; // margens internas dos layouts
import javafx.scene.Scene; // a "cena" (conteúdo da janela)
import javafx.scene.control.*; // Button, Label, TextField, TableView, ComboBox, Alert...
import javafx.scene.layout.HBox; // layout em linha (horizontal)
import javafx.scene.layout.VBox; // layout em coluna (vertical)
import javafx.stage.Stage; // a janela
import model.Prioridade; // seu enum (BAIXA, MEDIA, ALTA)
import model.Tarefa; // sua classe pai
import model.TarefaPrioritaria; // sua classe filha

public class TaskManagerFX extends Application { // a tela é uma aplicação JavaFX

    private final TarefaDAO dao = new TarefaDAO(); // reaproveita o DAO (ele já cria a tabela sozinho)
    private final ObservableList<Tarefa> dados = FXCollections.observableArrayList(); // lista que alimenta a tabela
    private final TableView<Tarefa> tabela = new TableView<>(); // tabela que mostra as tarefas
    private final Label statusLabel = new Label("Pronto."); // mensagem de status no rodapé

    @Override // sobrescreve o método que o JavaFX chama ao abrir
    public void start(Stage janela) { // "janela" é a janela principal

        // ---------- COLUNAS DA TABELA ----------
        TableColumn<Tarefa, String> colId = new TableColumn<>("ID"); // coluna do id
        colId.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getId()))); // pega o id e converte para texto


        TableColumn<Tarefa, String> colTitulo = new TableColumn<>("Título"); // coluna do título
        colTitulo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTitulo())); // pega o título da tarefa

        TableColumn<Tarefa, String> colDescricao = new TableColumn<>("Descrição"); // coluna da descrição
        colDescricao.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDescricao())); // pega a descrição

        TableColumn<Tarefa, String> colPrioridade = new TableColumn<>("Prioridade"); // coluna da prioridade
        colPrioridade.setCellValueFactory(c -> { // monta o texto da prioridade
            Tarefa t = c.getValue(); // tarefa da linha atual
            String texto = (t instanceof TarefaPrioritaria) // polimorfismo: só a filha tem prioridade
                    ? ((TarefaPrioritaria) t).getPrioridade().name() // se for a filha, mostra BAIXA/MEDIA/ALTA
                    : "-"; // se for tarefa comum, mostra um traço
            return new SimpleStringProperty(texto); // devolve o texto para a célula
        });

        TableColumn<Tarefa, String> colStatus = new TableColumn<>("Status"); // coluna do status
        colStatus.setCellValueFactory(c -> new SimpleStringProperty( // monta o texto do status
                c.getValue().isConcluida() ? "✔ Concluída" : "Pendente")); // concluída ou pendente

        tabela.getColumns().addAll(colId, colTitulo, colDescricao, colPrioridade, colStatus); // adiciona as colunas à tabela
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // colunas dividem a largura disponível
        tabela.setItems(dados); // liga a tabela à lista de dados
        tabela.setPlaceholder(new Label("Nenhuma tarefa cadastrada.")); // texto quando a tabela está vazia

        // ---------- FORMULÁRIO DE CADASTRO ----------
        TextField campoTitulo = new TextField(); // campo do título
        campoTitulo.setPromptText("Título da tarefa"); // texto de dica dentro do campo

        TextField campoDescricao = new TextField(); // campo da descrição
        campoDescricao.setPromptText("Descrição (opcional)"); // texto de dica


        ComboBox<Prioridade> comboPrioridade = new ComboBox<>( // lista suspensa de prioridades
                FXCollections.observableArrayList(Prioridade.values())); // usa todos os valores do enum
        comboPrioridade.setValue(Prioridade.MEDIA); // valor inicial: MEDIA

        Button botaoAdicionar = new Button("Adicionar"); // botão de cadastrar

        botaoAdicionar.setOnAction(e -> { // o que acontece ao clicar em Adicionar
            try { // tenta executar; se der erro, cai no catch
                Tarefa nova = new TarefaPrioritaria( // cria a tarefa (valida o título no construtor)
                        0, campoTitulo.getText(), campoDescricao.getText(), comboPrioridade.getValue()); // id 0: o banco gera o id real
                dao.inserir(nova); // salva no SQLite
                campoTitulo.clear(); // limpa o campo do título
                campoDescricao.clear(); // limpa o campo da descrição
                carregarTarefas(); // atualiza a tabela
                statusLabel.setText("Tarefa adicionada com sucesso!"); // avisa no rodapé
            } catch (TarefaException ex) { // título vazio ou prioridade nula
                mostrarErro(ex.getMessage()); // mostra a mensagem da SUA exceção
            } catch (SQLException ex) { // falha no banco
                mostrarErro(ex.getMessage()); // mostra o erro do banco
            }
        });

        HBox formulario = new HBox(10, campoTitulo, campoDescricao, comboPrioridade, botaoAdicionar); // junta o formulário numa linha
        HBox.setHgrow(campoTitulo, javafx.scene.layout.Priority.ALWAYS); // título cresce com a janela
        HBox.setHgrow(campoDescricao, javafx.scene.layout.Priority.ALWAYS); // descrição também cresce

        // ---------- BOTÕES DE AÇÃO ----------
        Button botaoConcluir = new Button("Marcar como concluída"); // botão de concluir
        botaoConcluir.setOnAction(e -> { // ao clicar em concluir
            Tarefa selecionada = tabela.getSelectionModel().getSelectedItem(); // pega a linha selecionada
            if (selecionada == null) { // se não escolheu nenhuma
                mostrarAviso("Selecione uma tarefa na tabela primeiro."); // pede para selecionar

                return; // sai do método
            }
            try { // tenta concluir
                dao.concluirTarefa(selecionada.getId()); // atualiza no banco pelo id
                carregarTarefas(); // atualiza a tabela
                statusLabel.setText("Tarefa concluída!"); // avisa no rodapé
            } catch (SQLException ex) { // falha no banco
                mostrarErro(ex.getMessage()); // mostra o erro
            }
        });

        Button botaoRemover = new Button("Remover"); // botão de remover
        botaoRemover.setOnAction(e -> { // ao clicar em remover
            Tarefa selecionada = tabela.getSelectionModel().getSelectedItem(); // pega a linha selecionada
            if (selecionada == null) { // se não escolheu nenhuma
                mostrarAviso("Selecione uma tarefa na tabela primeiro."); // pede para selecionar
                return; // sai do método
            }
            Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION, // janelinha de confirmação
                    "Remover a tarefa \"" + selecionada.getTitulo() + "\"?"); // pergunta ao usuário
            if (confirmacao.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) { // se clicou em OK
                try { // tenta remover
                    dao.remover(selecionada.getId()); // apaga do banco pelo id
                    carregarTarefas(); // atualiza a tabela
                    statusLabel.setText("Tarefa removida."); // avisa no rodapé
                } catch (SQLException ex) { // falha no banco
                    mostrarErro(ex.getMessage()); // mostra o erro
                }
            }
        });

        Button botaoAtualizar = new Button("Atualizar lista"); // botão de recarregar

        botaoAtualizar.setOnAction(e -> carregarTarefas()); // recarrega a tabela ao clicar

        HBox acoes = new HBox(10, botaoConcluir, botaoRemover, botaoAtualizar); // junta os botões numa linha

        // ---------- MONTAGEM DA JANELA ----------
        Label titulo = new Label("TaskManager"); // título da tela
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;"); // fonte grande e negrito

        VBox raiz = new VBox(12, titulo, formulario, tabela, acoes, statusLabel); // empilha tudo em coluna
        raiz.setPadding(new Insets(15)); // margem interna de 15px
        VBox.setVgrow(tabela, javafx.scene.layout.Priority.ALWAYS); // a tabela ocupa o espaço que sobrar

        janela.setTitle("TaskManager - Interface Gráfica"); // título da janela
        janela.setScene(new Scene(raiz, 780, 480)); // define a cena e o tamanho
        janela.show(); // exibe a janela

        carregarTarefas(); // carrega as tarefas do banco assim que abre
    }

    private void carregarTarefas() { // busca no banco e atualiza a tabela
        try { // tenta listar
            dados.setAll(dao.listar()); // troca o conteúdo da lista (a tabela atualiza sozinha)
            statusLabel.setText(dados.size() + " tarefa(s) carregada(s)."); // mostra a quantidade
        } catch (SQLException | TarefaException ex) { // erro do banco ou dado inválido
            mostrarErro(ex.getMessage()); // mostra o erro
        }
    }

    private void mostrarErro(String mensagem) { // janelinha de erro
        new Alert(Alert.AlertType.ERROR, mensagem).showAndWait(); // exibe e espera fechar
    }


    private void mostrarAviso(String mensagem) { // janelinha de aviso
        new Alert(Alert.AlertType.WARNING, mensagem).showAndWait(); // exibe e espera fechar
    }
}