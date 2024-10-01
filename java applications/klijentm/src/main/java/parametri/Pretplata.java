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
public class Pretplata {
    private int id;
    private Paket paket;
    private Date datum_i_vreme;
    private int cena;

    public Pretplata(int id, Paket paket, Date datum_i_vreme, int cena) {
        this.id = id;
        
        this.paket = paket;
        this.datum_i_vreme = datum_i_vreme;
        this.cena = cena;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Paket getPaket() {
        return paket;
    }

    public void setPaket(Paket paket) {
        this.paket = paket;
    }

    public Date getDatum_i_vreme() {
        return datum_i_vreme;
    }

    public void setDatum_i_vreme(Date datum_i_vreme) {
        this.datum_i_vreme = datum_i_vreme;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    @Override
    public String toString() {
        return "Pretplata{" + "id=" + id  + ", paket=" + paket + ", datum_i_vreme=" + datum_i_vreme + ", cena=" + cena + '}';
    }
    
    
}
