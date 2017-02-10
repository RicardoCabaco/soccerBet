package soccerBet.project.betfair.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import soccerBet.project.objects.*;
import soccerBet.project.utils.*;

public class ApostasDAO {

	private Apostas apostaObj;
	
	private List<Apostas> apostaObjList;

	public void getAposta(Integer idAposta) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Apostas aposta = (Apostas)  session.
		createQuery("select a from Apostas as a where a.idAposta = ?").setInteger(0, idAposta).uniqueResult();
		
		session.close();
		
		this.setApostaObj(aposta);
		
	

	}
	
	public void getApostasByMercado(Integer idMercado) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		
		List<Apostas> apostasList = (List<Apostas>)  session.
		createQuery("select a from Apostas as a join a.mercados as m where m.idMercado = ? order by a.ordem asc").setInteger(0, idMercado).list();
		
		session.close();
		
		this.setApostaObjList(apostasList);

	}

	public void setApostaObj(Apostas apostaObj) {
		this.apostaObj = apostaObj;
	}

	public Apostas getApostaObj() {
		return apostaObj;
	}

	public void setApostaObjList(List<Apostas> apostaObjList) {
		this.apostaObjList = apostaObjList;
	}

	public List<Apostas> getApostaObjList() {
		return apostaObjList;
	}
	




}