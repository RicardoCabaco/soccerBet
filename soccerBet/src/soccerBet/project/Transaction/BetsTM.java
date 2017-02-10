package soccerBet.project.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import soccerBet.project.Calculation.AvaliationFunction;
import soccerBet.project.Calculation.Team;
import soccerBet.project.betfair.dao.ApostasDAO;
import soccerBet.project.betfair.dao.ClassificacaoDAO;
import soccerBet.project.betfair.dao.JogoDAO;
import soccerBet.project.betfair.dao.MapeamentoMercadosDAO;
import soccerBet.project.betfair.dao.MercadoDAO;
import soccerBet.project.betfair.dao.PrecosDAO;
import soccerBet.project.betfair.dao.RunnerDAO;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.Runner;
import soccerBet.project.betfair.util.InflatedMarketPrices;
import soccerBet.project.betfair.util.InflatedMarketPrices.InflatedPrice;
import soccerBet.project.betfair.util.InflatedMarketPrices.InflatedRunner;
import soccerBet.project.contracts.RunnerContract;
import soccerBet.project.objects.Classificacao;
import soccerBet.project.objects.Jogo;
import soccerBet.project.objects.MapeamentoMercados;
import soccerBet.project.objects.Mercados;
import soccerBet.project.objects.Precos;
import soccerBet.project.ruleEngine.EngineStart;
import soccerBet.project.ruleEngine.TempoJogo;

public class BetsTM {

	public BetsTM() {

	}

	public List<Precos> makeTransaction(int[] marketId, List<RunnerContract> runners,
			Jogo jogo, Mercados mercado) {
		// TODO Auto-generated method stub

		if (runners != null &&  runners.size()>0) {

			MapeamentoMercadosDAO mapMercDao = new MapeamentoMercadosDAO();
			JogoDAO jogoDao = new JogoDAO();
			MercadoDAO mercDao = new MercadoDAO();
			RunnerDAO runDao = new RunnerDAO();
			PrecosDAO precoDao = new PrecosDAO();
			ApostasDAO apostasDao = new ApostasDAO();

			apostasDao.getApostasByMercado(mercado.getIdMercado());

			mapMercDao.getMapeamentoMercadoByMaketAndJogo(marketId[0],
					jogo.getIdJogo());

			MapeamentoMercados mapMerc = mapMercDao
					.getMapeamentoMercadoObj();

			if (mapMerc == null) {
				mapMerc = new MapeamentoMercados();
				mapMerc.setMarketId(marketId[0]);
				jogoDao.getJogo(jogo.getIdJogo());
				mapMerc.setJogo(jogoDao.getJogoObj());
				mercDao.getMercado(mercado.getIdMercado());
				mapMerc.setMercados(mercDao.getMercadoObj());
				mapMercDao.insertToDB(mapMerc);

			}

			int i = 0;
			List<Precos> priList = new ArrayList<Precos>();
			for (RunnerContract r : runners) {

				runDao.geRunnerBySelectionIdAndMapMercado(
						r.getSelectionId(), mapMerc.getIdMapMercado());

				soccerBet.project.objects.Runner run = runDao
						.getRunnerObj();

				if (run == null) {
					run = new soccerBet.project.objects.Runner();
					run.setSelectionId(r.getSelectionId());
					run.setMapeamentoMercados(mapMerc);
					run.setApostas(apostasDao.getApostaObjList().get(i));
					runDao.guardarRunner(run);
				}

				Precos pri = new Precos();
				pri.setData(new Date());
				pri.setBack(r.getBack());
				pri.setLay(r.getLay());
				pri.setRunner(run);
				pri.setSelectionId(r.getSelectionId());
				pri.setPlacedBet(0);
				precoDao.guardarPrecos(pri);
				priList.add(pri);
				
				i++;
			}
			
			return priList;
		}
		return null;
		

	}


	public void closedMarket(int[] marketId, Jogo jogo, Mercados mercado) {
		// TODO Auto-generated method stub


			MapeamentoMercadosDAO mapMercDao = new MapeamentoMercadosDAO();
			JogoDAO jogoDao = new JogoDAO();
			MercadoDAO mercDao = new MercadoDAO();


			mapMercDao.getMapeamentoMercadoByMaketAndJogo(marketId[0],
					jogo.getIdJogo());

			MapeamentoMercados mapMerc = mapMercDao
					.getMapeamentoMercadoObj();

			if (mapMerc == null) {
				mapMerc = new MapeamentoMercados();
				mapMerc.setMarketId(marketId[0]);
				jogoDao.getJogo(jogo.getIdJogo());
				mapMerc.setJogo(jogoDao.getJogoObj());
				mercDao.getMercado(mercado.getIdMercado());
				mapMerc.setMercados(mercDao.getMercadoObj());
				mapMerc.setEstado(2);
				mapMercDao.insertToDB(mapMerc);
			} else {
				mapMerc.setEstado(2);
				mapMercDao.updateToDB(mapMerc);
			}

		} 
}


