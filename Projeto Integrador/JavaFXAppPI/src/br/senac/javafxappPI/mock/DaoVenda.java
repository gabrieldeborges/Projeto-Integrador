package br.senac.javafxappPI.mock;

import br.senac.javafxappPI.Pessoa.RelatorioBean;
import br.senac.javafxappPI.Pessoa.VendaBean;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DaoVenda {

    //colcoca os produtos na tabela de venda
    
     public static int totalVenda = 1;
    /** Armazena a lista de clientes inseridos para manipulação. #MOCK **/    
    public static List<VendaBean> listaVenda = new ArrayList<VendaBean>();
    
    
    public static void inserirHora(VendaBean venda)
            throws Exception {
        
        venda.setId(totalVenda++);
        
        
        
        
        listaVenda.add(venda);
        
    }
    
    public static List<VendaBean> listarHora()
            throws Exception {       
        //Retorna a lista de clientes
        return listaVenda;
    }
    
    
    
    
    
    
    
    public static void inserir(RelatorioBean venda) throws Exception {
    
        String sql = "INSERT INTO venda (id_cliente, nome_cliente, data_venda, total_gasto) "
                + " VALUES (?, ?, ?,?)";

        //Conexão para abertura e fechamento
        Connection connection = null;
        
        PreparedStatement preparedStatement = null;

        connection = ConnectionUtils.getConnection();

        preparedStatement = connection.prepareStatement(sql);
        System.out.println(venda.getIdCliente());
        //Configura os parâmetros do "PreparedStatement"
        preparedStatement.setInt(1, venda.getIdCliente());
        preparedStatement.setString(2, venda.getNome());
        preparedStatement.setDate(3, Date.valueOf(venda.getData()));
        preparedStatement.setDouble(4, venda.getValor());

       

        //Executa o comando no banco de dados
        preparedStatement.execute();

        //Fecha o statement
        preparedStatement.close();

        //Fecha a conexão
        connection.close();
    }    
    
    public static List<VendaBean> listar()
            throws Exception {

        //Monta a string de listagem de clientes no banco, considerando
        //apenas a coluna de ativação de clientes ("enabled")
        String sql = "SELECT * FROM venda)";

        //Lista de clientes de resultado
        List<VendaBean> listaVenda = null;

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
            if (listaVenda == null) {
                listaVenda = new ArrayList<VendaBean>();
            }

            //Cria uma instância de Cliente e popula com os valores do BD
            VendaBean venda = new VendaBean();
            venda.setIdCliente(result.getInt("id_cliente"));
            venda.setPreco(result.getDouble("total_gasto"));
            venda.setData(result.getDate("data_venda").toLocalDate());
            
            //Adiciona a instância na lista
            listaVenda.add(venda);

        }

        //Fecha o result        
        result.close();

        //Fecha o statement
        preparedStatement.close();

        //Fecha a conexão
        connection.close();

        //Retorna a lista de clientes do banco de dados
        return listaVenda;
    }
}
