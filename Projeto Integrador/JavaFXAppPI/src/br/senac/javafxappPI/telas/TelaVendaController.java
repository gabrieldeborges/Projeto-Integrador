package br.senac.javafxappPI.telas;

import br.senac.javafxappPI.Pessoa.ProdutosBean;
import br.senac.javafxappPI.Pessoa.VendaBean;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import br.senac.javafxappPI.Pessoa.RelatorioBean;
import br.senac.javafxappPI.mock.ConnectionUtils;
import java.util.Date;
import java.util.*;
import java.text.*;

import java.util.Date;

import br.senac.javafxappPI.mock.DaoCliente;
import br.senac.javafxappPI.mock.DaoProdutos;
//import br.senac.javafxappPI.mock.MockVenda;

import br.senac.javafxappPI.mock.DaoProdutos;
import br.senac.javafxappPI.mock.DaoVenda;
import java.net.URL;
import java.sql.Connection;
//import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class TelaVendaController implements Initializable {

    private javafx.scene.control.Button fecharTela;

    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtProduto;
    @FXML
    private TextField txtQuantidade;
    @FXML
    private TableView<VendaBean> tableVenda;
    @FXML
    private TextField txtCpf;
    @FXML
    private TextField txtNomeCliente;
    @FXML
    private TextField txtValorTotal;
    private TableColumn<VendaBean, Integer> ColunaCodigo;
    @FXML
    private TableColumn<VendaBean, String> ColunaProduto;

    int idProduto = 0;
    int idCliente = 0;
    @FXML
    private DatePicker dpDataVenda;
    @FXML
    private TableColumn<VendaBean, Integer> colunaPreco;
    @FXML
    private TableColumn<VendaBean, Integer> colunaQuantidade;
    @FXML
    private TableColumn<VendaBean, Integer> colunaSub;
    @FXML
    private Button BTConfirma;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        DaoVenda.listaVenda.clear();
        dpDataVenda.setValue(hoje());

        try {
            List listaProdutos = DaoProdutos.listar();
            List listaClientes = DaoCliente.listar();

            System.out.println("INICIO");

            colunaPreco.setCellValueFactory(
                    new PropertyValueFactory("preco")
            );
            colunaQuantidade.setCellValueFactory(
                    new PropertyValueFactory("quantidade")
            );

            ColunaProduto.setCellValueFactory(
                    new PropertyValueFactory("produto")
            );

            colunaSub.setCellValueFactory(
                    new PropertyValueFactory("precohora")
            );

//limpra a lista de venda após cada compra e manda as que foram feitas para o relatorio
            //MockVenda.listaVenda.clear();
            if (listaProdutos.isEmpty()) {

                JOptionPane.showMessageDialog(null, "Não há produtos no estqoue para realizar a venda");

                Stage stage = (Stage) btnFechar.getScene().getWindow();
                stage.close();

            }

            if (listaClientes.isEmpty()) {

                JOptionPane.showMessageDialog(null, "Favor cadastrar o cliente para realizar a compra");

                Stage stage = (Stage) btnFechar.getScene().getWindow();
                stage.close();

            }
        } catch (Exception ex) {
            Logger.getLogger(TelaVendaController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    private Button btnFechar;

    private void fechar() {
        Stage stage = (Stage) btnFechar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void acaoPesquisa(ActionEvent event) throws SQLException, Exception {
        int codigo = Integer.parseInt(txtCodigo.getText());
        String sql = "Select * from produto Where id_produto = ?;";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        connection = ConnectionUtils.getConnection();

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, codigo);

        result = preparedStatement.executeQuery();
        if ((result.next() == false)) {
            JOptionPane.showMessageDialog(null, "Codigo do produto não corresponde a nenhum produto");
        } else {
            txtProduto.setText(result.getString("nome_produto"));

            result.close();

            //Fecha o statement
            preparedStatement.close();

            //Fecha a conexão
            connection.close();
        }
    }

    @FXML
    private void acaoAdicionar(ActionEvent event) throws Exception {
//adiciona produto no carrinho
        int codigo = Integer.parseInt(txtCodigo.getText());
        String sql = "Select * from produto Where id_produto = ?;";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        connection = ConnectionUtils.getConnection();

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, codigo);

        result = preparedStatement.executeQuery();
        result.next();

        int quantidade = (result.getInt("quantidade_produto"));

        int quantidadeDigitada = Integer.parseInt(txtQuantidade.getText());

        //verifica se a quantidade requerida é valida
        if ((quantidade - quantidadeDigitada) >= 1) {
            VendaBean venda = new VendaBean();

            venda.setQuantidade(quantidadeDigitada);
            venda.setIdProduto(result.getInt("id_produto"));
            venda.setPreco(result.getDouble("preco_produto"));
            venda.setPrecohora(quantidadeDigitada * (result.getInt("preco_produto")));

            venda.setProduto(result.getString("nome_produto"));

            venda.setPrecohora(result.getInt("preco_produto") * Integer.parseInt(txtQuantidade.getText()));
            DaoVenda.inserirHora(venda);

            result.close();

            preparedStatement.close();
            connection.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erro");
            alert.setContentText("A quantidade não está disponivel em estoque. A quantidade disponivel"
                    + " é de " + quantidade);
            alert.showAndWait();

        }
//coloca o item na tabela de venda
        tableVenda.getItems().clear();

        //Obtém os resultados de pesquisa do mock
        List resultados = DaoVenda.listarHora();

        //Se há resultados, atualiza a tabela
        if (resultados != null) {
            tableVenda.setItems(
                    FXCollections.observableArrayList(resultados)
            );
        }

        //guarda a soma dos precos
        int preco = 0;
        for (int j = 0; j < DaoVenda.listaVenda.size(); j++) {
            System.out.println("sai");

            if (j == 0) {
                preco = DaoVenda.listaVenda.get(j).getPrecohora();
            } else {
                //continuar somando até chegar ao produto 1
                int k = j;

                while (k != 0) {
                    System.out.println("sai logo");
                    preco = DaoVenda.listaVenda.get(j).getPrecohora() + DaoVenda.listaVenda.get(j - 1).getPrecohora();
                    k--;
                }

            }

        }
        txtValorTotal.setText(Integer.toString((preco)));

        //limpando os campos
        txtCodigo.setText("");
        txtProduto.setText("");
        txtQuantidade.setText("");

    }

    public static final LocalDate hoje() {
        String date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate;
    }

    @FXML
    private void acaoConfirmar(ActionEvent event) throws Exception {
        //Acão que realiza a compra
        //adiciona um 0 no final da lista do carrinho para consulta a consulta
        
        
        
        
        
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        
        
            String ultimo = "select max(id_venda) from venda";

                preparedStatement = connection.prepareStatement(ultimo);

                result = preparedStatement.executeQuery();
                result.next();

                int id_venda = (result.getInt("max(id_venda)"));
                result.close();
                preparedStatement.close();
                connection.close();

        
        
        
        
        
        
        connection = ConnectionUtils.getConnection();

        VendaBean venda = new VendaBean();

//        TERMINAR DIMINUIR QUANTIDADE NO PRODUTO
        
        //asdasdsa
        if (txtValorTotal != null) {
            int dialogButton = 0;

            dialogButton = JOptionPane.showConfirmDialog(null, "Deseja realizar a compra no valor de :" + txtValorTotal.getText(), "Warning", dialogButton);

            if (dialogButton == JOptionPane.YES_OPTION) {

                String sql = "INSERT INTO item_venda (id_produto,id_venda,qtd) "
                        + " VALUES (?, ?, ?)";

                connection = ConnectionUtils.getConnection();

                RelatorioBean relatorio = new RelatorioBean();

                relatorio.setValor(Integer.parseInt(txtValorTotal.getText()));

                relatorio.setNome(txtNomeCliente.getText());
                
                //relatorio.setIdProduto(MockVenda.getIdproduto(idProduto));
                relatorio.setIdCliente(idCliente);
                relatorio.setIdVenda(id_venda);
                //manda a data
                relatorio.setData(dpDataVenda.getValue());

                //relatorio.setMes(dpDataVenda.getValue().getMonthValue());
                //relatorio.setCPF(Integer.parseInt(txtCpf.getText()));
                DaoVenda.inserir(relatorio);

                
                int id_p = 0;
                //insere na tabela item_venda
                for (int i = 0; i < DaoVenda.listaVenda.size(); i++) {
                    id_p = DaoVenda.listaVenda.get(i).getIdProduto();
                    String p = "insert into item_venda (id_venda, id_produto, quantidade) Values(?,?,?)";
                    connection = ConnectionUtils.getConnection();

                    preparedStatement = connection.prepareStatement(p);

                    preparedStatement.setInt(1, id_venda);
                    preparedStatement.setInt(2, id_p);
                    preparedStatement.setInt(3, DaoVenda.listaVenda.get(i).getQuantidade());
                    preparedStatement.execute();

                }
                //coloca novo valor no BD
                int quantidadeProduto = 0;
        for (int i = 0; i < DaoVenda.listaVenda.size(); i++) {
            //pega a quantidade do produto
            String pegaQtd = "Select quantidade_produto from produto where id_produto=?";
                    

            connection = ConnectionUtils.getConnection();

            preparedStatement = connection.prepareStatement(pegaQtd);
            preparedStatement.setInt(1, DaoVenda.listaVenda.get(i).getIdProduto());
            
            result = preparedStatement.executeQuery();
            result.next();
            
            int quantidadeBD = (result.getInt("quantidade_produto"));
            
            quantidadeProduto = DaoVenda.listaVenda.get(i).getQuantidade();
            
            quantidadeBD=quantidadeBD-quantidadeProduto;
            
            result.close();
            preparedStatement.close();

            //atualiza o bd com o novo valor da quantidade
            String UPDATE ="UPDATE produto SET quantidade_produto=? WHERE (id_produto=?)";

            connection = ConnectionUtils.getConnection();
            preparedStatement = null;
            preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setInt(1, quantidadeBD);
            preparedStatement.setInt(2, DaoVenda.listaVenda.get(i).getIdProduto());
            
            preparedStatement.execute();
            preparedStatement.close();
        }

                
                    result.close();
                    preparedStatement.close();
                    connection.close();

                JOptionPane.showMessageDialog(null, "Feito");

                Stage stage = (Stage) BTConfirma.getScene().getWindow();
                stage.close();
                //clausula se nao houver produto ou cliente
            } else {

            }
        } //se não houver produtos no carrinho
        //fechando o caso do JOption
        else {
            JOptionPane.showMessageDialog(null, "Não há produtos no carrinho");

        }

    }

    @FXML
    private void acaoCancelar(ActionEvent event) {

    }

    @FXML
    private void acaoRemover(ActionEvent event) {
        VendaBean venda = tableVenda.getSelectionModel().getSelectedItem();

        //Se há um item selecionado, habilita a edição e grava os respectivos
        //dados na variável clienteEdicao para uso posterior
        if (venda != null) {
            //Monta e exibe um diálogo de confirmação de exclusão
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Excluir Produto");
            alert.setContentText("Excluir o produto " + venda.getProduto());

            //Mostra o diálogo esperando um resultado
            Optional<ButtonType> result = alert.showAndWait();

            //Se o resultado for afirmativo, exclui o cliente
            if (result.get() == ButtonType.OK) {
                //Exclui o cliente e atualiza a tabela
                try {

                    excluir(venda);
                    //   List resultados = MockVenda.listar();

                    List resultados = DaoVenda.listar();

                    if (resultados != null) {
                        tableVenda.setItems(
                                FXCollections.observableArrayList(resultados)
                        );
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Alert alertErro = new Alert(Alert.AlertType.ERROR);
                    alertErro.setTitle("Erro");
                    alertErro.setContentText("Ocorreu um erro ao excluir"
                            + " o produto");
                    alertErro.showAndWait();
                }
            }

        }

    }

    private void excluir(VendaBean venda) throws Exception {
        //     MockVenda.excluir(venda.getId());
    }

    @FXML

    //achar o cliente
    private void acaoConfirma(ActionEvent event) throws Exception {

        int cpf = Integer.parseInt(txtCpf.getText());
        String sql = "Select * from cliente Where cpf_cliente = ?;";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        connection = ConnectionUtils.getConnection();

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, cpf);

        result = preparedStatement.executeQuery();
        result.next();
        System.out.println(result.getString("nome_cliente"));
        txtNomeCliente.setText(result.getString("nome_cliente"));

        idCliente = result.getInt("id_cliente");
        System.out.println(idCliente);
        result.close();

        //Fecha o statement
        preparedStatement.close();

        //Fecha a conexão
        connection.close();

//        
//        if (txtNomeCliente.getText() != null && dpDataVenda != null) {
//
//            for (int i = 0; i <listaClientes.size(); i++) {
//
//                if (valor != "0" && !listaClientes.isEmpty()) {
//                    {
//                        if (listaClientes != null) {
//
////guarda na variavel compara o que esta na lista                    
//                            String compara = listaClientes.get(i).getCpf();
//
//                            //comprar o valor que foi digitado com o que esta na lista
//                            if (compara == valor) {
//                                
//                                
//                                txtNomeCliente.setText(listaClientes.get(i).getNome());
//
//                                
//                            }
//                        }
//                    }
//                }
//
//            }
//        } else {
//            JOptionPane.showMessageDialog(null, "Favor escolher  um cliente e uma data");
//
//        }
    }

    private void fecharTelaAction() {
        Stage stage = (Stage) fecharTela.getScene().getWindow(); //Obtendo a janela atual
        stage.close(); //Fechando o Stage
    }
}
