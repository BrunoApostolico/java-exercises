package br.com.sankhya.fattu.vendas;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.fattu.dados.consultaDados;
import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.PersistenceException;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;

public class AvisoLimite implements EventoProgramavelJava {

	@Override
	public void afterDelete(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterInsert(PersistenceEvent arg0) throws Exception {
		
		EntityFacade dwf = EntityFacadeFactory.getDWFFacade();
		JdbcWrapper jdbcWrapper = dwf.getJdbcWrapper();
		jdbcWrapper.openSession();
		

		
		if (consultaDados.consulta(arg0, jdbcWrapper) == 1) throw new PersistenceException("Cliente com limite de crédito excedido! Será necessário solicitar liberação!");
	
	
		jdbcWrapper.closeSession();
	}

	@Override
	public void afterUpdate(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub
		EntityFacade dwf = EntityFacadeFactory.getDWFFacade();
		JdbcWrapper jdbcWrapper = dwf.getJdbcWrapper();
		jdbcWrapper.openSession();
		

		
		if (consultaDados.consulta(arg0, jdbcWrapper) == 1) throw new PersistenceException("Cliente com limite de crédito excedido! Será necessário solicitar liberação!");
	
	
		jdbcWrapper.closeSession();
		
	}

	@Override
	public void beforeCommit(TransactionContext arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeDelete(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeInsert(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeUpdate(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
