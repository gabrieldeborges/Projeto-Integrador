/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.javafxappPI.telas;

import br.senac.javafxappPI.Pessoa.Cliente;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import br.senac.javafxappPI.Pessoa.RelatorioBean;
import br.senac.javafxappPI.Pessoa.detalhesBean;
import br.senac.javafxappPI.mock.ConnectionUtils;
import br.senac.javafxappPI.mock.MockDetalhes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
//import br.senac.javafxappPI.mock.MockVenda;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
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

    List<RelatorioBean> listaVendas = null;
    @FXML
    private TableColumn<RelatorioBean, String> colunaID;
    @FXML
    private AnchorPane colunaId;

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

        colunaID.setCellValueFactory(
                new PropertyValueFactory("idVenda")
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
                new PropertyValueFactory("idCliente")
        );

    }
    int mes = 0;

    @FXML
    private void acaoGerar(ActionEvent event) throws Exception {

        
        if (cbMes.getValue() != null) {
            switch (cbMes.getValue()) {

                case "Janeiro":
                    mes = 1;
                    break;

                case "Fevereiro":
                    mes = 2;
                    break;

                case "Abril":
                    mes = 4;
                    break;

                case "Março":
                    mes = 3;
                    break;

                case "Maio":
                    mes = 05;
                    break;

                case "Junho":
                    mes = 6;
                    break;

                case "Julho":
                    mes = 7;
                    break;

                case "Agosto":
                    mes = 8;
                    break;

                case "Setembro":
                    mes = 9;
                    break;

                case "Outubro":
                    mes = 10;
                    break;

                case "Novembro":
                    mes = 11;
                    break;

                case "Dezembro":

                    mes = 12;
                    break;
            }

            if (mes == 11) {

                String sql = "SELECT * FROM venda WHERE MONTH(data_venda) = '11'";

                Connection connection = null;
                PreparedStatement preparedStatement = null;
                ResultSet result = null;

                connection = ConnectionUtils.getConnection();

                preparedStatement = connection.prepareStatement(sql);
                //preparedStatement.setInt(1, 11);

                result = preparedStatement.executeQuery();

                if (result.next() != false) {

                    while (result.next()) {

                        //Se a lista não foi inicializada, a inicializa
                        if (listaVendas == null) {
                            listaVendas = new ArrayList<RelatorioBean>();
                        }

                        //Cria uma instância de Cliente e popula com os valores do BD
                        RelatorioBean rel = new RelatorioBean();
                        
                        rel.setData(result.getDate("data_venda").toLocalDate());
                        
                        rel.setIdVenda(result.getInt("id_venda"));
                        rel.setNome(result.getString("nome_cliente"));
                        rel.setIdCliente(result.getInt("id_cliente"));
                        rel.setValor(result.getInt("total_gasto"));

                        listaVendas.add(rel);

                        List mostra = listaVendas;

                        tblRelatorio.setItems(
                                FXCollections.observableArrayList(mostra)
                        );

                    }
                    result.close();

                    preparedStatement.close();
                    connection.close();
                    int valorTotal = 0;
                    for (int i = 0; i < listaVendas.size(); i++) {
                        valorTotal += listaVendas.get(i).getValor();

                    }
                    totalMes.setText(Integer.toString(valorTotal));

                } else {
                    JOptionPane.showMessageDialog(null, "não houve compras nesse mes");
                }

            }

            if (mes == 12) {
                String sql = "SELECT * FROM venda WHERE MONTH(data_venda) = '12'";

                Connection connection = null;
                PreparedStatement preparedStatement = null;
                ResultSet result = null;

                connection = ConnectionUtils.getConnection();

                preparedStatement = connection.prepareStatement(sql);
                //preparedStatement.setInt(1, 11);

                result = preparedStatement.executeQuery();

                if (result.next() != false) {

                    while (result.next()) {

                        //Se a lista não foi inicializada, a inicializa
                        if (listaVendas == null) {
                            listaVendas = new ArrayList<RelatorioBean>();
                        }

                        //Cria uma instância de Cliente e popula com os valores do BD
                        RelatorioBean rel = new RelatorioBean();

                        rel.setData(result.getDate("data_venda").toLocalDate());
                        
                        rel.setIdVenda(result.getInt("id_venda"));
                        rel.setNome(result.getString("nome_cliente"));
                        rel.setIdCliente(result.getInt("id_cliente"));
                        rel.setValor(result.getInt("total_gasto"));

                        listaVendas.add(rel);

                        List mostra = listaVendas;

                        tblRelatorio.setItems(
                                FXCollections.observableArrayList(mostra)
                        );

                    }
                    result.close();

                    preparedStatement.close();
                    connection.close();
                    int valorTotal = 0;
                    for (int i = 0; i < listaVendas.size(); i++) {
                        valorTotal += listaVendas.get(i).getValor();

                    }
                    totalMes.setText(Integer.toString(valorTotal));

                } else {
                    JOptionPane.showMessageDialog(null, "não houve compras nesse mes");
                }

            }

        } else {
            JOptionPane.showMessageDialog(null, "Por favor escolher mês");
        }
    }

    @FXML
    private void acaoDetalhe(ActionEvent event) {

        RelatorioBean relatorio = tblRelatorio.getSelectionModel().getSelectedItem();
        
        
        
        

    }

}
