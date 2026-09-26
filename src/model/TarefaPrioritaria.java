/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import exceptions.TarefaException;

/**
 *
 * @author Lara
 */
public class TarefaPrioritaria extends Tarefa {
    private Prioridade prioridade;
    
    public TarefaPrioritaria(
    int id,
    String titulo,
    String descricao,
    Prioridade prioridade
   ) throws TarefaException {
     super(id, titulo, descricao);
     if(prioridade == null){
     throw new TarefaException("A prioridade não pode ser nula");
     }
        this.prioridade = prioridade;
    }
    public Prioridade getPrioridade(){
        return prioridade;
}
    public void setPrioridade(Prioridade prioridade){
    this.prioridade = prioridade;
    }
    
   @Override
   public String toString(){
   return super.toString() + "\nPrioridadde: " + prioridade.getDescricao();
   }
    
}
    

