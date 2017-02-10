package soccerBet.project.ruleEngine;

import java.util.ArrayList;

import org.kie.api.KieServices;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import soccerBet.project.Calculation.AvaliationFunction;
import soccerBet.project.Calculation.Team;
import soccerBet.project.betfair.dao.ClassificacaoDAO;
import soccerBet.project.betfair.dao.JogoDAO;
import soccerBet.project.betfair.dao.PrecosDAO;
import soccerBet.project.objects.Classificacao;
import soccerBet.project.objects.Jogo;
import soccerBet.project.objects.Precos;

public class EngineStart {
	
	private KieSession ksession;
	
	public JogoDAO jogoDao = new JogoDAO();
	public PrecosDAO precosDao = new PrecosDAO();
	
	public EngineStart(){
		
		this.ksession = callRuleEngine();
	}
	
	public void Start(Jogo jogo, Precos pri) {

		
		ClassificacaoDAO cDao = new ClassificacaoDAO();

		TempoJogo tjogo = new TempoJogo();
		tjogo.getTimePlayed(jogo.getData().getTime());

		jogoDao.getEquipaCasa(jogo.getIdJogo());
		
		cDao.getClassificacao(jogoDao.getEquipaCasaObj().getIdEquipa());
		Classificacao cHome = cDao.getClassificacaoObj();

		Team home = new Team(cHome, jogoDao.getEquipaCasaObj());
		
		jogoDao.getEquipaFora(jogo.getIdJogo());
		cDao.getClassificacao(jogoDao.getEquipaForaObj().getIdEquipa());
		
		Classificacao cAway = cDao.getClassificacaoObj();

		Team away = new Team(cAway, jogoDao.getEquipaForaObj());

		AvaliationFunction af = new AvaliationFunction(home, away);

		ApostaRule ar = new ApostaRule();
		
		precosDao.getAposta(pri.getIdPrecos());
		precosDao.getRunner(pri.getIdPrecos());
		
		ar.setTipoAposta(precosDao.getApostaObj().getDescricao());
		ar.setEstado(precosDao.getRunnerObj().getEstado());
		
		this.ksession.insert(ar);
		this.ksession.insert(af.getHome());
		this.ksession.insert(af.getAway());

		this.ksession.insert(tjogo);
		this.ksession.insert(pri);
		// and fire the rules
		this.ksession.fireAllRules();

		

	}

	private KieSession callRuleEngine() {
		// TODO Auto-generated method stub

		KieServices ks = KieServices.Factory.get();

		// From the kie services, a container is created from the classpath
		KieContainer kc = ks.getKieClasspathContainer();

		// From the container, a session is created based on
		// its definition and configuration in the META-INF/kmodule.xml file
		KieSession ksess= kc.newKieSession("HelloWorldKS");

		// Once the session is created, the application can interact with it
		// In this case it is setting a global as defined in the
		// org/drools/examples/helloworld/HelloWorld.drl file
		ksess.setGlobal("list", new ArrayList<Object>());

		// The application can also setup listeners
		ksess.addEventListener(new DebugAgendaEventListener());
		ksess.addEventListener(new DebugRuleRuntimeEventListener());

		return ksess;

		// To setup a file based audit logger, uncomment the next line
		// KieRuntimeLogger logger = ks.getLoggers().newFileLogger( ksession,
		// "./helloworld" );

		// To setup a ThreadedFileLogger, so that the audit view reflects events
		// whilst debugging,
		// uncomment the next line
		// KieRuntimeLogger logger = ks.getLoggers().newThreadedFileLogger(
		// ksession, "./helloworld", 1000 );

	}

	public void setKsession(KieSession ksession) {
		this.ksession = ksession;
	}

	public KieSession getKsession() {
		return ksession;
	}

}
