/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.javafxappPI.mock;

import br.senac.javafxappPI.Pessoa.ProdutosBean;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LUANA
 */
public class DaoProdutos {
    
    public static void inserir(ProdutosBean produto) throws Exception {
    //Monta a string de inserção de um cliente no BD,
        //utilizando os dados do clientes passados como parâmetro
        String sql = "INSERT INTO produto (nome_produto,categoria_produto , quantidade_produto , preco_produto "
                + ", descricao_produto)"
                + " VALUES (?, ?, ?, ?,?)";

        //Conexão para abertura e fechamento
        Connection connection = null;
        //Statement para obtenção através da conexão e execução de
        //comandos SQL
        PreparedStatement preparedStatement = null;

        //Abre uma conexão com o banco de dados
        connection = ConnectionUtils.getConnection();

        //Cria um statement para execução de instruções SQL
        preparedStatement = connection.prepareStatement(sql);

        //Configura os parâmetros do "PreparedStatement"
        preparedStatement.setString(1, produto.getNome());
        preparedStatement.setString(2, produto.getCategoria());
        preparedStatement.setInt(3, produto.getQuantidade());
        preparedStatement.setInt(4, produto.getPreco());
       
        preparedStatement.setString(5, produto.getDescricao());
       

        //Executa o comando no banco de dados
        preparedStatement.execute();

        //Fecha o statement
        preparedStatement.close();

        //Fecha a conexão
        connection.close();
    }

    //Realiza a atualização dos dados de um cliente, com ID e dados
    //fornecidos como parâmetro através de um objeto da classe "Cliente"
    public static void atualizar(ProdutosBean produto)
            throws Exception {

        //Monta a string de atualização do cliente no BD, utilizando
        //prepared statement
        String sql = "UPDATE produto  nome_produto =?,SET categoria_produto  =?, quantidade_produto =?, preco_produto  =?"
                + ", descricao_produto =?"
                + " WHERE (id_produto =?)";

        //Conexão para abertura e fechamento
        Connection connection = null;
        //Statement para obtenção através da conexão, execução de
        //comandos SQL e fechamentos
        PreparedStatement preparedStatement = null;

        //Abre uma conexão com o banco de dados
        connection = ConnectionUtils.getConnection();

        //Cria um statement para execução de instruçães SQL
        preparedStatement = connection.prepareStatement(sql);

        //Configura os parâmetros do "PreparedStatement"
        preparedStatement.setString(1, produto.getNome());
        preparedStatement.setString(2, produto.getCategoria());
        preparedStatement.setInt(3, produto.getQuantidade());
        preparedStatement.setInt(4, produto.getPreco());
        preparedStatement.setString(5, produto.getDescricao());
        
        //Executa o comando no banco de dados
        preparedStatement.execute();

        //Fecha o statement
        preparedStatement.close();

        //Fecha a conexão
        connection.close();
    }

    //Realiza a exclusão lógica de um cliente no BD, com ID fornecido
    //como parâmetro. A exclusão lógica simplesmente "desliga" o
    //cliente, configurando um atributo específico, a ser ignorado
    //em todas as consultas de cliente ("enabled").
    public static void excluir(Integer id) throws Exception {

        //Monta a string de atualização do cliente no BD, utilizando
        //prepared statement
        String sql = "delete from produto WHERE (id_produto=?)";

        //Conexão para abertura e fechamento
        Connection connection = null;
        //Statement para obtenção através da conexão e execução de
        //comandos SQL
        PreparedStatement preparedStatement = null;

        //Abre uma conexão com o banco de dados
        connection = ConnectionUtils.getConnection();

        //Cria um statement para execução de instruçães SQL
        preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, id);

        //Executa o comando no banco de dados
        preparedStatement.execute();

        //Fecha o statement
        preparedStatement.close();

