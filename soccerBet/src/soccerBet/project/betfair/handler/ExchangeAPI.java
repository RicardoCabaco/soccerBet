package soccerBet.project.betfair.handler;

import soccerBet.project.betfair.util.APIContext;
import soccerBet.project.betfair.util.InflatedCompleteMarketPrices;
import soccerBet.project.betfair.util.InflatedMarketPrices;
import soccerBet.project.betfair.util.UsageMap;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.APIRequestHeader;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.APIResponseHeader;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.ArrayOfCancelBets;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.ArrayOfPlaceBets;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.ArrayOfUpdateBets;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.BetStatusEnum;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.BetsOrderByEnum;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.CancelBets;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.CancelBetsE;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.CancelBetsErrorEnum;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.CancelBetsReq;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.CancelBetsResp;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.CancelBetsResult;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.GetAccountFunds;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.GetAccountFundsErrorEnum;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.GetAccountFundsReq;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.GetAccountFundsResp;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.GetCompleteMarketPricesCompressed;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.GetCompleteMarketPricesCompressedReq;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.GetCompleteMarketPricesCompressedResp;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.GetCompleteMarketPricesErrorEnum;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.GetMUBets;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.GetMUBetsErrorEnum;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.GetMUBetsReq;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.GetMUBetsResp;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.GetMarket;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.GetMarketErrorEnum;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.GetMarketPricesCompressed;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.GetMarketPricesCompressedReq;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.GetMarketPricesCompressedResp;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.GetMarketPricesErrorEnum;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.GetMarketReq;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.GetMarketResp;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.MUBet;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.Market;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.PlaceBets;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.PlaceBetsE;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.PlaceBetsErrorEnum;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.PlaceBetsReq;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.PlaceBetsResp;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.PlaceBetsResult;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.SortOrderEnum;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.UpdateBets;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.UpdateBetsE;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.UpdateBetsErrorEnum;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.UpdateBetsReq;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.UpdateBetsResp;
import soccerBet.project.betfair.exchange.BFExchangeServiceStub.UpdateBetsResult;

public class ExchangeAPI {

	// There are 2 Betfair exchanges, so a stub connection is needed for each
	public static enum Exchange {UK, AUS};
	private static BFExchangeServiceStub stub_UK;
	private static BFExchangeServiceStub stub_AUS;

	// This stub is used to make all requests to the Betfair Exchange API
	// The exchange API is used to place bets and query markets.
	private static BFExchangeServiceStub getStub(Exchange exch) throws Exception {
		switch (exch) {
			case UK: 
				// Lazy load the Exchange service stub soccerBet.project.betfair by AXIS.
				if (stub_UK == null) {
					stub_UK = new BFExchangeServiceStub("https://api.betfair.com/exchange/v5/BFExchangeService");
			        
			        // You may set up the connection parameters of the stub here if necessary
			        // For example: Wait 20 seconds for a response from the API
					stub_UK._getServiceClient().getOptions().setTimeOutInMilliSeconds(20 * 1000); 
					stub_UK._getServiceClient().getOptions().setProperty(org.apache.axis2.transport.http.HTTPConstants.MC_ACCEPT_GZIP, "true");
					stub_UK._getServiceClient().getOptions().setProperty(org.apache.axis2.transport.http.HTTPConstants.MC_GZIP_RESPONSE, "true");
					//ADICIONEI ESTAS 2 LINHAS
					stub_UK._getServiceClient().getOptions().setProperty(org.apache.axis2.transport.http.HTTPConstants.AUTO_RELEASE_CONNECTION, "true");
					stub_UK._getServiceClient().getOptions().setProperty(org.apache.axis2.transport.http.HTTPConstants.CACHED_HTTP_CLIENT, "true");
				}
				return stub_UK;
			
			case AUS:
				// Lazy load the Exchange service stub soccerBet.project.betfair by AXIS.
				if (stub_AUS == null) {
					stub_AUS = new BFExchangeServiceStub("https://api-au.betfair.com/exchange/v5/BFExchangeService");
			        
			        // You may set up the connection parameters of the stub here if necessary
			        // For example: Wait 20 seconds for a response from the API
					stub_AUS._getServiceClient().getOptions().setTimeOutInMilliSeconds(20 * 1000); 
					stub_AUS._getServiceClient().getOptions().setProperty(org.apache.axis2.transport.http.HTTPConstants.MC_ACCEPT_GZIP, "true");
					stub_AUS._getServiceClient().getOptions().setProperty(org.apache.axis2.transport.http.HTTPConstants.MC_GZIP_RESPONSE, "true");
				}
				return stub_AUS;
		}

		// Should never happen 
		throw new RuntimeException("Unable to get stub for exchange "+exch);
	}
	
