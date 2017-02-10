package soccerBet.project.ruleEngine;

import java.util.Date;

public class TempoJogo {

	private long minutos;
	
	public TempoJogo(long minutos){
		this.minutos=minutos;
	}

	public TempoJogo() {
		// TODO Auto-generated constructor stub
	}

	public void setMinutos(long minutos) {
		this.minutos = minutos;
	}

	public long getMinutos() {
		return minutos;
	}

	public void getTimePlayed(long time) {
		// TODO Auto-generated method stub
		
		this.minutos = ((new Date().getTime()) - time) / (60 * 1000);
		
	}
	
}
