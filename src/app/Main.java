package app;

import controller.TaskManager;
import model.Tarefa;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        TaskManager manager = new TaskManager();
        Scanner scanner = new Scanner(System.in);
        int opcao;
        int proximoId = 1; // controla o ID autoincremental

        do {
            System.out.println("\n==Gerenciador de Tarefas==");
            System.out.println("1. Criar nova tarefa");
            System.out.println("2. Listar tarefas");
            System.out.println("3. Concluir tarefa");
            System.out.println("4. Remover tarefa");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // limpa o buffer após nextInt()

            switch (opcao) {
                case 1:
                    System.out.print("Título: ");
                    String titulo = scanner.nextLine();

                    System.out.print("Descrição: ");
                    String descricao = scanner.nextLine();

                    Tarefa nova = new Tarefa(proximoId++, titulo, descricao);
                    manager.adicionarTarefa(nova);
                    System.out.println("✔ Tarefa criada com sucesso!");
                    break;

                case 2:
                    manager.listarTarefas();
                    break;

                case 3:
                    System.out.print("ID da tarefa a concluir: ");
                    int idConcluir = scanner.nextInt();
                    manager.concluirTarefa(idConcluir);
                    break;

                case 4:
                    System.out.print("ID da tarefa a remover: ");
                    int idRemover = scanner.nextInt();
                    manager.removerTarefa(idRemover);
                    break;

                case 5:
                    System.out.println("Saindo do sistema...");
                    break;

                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (opcao != 5); // loop termina quando usuário escolhe 5

        scanner.close();
    }
}