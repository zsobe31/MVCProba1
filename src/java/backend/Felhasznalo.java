
package backend;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author zsobe31
 */
@Entity
@Table(name = "felhasznalo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Felhasznalo.findAll", query = "SELECT f FROM Felhasznalo f")
    , @NamedQuery(name = "Felhasznalo.findById", query = "SELECT f FROM Felhasznalo f WHERE f.id = :id")
    , @NamedQuery(name = "Felhasznalo.findByFelhasznalonev", query = "SELECT f FROM Felhasznalo f WHERE f.felhasznalonev = :felhasznalonev")
    , @NamedQuery(name = "Felhasznalo.findByJelszo", query = "SELECT f FROM Felhasznalo f WHERE f.jelszo = :jelszo")})
public class Felhasznalo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "felhasznalonev")
    private String felhasznalonev;
    @Basic(optional = false)
    @Column(name = "jelszo")
    private String jelszo;

    public Felhasznalo() {
    }

    public Felhasznalo(Integer id) {
        this.id = id;
    }

    public Felhasznalo(Integer id, String felhasznalonev, String jelszo) {
        this.id = id;
        this.felhasznalonev = felhasznalonev;
        this.jelszo = jelszo;
    }

    public Integer getId() {
        return id;
    }
/*
    public void setId(Integer id) {
        this.id = id;
    }
*/
    public String getFelhasznalonev() {
        return felhasznalonev;
    }
/*
    public void setFelhasznalonev(String felhasznalonev) {
        this.felhasznalonev = felhasznalonev;
    }
*/
    public String getJelszo() {
        return jelszo;
    }

    public void setJelszo(String jelszo) {
       // this.jelszo = jelszo;
    }
    
    // login tárolt eljárás 
    public static Felhasznalo login(EntityManager em, String user, String passwd){
        Felhasznalo f = null;
        
        try{
            //tárolt eljárás létrehozása/meghívása:
            StoredProcedureQuery spq = em.createStoredProcedureQuery("login");
            //nevIN param. regisztrálása:
            spq.registerStoredProcedureParameter("nevIN", String.class, ParameterMode.IN);
            //jelszoIN parameter regisztralasa:
            spq.registerStoredProcedureParameter("jelszoIN", String.class, ParameterMode.IN);
            //idOUT param. regisztrálása:
            spq.registerStoredProcedureParameter("idOUT", Integer.class, ParameterMode.OUT);
            //nevIN értékadás:
            spq.setParameter("nevIN", user);
            //jelszoIN értékadás:
            spq.setParameter("jelszoIN", passwd);
            //execute:
            spq.execute();
            //idOUT kiolvasása:
            Object idO = spq.getOutputParameterValue("idOUT");
            String idS = idO.toString();
            int id = Integer.parseInt(idS);
            //em.find-al példányosítju az f felhasználónkat:
            f = em.find(Felhasznalo.class, id);
            
        }
        catch(Exception ex){
            System.out.println("Hiba: " + ex.toString());
        }
        
        return f;
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
        if (!(object instanceof Felhasznalo)) {
            return false;
        }
        Felhasznalo other = (Felhasznalo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "backend.Felhasznalo[ id=" + id + " ]";
    }
    
}
