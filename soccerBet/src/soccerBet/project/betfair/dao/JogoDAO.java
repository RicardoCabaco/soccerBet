package soccerBet.project.betfair.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import soccerBet.project.objects.*;
import soccerBet.project.utils.*;

public class JogoDAO {

	private Jogo jogoObj;
	
	private Equipa equipaCasaObj;
	
	private Equipa equipaForaObj;
	
	private List<Jogo> jogoObjList;


	public void getJogo(Integer jogoId) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Jogo jogo = (Jogo)  session.
		createQuery("select j from Jogo as j where j.idJogo = ?").setInteger(0, jogoId).uniqueResult();
		
		session.close();
		
		this.setJogoObj(jogo);


	}
	
	public void getJogoByDate(String data) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		
		List<Jogo> jogosList = (List<Jogo>)  session.
		createQuery("select j from Jogo as j where j.date = ?").setString(0, data).list();
		
		session.close();
		
		this.setJogoObjList(jogosList);

	}
	
	public void getEquipas(Integer jogoId) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Equipa equipaCasa= (Equipa)  session.
		createQuery("select e from Jogo as j join j.equipaByIdEquipaCasa as e where j.idJogo = ?").setInteger(0, jogoId).uniqueResult();
		
		this.setEquipaCasaObj(equipaCasa);
		
		Equipa equipaFora= (Equipa)  session.
		createQuery("select e from Jogo as j join j.equipaByIdEquipaFora as e where j.idJogo = ?").setInteger(0, jogoId).uniqueResult();
		
		this.setEquipaForaObj(equipaFora);
		
		session.close();
		

	}
	
	public void getEquipaCasa(Integer jogoId) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Equipa equipaCasa= (Equipa)  session.
		createQuery("select e from Jogo as j join j.equipaByIdEquipaCasa as e where j.idJogo = ?").setInteger(0, jogoId).uniqueResult();
		
		this.setEquipaCasaObj(equipaCasa);
		
		session.close();
		

	}
	
	public void getEquipaFora(Integer jogoId) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Equipa equipaFora= (Equipa)  session.
		createQuery("select e from Jogo as j join j.equipaByIdEquipaFora as e where j.idJogo = ?").setInteger(0, jogoId).uniqueResult();
		
		this.setEquipaForaObj(equipaFora);
		
		session.close();
		

	}


	public void setJogoObj(Jogo jogoObj) {
		this.jogoObj = jogoObj;
	}

	public Jogo getJogoObj() {
		return jogoObj;
	}

	public void setJogoObjList(List<Jogo> jogoObjList) {
		this.jogoObjList = jogoObjList;
	}

	public List<Jogo> getJogoObjList() {
		return jogoObjList;
	}

	public void setEquipaCasaObj(Equipa equipaCasaObj) {
		this.equipaCasaObj = equipaCasaObj;
	}

	public Equipa getEquipaCasaObj() {
		return equipaCasaObj;
	}

	public void setEquipaForaObj(Equipa equipaForaObj) {
		this.equipaForaObj = equipaForaObj;
	}

	public Equipa getEquipaForaObj() {
		return equipaForaObj;
	}


}