
package backend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author zsobe31
 */
@Entity
@Table(name = "boritek")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Boritek.findAll", query = "SELECT b FROM Boritek b")
    , @NamedQuery(name = "Boritek.findById", query = "SELECT b FROM Boritek b WHERE b.id = :id")
    , @NamedQuery(name = "Boritek.findByMeret", query = "SELECT b FROM Boritek b WHERE b.meret = :meret")
    , @NamedQuery(name = "Boritek.findBySuly", query = "SELECT b FROM Boritek b WHERE b.suly = :suly")
    , @NamedQuery(name = "Boritek.findByCimzet", query = "SELECT b FROM Boritek b WHERE b.cimzet = :cimzet")
    , @NamedQuery(name = "Boritek.findByFelado", query = "SELECT b FROM Boritek b WHERE b.felado = :felado")
    , @NamedQuery(name = "Boritek.findByIdo", query = "SELECT b FROM Boritek b WHERE b.ido = :ido")})
public class Boritek implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "meret")
    private String meret;
    @Basic(optional = false)
    @Column(name = "suly")
    private int suly;
    @Basic(optional = false)
    @Column(name = "cimzet")
    private String cimzet;
    @Basic(optional = false)
    @Column(name = "felado")
    private String felado;
    @Basic(optional = false)
    @Column(name = "ido")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ido;

    public Boritek() {
    }

    public Boritek(Integer id) {
        this.id = id;
    }

    public Boritek(Integer id, String meret, int suly, String cimzet, String felado, Date ido) {
        this.id = id;
        this.meret = meret;
        this.suly = suly;
        this.cimzet = cimzet;
        this.felado = felado;
        this.ido = ido;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMeret() {
        return meret;
    }

    public void setMeret(String meret) {
        this.meret = meret;
    }

    public int getSuly() {
        return suly;
    }

    public void setSuly(int suly) {
        this.suly = suly;
    }

    public String getCimzet() {
        return cimzet;
    }

    public void setCimzet(String cimzet) {
        this.cimzet = cimzet;
    }

    public String getFelado() {
        return felado;
    }

    public void setFelado(String felado) {
        this.felado = felado;
    }

    public Date getIdo() {
        return ido;
    }

    public void setIdo(Date ido) {
        this.ido = ido;
    }
    
    public static List<Boritek> getAllBoritek(EntityManager em){
        List<Boritek> boritekok = new ArrayList<>();
        
        StoredProcedureQuery spq = em.createStoredProcedureQuery("getAllBoritek");
        List<Object[]> elemek = spq.getResultList();
        for(Object[] elem : elemek){
            Boritek b = em.find(Boritek.class, elem[0]);
            boritekok.add(b);
        }
        
        return boritekok;
    } 
    
    
    // új boríték hozzáadása
    public static Boritek addNewBoritek(EntityManager em, String meret, int suly, String felado, String cimzett){
        Boritek b = null; // new Boritek();
        try{
            StoredProcedureQuery spq = em.createStoredProcedureQuery("addNewBoritek");
            spq.registerStoredProcedureParameter("meretIN", String.class, ParameterMode.IN);
            spq.setParameter("meretIN", meret);
            spq.registerStoredProcedureParameter("sulyIN", Integer.class, ParameterMode.IN);
            spq.setParameter("sulyIN", suly);
            spq.registerStoredProcedureParameter("cimzetIN", String.class, ParameterMode.IN);
            spq.setParameter("cimzetIN", cimzett);
            spq.registerStoredProcedureParameter("feladoIN", String.class, ParameterMode.IN);
            spq.setParameter("feladoIN", felado);       
            spq.execute();
            
            StoredProcedureQuery spq2 = em.createStoredProcedureQuery("lastInsertId");
            spq2.registerStoredProcedureParameter("idOUT", Integer.class, ParameterMode.OUT);
            spq2.execute();
            Object o = spq2.getOutputParameterValue("idOUT");
            int id = Integer.parseInt(o.toString());
            
            b = em.find(Boritek.class, id);
        }
        catch(Exception ex){
            System.out.println("Hiba: " + ex.toString());
        }
        return b;
    }
    
    // boríték törlése
    public static Boritek deleteBoritekById(EntityManager em, int id){
        Boritek b1 = null;
        
        try{
            StoredProcedureQuery spq3 = em.createStoredProcedureQuery("deleteBoritekByID");
            spq3.registerStoredProcedureParameter("idIN", Integer.class, ParameterMode.IN);
            spq3.setParameter("idIN", id);
            spq3.execute();
        }
        catch(Exception ex){
            System.out.println("Hiba: " + ex.toString());
        }
        
        return b1;
    }
    
    // boríték módosítása
    public static Boritek updateBoritek(EntityManager em, String meret, int suly, String cimzett, String felado, int id){
        Boritek b2 = null;
        
        try{
            StoredProcedureQuery spq4 = em.createStoredProcedureQuery("updateBoritek");
            spq4.registerStoredProcedureParameter("meretIN", String.class, ParameterMode.IN);
            spq4.setParameter("meretIN", meret);
            spq4.registerStoredProcedureParameter("sulyIN", Integer.class, ParameterMode.IN);
            spq4.setParameter("sulyIN", suly);
            spq4.registerStoredProcedureParameter("cimzettIN", String.class, ParameterMode.IN);
            spq4.setParameter("cimzettIN", cimzett);
            spq4.registerStoredProcedureParameter("feladoIN", String.class, ParameterMode.IN);
            spq4.setParameter("feladoIN", felado);
            spq4.registerStoredProcedureParameter("idIN", Integer.class, ParameterMode.IN);
            spq4.setParameter("idIN", id);
            spq4.execute();
            
        }
        catch(Exception ex){
            System.out.println("Hiba: " + ex.toString());
        }
        
        return b2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Boritek)) {
            return false;
        }
        Boritek other = (Boritek) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "backend.Boritek[ id=" + id + " ]";
    }
    
}
