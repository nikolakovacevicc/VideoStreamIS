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
public class Ocena {
    private int id;
    private Korisnik korisnik;
    private Date datum_i_vreme;
    private int ocena;

    public Ocena(int id, Korisnik korisnik, Date datum_i_vreme, int ocena) {
        this.id = id;
        this.korisnik = korisnik;
        this.datum_i_vreme = datum_i_vreme;
        this.ocena = ocena;
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

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    @Override
    public String toString() {
        return "Ocena{" + "id=" + id + ", korisnik=" + korisnik + ", datum_i_vreme=" + datum_i_vreme + ", ocena=" + ocena + '}';
    }
    
}
