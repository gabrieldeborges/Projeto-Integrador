/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.javafxappPI.telas;

import br.senac.javafxappPI.Pessoa.detalhesBean;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;



import static br.senac.javafxappPI.mock.MockCliente.listaClientes;
import br.senac.javafxappPI.mock.MockDetalhes;
import static br.senac.javafxappPI.mock.MockProduto.listaProdutos;
import java.time.LocalDate;
import static java.time.temporal.TemporalQueries.localDate;


/**
 * FXML Controller class
 *
 * @author LUANA
 */
public class DetalhesController implements Initializable {
    @FXML
    private Label lblNome;
    @FXML
    private Label lblCpf;
    @FXML
    private Label lblTelefone;
    @FXML
    private Label lblValor;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblData;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        detalhesBean del = new detalhesBean();
        
        
        lblNome.setText(del.getNome());
        lblCpf.setText(Integer.toString(del.getCpf()));
        lblValor.setText(Integer.toString(del.getValor()));
        //lblData.setText(LocalDate.now().format(del.getDia().ofPattern("dd/MM/yyyy")));
        
        
        
        
    }    
    
}