        //Fecha a conexão
        connection.close();
    }

    //Lista todos os clientes da tabela clientes
    public static List<ProdutosBean> listar()
            throws Exception {

        //Monta a string de listagem de clientes no banco, considerando
        //apenas a coluna de ativação de clientes ("enabled")
        String sql = "SELECT * FROM Produto ";

        //Lista de clientes de resultado
        List<ProdutosBean> listaProdutos = null;

        //Conexão para abertura e fechamento
        Connection connection = null;
        //Statement para obtenção através da conexão e execução de
        //comandos SQL
        PreparedStatement preparedStatement = null;
        //Armazenarã os resultados do banco de dados
        ResultSet result = null;

        //Abre uma conexão com o banco de dados
        connection = ConnectionUtils.getConnection();

        //Cria um statement para execução de instruçães SQL
        preparedStatement = connection.prepareStatement(sql);
        
        //Executa a consulta SQL no banco de dados
        result = preparedStatement.executeQuery();

        //Itera por cada item do resultado
        while (result.next()) {

            //Se a lista não foi inicializada, a inicializa
            if (listaProdutos == null) {
                listaProdutos = new ArrayList<ProdutosBean>();
            }

            //Cria uma instância de Cliente e popula com os valores do BD
            ProdutosBean produto = new ProdutosBean();
            
        produto.setId(result.getInt("id_produto"));
        produto.setNome(result.getString("nome_produto"));
        produto.setCategoria(result.getString("categoria_produto"));
        produto.setQuantidade(result.getInt("quantidade_produto"));
        produto.setPreco(result.getInt("preco_produto"));
        produto.setDescricao(result.getString("descricao_produto"));
        
            //Adiciona a instância na lista
            listaProdutos.add(produto);

        }

        //Fecha o result        
        result.close();

        //Fecha o statement
        preparedStatement.close();

        //Fecha a conexão
        connection.close();

        //Retorna a lista de clientes do banco de dados
        return listaProdutos;
    }

    //Procura um cliente no banco de dados, de acordo com o nome passado como parãmetro
    public static List<ProdutosBean> procurar(String valor)
            throws Exception {

        //Monta a string de consulta de clientes no banco, utilizando
        //o valor passado como parãmetro para busca na coluna de
        //nome. Além disso, também considera apenas os elementos
        //que possuem a coluna de ativação de clientes configurada com
        //o valor correto ("enabled" com "true")		
        String sql = "SELECT * FROM produto WHERE (nome_produto LIKE ?)";

        //Lista de clientes de resultado
        List<ProdutosBean> listaProdutos = null;

        //Conexão para abertura e fechamento
        Connection connection = null;
        //Statement para obtenção através da conexão, execução de
        //comandos SQL e fechamentos
        PreparedStatement preparedStatement = null;
        //Armazenarã os resultados do banco de dados
        ResultSet result = null;

        //Abre uma conexão com o banco de dados
        connection = ConnectionUtils.getConnection();

        //Cria um statement para execução de instruçães SQL
        preparedStatement = connection.prepareStatement(sql);

        //Configura os parâmetros do "PreparedStatement"
        preparedStatement.setString(1, "%" + valor + "%");

        //Executa a consulta SQL no banco de dados
        result = preparedStatement.executeQuery();

        //Itera por cada item do resultado
        while (result.next()) {

            //Se a lista não foi inicializada, a inicializa
            if (listaProdutos == null) {
                listaProdutos = new ArrayList<ProdutosBean>();
            }

            //Cria uma instância de Cliente e popula com os valores do BD
            ProdutosBean produto = new ProdutosBean();
            produto.setId(result.getInt("id_produto"));
            produto.setNome(result.getString("nome_produto"));
            produto.setCategoria(result.getString("categoria_produto"));
            produto.setQuantidade(result.getInt("quantidade_produto"));
            produto.setPreco(result.getInt("preco_produto"));
            produto.setDescricao(result.getString("descricao_produto"));
            
            //Adiciona a instância na lista
            listaProdutos.add(produto);

        }

        //Fecha o result
        result.close();

        //Fecha o statement
        preparedStatement.close();

        //Fecha a conexão
        connection.close();

        //Retorna a lista de clientes do banco de dados
        return listaProdutos;

    }

    //Obtém uma instância da classe "Produto" através de dados do
    //banco de dados, de acordo com o ID fornecido como parãmetro
    public static ProdutosBean obter(Integer id)
            throws Exception {

        //Compãe uma String de consulta que considera apenas o cliente
        //com o ID informado e que esteja ativo ("enabled" com "true")
        String sql = "SELECT * FROM produto WHERE (id_poduto=?)";

        //Conexão para abertura e fechamento
        Connection connection = null;
        //Statement para obtenção através da conexão e execução de
        //comandos SQL
        PreparedStatement preparedStatement = null;
        //Armazenará os resultados do banco de dados
        ResultSet result = null;

        //Abre uma conexão com o banco de dados
        connection = ConnectionUtils.getConnection();

        //Cria um statement para execução de instruçães SQL
        preparedStatement = connection.prepareStatement(sql);

        //Configura os parâmetros do "PreparedStatement"
        preparedStatement.setInt(1, id);
        
        //Executa a consulta SQL no banco de dados
        result = preparedStatement.executeQuery();

        //Verifica se há pelo menos um resultado
        if (result.next()) {

            //Cria uma instância de Cliente e popula com os valores do BD
            ProdutosBean produto = new ProdutosBean();
            produto.setId(result.getInt("cliente_id"));
            produto.setNome(result.getString("nome_produto"));
            produto.setCategoria(result.getString("categoria_produto"));
          produto.setPreco(result.getInt("preco_produto "));
          produto.setQuantidade(result.getInt("quantidade_produto"));
          produto.setDescricao(result.getString("descricao_produto"));
          

            //Fecha o result
            result.close();

            //Fecha o statement
            preparedStatement.close();

            //Fecha a conexão
            connection.close();

            //Retorna o resultado
            return produto;

        }

        //Se chegamos aqui, o "return" anterior não foi executado porque
        //a pesquisa não teve resultados
        //Neste caso, não hã um elemento a retornar, então retornamos "null"
        
        //Fecha o result
        result.close();

        //Fecha o statement
        preparedStatement.close();

        //Fecha a conexão
        connection.close();

        return null;

    }
    
}
