/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.javafxappPI.mock;

import br.senac.javafxappPI.Pessoa.VendaBean;
import java.util.ArrayList;
import java.util.List;


public class MockVenda {

     public static int totalVenda = 1;
    /** Armazena a lista de clientes inseridos para manipulação. #MOCK **/    
    public static List<VendaBean> listaVenda = new ArrayList<VendaBean>();
    
    
    
    
    public static int getIdproduto (int index){
        
       int Id = MockProduto.listaProdutos.get(index).getId();
        
        
        
        return Id;
    }
    
    
    public static int getIdcli (int index){
        
       int Id = MockCliente.listaClientes.get(index).getId();
        
        
        
        return Id;
    }
    
    
    public static void inserir(VendaBean venda)
            throws Exception {
        
        venda.setId(totalVenda++);
        
        
        
        
        listaVenda.add(venda);
        
    }
    
    public static List<VendaBean> listar()
            throws Exception {       
        //Retorna a lista de clientes
        return listaVenda;
    }
    
    
   

    
    
    
    public static void excluir(Integer id) throws Exception {
        if (id != null && !listaVenda.isEmpty()) {
            for (int i = 0; i < listaVenda.size(); i++) {
                VendaBean vendaLi = listaVenda.get(i);
                if (vendaLi != null && vendaLi.getId() == id) {
                    listaVenda.remove(i);
                    break;
                }
            }
        }
    }
    
    
    
    
    
    
    
}
