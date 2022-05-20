package br.com.sankhya.fattu.vendas;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.QueryExecutor;
import br.com.sankhya.extensions.actionbutton.Registro;

public class AcaoTeste implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao arg0) throws Exception {
		//Obtemos uma consulta para buscar os lançamentos
		QueryExecutor query = arg0.getQuery();
 
		//preparamos a execução da query, incluindo o parâmetro CODVEICULO.
		query.setParam("CODVEICULO", arg0.getParam("CODVEICULO"));
 
		query.nativeSelect("SELECT * FROM AD_TADCKM WHERE CODVEICULO = {CODVEICULO}");
 
		double vlrDesdob = 0;
		while(query.next()){
			double reembolso = query.getDouble("REEMBOLSO");
 
			//Só permitimos gerar o título quando todos
			//os lançamentos estiverem com reembolso calculado.
			if(reembolso > 0){
				vlrDesdob += reembolso;
			} else {
				arg0.mostraErro("O reembolso do lançamento " + query.getInt("SEQUENCIA") + " não foi calculado ainda.");
			}
		}
 
		if(vlrDesdob == 0){
		    arg0.confirmar("Valor do título zerado", "O veículo informado não possui lançamentos para reembolso, o título terá valor de desdobramento igual a zero. Deseja continuar?", 1);
		}
 
		//por questões de desempenho é aconselhavel
		//fechar a consulta sempre que ela não for mais necessária.
		query.close();
 
		//Solicitamos a inclusão de uma linha no financeiro
		Registro financeiro = arg0.novaLinha("TGFFIN");
 
		//Informamos os campos desejados para incluir o financeiro
		financeiro.setCampo("VLRDESDOB", vlrDesdob);
 
		financeiro.setCampo("RECDESP", -1);
		financeiro.setCampo("CODEMP", 11);
		financeiro.setCampo("NUMNOTA", 0);
		financeiro.setCampo("DTNEG", "04/10/2012");
		financeiro.setCampo("CODPARC", 0);
		financeiro.setCampo("CODNAT", 3050200);
		financeiro.setCampo("CODBCO", 0);
		financeiro.setCampo("CODTIPTIT", 2);
		financeiro.setCampo("DTVENC", "04/10/2012");
		financeiro.setCampo("HISTORICO", "REEMBOLSO DE KM PARA O VEÍCULO " + arg0.getParam("CODVEICULO"));
 
		//Quando o "save" do registro é acionado,
		//a alteração é feita no Banco de dados.
		//Portanto aqui estamos incluindo um registro na TGFFIN.
		financeiro.save();
 
		//Finalmente configuramos uma mensagem para ser exibida após a execução da ação.
		StringBuffer mensagem = new StringBuffer();
		mensagem.append("Foi gerado o título ");
		mensagem.append(financeiro.getCampo("NUFIN"));
		mensagem.append(" no valor de ");
		mensagem.append(financeiro.getCampo("VLRDESDOB"));
		mensagem.append(" como reembolso de KM para o veículo ");
		mensagem.append(arg0.getParam("CODVEICULO"));
 
		arg0.setMensagemRetorno(mensagem.toString());
		
	}

}