	// Save the data from the request header into the context
	private static void setHeaderDataToContext(APIContext context, APIResponseHeader header) {
		context.setToken(header.getSessionToken()); // May be updated in each call.
		context.setLastCall(header.getTimestamp().getTime());
	}
	
	
	// Get the request header to add to the request
	private static APIRequestHeader getHeader(String token) {
        APIRequestHeader header = new APIRequestHeader();
        // The header must have the session context.getToken() attached.
        header.setSessionToken(token);
        return header;
	}

	// Get the account funds for the exchange
	public static GetAccountFundsResp getAccountFunds(Exchange exch, APIContext context) throws Exception {
		// Create a request object
        GetAccountFundsReq request = new GetAccountFundsReq();
        request.setHeader(getHeader(context.getToken()));
        
        // Create the GetAccountFunds message and attach the request to it.
        GetAccountFunds msg = new GetAccountFunds();
        msg.setRequest(request);
        
        // Send the request to the Betfair Exchange Service.
        GetAccountFundsResp resp = getStub(exch).getAccountFunds(msg).getResult();
        context.getUsage().addCall("getAccountFunds");
        
        // Check the response code, and throw and exception if login failed
        if (resp.getErrorCode() != GetAccountFundsErrorEnum.OK)
        {
        	throw new IllegalArgumentException("Failed to retrieve account funds: "+resp.getErrorCode() + " Minor Error:"+resp.getMinorErrorCode()+ " Header Error:"+resp.getHeader().getErrorCode());
        }

        // Transfer the response data back to the API context
        setHeaderDataToContext(context, resp.getHeader());
        getStub(exch)._getServiceClient().cleanupTransport();
        return resp;
	}
	
	// Get the account funds for the exchange
	public static Market getMarket(Exchange exch, APIContext context, int marketId) throws Exception {
		// Create a request object
        GetMarketReq request = new GetMarketReq();
        request.setHeader(getHeader(context.getToken()));
        
        // Set the parameters
        request.setMarketId(marketId);
        
        // Create the GetMarket message and attach the request to it.
        GetMarket msg = new GetMarket();
        msg.setRequest(request);
        
        // Send the request to the Betfair Exchange Service.
        GetMarketResp resp = getStub(exch).getMarket(msg).getResult();
        context.getUsage().addCall("getMarket");
        
        // Check the response code, and throw and exception if call failed
        if (resp.getErrorCode() != GetMarketErrorEnum.OK)
        {
        	throw new IllegalArgumentException("Failed to retrieve data: "+resp.getErrorCode() + " Minor Error:"+resp.getMinorErrorCode()+ " Header Error:"+resp.getHeader().getErrorCode());
        	
        }
        // Transfer the response data back to the API context
        setHeaderDataToContext(context, resp.getHeader());

        return resp.getMarket();
	}

