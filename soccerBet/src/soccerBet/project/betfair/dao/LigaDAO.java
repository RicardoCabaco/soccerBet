package soccerBet.project.betfair.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import soccerBet.project.objects.*;
import soccerBet.project.utils.*;

public class LigaDAO {

	private Liga ligaObj;
	



	
	public void getLigaByJogo(Integer idjogo) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Liga liga = (Liga)  session.
		createQuery("select l from Jogo as j join j.jornada as jorn join jorn.epocaLiga as el join el.liga as l where j.idJogo = ?").setInteger(0, idjogo).uniqueResult();
		
		this.setLigaObj(liga);
		
		session.close();
		
		

	}





	public void setLigaObj(Liga ligaObj) {
		this.ligaObj = ligaObj;
	}





	public Liga getLigaObj() {
		return ligaObj;
	}






}