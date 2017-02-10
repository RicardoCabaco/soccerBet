package soccerBet.project.objects;
// Generated 14/Dez/2013 19:38:56 by Hibernate Tools 3.2.2.GA


import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Jornada generated by hbm2java
 */
@Entity
@Table(name="jornada"
)
public class Jornada  implements java.io.Serializable {


     private int idJornada;
     private EpocaLiga epocaLiga;
     private String nome;
     private Set<Jogo> jogos = new HashSet<Jogo>(0);

    public Jornada() {
    }

	
    public Jornada(int idJornada, EpocaLiga epocaLiga) {
        this.idJornada = idJornada;
        this.epocaLiga = epocaLiga;
    }
    public Jornada(int idJornada, EpocaLiga epocaLiga, String nome, Set<Jogo> jogos) {
       this.idJornada = idJornada;
       this.epocaLiga = epocaLiga;
       this.nome = nome;
       this.jogos = jogos;
    }
   
     @Id 
    
    @Column(name="idJornada", unique=true, nullable=false)
    public int getIdJornada() {
        return this.idJornada;
    }
    
    public void setIdJornada(int idJornada) {
        this.idJornada = idJornada;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idEpoca_Liga", nullable=false)
    public EpocaLiga getEpocaLiga() {
        return this.epocaLiga;
    }
    
    public void setEpocaLiga(EpocaLiga epocaLiga) {
        this.epocaLiga = epocaLiga;
    }
    
    @Column(name="nome", length=45)
    public String getNome() {
        return this.nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="jornada")
    public Set<Jogo> getJogos() {
        return this.jogos;
    }
    
    public void setJogos(Set<Jogo> jogos) {
        this.jogos = jogos;
    }




}


