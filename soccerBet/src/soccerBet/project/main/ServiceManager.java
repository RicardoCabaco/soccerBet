package soccerBet.project.main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.axis2.databinding.types.xsd.DateTime;
import org.kie.api.KieServices;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import soccerBet.project.Calculation.AvaliationFunction;
import soccerBet.project.Calculation.Team;
import soccerBet.project.Transaction.BetsTM;
import soccerBet.project.betfair.dao.ApostasDAO;
import soccerBet.project.betfair.dao.ClassificacaoDAO;
import soccerBet.project.betfair.dao.JogoDAO;
import soccerBet.project.betfair.dao.LigaDAO;
import soccerBet.project.betfair.dao.MapeamentoMercadosDAO;
import soccerBet.project.betfair.dao.MercadoDAO;
import soccerBet.project.betfair.dao.PrecosDAO;
import soccerBet.project.betfair.dao.RunnerDAO;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.BetCategoryTypeEnum;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.BetPersistenceTypeEnum;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.BetTypeEnum;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.PlaceBets;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.PlaceBetsResult;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.Runner;
import soccerBet.project.betfair.global.BFGlobalServiceStub.BFEvent;
import soccerBet.project.betfair.global.BFGlobalServiceStub.GetEventsResp;
import soccerBet.project.betfair.global.BFGlobalServiceStub.MarketSummary;
import soccerBet.project.betfair.handler.ExchangeAPI;
import soccerBet.project.betfair.handler.GlobalAPI;
import soccerBet.project.betfair.handler.ExchangeAPI.Exchange;
import soccerBet.project.betfair.util.APIContext;
import soccerBet.project.betfair.util.InflatedMarketPrices;
import soccerBet.project.betfair.util.InflatedMarketPrices.InflatedPrice;
import soccerBet.project.betfair.util.InflatedMarketPrices.InflatedRunner;
import soccerBet.project.objects.Classificacao;
import soccerBet.project.objects.Jogo;
import soccerBet.project.objects.MapeamentoMercados;
import soccerBet.project.objects.Mercados;
import soccerBet.project.objects.Precos;
import soccerBet.project.ruleEngine.EngineStart;
import soccerBet.project.ruleEngine.TempoJogo;

import soccerBet.project.betfair.dao.JogoDAO;
import soccerBet.project.contracts.RunnerContract;

public class ServiceManager extends Thread {

	// The session token
	public static APIContext apiContext = new APIContext();
	
//	private Integer LigaPortuguesaId = 269462;
	
	public JogoDAO jogoDao = new JogoDAO();
	
	public MercadoDAO mercadoDao = new MercadoDAO();
	
	public LigaDAO LigaDao = new LigaDAO();
	
	private Jogo jogo;
	
	public ServiceManager(){
		
	}
	
	public ServiceManager(Jogo jogo){
		
		this.jogo=jogo;
	}
	
	
	
