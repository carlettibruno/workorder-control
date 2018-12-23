package org.csi.controle.servico.impl;

import java.io.InputStream;
import java.util.StringTokenizer;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.apache.commons.fileupload.util.Streams;
import org.csi.controle.core.entidade.Cliente;
import org.csi.controle.core.entidade.Envio;
import org.csi.controle.servico.ClienteService;
import org.csi.controle.servico.ImportClienteService;
import org.csi.controle.servico.util.ConversorTxt;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ImportClienteServiceImpl implements ImportClienteService {

	@EJB
	private ClienteService clienteService;
	
	@PersistenceContext(name="controlePU")
	protected EntityManager em;
	
    @Resource
    private UserTransaction ut;	
	
	@Override
	public void inserirClientes(InputStream inputStream, Envio envio) throws Exception {
		ConversorTxt conversor = new ConversorTxt();
		
		String arquivoStr = Streams.asString(inputStream, "ISO-8859-1");
		StringTokenizer st = new StringTokenizer(arquivoStr, "\n");
		envio.setTotal(st.countTokens() - 1);
		
        st.nextElement(); //pula cabeçalho
        while (st.hasMoreElements()) {
        	String linha = (String) st.nextElement();
        	try {						
        		ut.begin();
        		Cliente cliente = conversor.txtToCliente(linha);
        		Cliente clienteBase = clienteService.obterCliente(cliente.getCode());
        		if(clienteBase == null) {
        			clienteService.inserirCliente(cliente);
        		} else {
        			clienteService.atualizarCliente(clienteBase.getIdCliente(), cliente);
        		}
        		
        		envio.setQtdeCarregada(envio.getQtdeCarregada() + 1);
        		this.em.merge(envio);
        		ut.commit();
			} catch (Exception e) {
				e.printStackTrace();
				ut.rollback();
			}
		}		
	}
	
}