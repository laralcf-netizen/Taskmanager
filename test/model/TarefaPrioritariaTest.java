package model; // mesmo pacote da classe testada

import static org.junit.Assert.*; // JUnit 4: asserts
import org.junit.Test; // JUnit 4: anotação @Test
import exceptions.TarefaException; // sua exceção personalizada

public class TarefaPrioritariaTest { // nome da classe = nome do arquivo

    @Test // caminho feliz
    public void deveCriarTarefaComPrioridadeAlta() throws TarefaException { // throws por causa do construtor
        TarefaPrioritaria t = new TarefaPrioritaria(1, "Estudar JUnit", "Aprender testes", Prioridade.ALTA); // 4 argumentos, na ordem real
        assertEquals(Prioridade.ALTA, t.getPrioridade()); // confere a prioridade guardada
        assertEquals("Estudar JUnit", t.getTitulo()); // confere que o título (herdado de Tarefa) também funciona
    }

    @Test // teste de polimorfismo
    public void tarefaPrioritariaDeveSerUmaTarefa() throws TarefaException { // pode lançar exceção
        Tarefa t = new TarefaPrioritaria(2, "Pagar conta", "Luz", Prioridade.MEDIA); // variável do tipo pai, objeto filho
        assertTrue(t instanceof TarefaPrioritaria); // o objeto real é o filho
    }

    @Test(expected = TarefaException.class) // espera exceção
    public void naoDeveCriarComPrioridadeNula() throws TarefaException { // pode lançar exceção
        new TarefaPrioritaria(3, "Estudar", "desc", null); // prioridade nula deve lançar TarefaException
    }

    @Test(expected = TarefaException.class) // a validação do pai também vale para o filho
    public void naoDeveCriarComTituloVazio() throws TarefaException { // pode lançar exceção
        new TarefaPrioritaria(4, "", "desc", Prioridade.BAIXA); // super() lança a exceção do título
    }

    @Test // teste do setter de prioridade
    public void devePermitirTrocarAPrioridade() throws TarefaException { // pode lançar exceção
        TarefaPrioritaria t = new TarefaPrioritaria(5, "Estudar", "desc", Prioridade.BAIXA); // começa BAIXA
        t.setPrioridade(Prioridade.ALTA); // troca para ALTA
        assertEquals(Prioridade.ALTA, t.getPrioridade()); // confere a troca
    }
}