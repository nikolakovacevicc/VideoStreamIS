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
@Table(name = "gledanje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gledanje.findAll", query = "SELECT g FROM Gledanje g"),
    @NamedQuery(name = "Gledanje.findByIdgled", query = "SELECT g FROM Gledanje g WHERE g.idgled = :idgled"),
    @NamedQuery(name = "Gledanje.findByDatumIVreme", query = "SELECT g FROM Gledanje g WHERE g.datumIVreme = :datumIVreme"),
    @NamedQuery(name = "Gledanje.findBySekundPoc", query = "SELECT g FROM Gledanje g WHERE g.sekundPoc = :sekundPoc"),
    @NamedQuery(name = "Gledanje.findBySekundStigao", query = "SELECT g FROM Gledanje g WHERE g.sekundStigao = :sekundStigao")})
public class Gledanje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idgled")
    private Integer idgled;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datum_i_vreme")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumIVreme;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sekund_poc")
    private int sekundPoc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sekund_stigao")
    private int sekundStigao;
    @JoinColumn(name = "kor", referencedColumnName = "idkor")
    @ManyToOne(optional = false)
    private Korisnik kor;
    @JoinColumn(name = "vid", referencedColumnName = "idvid")
    @ManyToOne(optional = false)
    private Videosnimak vid;

    public Gledanje() {
    }

    public Gledanje(Integer idgled) {
        this.idgled = idgled;
    }

    public Gledanje(Integer idgled, Date datumIVreme, int sekundPoc, int sekundStigao) {
        this.idgled = idgled;
        this.datumIVreme = datumIVreme;
        this.sekundPoc = sekundPoc;
        this.sekundStigao = sekundStigao;
    }

    public Integer getIdgled() {
        return idgled;
    }

    public void setIdgled(Integer idgled) {
        this.idgled = idgled;
    }

    public Date getDatumIVreme() {
        return datumIVreme;
    }

    public void setDatumIVreme(Date datumIVreme) {
        this.datumIVreme = datumIVreme;
    }

    public int getSekundPoc() {
        return sekundPoc;
    }

    public void setSekundPoc(int sekundPoc) {
        this.sekundPoc = sekundPoc;
    }

    public int getSekundStigao() {
        return sekundStigao;
    }

    public void setSekundStigao(int sekundStigao) {
        this.sekundStigao = sekundStigao;
    }

    public Korisnik getKor() {
        return kor;
    }

    public void setKor(Korisnik kor) {
        this.kor = kor;
    }

    public Videosnimak getVid() {
        return vid;
    }

    public void setVid(Videosnimak vid) {
        this.vid = vid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idgled != null ? idgled.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gledanje)) {
            return false;
        }
        Gledanje other = (Gledanje) object;
        if ((this.idgled == null && other.idgled != null) || (this.idgled != null && !this.idgled.equals(other.idgled))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Gledanje[ idgled=" + idgled + " ]";
    }
    
}
