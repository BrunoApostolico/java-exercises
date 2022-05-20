package br.com.sankhya.fattu.dados;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.modelcore.comercial.ContextoRegra;

public class consultaDados {
	
	public static int consulta(PersistenceEvent arg0, JdbcWrapper jdbcWrapper) throws Exception {
		
		DynamicVO data = (DynamicVO) arg0.getVo();
		BigDecimal codParc = data.asBigDecimalOrZero("CODPARC");
		
		String query = "SELECT SUM(FIN.VLRDESDOB * FIN.RECDESP) VALOR, PAR.LIMCRED   \r\n"
				+ "     FROM TGFFIN FIN\r\n"
				+ "	 JOIN TGFPAR PAR ON FIN.CODPARC = PAR.CODPARC\r\n"
				+ "    WHERE FIN.CODPARC   = "+codParc+"\r\n"
				+ "      AND FIN.DHBAIXA   IS NULL\r\n"
				+ "      AND FIN.RECDESP   = 1\r\n"
				+ "      AND FIN.CODTIPTIT = 4\r\n"
				+ "    GROUP BY PAR.LIMCRED";
		
		PreparedStatement pstmt = jdbcWrapper.getPreparedStatement(query);
		ResultSet rs = pstmt.executeQuery();
		
		BigDecimal valor = null;
		BigDecimal limCred = null;
		
		
		while (rs.next()) {
			valor = rs.getBigDecimal("VALOR");
			limCred = rs.getBigDecimal("LIMCRED");
		}
		
		
		  if (valor != null & limCred != null) return valor.compareTo(limCred);
		    else return 0;
	}
	
	public static int consultaLimite(ContextoRegra ctx, JdbcWrapper jdbcWrapper) throws Exception {
			
    	//DynamicVO cabVO = ctx.getPrePersistEntityState().getNewVO();
    	//Integer oldCodParc = ctx.getPrePersistEntityState().getOldVO().asInt("CabecalhoNota.CODPARC");
    	Integer newCodParc = ctx.getPrePersistEntityState().getNewVO().asInt("CODPARC");
    	
    	//BigDecimal codParc = cabVO.asBigDecimalOrZero("CODPARC");
    		
    		String query = "SELECT SUM(FIN.VLRDESDOB * FIN.RECDESP) VALOR, PAR.LIMCRED   \r\n"
    				+ "     FROM TGFFIN FIN\r\n"
    				+ "	 JOIN TGFPAR PAR ON FIN.CODPARC = PAR.CODPARC\r\n"
    				+ "    WHERE FIN.CODPARC   = "+newCodParc+"\r\n"
    				+ "      AND FIN.DHBAIXA   IS NULL\r\n"
    				+ "      AND FIN.RECDESP   = 1\r\n"
    				+ "      AND FIN.CODTIPTIT = 4\r\n"
    				+ "    GROUP BY PAR.LIMCRED";
    		
    		PreparedStatement pstmt = jdbcWrapper.getPreparedStatement(query);
    		ResultSet rs = pstmt.executeQuery();
    		
    		BigDecimal valor = null;
    		BigDecimal limCred = null;
    		
    		while (rs.next()) {
    			valor = rs.getBigDecimal("VALOR");
    			limCred = rs.getBigDecimal("LIMCRED");
    		}
    		
    if (valor != null & limCred != null) return valor.compareTo(limCred);
    else return 0;
        
		}
	
	public static double consultaDebito(ContextoRegra ctx, JdbcWrapper jdbcWrapper) throws Exception {
		
		//DynamicVO cabVO = ctx.getPrePersistEntityState().getNewVO();
    	//Integer oldCodParc = ctx.getPrePersistEntityState().getOldVO().asInt("CabecalhoNota.CODPARC");
    	Integer newCodParc = ctx.getPrePersistEntityState().getNewVO().asInt("CODPARC");
    	
    	//BigDecimal codParc = cabVO.asBigDecimalOrZero("CODPARC");
    		
    		String query = "SELECT SUM(VLRDESDOB * RECDESP) VALOR  \r\n"
    				+ "     FROM TGFFIN\r\n"
    				+ "    WHERE CODPARC = "+newCodParc+"\r\n"
    				+ "	  AND VLRBAIXA = 0\r\n"
    				+ "      AND ORIGEM = 'E'\r\n"
    				+ "      AND NUMNOTA > 0\r\n"
    				+ "      AND DESDOBRAMENTO > 0";
    		
    		PreparedStatement pstmt = jdbcWrapper.getPreparedStatement(query);
    		ResultSet rs = pstmt.executeQuery();
    		
    		BigDecimal valor = null;
    	
    		while (rs.next()) {
    			valor = rs.getBigDecimal("VALOR");
    		}
    if (valor != null) return valor.doubleValue();
    else return -1;
		
	}

}
