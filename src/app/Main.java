package app;

import controller.TaskManager;
import dao.TarefaDAO;
import database.ConexaoBD;
import exceptions.TarefaException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import model.Tarefa;
import java.util.Scanner;
import model.Prioridade;
import model.TarefaPrioritaria;

public class Main {
    public static void main(String[] args) {

        try {
            Connection testeConn = ConexaoBD.conectar();
            System.out.println("Conexão com a base de dados estabelecida com sucesso!");
            testeConn.close();
        } catch (SQLException e) {
            System.out.println("Aviso de Conexão: " + e.getMessage());
        }
        
        TaskManager<Tarefa> manager = new TaskManager<>();
        TarefaDAO tarefaDAO = new TarefaDAO();
        Scanner scanner = new Scanner(System.in);
        
        int opcao = 0;

        do {
            System.out.println("\n== Gerenciador de Tarefas ==");
            System.out.println("1. Criar nova tarefa");
            System.out.println("2. Listar tarefas");
            System.out.println("3. Concluir tarefa");
            System.out.println("4. Remover tarefa");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); 
            } catch (InputMismatchException e) {
                System.out.println("Erro: Digite apenas números!");
                scanner.nextLine();
                continue;
            }
            
            switch (opcao) {
                case 1:
                    System.out.print("Título: ");
                    String titulo = scanner.nextLine();

                    System.out.print("Descrição: ");
                    String descricao = scanner.nextLine();

                    System.out.print("A tarefa é prioritária? (1 - Sim / 2 - Não): ");
                    int tipo = 2;
                    try {
                        tipo = scanner.nextInt();
                        scanner.nextLine(); 
                    } catch (InputMismatchException e) {
                        scanner.nextLine(); 
                        tipo = 2; 
                    }

                    try {
                        Tarefa novaTarefa;
                        int idTemporario = 0; // O ID real será gerado automaticamente pela base de dados
                        
                        if (tipo == 1) {
                            System.out.println("Escolha a prioridade:");
                            System.out.println("1. BAIXA");
                            System.out.println("2. MÉDIA");
                            System.out.println("3. ALTA");
                            System.out.print("Opção: ");

                            int opPrio = scanner.nextInt();
                            scanner.nextLine(); 

                            Prioridade prioridadeSelecionada;
                            switch (opPrio) {
                                case 1:
                                    prioridadeSelecionada = Prioridade.BAIXA;
                                    break;
                                case 2:
                                    prioridadeSelecionada = Prioridade.MEDIA;
                                    break;
                                case 3:
                                    prioridadeSelecionada = Prioridade.ALTA;
                                    break;
                                default:
                                    prioridadeSelecionada = Prioridade.MEDIA;
                            }

                            novaTarefa = new TarefaPrioritaria(idTemporario, titulo, descricao, prioridadeSelecionada);
                        } else {
                            novaTarefa = new Tarefa(idTemporario, titulo, descricao);
                        }
                        
                        tarefaDAO.inserir(novaTarefa);
                        
                    } catch (TarefaException | SQLException e) {
                        System.out.println("Erro ao criar tarefa ou salvar: " + e.getMessage());
                    }
                    break;

                case 2:
                    try {
                        List<Tarefa> tarefasDoBanco = tarefaDAO.listar();
                        if (tarefasDoBanco.isEmpty()) {
                            System.out.println("Nenhuma tarefa encontrada na base de dados.");
                        } else {
                            System.out.println("\n-- LISTA DE TAREFAS --");
                            for (Tarefa t : tarefasDoBanco) {
                                System.out.println(t);
                                System.out.println("-------------------");
                            }
                        }
                    } catch (SQLException | TarefaException e) {
                        System.out.println("Erro ao listar da base de dados: " + e.getMessage());
                    }
                    break;

                case 3:
                    try {
                        System.out.print("Digite o ID da tarefa que deseja marcar como concluída: ");
                        int idConcluir = scanner.nextInt();
                        scanner.nextLine();
                        
                        tarefaDAO.concluirTarefa(idConcluir);
                    } catch (InputMismatchException e) {
                        System.out.println("Erro: ID inválido.");
                        scanner.nextLine();
                    } catch (SQLException e) {
                        System.out.println("Erro ao concluir tarefa: " + e.getMessage());
                    }
                    break;

                case 4:
                    try {
                        System.out.print("Digite o ID da tarefa que deseja remover: ");
                        int idRemover = scanner.nextInt();
                        scanner.nextLine();
                        
                        tarefaDAO.remover(idRemover);
                    } catch (InputMismatchException e) {
                        System.out.println("Erro: ID inválido.");
                        scanner.nextLine();
                    } catch (SQLException e) {
                        System.out.println("Erro ao remover tarefa: " + e.getMessage());
                    }
                    break;

                case 5:
                    System.out.println("A encerrar o sistema. Boa sorte na entrega de hoje!");
                    break;

                default:
                    System.out.println("Opção inválida! Escolha entre 1 e 5.");
                    break;
            }

        } while (opcao != 5); 

        scanner.close();
    }
}