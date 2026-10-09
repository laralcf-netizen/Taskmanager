package app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TaskManagerFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("TaskManager - Interface Gráfica");

        // Componentes visuais simples e modernos
        Label tituloLabel = new Label("Bem-vindo ao TaskManager!");
        tituloLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button btnListar = new Button("Atualizar e Listar Tarefas");
        Label statusLabel = new Label("Estado: Pronto para conectar ao Banco de Dados.");

        btnListar.setOnAction(e -> {
            statusLabel.setText("Estado: Ligação ao SQLite bem-sucedida!");
        });

        // Layout vertical (VBox)
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(tituloLabel, btnListar, statusLabel);

        // Cena e Janela
        Scene scene = new Scene(vbox, 400, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
