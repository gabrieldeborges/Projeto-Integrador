/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.javafxappPI.mock;

import br.senac.javafxappPI.Pessoa.Cliente;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gabriel.bfreitas1
 */
public class DaoCliente {
    
    public static void inserir(Cliente cliente) throws Exception {
    //Monta a string de inserção de um cliente no BD,
        //utilizando os dados do clientes passados como parâmetro
        String sql = "INSERT INTO cliente (nome_cliente,cpf_cliente, rg_cliente, endereco_cliente, telefone_cliente, email_cliente , "
                + "estadocivil_cliente, data_cliente, genero_cliente) "
                + " VALUES (?, ?, ?, ?, ?, ?,?,?,?)";

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
        preparedStatement.setString(2, cliente.getCpf());
        preparedStatement.setString(1, cliente.getNome());
        preparedStatement.setString(3, cliente.getRG());
        preparedStatement.setString(4, cliente.getEndereco());
        preparedStatement.setString(5, cliente.getTelefone());
        preparedStatement.setString(6, cliente.getEmail());
        preparedStatement.setString(7, cliente.getEstadoCivil());
        preparedStatement.setDate(8, Date.valueOf(cliente.getDatanascimento()));
        preparedStatement.setString(9, cliente.getGenero());
        

        

       

        //Executa o comando no banco de dados
        preparedStatement.execute();

        //Fecha o statement
        preparedStatement.close();

        //Fecha a conexão
        connection.close();
    }

    //Realiza a atualização dos dados de um cliente, com ID e dados
    //fornecidos como parâmetro através de um objeto da classe "Cliente"
    public static void atualizar(Cliente cliente)
            throws Exception {

        //Monta a string de atualização do cliente no BD, utilizando
        //prepared statement
        String sql = "UPDATE cliente SET nome_cliente=?,cpf_cliente =?, rg_cliente=?, endereco_cliente =?,"
                + " telefone_cliente=?, email_cliente =?, "
                + "estadocivil_cliente=?, data_cliente=?, genero_cliente=?"
                + " WHERE (id_cliente=?)";

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
        preparedStatement.setString(2, cliente.getCpf());
        preparedStatement.setString(1, cliente.getNome());
        preparedStatement.setString(3, cliente.getRG());
        preparedStatement.setString(4, cliente.getEndereco());
        preparedStatement.setString(5, cliente.getTelefone());
        preparedStatement.setString(6, cliente.getEmail());
        preparedStatement.setString(7, cliente.getEstadoCivil());
        preparedStatement.setDate(8, Date.valueOf(cliente.getDatanascimento()));
        preparedStatement.setString(9, cliente.getGenero());
        preparedStatement.setInt(10, cliente.getId());
        
      
        

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
        String sql = "delete from cliente WHERE (id_cliente=?)";

        //Conexão para abertura e fechamento
        Connection connection = null;
        //Statement para obtenção através da conexão e execução de
        //comandos SQL
        PreparedStatement preparedStatement = null;

        //Abre uma conexão com o banco de dados
        connection = ConnectionUtils.getConnection();

        //Cria um statement para execução de instruçães SQL
        preparedStatement = connection.prepareStatement(sql);

        //Configura os parâmetros do "PreparedStatement"
        preparedStatement.setInt(1, id);

        //Executa o comando no banco de dados
        preparedStatement.execute();

        //Fecha o statement
        preparedStatement.close();

        //Fecha a conexão
        connection.close();
    }

