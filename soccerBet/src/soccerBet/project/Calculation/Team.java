package soccerBet.project.Calculation;

import org.apache.commons.math3.distribution.PoissonDistribution;

import soccerBet.project.objects.Classificacao;
import soccerBet.project.objects.Equipa;

public class Team {

	private double AGSHome;
	private double AGSAway;
	private double AGCHome;
	private double AGCAway;
	
	private double AGS;
	private double AGC;
	
	private double atackStrenght;
	private double defenceStrenght;
	public double H;
	public double valueBet;
	
	private boolean favorite = false;
		
	
	public Team(Classificacao cHome, Equipa equipa) {
		// TODO Auto-generated constructor stub
		
	    this.setAGSHome((double)cHome.getGolosMarcadosCasa()/cHome.getJogosCasa());
	    this.setAGSAway((double)cHome.getGolosMarcadosFora()/cHome.getJogosFora());
	    
	    this.setAGCHome((double)cHome.getGolosSofridosCasa()/cHome.getJogosCasa());
	    this.setAGCAway((double)cHome.getGolosSofridosFora()/cHome.getJogosFora());
		
	    this.setAGS(((double)cHome.getGolosMarcadosCasa()+cHome.getGolosMarcadosFora())/(cHome.getJogosCasa()+cHome.getJogosFora()));
	    this.setAGC(((double)cHome.getGolosSofridosCasa()+cHome.getGolosSofridosFora())/(cHome.getJogosCasa()+cHome.getJogosFora()));
		
	    this.favorite = (equipa.getFavorita() != 0);
	}





	public void setH(double h) {
		H = h;
	}


	public double getH() {
		return H;
	}


	public void setValueBet(double valueBet) {
		this.valueBet = valueBet;
	}


	public double getValueBet() {
		return valueBet;
	}





	public void setAGSHome(double aGSHome) {
		AGSHome = aGSHome;
	}





	public double getAGSHome() {
		return AGSHome;
	}





	public void setAGSAway(double aGSAway) {
		AGSAway = aGSAway;
	}





	public double getAGSAway() {
		return AGSAway;
	}





	public void setAGCHome(double aGCHome) {
		AGCHome = aGCHome;
	}





	public double getAGCHome() {
		return AGCHome;
	}





	public void setAGCAway(double aGCAway) {
		AGCAway = aGCAway;
	}





	public double getAGCAway() {
		return AGCAway;
	}





	public void setAtackStrenght(double atackStrenght) {
		this.atackStrenght = atackStrenght;
	}





	public double getAtackStrenght() {
		return atackStrenght;
	}





	public void setDefenceStrenght(double defenceStrenght) {
		this.defenceStrenght = defenceStrenght;
	}





	public double getDefenceStrenght() {
		return defenceStrenght;
	}





	public void setAGS(double aGS) {
		AGS = aGS;
	}





	public double getAGS() {
		return AGS;
	}





	public void setAGC(double aGC) {
		AGC = aGC;
	}





	public double getAGC() {
		return AGC;
	}





	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}





	public boolean isFavorite() {
		return favorite;
	}

}
