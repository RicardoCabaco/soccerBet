package soccerBet.project.funds;

import soccerBet.project.betfair.exchange.BFExchangeServiceStub.GetAccountFundsResp;
import soccerBet.project.betfair.global.BFGlobalServiceStub.GetEventsResp;
import soccerBet.project.betfair.handler.ExchangeAPI;
import soccerBet.project.betfair.handler.GlobalAPI;
import soccerBet.project.betfair.handler.ExchangeAPI.Exchange;
import soccerBet.project.main.FlowManager;
import soccerBet.project.main.ServiceManager;

public class Funds extends Thread {

	public static double avalableFunds = 0.0;
	
	
	public void run() {
		// TODO Auto-generated method stub
		
		while(true){
		
		try {
			GetEventsResp resp = GlobalAPI.getEvents(ServiceManager.apiContext, 269462);
			//GetAccountFundsResp funds = ExchangeAPI.getAccountFunds(Exchange.UK, ServiceManager.apiContext);
		
//			if(funds.getBalance()<=20)
//				avalableFunds = 0;
//			else
//				avalableFunds = funds.getBalance() * 0.1;
//			
//			System.out.println("Funds Avalable: "+avalableFunds);
//			
		this.sleep(20000);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		
	}
	
}
