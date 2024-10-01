/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nikola
 */
@Entity
@Table(name = "ocena")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ocena.findAll", query = "SELECT o FROM Ocena o"),
    @NamedQuery(name = "Ocena.findByIdocena", query = "SELECT o FROM Ocena o WHERE o.idocena = :idocena"),
    @NamedQuery(name = "Ocena.findByDatumIVreme", query = "SELECT o FROM Ocena o WHERE o.datumIVreme = :datumIVreme"),
    @NamedQuery(name = "Ocena.findByOcena", query = "SELECT o FROM Ocena o WHERE o.ocena = :ocena")})
public class Ocena implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idocena")
    private Integer idocena;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datum_i_vreme")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumIVreme;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ocena")
    private int ocena;
    @JoinColumn(name = "idkor", referencedColumnName = "idkor")
    @ManyToOne(optional = false)
    private Korisnik idkor;
    @JoinColumn(name = "idvid", referencedColumnName = "idvid")
    @ManyToOne(optional = false)
    private Videosnimak idvid;

    public Ocena() {
    }

    public Ocena(Integer idocena) {
        this.idocena = idocena;
    }

    public Ocena(Integer idocena, Date datumIVreme, int ocena) {
        this.idocena = idocena;
        this.datumIVreme = datumIVreme;
        this.ocena = ocena;
    }

    public Integer getIdocena() {
        return idocena;
    }

    public void setIdocena(Integer idocena) {
        this.idocena = idocena;
    }

    public Date getDatumIVreme() {
        return datumIVreme;
    }

    public void setDatumIVreme(Date datumIVreme) {
        this.datumIVreme = datumIVreme;
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    public Korisnik getIdkor() {
        return idkor;
    }

    public void setIdkor(Korisnik idkor) {
        this.idkor = idkor;
    }

    public Videosnimak getIdvid() {
        return idvid;
    }

    public void setIdvid(Videosnimak idvid) {
        this.idvid = idvid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idocena != null ? idocena.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ocena)) {
            return false;
        }
        Ocena other = (Ocena) object;
        if ((this.idocena == null && other.idocena != null) || (this.idocena != null && !this.idocena.equals(other.idocena))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Ocena[ idocena=" + idocena + " ]";
    }
    
}
