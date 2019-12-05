/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.javafxappPI.mock;
import br.senac.javafxappPI.Pessoa.RelatorioBean;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;

public class MockRelatorio {

    
    
     public static int totalRelatorio = 1;
    /** Armazena a lista de clientes inseridos para manipulação. #MOCK **/    
    public static List<RelatorioBean> listaRelatorio = new ArrayList<RelatorioBean>();

    public static List<RelatorioBean> listaRelatorioMostra = new ArrayList<RelatorioBean>();

    
    
    
    public static void inserir(RelatorioBean relatorio)
            throws Exception {
        
        relatorio.setIdVenda(totalRelatorio++);
        
        
        listaRelatorio.add(relatorio);
        
    }
    
    public static void inserir2(RelatorioBean relatorio)
            throws Exception {
        
        
        
        listaRelatorioMostra.add(relatorio);
        
        
    }
    
    public static List<RelatorioBean> listar()
            throws Exception {       
        //Retorna a lista de clientes
        
        return listaRelatorioMostra;
    }
    
    
    
    
}
