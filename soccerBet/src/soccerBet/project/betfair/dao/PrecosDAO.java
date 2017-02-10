package soccerBet.project.betfair.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import soccerBet.project.objects.*;
import soccerBet.project.utils.*;

public class PrecosDAO {
	
	private Precos precoObj;
	
	private Apostas apostaObj;
	
	private Runner runnerObj;
	
	
	public void guardarPrecos(Precos preco){
	       

		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		session.saveOrUpdate(preco);

		// Commit the transaction
		session.getTransaction().commit();
		HibernateUtil.shutdown();
		session.close();
	 
		
	 }
	
	 public void gePreco(Integer idPrecos) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Precos preco = (Precos)  session.
		createQuery("select p from Precos as p where p.idPrecos = ?").setInteger(0, idPrecos).uniqueResult();
		
		this.setPrecoObj(preco);
		HibernateUtil.shutdown();
		session.close();

	 }

	public void setPrecoObj(Precos precoObj) {
		this.precoObj = precoObj;
	}

	public Precos getPrecoObj() {
		return precoObj;
	}

	public void guardarPrecosRunner(Precos preco) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		session.saveOrUpdate(preco);
		session.saveOrUpdate(preco.getRunner());

		// Commit the transaction
		session.getTransaction().commit();
		HibernateUtil.shutdown();
		session.close();
	}

	public void getAposta(Integer idPrecos) {
		// TODO Auto-generated method stub
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Apostas aposta = (Apostas)  session.
		createQuery("select a from Precos as p join p.runner as r join r.apostas as a where p.idPrecos = ?").setInteger(0, idPrecos).uniqueResult();
		
		this.setApostaObj(aposta);
		HibernateUtil.shutdown();
		session.close();
	}

	public void getRunner(Integer idPrecos) {
		// TODO Auto-generated method stub
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Runner runner = (Runner)  session.
		createQuery("select r from Precos as p join p.runner as r where p.idPrecos = ?").setInteger(0, idPrecos).uniqueResult();
		
		this.setRunnerObj(runner);
		
		HibernateUtil.shutdown();
		session.close();
	}
	
	public void setApostaObj(Apostas apostaObj) {
		this.apostaObj = apostaObj;
	}

	public Apostas getApostaObj() {
		return apostaObj;
	}

	public void setRunnerObj(Runner runnerObj) {
		this.runnerObj = runnerObj;
	}

	public Runner getRunnerObj() {
		return runnerObj;
	}
	
	    
	}