/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import database.ConexaoBD;
import exceptions.TarefaException;
import java.util.ArrayList;
import model.Prioridade;
import model.Tarefa;
import model.TarefaPrioritaria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Lara
 */
public class TarefaDAO {

    public TarefaDAO() {
        criarTabelaSeNaoExistir();
    }

    private void criarTabelaSeNaoExistir() {
        String sql = "CREATE TABLE IF NOT EXISTS tarefas ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "titulo TEXT NOT NULL, "
                + "descricao TEXT, "
                + "concluida INTEGER DEFAULT 0, "
                + "prioridade TEXT DEFAULT 'MEDIA')";

        try (Connection conn = ConexaoBD.conectar(); java.sql.Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela no SQLite: " + e.getMessage());
        }
    }

    public void inserir(Tarefa tarefa) throws SQLException {
        String sql = "INSERT INTO tarefas (titulo, descricao, concluida, prioridade) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tarefa.getTitulo());
            stmt.setString(2, tarefa.getDescricao());
            stmt.setBoolean(3, tarefa.isConcluida());
            
            if (tarefa instanceof TarefaPrioritaria) {
                Prioridade p = ((TarefaPrioritaria) tarefa).getPrioridade();
                stmt.setString(4, p != null ? p.name() : "MEDIA");
            } else {
                stmt.setString(4, "MEDIA");
            }
           
            stmt.executeUpdate();
            System.out.println("SUCESSO: Tarefa guardada na base de dados!"); 
        } catch (SQLException e) {
             throw new SQLException("ERRO ao inserir tarefa no SQLite: " + e.getMessage());
        }
    }

    public List<Tarefa> listar() throws SQLException, TarefaException {
        List<Tarefa> lista = new ArrayList<>();
        String sql = "SELECT id, titulo, descricao, concluida, prioridade FROM tarefas";
        
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idDb = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String descricao = rs.getString("descricao");
                boolean concluida = rs.getBoolean("concluida");
                String prioridadeStr = rs.getString("prioridade");

                Tarefa tarefa;
                if (prioridadeStr != null && !prioridadeStr.isEmpty()) {
                    Prioridade prioridade = Prioridade.valueOf(prioridadeStr);
                    tarefa = new TarefaPrioritaria(idDb, titulo, descricao, prioridade);
                } else {
                    tarefa = new Tarefa(idDb, titulo, descricao);
                }

                tarefa.setConcluida(concluida);
                lista.add(tarefa);
            }

        } catch (SQLException e) {
            throw new SQLException("Erro ao listar tarefas da base de dados: " + e.getMessage());
        }

        return lista;
    }

    public void concluirTarefa(int id) throws SQLException {
        String sql = "UPDATE tarefas SET concluida = 1 WHERE id = ?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("SUCESSO: Tarefa marcada como concluída na base de dados!");
            } else {
                System.out.println("AVISO: Nenhuma tarefa encontrada com o ID informado.");
            }

        } catch (SQLException e) {
            throw new SQLException("Erro ao concluir tarefa no SQLite: " + e.getMessage());
        }
    }

    public void remover(int id) throws SQLException {
        String sql = "DELETE FROM tarefas WHERE id = ?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("SUCESSO: Tarefa removida da base de dados!");
            } else {
                System.out.println("AVISO: Nenhuma tarefa encontrada com o ID informado para remoção.");
            }

        } catch (SQLException e) {
            throw new SQLException("Erro ao remover tarefa no SQLite: " + e.getMessage());
        }
    }
}