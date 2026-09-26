/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import exceptions.TarefaException;
import java.util.ArrayList;
import model.Tarefa;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Lara
 * @param <T>
 */
public class TaskManager <T extends Tarefa>  { //T representa o tipo generico da lista 
    private final List<T> tarefas;
    
    public TaskManager(){
    this.tarefas = new ArrayList<>();
    
    }
    
   public void adicionarTarefa(T tarefa) throws TarefaException { //throws serve para declarar na assinatura de um método que ele pode vir a lançar uma ou mais exceções para quem o chamou.
       if (tarefa == null){
       throw new TarefaException ("Tarefa não pode ser vazia"); // throw serve para lançar (disparar) uma exceção explicitamente dentro do código
       }    
       tarefas.add(tarefa); //adiciona a lista
   }
   
   public void listarTarefas(){
     if (tarefas.isEmpty()){
     System.out.println("Nenhuma tarefa cadastrada... Adicione uma tarefa!");
     } else{
     for (T tarefa : tarefas){
     System.out.println(tarefa);
      }
}
   }
   
    public void concluirTarefa(int id) throws TarefaException{
     for (T tarefa : tarefas) {
        if(tarefa.getId()== id){
           tarefa.setConcluida(true);
           return;
    }
}
     throw new TarefaException("Tarefa com id " + id + " não encontrada!");
    }
    
    public void removerTarefa(int id) throws TarefaException {
    Iterator<T> iterator = tarefas.iterator();
    while (iterator.hasNext()) {
        T tarefa = iterator.next();
        if (tarefa.getId() == id) {
            iterator.remove();
            return;
        }
    }
    throw new TarefaException("Tarefa com o id " + id + " não encontrada.");
    }
}
