package soccerBet.project.betfair.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import soccerBet.project.objects.*;
import soccerBet.project.utils.*;

public class ClassificacaoDAO {

	private Classificacao classificacaoObj;
	


	public void getClassificacao(Integer idEquipa) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Classificacao classificacao = (Classificacao)  session.
		createQuery("select c from Classificacao as c join c.equipa as e where e.idEquipa = ?").setInteger(0, idEquipa).uniqueResult();
		
		session.close();
		
		this.setClassificacaoObj(classificacao);
		
		

	}



	public void setClassificacaoObj(Classificacao classificacaoObj) {
		this.classificacaoObj = classificacaoObj;
	}



	public Classificacao getClassificacaoObj() {
		return classificacaoObj;
	}
	
	

}