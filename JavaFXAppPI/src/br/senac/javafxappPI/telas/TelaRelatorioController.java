/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.javafxappPI.telas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import br.senac.javafxappPI.Pessoa.RelatorioBean;
import br.senac.javafxappPI.Pessoa.detalhesBean;
import br.senac.javafxappPI.mock.MockDetalhes;
import br.senac.javafxappPI.mock.MockRelatorio;
import br.senac.javafxappPI.mock.MockVenda;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author LUANA
 */
public class TelaRelatorioController implements Initializable {
    @FXML
    private ChoiceBox<String> cbMes;
    @FXML
    private TableView<RelatorioBean> tblRelatorio;
    @FXML
    private TableColumn<RelatorioBean, String> colunaDia;
    @FXML
    private TableColumn<RelatorioBean, String> colunaCliente;
    @FXML
    private TableColumn<RelatorioBean, String> colunaTotal;
    @FXML
    private TextField totalMes;
    @FXML
    private TableColumn<RelatorioBean, String> colunaCpf;

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbMes.getItems().addAll(
        
                
            "Janeiro",
                "Feveveiro",
            "Março",
                "Abril",
                "Maio",
                "Junho",
                "Julho",
                "Agosto",
                "Setembro",
                "Outubro",
            "Novembro",
            "Dezembro"
        );
        
        colunaCliente.setCellValueFactory(
                new PropertyValueFactory("nome")
            );        
        colunaTotal.setCellValueFactory(
                new PropertyValueFactory("valor")
            );
        colunaDia.setCellValueFactory(
                new PropertyValueFactory("data")
            );
        
        colunaCpf.setCellValueFactory(
                new PropertyValueFactory("CPF")
            );
        
        
        
        MockRelatorio.listaRelatorioMostra.clear();
        
        
    }    
int mes=0;
    @FXML
    private void acaoGerar(ActionEvent event) throws Exception {
    
    if(cbMes.getValue() !=null){
        switch(cbMes.getValue()){
        
            
            case "Janeiro":
                mes=1;
                break;
            
                
            case "Fevereiro":
                mes=2;
                break;
                
                
            case "Abril":
                mes=4;
                break;
            
            
            case "Março":
                mes=3;
                break;
            
            
            case "Maio":
                mes=05;
                break;
            
            case "Junho":
                mes=6;
                break;
            
            
            case "Julho":
                mes=7;
                break;
            
            case "Agosto":
                mes=8;
                break;
           
            case "Setembro":
                mes=9;
                break;
            
            
            case "Outubro":
        mes=10;
        break;

        case "Novembro":
            mes=11;
            break;
            
        case "Dezembro":    
    
                mes=12;
            break;
                }
        
        if (mes==11){
            for (int i = 0; i < MockRelatorio.listaRelatorio.size(); i++) {
                
            
                
                if (MockRelatorio.listaRelatorio.get(i).getMes() == mes) {
                    System.out.println("eh igual o mes");
                    RelatorioBean rel = new RelatorioBean();
                    
                rel.setData(MockRelatorio.listaRelatorio.get(i).getData());
                rel.setNome(MockRelatorio.listaRelatorio.get(i).getNome());
                rel.setValor(MockRelatorio.listaRelatorio.get(i).getValor());
                rel.setCPF(MockRelatorio.listaRelatorio.get(i).getCPF());
                MockRelatorio.inserir2(rel);
 
                List mostra = listarPesquisar();

               tblRelatorio.setItems(
                    FXCollections.observableArrayList(mostra)
            ); 
 
             int valorGasto= 0;

                    for (int j = 0; j < MockRelatorio.listaRelatorioMostra.size(); j++) {
                        
             valorGasto += MockRelatorio.listaRelatorioMostra.get(j).getValor();
             
                    }
                    totalMes.setText(Integer.toString(valorGasto)); 
                }
   
            }
            
        }else{
                JOptionPane.showMessageDialog(null, "não houve compras nesse mes");
        }
        
        
    }else{
        JOptionPane.showMessageDialog(null,"Por favor escolher mês");
    }
    
    
 
    }
     public List listarPesquisar() {
        //Obtém os itens do mock
        List resultados;
        try {
            //Se há dados para pesquisa, faz uma busca pelo valor no mock
            //Caso contrário, faz a listagem
                resultados = MockRelatorio.listar();
           
        }
        catch(Exception e) {
            e.printStackTrace();
            resultados = null;
        }
        return resultados;
    }

    private void acaoDetalhe(ActionEvent event) {
        
        
        RelatorioBean rel = tblRelatorio.getSelectionModel().getSelectedItem();
        
        detalhesBean del = new detalhesBean();
        
        if(tblRelatorio.getSelectionModel().getSelectedItem()!=null){
            
            
            del.setNome(rel.getNome());
            del.setCpf(MockDetalhes.getCpf(rel.getIdCliente()));
            del.setValor(rel.getValor());
            del.setDia(rel.getData());
            
            
        }else{
            JOptionPane.showMessageDialog(null,"Por favor escolha um item da tabela");
        }
        
        
    }
    
    
    
}
