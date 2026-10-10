package app; // mesmo pacote do Main e do TaskManagerFX

public class Launcher { // classe nova, só para iniciar o JavaFX
    public static void main(String[] args) { // ponto de entrada: o Java começa a executar por aqui
        javafx.application.Application.launch(TaskManagerFX.class, args); // chama a sua tela gráfica (TaskManagerFX)
    }
}