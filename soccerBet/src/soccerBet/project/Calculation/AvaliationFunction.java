package soccerBet.project.Calculation;

import org.apache.commons.math3.distribution.PoissonDistribution;

import soccerBet.project.objects.Classificacao;

public class AvaliationFunction {

	private Team home;
	private Team away;
	
	
	public AvaliationFunction(){
		
	}
	
	
	public AvaliationFunction(Team home, Team away) {
		// TODO Auto-generated constructor stub
		
		home.setAtackStrenght((double)home.getAGSHome()/(home.getAGS())); 
		home.setDefenceStrenght((double)away.getAGCAway()/(away.getAGC()));
		
		away.setAtackStrenght((double)away.getAGSHome()/(away.getAGS())); 
		away.setDefenceStrenght((double)home.getAGCAway()/(home.getAGC()));
	
		home.H = ((double)home.getAtackStrenght() * (double)away.getDefenceStrenght() * (double)home.getAGS());
		
		away.H = away.getAtackStrenght() * home.getDefenceStrenght() *  away.getAGS();
		
        PoissonDistribution HOMEteam = new PoissonDistribution(home.H);
		home.setValueBet(100-(HOMEteam.probability(0) * 100));
		
		this.home = home;
		
        PoissonDistribution awayteam = new PoissonDistribution(away.H);
        away.setValueBet(100-(awayteam.probability(0) * 100));
		
		this.away = away;
	}

	public double averageGoalsScored(Integer goals,Integer games){
		return goals/games;
	}
	
	
	public double strenght(double HomeAvarage,double seasonAverage){
		return HomeAvarage/seasonAverage;
	}
	




	public void setHome(Team home) {
		this.home = home;
	}


	public Team getHome() {
		return home;
	}


	public void setAway(Team away) {
		this.away = away;
	}


	public Team getAway() {
		return away;
	}
}