	// Get the compressed version of the market prices data and re-inflate it.
	public static InflatedMarketPrices getMarketPrices(Exchange exch, APIContext context, int marketId) throws Exception {
		// Create a request object
		GetMarketPricesCompressedReq request = new GetMarketPricesCompressedReq();
        request.setHeader(getHeader(context.getToken()));
        
        // Set the parameters
        request.setMarketId(marketId);

        // Create the message and attach the request to it.
        GetMarketPricesCompressed msg = new GetMarketPricesCompressed();
        msg.setRequest(request);
        
        // Send the request to the Betfair Exchange Service.
        GetMarketPricesCompressedResp resp = getStub(exch).getMarketPricesCompressed(msg).getResult();
        context.getUsage().addCall("getMarketPricesCompressed");

        // Check the response code, and throw and exception if call failed
        if (resp.getErrorCode() != GetMarketPricesErrorEnum.OK)
        {
        	throw new IllegalArgumentException("Failed to retrieve data: "+resp.getErrorCode() + " Minor Error:"+resp.getMinorErrorCode()+ " Header Error:"+resp.getHeader().getErrorCode());
        	
        }
        // Transfer the response data back to the API context
        setHeaderDataToContext(context, resp.getHeader());

        InflatedMarketPrices iMP = new InflatedMarketPrices(resp.getMarketPrices());
        getStub(exch)._getServiceClient().cleanupTransport();
        return iMP;
	}
	
	public static InflatedCompleteMarketPrices getCompleteMarketPrices(Exchange exch, APIContext context, int marketId) throws Exception {
		// create a request object
		GetCompleteMarketPricesCompressedReq request = new GetCompleteMarketPricesCompressedReq();
		request.setHeader(getHeader(context.getToken()));
		
		// Set the parameters
		request.setMarketId(marketId);
		
		// Create the message and attach the request to it.
		GetCompleteMarketPricesCompressed msg = new GetCompleteMarketPricesCompressed();
		msg.setRequest(request);
		
		// Send the request to the Betfair Exchange Service.
		GetCompleteMarketPricesCompressedResp resp = getStub(exch).getCompleteMarketPricesCompressed(msg).getResult();
		context.getUsage().addCall("getCompleteMarketPricesCompressed");
		
		// Check the response code and throw an exception if the call failed 
		if (resp.getErrorCode() != GetCompleteMarketPricesErrorEnum.OK) {
			throw new IllegalArgumentException("Failed to retrieve data: "+resp.getErrorCode() + " Minor Error:"+resp.getMinorErrorCode()+ " Header Error:"+resp.getHeader().getErrorCode());
		}
		
		// Transfer the response data back to the API Context
		setHeaderDataToContext(context, resp.getHeader());
		
		return new InflatedCompleteMarketPrices(resp.getCompleteMarketPrices());
	}
	
	// Get all matched and unmatched bets on the market
	public static MUBet[] getMUBets(Exchange exch, APIContext context, int marketId) throws Exception {
		
		// Create a request object
		GetMUBetsReq request = new GetMUBetsReq();
		request.setHeader(getHeader(context.getToken()));
		
        // Set the parameters
		if (marketId > 0) {
			request.setMarketId(marketId);
		}
        request.setBetStatus(BetStatusEnum.MU);
        request.setSortOrder(SortOrderEnum.ASC);
        request.setOrderBy(BetsOrderByEnum.BET_ID);
        request.setRecordCount(100);
        request.setStartRecord(0);

        // Create the message and attach the request to it.
        GetMUBets msg = new GetMUBets();
        msg.setRequest(request);

        // Send the request to the Betfair Exchange Service.
        GetMUBetsResp resp = getStub(exch).getMUBets(msg).getResult();
        context.getUsage().addCall("getMUBets");
        
        // Check the response code, and throw and exception if call failed
        if ((resp.getErrorCode() != GetMUBetsErrorEnum.OK) &&
        		(resp.getErrorCode() != GetMUBetsErrorEnum.NO_RESULTS))
        {
        	throw new IllegalArgumentException("Failed to retrieve data: "+resp.getErrorCode() + " Minor Error:"+resp.getMinorErrorCode()+ " Header Error:"+resp.getHeader().getErrorCode());
        	
        }
        // Transfer the response data back to the API context
        setHeaderDataToContext(context, resp.getHeader());

        if (resp.getErrorCode() == GetMUBetsErrorEnum.NO_RESULTS) {
        	return new MUBet[0];
        } else {
        	return resp.getBets().getMUBet();
        }
	}
	