    //Lista todos os clientes da tabela clientes
    public static List<Cliente> listar()
            throws Exception {

        //Monta a string de listagem de clientes no banco, considerando
        //apenas a coluna de ativação de clientes ("enabled")
        String sql = "SELECT * FROM cliente";

        //Lista de clientes de resultado
        List<Cliente> listaClientes = null;

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
            if (listaClientes == null) {
                listaClientes = new ArrayList<Cliente>();
            }

            //Cria uma instância de Cliente e popula com os valores do BD
            Cliente cliente = new Cliente();
            cliente.setId(result.getInt("id_cliente"));
            cliente.setCpf(result.getString("cpf_cliente"));
            cliente.setNome(result.getString("nome_cliente"));
            cliente.setEmail(result.getString("email_cliente"));
            cliente.setDatanascimento(result.getDate("data_cliente").toLocalDate());
            cliente.setGenero(result.getString("genero_cliente"));
            cliente.setEstadoCivil(result.getString("estadocivil_cliente"));
            cliente.setTelefone(result.getString("telefone_cliente"));
            cliente.setGenero(result.getString("genero_cliente"));
            cliente.setEndereco(result.getString("endereco_cliente"));
            
            //Adiciona a instância na lista
            listaClientes.add(cliente);

        }

        //Fecha o result        
        result.close();

        //Fecha o statement
        preparedStatement.close();

        //Fecha a conexão
        connection.close();

        //Retorna a lista de clientes do banco de dados
        return listaClientes;   
    }

    //Procura um cliente no banco de dados, de acordo com o nome passado como parãmetro
    public static List<Cliente> procurar(String valor)
            throws Exception {

        //Monta a string de consulta de clientes no banco, utilizando
        //o valor passado como parãmetro para busca na coluna de
        //nome. Além disso, também considera apenas os elementos
        //que possuem a coluna de ativação de clientes configurada com
        //o valor correto ("enabled" com "true")		
        String sql = "SELECT * FROM cliente WHERE (nome_cliente LIKE ?)";

        //Lista de clientes de resultado
        List<Cliente> listaClientes = null;

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
            if (listaClientes == null) {
                listaClientes = new ArrayList<Cliente>();
            }

            
           

            //Cria uma instância de Cliente e popula com os valores do BD
            Cliente cliente = new Cliente();
            cliente.setId(result.getInt("id_cliente"));
            cliente.setCpf(result.getString("cpf_cliente"));
            cliente.setNome(result.getString("nome_cliente"));
            cliente.setRG(result.getString("rg_cliente"));
            cliente.setDatanascimento(result.getDate("data_cliente").toLocalDate());
            cliente.setEndereco(result.getString("endereco_cliente"));
            cliente.setTelefone(result.getString("telefone_cliente"));
            cliente.setEmail(result.getString("email_cliente"));
            cliente.setEstadoCivil(result.getString("estadocivil_cliente"));
            cliente.setGenero(result.getString("genero_cliente"));
            
            
            

            //Adiciona a instância na lista
            listaClientes.add(cliente);

        }

        //Fecha o result
        result.close();

        //Fecha o statement
        preparedStatement.close();

        //Fecha a conexão
        connection.close();

        //Retorna a lista de clientes do banco de dados
        return listaClientes;

    }

    //Obtém uma instância da classe "Cliente" através de dados do
    //banco de dados, de acordo com o ID fornecido como parãmetro
    public static Cliente obter(Integer id)
            throws Exception {

        //Compãe uma String de consulta que considera apenas o cliente
        //com o ID informado e que esteja ativo ("enabled" com "true")
        String sql = "SELECT * FROM cliente WHERE (cliente_id=?)";

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
        preparedStatement.setBoolean(2, true);

        //Executa a consulta SQL no banco de dados
        result = preparedStatement.executeQuery();

        //Verifica se há pelo menos um resultado
        if (result.next()) {

            //Cria uma instância de Cliente e popula com os valores do BD
            Cliente cliente = new Cliente();
            cliente.setId(result.getInt("cliente_id"));
            cliente.setCpf(result.getString("cpf"));
            cliente.setNome(result.getString("nome"));
          
            cliente.setDatanascimento(result.getDate("data_nasc").toLocalDate());
            String genero = result.getString("genero");

            if ("F".equals(genero)) {
                cliente.setGenero("Feminino");
            } else {
                cliente.setGenero("Masculino");
            }

            //Fecha o result
            result.close();

            //Fecha o statement
            preparedStatement.close();

            //Fecha a conexão
            connection.close();

            //Retorna o resultado
            return cliente;

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
