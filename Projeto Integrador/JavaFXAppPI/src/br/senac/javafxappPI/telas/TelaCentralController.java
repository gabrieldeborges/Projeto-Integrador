/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.javafxappPI.telas;

import br.senac.javafxappPI.Pessoa.Cliente;
import br.senac.javafxappPI.mock.DaoCliente;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author guilherme.cfortes
 */
public class TelaCentralController {

    private boolean editMode = false;
    private Cliente clienteEdicao;
    @FXML
    private TableView<Cliente> TableViewInformacoes;
    @FXML
    private TableColumn<Cliente, String> TVNome;
    @FXML
    private TableColumn<Cliente, String> TVCPF;
    @FXML
    private GridPane GPInformacoes;
    @FXML
    private Label LBLNome;
    @FXML
    private Label LBLCPF;
    @FXML
    private Label LBLRG;
    @FXML
    private Label LBLEndereco;
    private Label LBLNomeInsert;
    private Label LBLCPFInsert;
    @FXML
    private Label LBLTelefone;
    @FXML
    private Label LBLEmail;
    @FXML
    private Label LBLEstadoCivil;
    @FXML
    private Label LBLNatal;
    @FXML
    private Label LBLGenero;
    @FXML
    private Label LBLTitulo;
    @FXML
    private Button BTExcluir;
    @FXML
    private Button BTInserir;
    @FXML
    private Button BTAlterar;
    @FXML
    private TextField TFPesquisar;
    @FXML
    private Button BTPesquisar;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtCpf;
    @FXML
    private TextField txtEndereco;
    @FXML
    private TextField txtTelefone;
    @FXML
    private TextField txtRG;
    @FXML
    private TextField txtEmail;
    @FXML
    private ChoiceBox<String> cbEstado;
    @FXML
    private DatePicker dpDataNascimento;
    @FXML
    private ChoiceBox<String> cbGenero;

    public void initialize() throws Exception {

        TableViewInformacoes.getItems().clear();        
        
        //Obtém os resultados de pesquisa do mock
        List resultados = listarPesquisar();
        
        //Se há resultados, atualiza a tabela
        if (resultados != null) {
            TableViewInformacoes.setItems(
                    FXCollections.observableArrayList(resultados)
            );  
        }
        //Configura os elementos do combo
        cbGenero.getItems().addAll(
                "Feminino",
                "Masculino",
                "Outro"
        );

        cbEstado.getItems().addAll(
                "Solteiro",
                "Casado",
                "Namorando"
        );

        TVNome.setCellValueFactory(
                new PropertyValueFactory("nome")
        );

        TVCPF.setCellValueFactory(
                new PropertyValueFactory("cpf")
        );

//        TableViewInformacoes.setItems(
//          //      FXCollections.observableArrayList(MockCliente.listar())
//        );
    }

    private void excluirCliente(Cliente cliente) throws Exception {
        System.out.println("O ID "+cliente.getId());
        DaoCliente.excluir(cliente.getId());
    }

    @FXML
    private void Excluir(ActionEvent event) {
        Cliente cliente = TableViewInformacoes.getSelectionModel().getSelectedItem();

        //Se há um item selecionado, habilita a edição e grava os respectivos
        //dados na variável clienteEdicao para uso posterior
        if (cliente != null) {
            //Monta e exibe um diálogo de confirmação de exclusão
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Excluir Cliente");
            alert.setContentText("Excluir o cliente " + cliente.getNome());

            //Mostra o diálogo esperando um resultado
            Optional<ButtonType> result = alert.showAndWait();

            //Se o resultado for afirmativo, exclui o cliente
            if (result.get() == ButtonType.OK) {
                //Exclui o cliente e atualiza a tabela
                try {
                    excluirCliente(cliente);
                    Pesquisar(event);
                } catch (Exception e) {
                    e.printStackTrace();
                    Alert alertErro = new Alert(AlertType.ERROR);
                    alertErro.setTitle("Erro");
                    alertErro.setContentText("Ocorreu um erro ao excluir"
                            + " o cliente");
                    alertErro.showAndWait();
                }
            }
        }else{
            Alert alertErro = new Alert(AlertType.INFORMATION);
                    alertErro.setTitle("Erro");
                    alertErro.setContentText("Favor escolher um cliente");
                    alertErro.showAndWait();
        }
    }

