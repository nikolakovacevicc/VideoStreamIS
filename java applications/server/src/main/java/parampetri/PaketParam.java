/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parampetri;

/**
 *
 * @author Nikola
 */
public class PaketParam {
     private int id;
    private int cena;

    public PaketParam(int id, int cena) {
        this.id = id;
        this.cena = cena;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    @Override
    public String toString() {
        return "Paket{" + "id=" + id + ", cena=" + cena + '}';
    }
}
