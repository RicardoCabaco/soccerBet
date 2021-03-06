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
 * Liga generated by hbm2java
 */
@Entity
@Table(name="liga"
)
public class Liga  implements java.io.Serializable {


     private int idLiga;
     private Pais pais;
     private String nome;
     private Integer idLigaBetfair;
     private Set<EpocaLiga> epocaLigas = new HashSet<EpocaLiga>(0);

    public Liga() {
    }

	
    public Liga(int idLiga, Pais pais) {
        this.idLiga = idLiga;
        this.pais = pais;
    }
    public Liga(int idLiga, Pais pais, String nome, Integer idLigaBetfair, Set<EpocaLiga> epocaLigas) {
       this.idLiga = idLiga;
       this.pais = pais;
       this.nome = nome;
       this.idLigaBetfair = idLigaBetfair;
       this.epocaLigas = epocaLigas;
    }
   
     @Id 
    
    @Column(name="idLiga", unique=true, nullable=false)
    public int getIdLiga() {
        return this.idLiga;
    }
    
    public void setIdLiga(int idLiga) {
        this.idLiga = idLiga;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idPais", nullable=false)
    public Pais getPais() {
        return this.pais;
    }
    
    public void setPais(Pais pais) {
        this.pais = pais;
    }
    
    @Column(name="nome", length=100)
    public String getNome() {
        return this.nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    @Column(name="idLiga_Betfair")
    public Integer getIdLigaBetfair() {
        return this.idLigaBetfair;
    }
    
    public void setIdLigaBetfair(Integer idLigaBetfair) {
        this.idLigaBetfair = idLigaBetfair;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="liga")
    public Set<EpocaLiga> getEpocaLigas() {
        return this.epocaLigas;
    }
    
    public void setEpocaLigas(Set<EpocaLiga> epocaLigas) {
        this.epocaLigas = epocaLigas;
    }




}


