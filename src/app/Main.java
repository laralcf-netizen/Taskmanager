package app;

import controller.TaskManager;
import exceptions.TarefaException;
import java.util.InputMismatchException;
import model.Tarefa;
import java.util.Scanner;
import model.Prioridade;
import model.TarefaPrioritaria;

public class Main {
    public static void main(String[] args) {

        TaskManager<Tarefa> manager = new TaskManager<>();
        Scanner scanner = new Scanner(System.in);
        
        int opcao = 0;
        int proximoId = 1; // controla o ID autoincremental

        do {
            System.out.println("\n==Gerenciador de Tarefas==");
            System.out.println("1. Criar nova tarefa");
            System.out.println("2. Listar tarefas");
            System.out.println("3. Concluir tarefa");
            System.out.println("4. Remover tarefa");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            try{
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpa o buffer após nextInt()
            }catch (InputMismatchException e) {
                System.out.println("Digite apenas números!");
                scanner.nextLine();
                continue;
            }
            
            switch (opcao) {
                case 1:
                    System.out.print("Título: ");
                    String titulo = scanner.nextLine();

                    System.out.print("Descrição: ");
                    String descricao = scanner.nextLine();

                    System.out.println("A tarefa é prioritária? (1 - Sim / 2 - Não): ");
                    int tipo = 2;
                    try {
                        tipo = scanner.nextInt();
                        scanner.nextLine(); // Limpa o buffer
                    } catch (InputMismatchException e) {
                        scanner.nextLine(); // Limpa o buffer inválido
                        tipo = 2; // Assume padrão como não prioritária se digitar errado
                    }

                    try {
                        Tarefa novaTarefa;

                        if (tipo == 1) {
                            System.out.println("Escolha a prioridade:");
                            System.out.println("1. BAIXA");
                            System.out.println("2. MÉDIA");
                            System.out.println("3. ALTA");
                            System.out.print("Opção: ");

                            int opPrio = scanner.nextInt();
                            scanner.nextLine(); // Limpa o buffer

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
                                    System.out.println("Opção inválida! Definindo prioridade como MÉDIA por padrão.");
                                    prioridadeSelecionada = Prioridade.MEDIA;
                            }

                            // Cria uma TarefaPrioritaria
                            novaTarefa = new TarefaPrioritaria(proximoId, titulo, descricao, prioridadeSelecionada);
                        } else {
                            // Cria uma Tarefa normal
                            novaTarefa = new Tarefa(proximoId, titulo, descricao);
                        }

                        manager.adicionarTarefa(novaTarefa);
                        proximoId++;
                        System.out.println("Tarefa criada com sucesso!");

                    } catch (TarefaException e) {
                        System.out.println("Erro ao criar tarefa: " + e.getMessage());
                    }
                    break;

                case 2:
                    manager.listarTarefas();
                    break;

                case 3:
                    System.out.print("ID da tarefa a concluir: ");
                    try{
                    int idConcluir = scanner.nextInt();
                    scanner.nextLine();
                    manager.concluirTarefa(idConcluir);
                    System.out.println("Tarefa concluida!");
                    } catch (InputMismatchException e){
                        System.out.println("ID invalido, precisa digitar um numero.");
                        scanner.nextLine();
                    }catch (TarefaException e){
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                case 4:
                    System.out.print("ID da tarefa a remover: ");
                    try{
                    int idRemover = scanner.nextInt();
                    scanner.nextLine();
                    manager.removerTarefa(idRemover);
                    System.out.println("Tarefa removida com sucesso!");
                    } catch (InputMismatchException e){
                        System.out.println("ID invalido, precisa digitar um numero.");
                        scanner.nextLine();
                    } catch (TarefaException e){
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                case 5:
                    System.out.println("Saindo do sistema...");
                    break;

                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (opcao != 5); 

        scanner.close();
    }
}