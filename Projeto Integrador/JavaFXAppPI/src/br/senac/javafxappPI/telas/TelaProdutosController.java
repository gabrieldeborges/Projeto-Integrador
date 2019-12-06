/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.javafxappPI.telas;

import br.senac.javafxappPI.Pessoa.ProdutosBean;
import br.senac.javafxappPI.mock.DaoProdutos;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author LUANA
 */
public class TelaProdutosController implements Initializable {

    @FXML
    private TextField q1;
    @FXML
    private TextField q2;
    @FXML
    private TextField q3;
    @FXML
    private TextField q4;

    //private TableView<ProdutosBean> tabelaClientes;
    @FXML
    private ComboBox<String> cb;
    @FXML
    private TableView<ProdutosBean> tbl;
    //private TableColumn<ProdutosBean, String> colunaCodigo;
    @FXML
    private TableColumn<ProdutosBean, String> colunaNome;
    private ProdutosBean produtoEdicao;
    @FXML
    private TableColumn<ProdutosBean, String> colunaCodigo;
    @FXML
    private TextField txtPesquisa;
    public boolean editMode = false;
    @FXML
    private Button gg;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        
        
        tbl.getItems().clear();

        //Obtém os resultados de pesquisa do mock
        List resultados = listarPesquisar();

        //Se há resultados, atualiza a tabela
        if (resultados != null) {
            tbl.setItems(
                    FXCollections.observableArrayList(resultados)
            );
        }
        
        colunaCodigo.setCellValueFactory(
                new PropertyValueFactory("id")
        );
        colunaNome.setCellValueFactory(
                new PropertyValueFactory("nome")
        );

        //Configura os elementos do combo
        cb.getItems().addAll(
                "Jogos",
                "Video Games",
                "Outro"
        );

    }

    @FXML
    private void btn1(ActionEvent event) {
        //botão para adicionar

        if (!editMode) {
            //Cria um novo item de modelo de cliente
            ProdutosBean produto = new ProdutosBean();

            //Configura os valores no item de modelo
            if (q1.getText() != null && q2.getText() != null && q3.getText() != null && cb.getValue() != null) {
                produto.setNome(q1.getText());

                if (Integer.parseInt(q2.getText()) > 0) {

                    produto.setQuantidade(Integer.parseInt(q2.getText()));

                    produto.setPreco(Integer.parseInt(q3.getText()));

                    produto.setCategoria(cb.getValue());

                    produto.setDescricao(q4.getText());

                    //Insere o cliente no mock
                    inserirProduto(produto);

                    //Limpa os campos após a inserção
                    limparCampos();
                } else {

                    JOptionPane.showMessageDialog(null, "Por favor digite uma quantidade valida");

                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos");
            }

        } else {
            //Configura os valores no item de modelo
            produtoEdicao.setNome(q1.getText());

            produtoEdicao.setQuantidade(Integer.parseInt(q2.getText()));

            produtoEdicao.setPreco(Integer.parseInt(q3.getText()));
            produtoEdicao.setCategoria(cb.getValue());
            produtoEdicao.setDescricao(q4.getText());
            //Atualiza o cliente no mock
            atualizarCliente(produtoEdicao);

            //Limpa os campos após a edição
            limparCampos();
        }

    }

    @FXML
    private void btn2(ActionEvent event) {
        //botão para excluir
        //Obtém o item selecionado da tabela
        ProdutosBean produto = tbl.getSelectionModel().getSelectedItem();

        //Se há um item selecionado, habilita a edição e grava os respectivos
        //dados na variável clienteEdicao para uso posterior
        if (produto != null) {
            //Monta e exibe um diálogo de confirmação de exclusão
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Excluir Produto");
            alert.setContentText("Excluir o produto " + produto.getNome());

            //Mostra o diálogo esperando um resultado
            Optional<ButtonType> result = alert.showAndWait();

            //Se o resultado for afirmativo, exclui o cliente
            if (result.get() == ButtonType.OK) {
                //Exclui o cliente e atualiza a tabela
                try {
                    excluirProduto(produto);
                    acaoPesquisar(event);
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

    @FXML
    private void btn3(ActionEvent event) {
        //botao para alterar

        ProdutosBean produto = tbl.getSelectionModel().getSelectedItem();

        //Se há um item selecionado, habilita a edição e grava os respectivos
        //dados na variável clienteEdicao para uso posterior
        if (produto != null) {

            editMode = true;

            produtoEdicao = produto;

            //Coloca os dados do cliente selecionado na interface
            q1.setText(produtoEdicao.getNome());

            q2.setText(Integer.toString(produtoEdicao.getQuantidade()));

            q3.setText(Integer.toString(produtoEdicao.getPreco()));

            cb.setValue(produtoEdicao.getCategoria());
            q4.setText(produtoEdicao.getDescricao());

            //Atualiza o título do botão
            gg.setText("Salvar");
        } else {
            //Não há cliente selecionado, exibe uma mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("É necessário selecionar um produto");
            alert.showAndWait();
        }

    }

//metodos
    private void excluirProduto(ProdutosBean produto) throws Exception {
        DaoProdutos.excluir(produto.getId());
    }

    private void atualizarCliente(ProdutosBean produtoEdicao) {
        //Atualiza o cliente
        try {
            DaoProdutos.atualizar(produtoEdicao);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Produto Atualizado");
            alert.setContentText("Os dados do produto foram atualizados"
                    + " com sucesso");
            alert.showAndWait();
            limparCampos();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Ocorreu um erro ao atualizar o produto");
            alert.showAndWait();
        }
    }

    private List listarPesquisar() {
        //Obtém os itens do mock
        List resultados;
        try {
            //Se há dados para pesquisa, faz uma busca pelo valor no mock
            //Caso contrário, faz a listagem
            if (txtPesquisa.getText().equals("")) {
                resultados = DaoProdutos.listar();
            } else {
                resultados = DaoProdutos.procurar(txtPesquisa.getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultados = null;
        }
        return resultados;
    }

    private void inserirProduto(ProdutosBean produto) {
        //Adiciona o cliente
        try {

            DaoProdutos.inserir(produto);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Produto Inserido");
            alert.setContentText("O Produto foi inserido com sucesso");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Ocorreu um erro ao inserir");
            alert.showAndWait();
        }
    }

    private void limparCampos() {

        q1.setText("");
        q2.setText("");
        q3.setText("");
        cb.setValue(null);
        q4.setText("");
        editMode=false;
    }

    @FXML
    private void Limpar(ActionEvent event) {
        editMode = false;
        produtoEdicao = null;
        limparCampos();
    }

    @FXML
    private void acaoPesquisar(ActionEvent event) {

        tbl.getItems().clear();

        //Obtém os resultados de pesquisa do mock
        List resultados = listarPesquisar();

        //Se há resultados, atualiza a tabela
        if (resultados != null) {
            tbl.setItems(
                    FXCollections.observableArrayList(resultados)
            );
        }else{
            System.out.println("TABELA VAZIA");
        }

    }

}
