package soccerBet.project.betfair.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import soccerBet.project.objects.*;
import soccerBet.project.utils.*;


public class RunnerDAO {
	
	private Runner runnerObj;
	
	public void geRunner(Integer selectionId) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Runner run = (Runner)  session.
		createQuery("select r from Runner as r where r.selectionId = ?").setInteger(0, selectionId).uniqueResult();
		
		session.close();
		
		this.setRunnerObj(run);
		HibernateUtil.shutdown();

	}
	
	
	public void getRunnerByPreco(Integer idPreco) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Runner run = (Runner)  session.
		createQuery("select r from Precos as p join p.runner as r where p.idPrecos = ?").setInteger(0, idPreco).uniqueResult();
		
		session.close();
		
		this.setRunnerObj(run);
		HibernateUtil.shutdown();

	}
	
	
	 public void guardarRunner(Runner runner){
	       

		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		session.saveOrUpdate(runner);

		// Commit the transaction
		session.getTransaction().commit();
		HibernateUtil.shutdown(); 
		session.close();
	 
		
	 }


	public void setRunnerObj(Runner runnerObj) {
		this.runnerObj = runnerObj;
	}


	public Runner getRunnerObj() {
		return runnerObj;
	}


	public void geRunnerBySelectionIdAndMapMercado(int selectionId,
			int idMapMercado) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Runner run = (Runner)  session.
		createQuery("select r from Runner as r join r.mapeamentoMercados as mm where r.selectionId = ? and mm.idMapMercado = ?").setInteger(0, selectionId).setInteger(1, idMapMercado).uniqueResult();
		
		this.setRunnerObj(run);
		HibernateUtil.shutdown();
		session.close();
		
	}
 
	
	}