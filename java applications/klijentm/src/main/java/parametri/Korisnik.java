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
public class Korisnik {
    private int id;
    private String ime;
    private String email;
    private int godiste;
    private String pol;
    private Mesto mesto;

    public Korisnik(int id, String ime, String email, int godiste, String pol, Mesto mesto) {
        this.id = id;
        this.ime = ime;
        this.email = email;
        this.godiste = godiste;
        this.pol = pol;
        this.mesto = mesto;
    }

    @Override
    public String toString() {
        return "Korisnik{" + "id=" + id + ", ime=" + ime + ", email=" + email + ", godiste=" + godiste + ", pol=" + pol + ", mesto=" + mesto + '}';
    }

    public String getPol() {
        return pol;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }

    

    
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGodiste() {
        return godiste;
    }

    public void setGodiste(int godiste) {
        this.godiste = godiste;
    }

    public Mesto getMesto() {
        return mesto;
    }

    public void setMesto(Mesto mesto) {
        this.mesto = mesto;
    }
    
}
