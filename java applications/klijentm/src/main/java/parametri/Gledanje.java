/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parametri;

import java.util.Date;

/**
 *
 * @author Nikola
 */
public class Gledanje {
    private int id;
    private Korisnik korisnik;
    private Date datum_i_vreme;
    private int sek_poc;
    private int sek_kraj;

    public Gledanje(int id, Korisnik korisnik, Date datum_i_vreme, int sek_poc, int sek_kraj) {
        this.id = id;
        this.korisnik = korisnik;
        this.datum_i_vreme = datum_i_vreme;
        this.sek_poc = sek_poc;
        this.sek_kraj = sek_kraj;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public Date getDatum_i_vreme() {
        return datum_i_vreme;
    }

    public void setDatum_i_vreme(Date datum_i_vreme) {
        this.datum_i_vreme = datum_i_vreme;
    }

    public int getSek_poc() {
        return sek_poc;
    }

    public void setSek_poc(int sek_poc) {
        this.sek_poc = sek_poc;
    }

    public int getSek_kraj() {
        return sek_kraj;
    }

    public void setSek_kraj(int sek_kraj) {
        this.sek_kraj = sek_kraj;
    }

    @Override
    public String toString() {
        return "Gledanje{" + "id=" + id + ", korisnik=" + korisnik + ", datum_i_vreme=" + datum_i_vreme + ", sek_poc=" + sek_poc + ", sek_kraj=" + sek_kraj + '}';
    }
    
}
