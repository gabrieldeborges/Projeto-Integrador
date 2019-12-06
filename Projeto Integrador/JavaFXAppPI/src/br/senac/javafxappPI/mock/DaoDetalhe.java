/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.javafxappPI.mock;

import br.senac.javafxappPI.Pessoa.detalhesBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DaoDetalhe {

    
    public static List<detalhesBean> listar()
            throws Exception {

        //Monta a string de listagem de clientes no banco, considerando
        //apenas a coluna de ativação de clientes ("enabled")
        String sql = "SELECT * FROM venda";

        //Lista de clientes de resultado
        List<detalhesBean> listaDetalhes = null;

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
//
//            //Se a lista não foi inicializada, a inicializa
//            if (listaDetalhes == null) {
//                listaDetalhes = new ArrayList<detalhesBean>();
//            }
//
//            //Cria uma instância de Cliente e popula com os valores do BD
//            detalhesBean rel = new detalhesBean();
//            rel.setData(result.getDate("data").toLocalDate());
//            rel.setIdCliente(result.getInt("id_cliente"));
//            rel.setNome(result.getString("nome_cliente"));
//            rel.setValor(result.getInt("total_gasto"));
//
//
////Adiciona a instância na lista
//            listaVendas.add(rel);

        }

        //Fecha o result        
        result.close();

        //Fecha o statement
        preparedStatement.close();

        //Fecha a conexão
        connection.close();

        //Retorna a lista de clientes do banco de dados
        return listaDetalhes;
    }

    
    
}
