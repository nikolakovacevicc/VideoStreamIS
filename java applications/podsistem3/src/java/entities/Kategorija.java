/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Nikola
 */
@Entity
@Table(name = "kategorija")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kategorija.findAll", query = "SELECT k FROM Kategorija k"),
    @NamedQuery(name = "Kategorija.findByIdkat", query = "SELECT k FROM Kategorija k WHERE k.idkat = :idkat"),
    @NamedQuery(name = "Kategorija.findByNaziv", query = "SELECT k FROM Kategorija k WHERE k.naziv = :naziv")})
public class Kategorija implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idkat")
    private Integer idkat;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "naziv")
    private String naziv;
    @JoinTable(name = "videosnimak_kategorija", joinColumns = {
        @JoinColumn(name = "kategorija", referencedColumnName = "idkat")}, inverseJoinColumns = {
        @JoinColumn(name = "video", referencedColumnName = "idvid")})
    @ManyToMany
    private List<Videosnimak> videosnimakList;

    public Kategorija() {
    }

    public Kategorija(Integer idkat) {
        this.idkat = idkat;
    }

    public Kategorija(Integer idkat, String naziv) {
        this.idkat = idkat;
        this.naziv = naziv;
    }

    public Integer getIdkat() {
        return idkat;
    }

    public void setIdkat(Integer idkat) {
        this.idkat = idkat;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @XmlTransient
    public List<Videosnimak> getVideosnimakList() {
        return videosnimakList;
    }

    public void setVideosnimakList(List<Videosnimak> videosnimakList) {
        this.videosnimakList = videosnimakList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idkat != null ? idkat.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kategorija)) {
            return false;
        }
        Kategorija other = (Kategorija) object;
        if ((this.idkat == null && other.idkat != null) || (this.idkat != null && !this.idkat.equals(other.idkat))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Kategorija[ idkat=" + idkat + " ]";
    }
    
}
