/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.javafxappPI.mock;


import br.senac.javafxappPI.Pessoa.ProdutosBean;
import java.util.ArrayList;
import java.util.List;


public class MockProduto {
     public static int totalProdutos = 1;
    /** Armazena a lista de clientes inseridos para manipulação. #MOCK **/    
    public static List<ProdutosBean> listaProdutos = new ArrayList<ProdutosBean>();
    
    
    public static void inserir(ProdutosBean produto)
            throws Exception {
        produto.setId(totalProdutos++);
        listaProdutos.add(produto);
    }
    public static void atualizar(ProdutosBean produtoProcura)
            throws Exception {
        if (produtoProcura != null && produtoProcura.getId() != null && !listaProdutos.isEmpty()) {
            
            for (ProdutosBean produtoli : listaProdutos) {
                
                if (produtoli != null && produtoli.getId() == produtoProcura.getId()) {
                    
                    produtoli.setNome(produtoProcura.getNome());
                    produtoli.setId(produtoProcura.getId());
                    break;
            
                }
            }
        }
    }
    
    public static void excluir(Integer id) throws Exception {
        if (id != null && !listaProdutos.isEmpty()) {
            for (int i = 0; i < listaProdutos.size(); i++) {
                ProdutosBean produtosLi = listaProdutos.get(i);
                if (produtosLi != null && produtosLi.getId() == id) {
                    listaProdutos.remove(i);
                    break;
                }
            }
        }
    }
    
    public static List<ProdutosBean> listar()
            throws Exception {       
        //Retorna a lista de clientes
        return listaProdutos;
    }
    
    public static List<ProdutosBean> procurar(String valor)
            throws Exception {
        List<ProdutosBean> listaResultado = new ArrayList<ProdutosBean>();
        
        if (valor != null && !listaProdutos.isEmpty()) {
            for (ProdutosBean produtosLi : listaProdutos) {
                if (produtosLi != null && produtosLi.getNome() != null 
                    ) {
                    if (produtosLi.getNome().contains(valor)
                        
                        || produtosLi.getId().equals(valor)) {
                        listaResultado.add(produtosLi);
                    }
                }
            }
        }
        
        //Retorna a lista de clientes encontrados
        return listaResultado;
    }
    
    
    
    
    public static ProdutosBean obter(Integer id)
            throws Exception {
        if (id != null && !listaProdutos.isEmpty()) {
            for (int i = 0; i < listaProdutos.size(); i++) {
                if (listaProdutos.get(i) != null && listaProdutos.get(i).getId() == id) {
                    return listaProdutos.get(i);
                }                
            }
        }
        return null;
    }
    
    
}