	public void run() {
		
		GetEventsResp resp = null;
		int[] marketId = new int[1];
		BetsTM betsTM = new BetsTM();
		mercadoDao.getMercadosActivos();
		LigaDao.getLigaByJogo(jogo.getIdJogo());
		List<Mercados> removedMarkets = null;
		
		while(true){
			
			try {
				removedMarkets = new ArrayList<Mercados>();
				BFEvent[] eventDates = getEvents(resp, LigaDao.getLigaObj().getIdLigaBetfair());
				BFEvent[] gamesEvents = getEvents(resp,eventDates,jogo.getDate());

				MarketSummary[] markets = getMarkets(resp,gamesEvents,buildGameVs(this.jogo));

				for(Mercados mercado : mercadoDao.getMercadoObjList()){

					InflatedMarketPrices prices = getPrices(marketId,markets,mercado.getDesignacao());

					if(!prices.getMarketStatus().equals("CLOSED")){

						List<RunnerContract> runners = getRunners(prices);
						List<Precos> precosList = betsTM.makeTransaction(marketId, runners,this.jogo, mercado);
						callRulesEngine(this.jogo,precosList);
					}else{

						betsTM.closedMarket(marketId,this.jogo, mercado);
						removedMarkets.add(mercado);
					}
					
				}
				
				removeUnusedMarkets(mercadoDao.getMercadoObjList(),removedMarkets);
				
				Thread.sleep(10000);
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private void removeUnusedMarkets(List<Mercados> mercadoObjList,
			List<Mercados> removedMarkets) {
		// TODO Auto-generated method stub
		if(removedMarkets.size()>0){
			mercadoDao.getMercadoObjList().removeAll(removedMarkets);
		}
	}

	private void callRulesEngine(Jogo jogo, List<Precos> precosList) {
		// TODO Auto-generated method stub
		

		
		
		
		for(Precos p : precosList){
			EngineStart st = new EngineStart();
			st.Start(jogo, p);
			// and then dispose the session
			st.getKsession().dispose();
		}
		
		
		
	}

	private List<RunnerContract> getRunners(InflatedMarketPrices prices) {

		List<RunnerContract> runners = new ArrayList<RunnerContract>();
		
		for (InflatedRunner r : prices.getRunners()) {

			Double lay = 0.0;
			if (r.getLayPrices().size() > 0) {
				InflatedPrice p = r.getLayPrices().get(0);
				lay = p.getPrice();
			}

			Double back = 0.0;
			if (r.getBackPrices().size() > 0) {
				InflatedPrice p = r.getBackPrices().get(0);
				back = p.getPrice();
			}
			
			runners.add(new RunnerContract(back, lay, r.getSelectionId()));
		}
		
		return runners;
	}

	private String buildGameVs(Jogo jogo) {
		// TODO Auto-generated method stub
		jogoDao.getEquipas(jogo.getIdJogo());		
		
		String game = jogoDao.getEquipaCasaObj().getNome()+" x "+jogoDao.getEquipaForaObj().getNome();
		
		System.out.println(game);
		
		return game;
	}

	
	public BFEvent[] getEvents(GetEventsResp resp,Integer eventId){
		
		try {
			resp = GlobalAPI.getEvents(apiContext, eventId);
			BFEvent[] events = resp.getEventItems().getBFEvent();
			return events;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public BFEvent[] getEvents(GetEventsResp resp, BFEvent[] events, String str){

		try {
			BFEvent[] eventsList=null;

			if(events!=null){

				for(BFEvent event : events){
					if(event.getEventName().contains(str)){
						//prblema ao utilizar duas threads
						resp = GlobalAPI.getEvents(apiContext, event.getEventId());

						eventsList = resp.getEventItems().getBFEvent();

					}
				}
			
			}
			
			return eventsList;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;

	}
	
	public MarketSummary[] getMarkets(GetEventsResp resp,BFEvent[] gamesEvents,String gameStr){
		
		try {
		
			if(gamesEvents!=null){
			
				for(BFEvent game : gamesEvents){
					if(game.getEventName().contains(gameStr)){
						
						resp = GlobalAPI.getEvents(apiContext, game.getEventId());
						
						return resp.getMarketItems().getMarketSummary();
						
					}
				}
			}
					
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;

	}
	
	
	public InflatedMarketPrices getPrices(int[] marketId, MarketSummary[] markets,String str){
		
		InflatedMarketPrices prices = null;
		
		if(markets!=null){
			
			for(MarketSummary market : markets){
				
				if(market.getMarketName().contains(str)){
	
					try {
						prices = ExchangeAPI.getMarketPrices(Exchange.UK, apiContext, market.getMarketId());
						marketId[0] = market.getMarketId();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		if(prices==null){
			prices = new InflatedMarketPrices();
		}
		return prices;
			
	}
	
	public void efectuaLogin() {
		// TODO Auto-generated method stub
		
		String username = "perasoft";
		String password = "per@s0ft";
		
		try
		{
			GlobalAPI.login(apiContext, username, password);
		}
		catch (Exception e)
		{
			System.out.println("Não foi possivel efectuar o login");
		}
		
	}

	public static void placeBet(Precos preco){
		PrecosDAO precoDao = new PrecosDAO();
		RunnerDAO runDao = new RunnerDAO();
		
		preco.setPlacedBet(1);
		precoDao.guardarPrecos(preco);
		
		runDao.getRunnerByPreco(preco.getIdPrecos());
		
		runDao.getRunnerObj().setEstado(1);
		runDao.guardarRunner(runDao.getRunnerObj());
		
		
	}

	
	// Place a bet on the specified market.
	private static void placeBet(Integer marketId,Integer selectionId,BetTypeEnum typeBet,double price,double valor) throws Exception {
			
			// Set up the individual bet to be placed
			PlaceBets bet = new PlaceBets();
			bet.setMarketId(marketId);
			bet.setSelectionId(selectionId);
			bet.setBetCategoryType(BetCategoryTypeEnum.E);
			bet.setBetPersistenceType(BetPersistenceTypeEnum.NONE);
			bet.setBetType(typeBet);
			//odd
			bet.setPrice(price);
			//valor euros
			bet.setSize(valor);
			
				// We can ignore the array here as we only sent in one bet.
				PlaceBetsResult betResult = ExchangeAPI.placeBets(Exchange.UK, apiContext, new PlaceBets[] {bet})[0];
				
				if (betResult.getSuccess()) {
					System.out.println("Bet "+betResult.getBetId()+" placed. "+betResult.getSizeMatched() +" matched @ "+betResult.getAveragePriceMatched());
				} else {
					System.out.println("Failed to place bet: Problem was: "+betResult.getResultCode());
				}
	}


}
