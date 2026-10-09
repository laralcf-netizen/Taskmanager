package controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import model.Tarefa;

public class TaskManagerTest {

    @Test
    public void testAdicionarTarefa() {
        TaskManager<Tarefa> manager = new TaskManager<>();
        Tarefa t = new Tarefa(1, "Estudar Java", "Revisar DAO e Generics");
        
        manager.adicionarTarefa(t);
        
        // Verifica se a tarefa foi adicionada com sucesso à lista
        assertEquals(1, manager.listarTarefas().size());
        assertEquals("Estudar Java", manager.listarTarefas().get(0).getTitulo());
    }
}
