/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parametri;

/**
 *
 * @author Nikola
 */
public class Paket {
    private int id;
    private int cena;

    public Paket(int id, int cena) {
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
