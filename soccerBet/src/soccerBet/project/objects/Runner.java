package soccerBet.project.objects;
// Generated 14/Dez/2013 19:38:56 by Hibernate Tools 3.2.2.GA


import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Runner generated by hbm2java
 */
@Entity
@Table(name="runner"
)
public class Runner  implements java.io.Serializable {


     private Integer idRunner;
     private Apostas apostas;
     private MapeamentoMercados mapeamentoMercados;
     private int selectionId;
     private Set<Precos> precoses = new HashSet<Precos>(0);
     private int estado;

    public Runner() {
    }

	
    public Runner(Apostas apostas, MapeamentoMercados mapeamentoMercados, int selectionId) {
        this.apostas = apostas;
        this.mapeamentoMercados = mapeamentoMercados;
        this.selectionId = selectionId;
    }
    public Runner(Apostas apostas, MapeamentoMercados mapeamentoMercados, int selectionId, Set<Precos> precoses) {
       this.apostas = apostas;
       this.mapeamentoMercados = mapeamentoMercados;
       this.selectionId = selectionId;
       this.precoses = precoses;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="idRunner", unique=true, nullable=false)
    public Integer getIdRunner() {
        return this.idRunner;
    }
    
    public void setIdRunner(Integer idRunner) {
        this.idRunner = idRunner;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idAposta", nullable=false)
    public Apostas getApostas() {
        return this.apostas;
    }
    
    public void setApostas(Apostas apostas) {
        this.apostas = apostas;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idMap_Mercado", nullable=false)
    public MapeamentoMercados getMapeamentoMercados() {
        return this.mapeamentoMercados;
    }
    
    public void setMapeamentoMercados(MapeamentoMercados mapeamentoMercados) {
        this.mapeamentoMercados = mapeamentoMercados;
    }
    
    @Column(name="selectionId", nullable=false)
    public int getSelectionId() {
        return this.selectionId;
    }
    
    public void setSelectionId(int selectionId) {
        this.selectionId = selectionId;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="runner")
    public Set<Precos> getPrecoses() {
        return this.precoses;
    }
    
    public void setPrecoses(Set<Precos> precoses) {
        this.precoses = precoses;
    }

    @Column(name="estado", nullable=false)
    public int getEstado() {
        return this.estado;
    }
    
    public void setEstado(int estado) {
        this.estado = estado;
    }


}


