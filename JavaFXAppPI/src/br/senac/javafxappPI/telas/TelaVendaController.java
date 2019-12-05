package br.senac.javafxappPI.telas;

import br.senac.javafxappPI.Pessoa.ProdutosBean;
import br.senac.javafxappPI.Pessoa.VendaBean;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import br.senac.javafxappPI.Pessoa.RelatorioBean;

import br.senac.javafxappPI.mock.MockProduto;

import br.senac.javafxappPI.mock.MockCliente;
import static br.senac.javafxappPI.mock.MockCliente.listaClientes;
import static br.senac.javafxappPI.mock.MockProduto.listaProdutos;
import br.senac.javafxappPI.mock.MockRelatorio;
import br.senac.javafxappPI.mock.MockVenda;

import java.net.URL;

import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
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
        MockVenda.listaVenda.clear();

        if (MockProduto.listaProdutos.isEmpty()) {

            JOptionPane.showMessageDialog(null, "Não há produtos no estqoue para realizar a venda");

            Stage stage = (Stage) btnFechar.getScene().getWindow();
            stage.close();

        }
        if (MockCliente.listaClientes.isEmpty()) {

            JOptionPane.showMessageDialog(null, "Favor cadastrar o cliente para realizar a compra");

            Stage stage = (Stage) btnFechar.getScene().getWindow();
            stage.close();

        }

    }
    private Button btnFechar;

    private void fechar() {
        Stage stage = (Stage) btnFechar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void acaoPesquisa(ActionEvent event) {

        String aux = txtCodigo.getText();
        int valor = Integer.parseInt(aux);

        for (int i = 0; i < listaProdutos.size(); i++) {

            if (valor != 0 && !MockProduto.listaProdutos.isEmpty()) {
                {
                    if (listaProdutos != null) {

//guarda na variavel compara o que esta na lista                    
                        int compara = listaProdutos.get(i).getId();

                        //comprar o valor que foi digitado com o que esta na lista
                        if (compara == valor) {
                            //joga o nome correspondente ao codigo
                            txtProduto.setText(listaProdutos.get(i).getNome());

                        }
                    }
                }
            }

        }

    }

    @FXML
    private void acaoAdicionar(ActionEvent event) throws Exception {

        VendaBean venda = new VendaBean();

        //venda.setId(Integer.parseInt(txtCodigo.getText()));
        int cod = Integer.parseInt(txtCodigo.getText());

        int i = 0;
        for (int W; i < listaProdutos.size(); i++) {
            //verifica se o id é igual ao que é digitado se for, adiciona ao bean

            //pega o index do produto, e o id produto seria isso mais um
            if (listaProdutos.get(i).getId() == cod) {
                //guarda o id do qual ele foi tirado;
                idProduto = i;

                int qtd = listaProdutos.get(idProduto).getQuantidade();

//verifica se há em estoque a quantidade
                if (qtd >= Integer.parseInt(txtQuantidade.getText())) {

                    venda.setProduto(txtProduto.getText());
                    venda.setQuantidade(Integer.parseInt(txtQuantidade.getText()));

                    venda.setPreco(listaProdutos.get(i).getPreco());

                    System.out.println(listaProdutos.get(i).getPreco());

                    venda.setPrecohora((listaProdutos.get(i).getPreco()) * Integer.parseInt(txtQuantidade.getText()));
                    MockVenda.inserir(venda);
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Erro");
                    alert.setContentText("A quantidade não está disponivel em estoque. A quantidade disponivel é de" + MockProduto.listaProdutos.get(i).getQuantidade());
                    alert.showAndWait();
                }

            }
        }

        tableVenda.getItems().clear();

        //Obtém os resultados de pesquisa do mock
        List resultados = MockVenda.listar();

        //Se há resultados, atualiza a tabela
        if (resultados != null) {
            tableVenda.setItems(
                    FXCollections.observableArrayList(resultados)
            );
        }
        //guarda a soma dos precos
        int preco = 0;
        for (int j = 0; j < MockVenda.listaVenda.size(); j++) {
            System.out.println("sai");

            if (j == 0) {
                preco = MockVenda.listaVenda.get(j).getPrecohora();
            } else {
                //continuar somando até chegar ao produto 1
                int k = j;

                while (k != 0) {
                    System.out.println("sai logo");
                    preco = MockVenda.listaVenda.get(j).getPrecohora() + MockVenda.listaVenda.get(j - 1).getPrecohora();
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

    @FXML
    private void acaoConfirmar(ActionEvent event) throws Exception {
        //Acão que realiza a compra

        System.out.println(idProduto);
        int qtd = listaProdutos.get(idProduto).getQuantidade();

        //pga a quantidade digitada no carrinho
        int subtraiqtd = 0;

        for (int r = 0; r < MockVenda.listaVenda.size(); r++) {

            if (MockVenda.listaVenda.get(r).getId() == MockProduto.listaProdutos.get(idProduto).getId()) {
                subtraiqtd = MockVenda.listaVenda.get(r).getQuantidade();
                System.out.println("essa é a quantia sub" + subtraiqtd);
            }

        }

        int dialogButton = 0;

        if (txtValorTotal != null) {

            JOptionPane.showConfirmDialog(null, "Deseja realizar a compra no valor de :" + txtValorTotal.getText(), "Warning", dialogButton);

            if (dialogButton == JOptionPane.YES_OPTION) {

                if ((qtd - subtraiqtd) >= 0) {

                    RelatorioBean relatorio = new RelatorioBean();

                    relatorio.setValor(Integer.parseInt(txtValorTotal.getText()));

                    relatorio.setNome(txtNomeCliente.getText());

                    relatorio.setIdProduto(MockVenda.getIdproduto(idProduto));

                    relatorio.setIdCliente(MockVenda.getIdcli(idCliente));

                    relatorio.setValor(Integer.parseInt(txtValorTotal.getText()));

                    //manda a data
                    relatorio.setData(dpDataVenda.getValue());

                    relatorio.setMes(dpDataVenda.getValue().getMonthValue());

                    System.out.println(txtCpf.getText());

                    relatorio.setCPF(Integer.parseInt(txtCpf.getText()));

                    System.out.println(qtd);
                    System.out.println(subtraiqtd);
                    MockProduto.listaProdutos.get(idProduto).setQuantidade(qtd - subtraiqtd);
                    MockProduto.listar();

                    MockRelatorio.inserir(relatorio);

                    JOptionPane.showMessageDialog(null, "Feito");

                    Stage stage = (Stage) BTConfirma.getScene().getWindow();
                    stage.close();
                    //clausula se nao houver produto ou cliente
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Erro");
                    alert.setContentText("Quantidade insuficiente no estoque");
                    alert.showAndWait();
                }
            }

            //se não houver produtos no carrinho
            //fechando o caso do JOption
        } else {
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
                    List resultados = MockVenda.listar();

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
        MockVenda.excluir(venda.getId());
    }

    @FXML

    //achar o cliente
    private void acaoConfirma(ActionEvent event) {
        String aux = txtCpf.getText();
        int valor = Integer.parseInt(aux);

        if (txtNomeCliente.getText() != null && dpDataVenda != null) {

            for (int i = 0; i < MockCliente.listaClientes.size(); i++) {

                if (valor != 0 && !MockCliente.listaClientes.isEmpty()) {
                    {
                        if (MockCliente.listaClientes != null) {

//guarda na variavel compara o que esta na lista                    
                            int compara = MockCliente.listaClientes.get(i).getCpf();

                            //comprar o valor que foi digitado com o que esta na lista
                            if (compara == valor) {

                                txtNomeCliente.setText(listaClientes.get(i).getNome());

                            }
                        }
                    }
                }

            }
        } else {
            JOptionPane.showMessageDialog(null, "Favor escolher  um cliente e uma data");

        }
    }

    private void fecharTelaAction() {
        Stage stage = (Stage) fecharTela.getScene().getWindow(); //Obtendo a janela atual
        stage.close(); //Fechando o Stage
    }
}
