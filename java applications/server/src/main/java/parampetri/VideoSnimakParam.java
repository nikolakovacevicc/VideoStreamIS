/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parampetri;

import java.util.Date;

/**
 *
 * @author Nikola
 */
public class VideoSnimakParam {
    private int id;
    private String naziv;
    private int trajanje;
    private KorisnikParam vlasnik;
    private Date datum_i_vreme;

    public VideoSnimakParam(int id, String naziv, int trajanje, KorisnikParam vlasnik, Date datum_i_vreme) {
        this.id = id;
        this.naziv = naziv;
        this.trajanje = trajanje;
        this.vlasnik = vlasnik;
        this.datum_i_vreme = datum_i_vreme;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public KorisnikParam getVlasnik() {
        return vlasnik;
    }

    public void setVlasnik(KorisnikParam vlasnik) {
        this.vlasnik = vlasnik;
    }

    public Date getDatum_i_vreme() {
        return datum_i_vreme;
    }

    public void setDatum_i_vreme(Date datum_i_vreme) {
        this.datum_i_vreme = datum_i_vreme;
    }

    @Override
    public String toString() {
        return "VideoSnimak{" + "id=" + id + ", naziv=" + naziv + ", trajanje=" + trajanje + ", vlasnik=" + vlasnik + ", datum_i_vreme=" + datum_i_vreme + '}';
    }
}
