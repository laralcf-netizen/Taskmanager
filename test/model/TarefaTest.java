package model; // mesmo pacote da classe Tarefa

import static org.junit.Assert.*; // JUnit 4: asserts (assertEquals, assertTrue, assertFalse...)
import org.junit.Test; // JUnit 4: anotação @Test
import exceptions.TarefaException; // importa a sua exceção personalizada

public class TarefaTest { // nome da classe = nome do arquivo

    @Test // teste do caminho feliz
    public void deveCriarTarefaComDadosValidos() throws TarefaException { // throws porque o construtor pode lançar exceção
        Tarefa t = new Tarefa(1, "Estudar JUnit", "Aprender testes"); // cria com id, título e descrição
        assertEquals(1, t.getId()); // confere o id
        assertEquals("Estudar JUnit", t.getTitulo()); // confere o título
        assertEquals("Aprender testes", t.getDescricao()); // confere a descrição
    }

    @Test // teste do valor inicial de "concluida"
    public void tarefaNovaDeveComecarPendente() throws TarefaException { // pode lançar exceção
        Tarefa t = new Tarefa(1, "Pagar conta", "Luz"); // cria a tarefa
        assertFalse(t.isConcluida()); // no construtor, concluida = false
    }

    @Test // teste do trim() aplicado no título
    public void deveRemoverEspacosDoTitulo() throws TarefaException { // pode lançar exceção
        Tarefa t = new Tarefa(1, "  Estudar  ", "desc"); // título com espaços nas pontas
        assertEquals("Estudar", t.getTitulo()); // espera o título já sem espaços
    }

    @Test // teste da descrição nula
    public void descricaoNulaDeveVirarTextoVazio() throws TarefaException { // pode lançar exceção
        Tarefa t = new Tarefa(1, "Estudar", null); // descrição nula
        assertEquals("", t.getDescricao()); // a classe converte null em ""

    }

    @Test(expected = TarefaException.class) // espera que ESTE método lance a exceção (senão o teste falha)
    public void naoDeveCriarTarefaComTituloVazio() throws TarefaException { // pode lançar exceção
        new Tarefa(1, "   ", "desc"); // título só com espaços deve lançar TarefaException
    }

    @Test(expected = TarefaException.class) // espera a exceção também com título nulo
    public void naoDeveCriarTarefaComTituloNulo() throws TarefaException { // pode lançar exceção
        new Tarefa(1, null, "desc"); // título nulo deve lançar TarefaException
    }

    @Test // teste do setter de concluída
    public void deveMarcarTarefaComoConcluida() throws TarefaException { // pode lançar exceção
        Tarefa t = new Tarefa(1, "Estudar", "desc"); // cria a tarefa pendente
        t.setConcluida(true); // marca como concluída
        assertTrue(t.isConcluida()); // confere se mudou
    }

    @Test(expected = TarefaException.class) // setTitulo também valida o título
    public void naoDeveAceitarTituloVazioNoSetter() throws TarefaException { // pode lançar exceção
        Tarefa t = new Tarefa(1, "Estudar", "desc"); // cria uma tarefa válida
        t.setTitulo(""); // tenta trocar para título vazio: deve lançar exceção
    }
}