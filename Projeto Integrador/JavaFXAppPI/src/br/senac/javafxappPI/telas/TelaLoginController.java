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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;





public class TelaLoginController implements Initializable {

    @FXML
    private PasswordField PFSenha;
    @FXML
    private TextField TFUser;
    @FXML
    private Button BTLogin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
     @FXML
    private void Login(ActionEvent event) throws IOException {
        String name = TFUser.getText();
        String senha = PFSenha.getText();
        
       if(name.equals("gerente")&&senha.equals("gerente")){
        Stage primaryStage = new Stage();
        
        Parent telaInicial = FXMLLoader.load(
                getClass().getResource(
                        "/br/senac/javafxappPI/telas/TelaDoGerente.fxml"
                )
        );
         Scene scene = new Scene(telaInicial);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        Stage stage = (Stage) BTLogin.getScene().getWindow();
        stage.close();
       } 
        
      else if(name.equals("funcionario")&&senha.equals("funcionario")){
        Stage primaryStage = new Stage();
        
        Parent telaFuncionario = FXMLLoader.load(
                getClass().getResource(
                        "/br/senac/javafxappPI/telas/TelaVendedor.fxml"
                )
        );
         Scene scene = new Scene(telaFuncionario);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        Stage stage = (Stage) BTLogin.getScene().getWindow();
        stage.close();
       }
         
       else if(name.equals("estoquista")&&senha.equals("estoquista")){
        Stage primaryStage = new Stage();
        
        Parent telaProdutos = FXMLLoader.load(
                getClass().getResource(
                        "/br/senac/javafxappPI/telas/TelaProdutos.fxml"
                )
        );
         Scene scene = new Scene(telaProdutos);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        Stage stage = (Stage) BTLogin.getScene().getWindow();
        stage.close();
       }
         
       else
       {
             Alert alertErro = new Alert(Alert.AlertType.ERROR);
                    alertErro.setTitle("Erro ao Logar");
                    alertErro.setContentText("O Usuário ou Senha não conferem");
                    alertErro.showAndWait();
         }
        
        
    }
    
}
