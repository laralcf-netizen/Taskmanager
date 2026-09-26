/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Lara
 */
public enum Prioridade {
    BAIXA("Baixa"),
    MEDIA("Media"),
    ALTA("Alta");
    
    private final String descricao;
    
    Prioridade (String descricao) {
        this.descricao = descricao;
    }
    public String getDescricao(){
    return descricao;
    }
    
}
