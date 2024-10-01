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
@Table(name = "pretplata")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pretplata.findAll", query = "SELECT p FROM Pretplata p"),
    @NamedQuery(name = "Pretplata.findByIdpret", query = "SELECT p FROM Pretplata p WHERE p.idpret = :idpret"),
    @NamedQuery(name = "Pretplata.findByDatumIVreme", query = "SELECT p FROM Pretplata p WHERE p.datumIVreme = :datumIVreme"),
    @NamedQuery(name = "Pretplata.findByCena", query = "SELECT p FROM Pretplata p WHERE p.cena = :cena")})
public class Pretplata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpret")
    private Integer idpret;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datum_i_vreme")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumIVreme;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cena")
    private int cena;
    @JoinColumn(name = "korisnik", referencedColumnName = "idkor")
    @ManyToOne(optional = false)
    private Korisnik korisnik;
    @JoinColumn(name = "paket", referencedColumnName = "idpak")
    @ManyToOne(optional = false)
    private Paket paket;

    public Pretplata() {
    }

    public Pretplata(Integer idpret) {
        this.idpret = idpret;
    }

    public Pretplata(Integer idpret, Date datumIVreme, int cena) {
        this.idpret = idpret;
        this.datumIVreme = datumIVreme;
        this.cena = cena;
    }

    public Integer getIdpret() {
        return idpret;
    }

    public void setIdpret(Integer idpret) {
        this.idpret = idpret;
    }

    public Date getDatumIVreme() {
        return datumIVreme;
    }

    public void setDatumIVreme(Date datumIVreme) {
        this.datumIVreme = datumIVreme;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public Paket getPaket() {
        return paket;
    }

    public void setPaket(Paket paket) {
        this.paket = paket;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpret != null ? idpret.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pretplata)) {
            return false;
        }
        Pretplata other = (Pretplata) object;
        if ((this.idpret == null && other.idpret != null) || (this.idpret != null && !this.idpret.equals(other.idpret))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Pretplata[ idpret=" + idpret + " ]";
    }
    
}