    @FXML
    private void Inserir(ActionEvent event) throws IOException {
        
            
        
        
        
        if (editMode != true) {
            //Cria um novo item de modelo de cliente
            Cliente cliente = new Cliente();

            //Configura os valores no item de modelo
            
            cliente.setCpf(txtCpf.getText());
            cliente.setNome(txtNome.getText());
            cliente.setRG(txtRG.getText());
            cliente.setEndereco(txtEndereco.getText());
            cliente.setTelefone(txtTelefone.getText());
            cliente.setEmail(txtEmail.getText());
            cliente.setDatanascimento(dpDataNascimento.getValue());
            cliente.setEstadoCivil(cbEstado.getValue());
            
            if (cbGenero.equals("Masculino")) {
            cliente.setGenero("M");
        } else {
            cliente.setGenero("F");
        }
            
            
            //Insere o cliente no mock
            inserirCliente(cliente);

            //Limpa os campos após a inserção
            limparCampos();
            
            
        } else {
            //Configura os valores no item de modelo
            clienteEdicao.setCpf(txtCpf.getText());
            clienteEdicao.setNome(txtNome.getText());
            clienteEdicao.setRG(txtRG.getText());
            clienteEdicao.setEndereco(txtEndereco.getText());
            clienteEdicao.setTelefone(txtTelefone.getText());
            clienteEdicao.setEmail(txtEmail.getText());
            clienteEdicao.setDatanascimento(dpDataNascimento.getValue());
            clienteEdicao.setEstadoCivil(cbEstado.getValue());
           
            if (cbGenero.equals("Masculino")) {
            clienteEdicao.setGenero("M");
        } else {
            clienteEdicao.setGenero("F");
        }
            //Atualiza o cliente no mock
            atualizarCliente(clienteEdicao);

            //Limpa os campos após a edição
            limparCampos();
        }
        
    }

    @FXML
    private void Alterar(ActionEvent event) throws IOException {
        Cliente cliente = TableViewInformacoes.getSelectionModel().getSelectedItem();

        //Se há um item selecionado, habilita a edição e grava os respectivos
        //dados na variável clienteEdicao para uso posterior
        if (cliente != null) {
            
            editMode = true;

            clienteEdicao = cliente;

           // System.out.println(clienteEdicao.getCpf());
            
            txtCpf.setText(clienteEdicao.getCpf());
            txtNome.setText(clienteEdicao.getNome());

            txtRG.setText(clienteEdicao.getRG());
            txtEndereco.setText(clienteEdicao.getEndereco());
            txtTelefone.setText(clienteEdicao.getTelefone());
            txtEmail.setText(clienteEdicao.getEmail());
            dpDataNascimento.setValue(clienteEdicao.getDatanascimento());

            if (clienteEdicao.getGenero()=="M"){
            
            cbGenero.setValue("Masculino");
            }else{
                cbGenero.setValue("Feminino");
            }
            
            cbEstado.setValue(clienteEdicao.getEstadoCivil());
            BTInserir.setText("Inserir");

            

            txtCpf.requestFocus();

            BTInserir.setText("Salvar");
            

        } else {
            //Não há cliente selecionado, exibe uma mensagem de erro
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("É necessário selecionar um cliente");
            alert.showAndWait();
        }
    }

    @FXML
    private void Pesquisar(ActionEvent event) {

      
    }

    @FXML
    private void Search(ActionEvent event) throws Exception {
          TableViewInformacoes.getItems().clear();        
        
        //Obtém os resultados de pesquisa do mock
        List resultados = listarPesquisar();
        
        //Se há resultados, atualiza a tabela
        if (resultados != null) {
            TableViewInformacoes.setItems(
                    FXCollections.observableArrayList(resultados)
            );  
        }

    }

    private List listarPesquisar() {
         List resultados;
        try {
            //Se há dados para pesquisa, faz uma busca pelo valor no mock
            //Caso contrário, faz a listagem
            if (TFPesquisar.getText().equals("")) {
                resultados = DaoCliente.listar();
            }
            else {
                resultados = DaoCliente.procurar(TFPesquisar.getText());                
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            resultados = null;
        }
        return resultados;
    

    }

    @FXML
    private void TVSelect(MouseEvent event) {

        
    }

    private void inserirCliente(Cliente cliente) {
        //Adiciona o cliente
        try {
            String clint = cliente.getCpf();
            System.out.println(clint);
            DaoCliente.inserir(cliente);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Cliente Inserido");
            alert.setContentText("O Cliente foi inserido com sucesso");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Ocorreu um erro ao inserir o cliente");
            alert.showAndWait();
        }
    }

    private void limparCampos() {
        txtCpf.setText("");
        txtNome.setText("");
        txtRG.setText("");
        txtEndereco.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        dpDataNascimento.setValue(null);

        cbGenero.setValue(null);
        cbEstado.setValue(null);
        BTInserir.setText("Inserir");
        editMode = false;
    }

    private void atualizarCliente(Cliente clienteEdicao) {
        //Atualiza o cliente
        try {
            DaoCliente.atualizar(clienteEdicao);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Cliente Atualizado");
            alert.setContentText("Os dados de cliente foram atualizados"
                    + " com sucesso");
            alert.showAndWait();
            limparCampos();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Ocorreu um erro ao atualizar o cliente");
            alert.showAndWait();
        }
    }

}
