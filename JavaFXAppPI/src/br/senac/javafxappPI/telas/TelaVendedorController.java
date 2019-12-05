/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 * FXML Controller class
 *
 * @author gabriel.bfreitas1
 */
public class TelaVendedorController implements Initializable {

     @FXML
    private Button BTRelatorio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
     @FXML
    private Button BTVenda;
    @FXML
    private Button BTProdutos;
    @FXML
    private Button BTUsuario;


    @FXML
    private void UsuarioBT(ActionEvent event) throws IOException {
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
    private void ProdutosBT(ActionEvent event) throws IOException {
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

    private void VendasBT(ActionEvent event) throws IOException {
        
        
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
    private void RelatorioBT(ActionEvent event) throws IOException {

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

    @FXML
    private void VendaBT(ActionEvent event) {
    }
    
    
}
