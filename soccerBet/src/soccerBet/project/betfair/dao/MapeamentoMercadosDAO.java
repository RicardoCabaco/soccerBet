package soccerBet.project.betfair.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import soccerBet.project.objects.*;
import soccerBet.project.utils.*;

public class MapeamentoMercadosDAO {

	private MapeamentoMercados mapeamentoMercadoObj;
	
	
	
	public void insertToDB(MapeamentoMercados mapMerc) {
		// TODO Auto-generated method stub

		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		session.save(mapMerc);

		// Commit the transaction
		session.getTransaction().commit();
		HibernateUtil.shutdown();
	}

	public void getMapeamentoMercado(Integer marketId) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		
		MapeamentoMercados merc = (MapeamentoMercados)  session.
		createQuery("select merc from MapeamentoMercados as merc where merc.marketId = ?").setInteger(0, marketId).uniqueResult();
		
		session.close();
		
		this.setMapeamentoMercadoObj(merc);

	}
	
	public void updateToDB(MapeamentoMercados mapMerc) {
		
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		session.update(mapMerc);

		// Commit the transaction
		session.getTransaction().commit();
		HibernateUtil.shutdown();
	}

	public void setMapeamentoMercadoObj(MapeamentoMercados mapeamentoMercadoObj) {
		this.mapeamentoMercadoObj = mapeamentoMercadoObj;
	}

	public MapeamentoMercados getMapeamentoMercadoObj() {
		return mapeamentoMercadoObj;
	}

	public void getMapeamentoMercadoByMaketAndJogo(int marketId, int idJogo) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		MapeamentoMercados merc = (MapeamentoMercados)  session.
		createQuery("select merc from MapeamentoMercados as merc join merc.jogo as j where merc.marketId = ? and j.idJogo = ?").setInteger(0, marketId).setInteger(1, idJogo).uniqueResult();
		
		session.close();
		
		this.setMapeamentoMercadoObj(merc);

	}

	


}