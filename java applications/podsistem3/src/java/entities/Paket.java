/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Nikola
 */
@Entity
@Table(name = "paket")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Paket.findAll", query = "SELECT p FROM Paket p"),
    @NamedQuery(name = "Paket.findByIdpak", query = "SELECT p FROM Paket p WHERE p.idpak = :idpak"),
    @NamedQuery(name = "Paket.findByCena", query = "SELECT p FROM Paket p WHERE p.cena = :cena")})
public class Paket implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpak")
    private Integer idpak;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cena")
    private int cena;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paket")
    private List<Pretplata> pretplataList;

    public Paket() {
    }

    public Paket(Integer idpak) {
        this.idpak = idpak;
    }

    public Paket(Integer idpak, int cena) {
        this.idpak = idpak;
        this.cena = cena;
    }

    public Integer getIdpak() {
        return idpak;
    }

    public void setIdpak(Integer idpak) {
        this.idpak = idpak;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    @XmlTransient
    public List<Pretplata> getPretplataList() {
        return pretplataList;
    }

    public void setPretplataList(List<Pretplata> pretplataList) {
        this.pretplataList = pretplataList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpak != null ? idpak.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paket)) {
            return false;
        }
        Paket other = (Paket) object;
        if ((this.idpak == null && other.idpak != null) || (this.idpak != null && !this.idpak.equals(other.idpak))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Paket[ idpak=" + idpak + " ]";
    }
    
}