	// Place some bets on the market
	public static PlaceBetsResult[] placeBets(Exchange exch, APIContext context, PlaceBets[] bets) throws Exception {
		
		// Create a request object
		PlaceBetsReq request = new PlaceBetsReq();
		request.setHeader(getHeader(context.getToken()));
		
        // Set the parameters
        ArrayOfPlaceBets betsArray = new ArrayOfPlaceBets();
        betsArray.setPlaceBets(bets);
        request.setBets(betsArray);

        // Create the message and attach the request to it.
        PlaceBetsE msg = new PlaceBetsE();
        msg.setRequest(request);

        // Send the request to the Betfair Exchange Service.
        PlaceBetsResp resp = getStub(exch).placeBets(msg).getResult();
        context.getUsage().addCall("placeBets");
        
        // Check the response code, and throw and exception if call failed
        if (resp.getErrorCode() != PlaceBetsErrorEnum.OK)
        {
        	throw new IllegalArgumentException("Failed to retrieve data: "+resp.getErrorCode() + " Minor Error:"+resp.getMinorErrorCode()+ " Header Error:"+resp.getHeader().getErrorCode());
        	
        }

        // Transfer the response data back to the API context
        setHeaderDataToContext(context, resp.getHeader());

        return resp.getBetResults().getPlaceBetsResult();
	}
	
	// Update a bet on the market
	public static UpdateBetsResult[] updateBets(Exchange exch, APIContext context, UpdateBets[] bets) throws Exception {
		
		// Create a request object
		UpdateBetsReq request = new UpdateBetsReq();
		request.setHeader(getHeader(context.getToken()));
		
        // Set the parameters
        ArrayOfUpdateBets betsArray = new ArrayOfUpdateBets();
        betsArray.setUpdateBets(bets);
        request.setBets(betsArray);

        // Create the message and attach the request to it.
        UpdateBetsE msg = new UpdateBetsE();
        msg.setRequest(request);

        // Send the request to the Betfair Exchange Service.
        UpdateBetsResp resp = getStub(exch).updateBets(msg).getResult();
        context.getUsage().addCall("updateBets");
        
        // Check the response code, and throw and exception if call failed
        if (resp.getErrorCode() != UpdateBetsErrorEnum.OK)
        {
        	throw new IllegalArgumentException("Failed to retrieve data: "+resp.getErrorCode() + " Minor Error:"+resp.getMinorErrorCode()+ " Header Error:"+resp.getHeader().getErrorCode());
        	
        }

        // Transfer the response data back to the API context
        setHeaderDataToContext(context, resp.getHeader());

        return resp.getBetResults().getUpdateBetsResult();
	}
	
	// Cancel a bet on the market
	public static CancelBetsResult[] cancelBets(Exchange exch, APIContext context, CancelBets[] bets) throws Exception {
		
		// Create a request object
		CancelBetsReq request = new CancelBetsReq();
		request.setHeader(getHeader(context.getToken()));
		
        // Set the parameters
        ArrayOfCancelBets betsArray = new ArrayOfCancelBets();
        betsArray.setCancelBets(bets);
        request.setBets(betsArray);

        // Create the message and attach the request to it.
        CancelBetsE msg = new CancelBetsE();
        msg.setRequest(request);

        // Send the request to the Betfair Exchange Service.
        CancelBetsResp resp = getStub(exch).cancelBets(msg).getResult();
        context.getUsage().addCall("cancelBets");
        
        // Check the response code, and throw and exception if call failed
        if (resp.getErrorCode() != CancelBetsErrorEnum.OK)
        {
        	throw new IllegalArgumentException("Failed to retrieve data: "+resp.getErrorCode() + " Minor Error:"+resp.getMinorErrorCode()+ " Header Error:"+resp.getHeader().getErrorCode());
        	
        }

        // Transfer the response data back to the API context
        setHeaderDataToContext(context, resp.getHeader());

        return resp.getBetResults().getCancelBetsResult();
	}
}
