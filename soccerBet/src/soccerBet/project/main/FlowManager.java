package soccerBet.project.main;

import java.util.ArrayList;
import java.util.Date;


import soccerBet.project.funds.Funds;

import org.kie.api.KieServices;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;


import soccerBet.project.betfair.util.InflatedMarketPrices.InflatedPrice;
import soccerBet.project.betfair.util.InflatedMarketPrices.InflatedRunner;
import soccerBet.project.betfair.handler.ExchangeAPI;
import soccerBet.project.betfair.util.InflatedMarketPrices;
import soccerBet.project.betfair.dao.JogoDAO;
import soccerBet.project.betfair.dao.MapeamentoMercadosDAO;
import soccerBet.project.betfair.dao.MercadoDAO;
import soccerBet.project.betfair.dao.PrecosDAO;
import soccerBet.project.betfair.dao.RunnerDAO;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.Market;
import soccerBet.project.betfair.handler.ExchangeAPI.Exchange;
import soccerBet.project.betfair.handler.GlobalAPI;
import soccerBet.project.betfair.util.APIContext;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.BetCategoryTypeEnum;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.BetPersistenceTypeEnum;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.BetTypeEnum;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.CancelBets;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.CancelBetsResult;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.GetAccountFundsResp;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.MUBet;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.Market;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.PlaceBets;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.PlaceBetsResult;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.Runner;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.UpdateBets;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.UpdateBetsResult;
import soccerBet.project.betfair.global.BFGlobalServiceStub.BFEvent;
import soccerBet.project.betfair.global.BFGlobalServiceStub.EventType;
import soccerBet.project.betfair.global.BFGlobalServiceStub.GetEventsResp;
import soccerBet.project.betfair.global.BFGlobalServiceStub.MarketSummary;
import soccerBet.project.objects.Jogo;
import soccerBet.project.objects.MapeamentoMercados;
import soccerBet.project.objects.Precos;



public class FlowManager {
	
	public JogoDAO jogoDao = new JogoDAO();
	
	public FlowManager(){
		
		ServiceManager sM = new ServiceManager();
		
		jogoDao.getJogoByDate("16 Dezembro");
		
		//Login
		sM.efectuaLogin();
		//EXCEEDED_THROTTLE
		for(int i=0;i<100;i++){
		//Thread obtem Fundos
		(new Funds()).start();
		}

//		for(Jogo jogo : jogoDao.getJogoObjList()){
//		    (new ServiceManager(jogo)).start();
//		}

		
		
	}

	
	
}
