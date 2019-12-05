/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.javafxappPI.mock;

import Principal.TelaClienteAplicacao;
import br.senac.javafxappPI.Pessoa.Cliente;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author guilherme.cfortes
 */
public class MockCliente {
    public static int totalClientes = 0;
    /** Armazena a lista de clientes inseridos para manipulação. #MOCK **/    
    public static List<Cliente> listaClientes = new ArrayList<Cliente>();
    
    //Insere um cliente no mock "cliente"
    public static void inserir(Cliente cliente)
            throws Exception {
        cliente.setId(totalClientes++);
        listaClientes.add(cliente);
    }

    //Realiza a atualização dos dados de um cliente, com ID e dados
    //fornecidos como parâmetro através de um objeto da classe "Cliente"
    public static void atualizar(Cliente clienteProcura)
            throws Exception {
        if (clienteProcura != null && clienteProcura.getId() != null && !listaClientes.isEmpty()) {
            for (Cliente clienteLi : listaClientes) {
                if (clienteLi != null && clienteLi.getId() == clienteProcura.getId()) {
                    
                    clienteLi.setNome(clienteProcura.getNome());
                    clienteLi.setCpf(clienteProcura.getCpf());
                    break;
                }
            }
        }
    }

    //Realiza a exclusão de um cliente no mock, com ID fornecido
    //como parâmetro.
    public static void excluir(Integer id) throws Exception {
        if (id != null && !listaClientes.isEmpty()) {
            for (int i = 0; i < listaClientes.size(); i++) {
                Cliente clienteLi = listaClientes.get(i);
                if (clienteLi != null && clienteLi.getId() == id) {
                    listaClientes.remove(i);
                    break;
                }
            }
        }
    }

    //Lista todos os clientes
    public static List<Cliente> listar()
            throws Exception {       
        //Retorna a lista de clientes
        return listaClientes;
    }

    //Procura um cliente no mock, de acordo com o nome
    //ou com o sobrenome, passado como parâmetro
    public static List<Cliente> procurar(String valor)
            throws Exception {
        List<Cliente> listaResultado = new ArrayList<Cliente>();
        
        if (valor != null && !listaClientes.isEmpty()) {
            for (Cliente clienteLi : listaClientes) {
                if (clienteLi != null && clienteLi.getNome() != null 
                    ) {
                    if (clienteLi.getNome().contains(valor)
                        
                        || clienteLi.getCpf().equals(valor)) {
                        listaResultado.add(clienteLi);
                    }
                }
            }
        }
        
        //Retorna a lista de clientes encontrados
        return listaResultado;
    }

    //Obtém um cliente da lista
    public static Cliente obter(Integer id)
            throws Exception {
        if (id != null && !listaClientes.isEmpty()) {
            for (int i = 0; i < listaClientes.size(); i++) {
                if (listaClientes.get(i) != null && listaClientes.get(i).getId() == id) {
                    return listaClientes.get(i);
                }                
            }
        }
        return null;
    }
}
