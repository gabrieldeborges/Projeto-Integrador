package br.senac.javafxappPI.aplicacao;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Aplicacao extends Application {

    public void start(Stage stage) throws Exception {
        Parent telaCadastro = FXMLLoader.load(
                getClass().getResource(
                        "/br/senac/javafxappPI/telas/TelaLogin.fxml"
                )
        );
        
        Scene scene = new Scene(telaCadastro);
        
        stage.setScene(scene);
        stage.setTitle("Tela Cliente");
        stage.setMinHeight(438);
        stage.setMinWidth(622);
        
        stage.show();
    }   
    
}