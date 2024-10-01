/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Nikola
 */
@Entity
@Table(name = "videosnimak")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Videosnimak.findAll", query = "SELECT v FROM Videosnimak v"),
    @NamedQuery(name = "Videosnimak.findByIdvid", query = "SELECT v FROM Videosnimak v WHERE v.idvid = :idvid"),
    @NamedQuery(name = "Videosnimak.findByNaziv", query = "SELECT v FROM Videosnimak v WHERE v.naziv = :naziv"),
    @NamedQuery(name = "Videosnimak.findByTrajanje", query = "SELECT v FROM Videosnimak v WHERE v.trajanje = :trajanje"),
    @NamedQuery(name = "Videosnimak.findByDatumIVreme", query = "SELECT v FROM Videosnimak v WHERE v.datumIVreme = :datumIVreme")})
public class Videosnimak implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idvid")
    private Integer idvid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "naziv")
    private String naziv;
    @Basic(optional = false)
    @NotNull
    @Column(name = "trajanje")
    private int trajanje;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datum_i_vreme")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumIVreme;
    @ManyToMany(mappedBy = "videosnimakList")
    private List<Kategorija> kategorijaList;
    @JoinColumn(name = "vlasnik", referencedColumnName = "idkor")
    @ManyToOne(optional = false)
    private Korisnik vlasnik;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vid")
    private List<Gledanje> gledanjeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idvid")
    private List<Ocena> ocenaList;

    public Videosnimak() {
    }

    public Videosnimak(Integer idvid) {
        this.idvid = idvid;
    }

    public Videosnimak(Integer idvid, String naziv, int trajanje, Date datumIVreme) {
        this.idvid = idvid;
        this.naziv = naziv;
        this.trajanje = trajanje;
        this.datumIVreme = datumIVreme;
    }

    public Integer getIdvid() {
        return idvid;
    }

    public void setIdvid(Integer idvid) {
        this.idvid = idvid;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }

    public Date getDatumIVreme() {
        return datumIVreme;
    }

    public void setDatumIVreme(Date datumIVreme) {
        this.datumIVreme = datumIVreme;
    }

    @XmlTransient
    public List<Kategorija> getKategorijaList() {
        return kategorijaList;
    }

    public void setKategorijaList(List<Kategorija> kategorijaList) {
        this.kategorijaList = kategorijaList;
    }

    public Korisnik getVlasnik() {
        return vlasnik;
    }

    public void setVlasnik(Korisnik vlasnik) {
        this.vlasnik = vlasnik;
    }

    @XmlTransient
    public List<Gledanje> getGledanjeList() {
        return gledanjeList;
    }

    public void setGledanjeList(List<Gledanje> gledanjeList) {
        this.gledanjeList = gledanjeList;
    }

    @XmlTransient
    public List<Ocena> getOcenaList() {
        return ocenaList;
    }

    public void setOcenaList(List<Ocena> ocenaList) {
        this.ocenaList = ocenaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idvid != null ? idvid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Videosnimak)) {
            return false;
        }
        Videosnimak other = (Videosnimak) object;
        if ((this.idvid == null && other.idvid != null) || (this.idvid != null && !this.idvid.equals(other.idvid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Videosnimak[ idvid=" + idvid + " ]";
    }
    
}
