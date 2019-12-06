package br.senac.javafxappPI.telas;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class TelaInicialController implements Initializable {

    @FXML
    private Button BTCliente;
    @FXML
    private Button BTOutros;
    @FXML
    private Button BTCliente1;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ClienteBT(ActionEvent event) throws IOException {
        Stage primaryStage = new Stage();
        
        Parent telaCliente = FXMLLoader.load(
                getClass().getResource(
                        "/br/senac/javafxappPI/telas/TelaCentral.fxml"
                )
        );
        
        Scene scene = new Scene(telaCliente);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void OutrosBT(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        
        Parent TelaProdutos = FXMLLoader.load(
                getClass().getResource(
                        "/br/senac/javafxappPI/telas/TelaProdutos.fxml"
                )
        );
        
        Scene scene = new Scene(TelaProdutos);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void VENDAABRE(ActionEvent event) throws IOException {
        
        
        Stage stage = new Stage();
        
        Parent TelaVenda = FXMLLoader.load(
                getClass().getResource(
                        "/br/senac/javafxappPI/telas/TelaVenda.fxml"
                )
        );
        
        Scene scene = new Scene(TelaVenda);
        stage.setScene(scene);
        stage.show();
        
        
    }

    @FXML
    private void abreRela(ActionEvent event) throws IOException {

        Stage stage = new Stage();
        
        Parent TelaRelatorio = FXMLLoader.load(
                getClass().getResource(
                        "/br/senac/javafxappPI/telas/TelaRelatorio.fxml"
                )
        );
        
        Scene scene = new Scene(TelaRelatorio);
        stage.setScene(scene);
        stage.show();
    
    
    }
    
}
