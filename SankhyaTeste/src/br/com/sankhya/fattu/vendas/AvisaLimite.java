package br.com.sankhya.fattu.vendas;

import br.com.sankhya.fattu.dados.consultaDados;
import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.modelcore.comercial.ContextoRegra;
import br.com.sankhya.modelcore.comercial.Regra;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;


public class AvisaLimite implements Regra {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void afterDelete(ContextoRegra ctx) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterInsert(ContextoRegra ctx) throws Exception {
		
		Boolean isCabecalhoNota = ctx.getPrePersistEntityState().getDao().getEntityName().equals("CabecalhoNota");
		Boolean hasTop1000 = ctx.getPrePersistEntityState().getNewVO().containsProperty("CODTIPOPER") ? ctx.getPrePersistEntityState().getNewVO().asInt("CODTIPOPER") == 1000 : false;
	
		if (isCabecalhoNota & hasTop1000){
			

			EntityFacade dwf = EntityFacadeFactory.getDWFFacade();
			JdbcWrapper jdbcWrapper = dwf.getJdbcWrapper();
			jdbcWrapper.openSession();

	    	if (consultaDados.consultaLimite(ctx, jdbcWrapper) == 1) ctx.getBarramentoRegra().addMensagem("Cliente com limite de crédito excedido! Será necessário solicitar liberação!");
	    	if (consultaDados.consultaDebito(ctx, jdbcWrapper) > 0) ctx.getBarramentoRegra().addMensagem("Cliente possui títulos em atraso!");

	 		jdbcWrapper.closeSession();
	
		}
			
			
		}
		
	

	@Override
	public void afterUpdate(ContextoRegra ctx) throws Exception {
		
		Boolean isCabecalhoNota = ctx.getPrePersistEntityState().getDao().getEntityName().equals("CabecalhoNota");
		
		Boolean hasTop1000 = ctx.getPrePersistEntityState().getNewVO().containsProperty("CODTIPOPER") ? ctx.getPrePersistEntityState().getNewVO().asInt("CODTIPOPER") == 1000 : false;
	
		if (isCabecalhoNota & hasTop1000){
			

			EntityFacade dwf = EntityFacadeFactory.getDWFFacade();
			JdbcWrapper jdbcWrapper = dwf.getJdbcWrapper();
			jdbcWrapper.openSession();

	    	if (consultaDados.consultaLimite(ctx, jdbcWrapper) == 1) ctx.getBarramentoRegra().addMensagem("Cliente com limite de crédito excedido! Será necessário solicitar liberação!");
	    	if (consultaDados.consultaDebito(ctx, jdbcWrapper) > 0) ctx.getBarramentoRegra().addMensagem("Cliente possui títulos em atraso!");

	 		jdbcWrapper.closeSession();
			
			
		}
		
	}

	@Override
	public void beforeDelete(ContextoRegra ctx) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeInsert(ContextoRegra ctx) throws Exception {

	}
	@Override
	public void beforeUpdate(ContextoRegra ctx) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
