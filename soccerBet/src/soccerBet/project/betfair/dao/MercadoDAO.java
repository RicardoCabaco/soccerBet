package soccerBet.project.betfair.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import soccerBet.project.objects.*;
import soccerBet.project.utils.*;

public class MercadoDAO {

	private Mercados mercadoObj;
	
	private List<Mercados> mercadoObjList;

	public void getMercado(Integer idMercado) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Mercados mercado = (Mercados)  session.
		createQuery("select m from Mercados as m where m.idMercado = ?").setInteger(0, idMercado).uniqueResult();
		
		session.close();
		
		this.setMercadoObj(mercado);


	}
	
	public void getMercadosActivos() {

		Session session = HibernateUtil.getSessionFactory().openSession();
		
		List<Mercados> mercadosList = (List<Mercados>)  session.
		createQuery("select m from Mercados as m where m.estado = 1").list();
		
		session.close();
		
		this.setMercadoObjList(mercadosList);

	}
	

	public void setMercadoObj(Mercados mercadoObj) {
		this.mercadoObj = mercadoObj;
	}

	public Mercados getMercadoObj() {
		return mercadoObj;
	}

	public void setMercadoObjList(List<Mercados> mercadoObjList) {
		this.mercadoObjList = mercadoObjList;
	}

	public List<Mercados> getMercadoObjList() {
		return mercadoObjList;
	}



}