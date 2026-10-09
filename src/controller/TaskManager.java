package controller;

import java.util.ArrayList;
import java.util.List;
import model.Tarefa;

public class TaskManager<T extends Tarefa> {
    private List<T> tarefas;

    public TaskManager() {
        this.tarefas = new ArrayList<>();
    }

    // Adiciona uma tarefa à lista em memória
    public void adicionarTarefa(T tarefa) {
        if (tarefa != null) {
            tarefas.add(tarefa);
        }
    }

    // Retorna a lista completa de tarefas
    public List<T> listarTarefas() {
        return tarefas;
    }

    // Método genérico para filtrar tarefas concluídas ou pendentes
    public List<Tarefa> filtrarPorStatus(boolean concluida) {
        List<Tarefa> filtradas = new ArrayList<>();
        for (T t : tarefas) {
            if (t.isConcluida() == concluida) {
                filtradas.add(t);
            }
        }
        return filtradas;
    }

    // Remove uma tarefa pelo índice
    public void removerPorIndice(int index) {
        if (index >= 0 && index < tarefas.size()) {
            tarefas.remove(index);
        }
    }
}