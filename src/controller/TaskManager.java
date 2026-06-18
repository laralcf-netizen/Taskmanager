/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import model.Tarefa;
import java.util.Iterator;

/**
 *
 * @author Lara
 */
public class TaskManager {
    private ArrayList<Tarefa> tarefas;
    
    public TaskManager(){
    tarefas = new ArrayList<> ();
    
    }
    
   public void adicionarTarefa(Tarefa tarefa){
       
    tarefas.add(tarefa);
    
    System.out.println("Tarefa adicionada com sucesso!");
   }
   
   public void listarTarefas(){
     if (tarefas.isEmpty()){
     System.out.println("Nenhuma tarefa cadastrada... Adicione uma tarefa!");
     } else{
     for (Tarefa tarefa : tarefas){
     System.out.println(tarefa);
      }
}
   }
   
    public void concluirTarefa(int id){
     
     boolean encontrada = false;
     
     for (Tarefa tarefa : tarefas) {
     
      if(tarefa.getId()== id){
      
          tarefa.setConcluida(true);
          
          encontrada = true;
          
          if (encontrada == false) {
          System.out.println("Tarefa concluida!");
      }
     }
    }
}
    public void removerTarefa(int id) {
    Iterator<Tarefa> iterator = tarefas.iterator();
    boolean encontrada = false;

    while (iterator.hasNext()) {
        Tarefa tarefa = iterator.next();
        if (tarefa.getId() == id) {
            iterator.remove();
            encontrada = true;
            System.out.println("Removida!");
            break;
        }
    }

    if (encontrada == false) {
        System.out.println("Tarefa não encontrada!");
    }
}
}