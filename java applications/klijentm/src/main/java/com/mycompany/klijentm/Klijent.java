/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.klijentm;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import parametri.Gledanje;
import parametri.Kategorija;
import parametri.Korisnik;
import parametri.Mesto;
import parametri.Ocena;
import parametri.Paket;
import parametri.Pretplata;
import parametri.VideoSnimak;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 *
 * @author Nikola
 */
public class Klijent {
    private static Scanner s = new Scanner(System.in);
    private static Retrofit retrofit;
   

    public static void main(String[] args) {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080/server/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        
        

        while(true){
            System.out.println("1. Kreiranje grada");
            System.out.println("2. Kreiranje korisnika");
            System.out.println("3. Promena email adrese za korisnika");
            System.out.println("4. Promena mesta za korisnika");
            System.out.println("5. Kreiranje kategorije");
            System.out.println("6. Kreiranje video snimka");
            System.out.println("7. Promena naziva video snimka");
            System.out.println("8. Dodavanje kategorije video snimku");
            System.out.println("9. Kreiranje paketa");
            System.out.println("10. Promena mesecne cene za paket");
            System.out.println("11. Kreiranje pretplate korisnika na paket");
            System.out.println("12. Kreiranje gledanja video snimka od strane korisnika");
            System.out.println("13. Kreiranje ocene korisnika za video snimak");
            System.out.println("14. Menjanje ocene korisnika za video snimak");
            System.out.println("15. Brisanje ocene korisnika za video snimak");
            System.out.println("16. Brisanje video snimka od strane korisnika koji ga je kreirao");
            System.out.println("17. Dohvatanje svih mesta");
            System.out.println("18. Dohvatanje svih korisnika");
            System.out.println("19. Dohvatanje svih kategorija");
            System.out.println("20. Dohvatanje svih video snimaka");
            System.out.println("21. Dohvatanje kategorija za određeni video snimak");
            System.out.println("22. Dohvatanje svih paketa");
            System.out.println("23. Dohvatanje svih pretplata za korisnika");
            System.out.println("24. Dohvatanje svih gledanja za video snimak");
            System.out.println("25. Dohvatanje svih ocena za video snimak");
            System.out.println("Izaberite jednu od ponudjenih opcija:");
            int opcija = s.nextInt();
            s.nextLine();
            switch(opcija){
                case 1:
                    request1();
                    break;
                case 2:
                    request2();
                    break;
                case 3:
                    request3();
                    break;
                case 4:
                    request4();
                    break;
                case 5:
                    request5();
                    break;
                case 6:
                    request6();
                    break;
                case 7:
                    request7();
                    break;
                case 8:
                    request8();
                    break;
                case 9:
                    request9();
                    break;
                case 10:
                    request10();
                    break;
                case 11:
                    request11();
                    break;
                case 12:
                    request12();
                    break;
                case 13:
                    request13();
                    break;
                case 14:
                    request14();
                    break;
                case 15:
                    request15();
                    break;
                case 16:
                    request16();
                    break;
                case 17:
                    request17();
                    break;
                case 18:
                    request18();
                    break;
                case 19:
                    request19();
                    break;
                case 20:
                    request20();
                    break;
                case 21:
                    request21();
                    break;
                case 22:
                    request22();
                    break;
                case 23:
                    request23();
                    break;
                case 24:
                    request24();
                    break;
                case 25:
                    request25();
                    break;
                default:
                    System.out.println("Izabrali ste nepostojeću opciju. Pokušajte ponovo!");
                    break;
            }
        }
    }

    private static void request1() {
        System.out.println("Unesite naziv mesta: ");
        String naziv = s.nextLine();

        MestoService service = retrofit.create(MestoService.class);
        Call<Void> call = service.createMesto(naziv);

        try {
            retrofit2.Response<Void> response = call.execute();
            if(response.isSuccessful()) {
                System.out.println("Mesto napravljeno");
            } else {
                System.out.println("Greška prilikom kreiranja mesta: " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void request2() {
        System.out.println("Unesite ime korisnika: ");
        String ime = s.nextLine();
        System.out.println("Unesite email korisnika: ");
        String email = s.nextLine();
        System.out.println("Unesite godište korisnika(broj): ");
        String godiste = s.nextLine();
        System.out.println("Unesite pol korisnika (muski/zenski): ");
        String pol = s.nextLine();
        System.out.println("Unesite id mesta korisnika(broj): ");
        String idmes = s.nextLine();

        KorisnikService service = retrofit.create(KorisnikService.class);
        Call<Void> call = service.createKorisnik(ime, email, godiste, pol, idmes);

        try {
            retrofit2.Response<Void> response = call.execute();
            if(response.isSuccessful()) {
                System.out.println("Korisnik napravljen");
            } else {
                System.out.println("Greška prilikom kreiranja korisnika: " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void request3() {
        System.out.println("Unesite trenutni email korisnika: ");
        String trenutniEmail = s.nextLine();
        System.out.println("Unesite novi email korisnika: ");
        String noviEmail = s.nextLine();

        KorisnikUpdateService service = retrofit.create(KorisnikUpdateService.class);
        Call<Void> call = service.updateKorisnikEmail(trenutniEmail, noviEmail);

        try {
            retrofit2.Response<Void> response = call.execute();
            if(response.isSuccessful()) {
                System.out.println("Email adresa korisnika ažurirana");
            } else {
                System.out.println("Greška prilikom ažuriranja email adrese korisnika: " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void request4() {
        System.out.println("Unesite email korisnika: ");
        String email = s.nextLine();
        System.out.println("Unesite novi id mesta korisnika(broj): ");
        String mesto = s.nextLine();

        KorisnikUpdateMestoService service = retrofit.create(KorisnikUpdateMestoService.class);
        Call<Void> call = service.updateKorisnikMesto(email, mesto);

        try {
            retrofit2.Response<Void> response = call.execute();
            if(response.isSuccessful()) {
                System.out.println("Mesto korisnika ažurirano");
            } else {
                System.out.println("Greška prilikom ažuriranja mesta korisnika: " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void request5() {
        System.out.println("Unesite naziv kategorije: ");
        String naziv = s.nextLine();

        KategorijaService service = retrofit.create(KategorijaService.class);
        Call<Void> call = service.createKategorija(naziv);

        try {
            retrofit2.Response<Void> response = call.execute();
            if(response.isSuccessful()) {
                System.out.println("Kategorija napravljena");
            } else {
                System.out.println("Greška prilikom kreiranja kategorije: " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void request6() {
        System.out.println("Unesite naziv video snimka: ");
        String naziv = s.nextLine();
        System.out.println("Unesite trajanje video snimka (broj): ");
        String trajanje = s.nextLine();
        System.out.println("Unesite vlasnika video snimka(email): ");
        String vlasnik = s.nextLine();
        System.out.println("Unesite datum i vreme (yyyy-MM-dd HH:mm:ss): ");
        String datum_i_vreme = s.nextLine();
        

        VideoSnimakService service = retrofit.create(VideoSnimakService.class);
        Call<Void> call = service.createVideoSnimak(naziv, trajanje, vlasnik, datum_i_vreme);

        try {
            retrofit2.Response<Void> response = call.execute();
            if(response.isSuccessful()) {
                System.out.println("Video snimak napravljen");
            } else {
                System.out.println("Greška prilikom kreiranja video snimka: " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void request7() {
        System.out.println("Unesite id video snimka: ");
        String video = s.nextLine();
        System.out.println("Unesite novi naziv video snimka: ");
        String naziv = s.nextLine();

        VideoSnimakUpdateService service = retrofit.create(VideoSnimakUpdateService.class);
        Call<Void> call = service.updateVideoSnimak(video, naziv);

        try {
            retrofit2.Response<Void> response = call.execute();
            if(response.isSuccessful()) {
                System.out.println("Naziv video snimka je ažuriran");
            } else {
                System.out.println("Greška prilikom ažuriranja naziva video snimka: " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    private static void request8() {
        System.out.println("Unesite id video snimka: ");
        String video = s.nextLine();
        System.out.println("Unesite kategoriju video snimka: ");
        String kategorija = s.nextLine();

        VideoSnimakAddKategorijaService service = retrofit.create(VideoSnimakAddKategorijaService.class);
        Call<Void> call = service.addKategorija(video, kategorija);

        try {
            retrofit2.Response<Void> response = call.execute();
            if(response.isSuccessful()) {
                System.out.println("Kategorija je dodata");
            } else {
                System.out.println("Greška prilikom dodavanje kategorije video snimku: " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    private static void request9() {
        System.out.println("Unesite cenu paketa: ");
        String cena = s.nextLine();

        PaketService service = retrofit.create(PaketService.class);
        Call<Void> call = service.createPaket(cena);

        try {
            retrofit2.Response<Void> response = call.execute();
            if(response.isSuccessful()) {
                System.out.println("Paket napravljen");
            } else {
                System.out.println("Greška prilikom kreiranja paketa: " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void request10() {
        System.out.println("Unesite id paketa : ");
        String paket = s.nextLine();
        System.out.println("Unesite novu cenu paketa : ");
        String cena = s.nextLine();

        PaketUpdateService service = retrofit.create(PaketUpdateService.class);
        Call<Void> call = service.updatePaket(paket,cena);

        try {
            retrofit2.Response<Void> response = call.execute();
            if(response.isSuccessful()) {
                System.out.println("Paket azuriran");
            } else {
                System.out.println("Greška prilikom azuriranja paketa: " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void request11() {
        System.out.println("Unesite email korisnika: ");
        String korisnik = s.nextLine();
        System.out.println("Unesite id paketa(broj): ");
        String paket = s.nextLine();
        
        
        

        PretplataService service = retrofit.create(PretplataService.class);
        Call<Void> call = service.createPretplata(korisnik,paket);

        try {
            retrofit2.Response<Void> response = call.execute();
            if(response.isSuccessful()) {
                System.out.println("Pretplata napravljena");
            } else {
                System.out.println("Greška prilikom kreiranja pretplate: " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    private static void request12() {
        System.out.println("Unesite email korisnika: ");
        String korisnik = s.nextLine();
        System.out.println("Unesite id videa(broj): ");
        String video = s.nextLine();
        System.out.println("Unesite datum i vreme kad ste zapoceli gledanje(yyyy-MM-dd HH:mm:ss): ");
        String datum_i_vreme = s.nextLine();
        System.out.println("Unesite od koje sekunde ste krenuli gledanje: ");
        String sekund1 = s.nextLine();
         System.out.println("Unesite do koje sekunde ste stigli sa gledanjem: ");
        String sekund2 = s.nextLine();
        
        
        

        GledanjeService service = retrofit.create(GledanjeService.class);
        Call<Void> call = service.createGledanje(korisnik,video,datum_i_vreme,sekund1,sekund2);

        try {
            retrofit2.Response<Void> response = call.execute();
            if(response.isSuccessful()) {
                System.out.println("Gledanje napravljeno");
            } else {
                System.out.println("Greška prilikom kreiranja gledanja: " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    private static void request13() {
        System.out.println("Unesite email korisnika: ");
        String korisnik = s.nextLine();
        System.out.println("Unesite id videa(broj): ");
        String video = s.nextLine();
         System.out.println("Unesite ocenu: ");
        String ocena = s.nextLine();
        
        
        

        OcenaService service = retrofit.create(OcenaService.class);
        Call<Void> call = service.createOcena(korisnik,video,ocena);

        try {
            retrofit2.Response<Void> response = call.execute();
            if(response.isSuccessful()) {
                System.out.println("Ocena napravljena");
            } else {
                System.out.println("Greška prilikom kreiranja ocene: " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void request14() {
        System.out.println("Unesite email korisnika: ");
        String korisnik = s.nextLine();
        System.out.println("Unesite id videa(broj): ");
        String video = s.nextLine();
         System.out.println("Unesite novu ocenu: ");
        String ocena = s.nextLine();
        
        
        

        UpdateOcenaService service = retrofit.create(UpdateOcenaService.class);
        Call<Void> call = service.updateOcena(korisnik,video,ocena);

        try {
            retrofit2.Response<Void> response = call.execute();
            if(response.isSuccessful()) {
                System.out.println("Ocena azurirana");
            } else {
                System.out.println("Greška prilikom azuriranja ocene: " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void request15() {
        System.out.println("Unesite email korisnika: ");
        String korisnik = s.nextLine();
        System.out.println("Unesite id videa(broj): ");
        String video = s.nextLine();
        

        DeleteOcenaService service = retrofit.create(DeleteOcenaService.class);
        Call<Void> call = service.deleteOcena(korisnik,video);

        try {
            retrofit2.Response<Void> response = call.execute();
            if(response.isSuccessful()) {
                System.out.println("Ocena izbrisana");
            } else {
                System.out.println("Greška prilikom brisanja ocene: " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void request16() {
        System.out.println("Unesite email korisnika: ");
        String korisnik = s.nextLine();
        System.out.println("Unesite id videa(broj): ");
        String video = s.nextLine();
        

        DeleteVideoSnimakService service = retrofit.create(DeleteVideoSnimakService.class);
        Call<Void> call = service.deleteVideoSnimak(korisnik,video);

        try {
            retrofit2.Response<Void> response = call.execute();
            if(response.isSuccessful()) {
                System.out.println("Video snimak izbrisan");
            } else {
                System.out.println("Greška prilikom brisanja video snimka: " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void request17() {
        ShowMestoService service = retrofit.create(ShowMestoService.class);
        Call<List<Mesto>> call = service.getAllMesta();
        try {
            retrofit2.Response<List<Mesto>> response = call.execute();
            if (response.isSuccessful()) {
                List<Mesto> mesta = response.body();
                if (mesta != null) {
                    for (Mesto mesto : mesta) {
                        System.out.println("ID: " + mesto.getId() + ", Naziv: " + mesto.getNaziv());
                    }
                } else {
                    System.out.println("Nema dostupnih mesta.");
                }
            } else {
                System.out.println("Greška prilikom dohvaćanja svih mesta: " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    private static void request18() {
        ShowKorisnikService service = retrofit.create(ShowKorisnikService.class);
        Call<List<Korisnik>> call = service.getAllKorisnik();
        try {
            retrofit2.Response<List<Korisnik>> response = call.execute();
            if (response.isSuccessful()) {
                List<Korisnik> korisnici = response.body();
                if (korisnici != null) {
                    for (Korisnik korisnik : korisnici) {
                        System.out.println("ID: " + korisnik.getId() + ", Ime: " + korisnik.getIme()+", Email: "+korisnik.getEmail()+", Godiste: "+korisnik.getGodiste()+", Pol: "+korisnik.getPol()+", Mesto: "+korisnik.getMesto());
                    }
                } else {
                    System.out.println("Nema dostupnih korisnika.");
                }
            } else {
                System.out.println("Greška prilikom dohvaćanja svih korisnika: " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void request19() {
        ShowKategorijaService service = retrofit.create(ShowKategorijaService.class);
        Call<List<Kategorija>> call = service.getAllKategorija();
        try {
            retrofit2.Response<List<Kategorija>> response = call.execute();
            if (response.isSuccessful()) {
                List<Kategorija> kategorije = response.body();
                if (kategorije != null) {
                    for (Kategorija kategorija : kategorije) {
                        System.out.println("ID: " + kategorija.getId() + ", Naziv: " + kategorija.getNaziv());
                    }
                } else {
                    System.out.println("Nema dostupnih kategorija.");
                }
            } else {
                System.out.println("Greška prilikom dohvaćanja svih kategorija: " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    private static void request20() {
        ShowVideoSnimakService service = retrofit.create(ShowVideoSnimakService.class);
        Call<List<VideoSnimak>> call = service.getAllVideoSnimak();
        try {
            retrofit2.Response<List<VideoSnimak>> response = call.execute();
            if (response.isSuccessful()) {
                List<VideoSnimak> videi = response.body();
                if (videi != null) {
                    for (VideoSnimak video : videi) {
                        System.out.println("ID: " + video.getId() + ", Naziv: " + video.getNaziv()+", Trajanje: "+video.getTrajanje()+", Vlasnik: "+video.getVlasnik()+", Datum i Vreme: "+video.getDatum_i_vreme());
                    }
                } else {
                    System.out.println("Nema dostupnih video snimaka.");
                }
            } else {
                System.out.println("Greška prilikom dohvaćanja svih video snimaka: " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void request21() {
        System.out.println("Unesite id videa(broj): ");
        String video = s.nextLine();
        ShowKategorijaForVideoService service = retrofit.create(ShowKategorijaForVideoService.class);
        Call<List<Kategorija>> call = service.getAllKategorijaForVideo(video);
        try {
            retrofit2.Response<List<Kategorija>> response = call.execute();
            if (response.isSuccessful()) {
                List<Kategorija> kategorije = response.body();
                if (kategorije != null) {
                    for (Kategorija kategorija : kategorije) {
                        System.out.println("ID: " + kategorija.getId() + ", Naziv: " + kategorija.getNaziv());
                    }
                } else {
                    System.out.println("Nema dostupnih kategorija.");
                }
            } else {
                System.out.println("Greška prilikom dohvacanja svih kategorija: " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void request22() {
        ShowPaketService service = retrofit.create(ShowPaketService.class);
        Call<List<Paket>> call = service.getAllPaket();
        try {
            retrofit2.Response<List<Paket>> response = call.execute();
            if (response.isSuccessful()) {
                List<Paket> paketi = response.body();
                if (paketi != null) {
                    for (Paket paket : paketi) {
                        System.out.println("ID: " + paket.getId() + ", Cena: " + paket.getCena());
                    }
                } else {
                    System.out.println("Nema dostupnih paketa.");
                }
            } else {
                System.out.println("Greška prilikom dohvaćanja svih paketa: " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void request23() {
        System.out.println("Unesite email korisnika: ");
        String korisnik = s.nextLine();
        ShowPrijaveForKorisnikService service = retrofit.create(ShowPrijaveForKorisnikService.class);
        Call<List<Pretplata>> call = service.getAllPrijaveForKorisnik(korisnik);
        try {
            retrofit2.Response<List<Pretplata>> response = call.execute();
            if (response.isSuccessful()) {
                List<Pretplata> pretplate = response.body();
                if (pretplate != null) {
                    for (Pretplata pretplata : pretplate) {
                        System.out.println("ID: " + pretplata.getId() + ", Paket: " + pretplata.getPaket()+", Datum i Vreme: "+pretplata.getDatum_i_vreme()+", Cena:"+pretplata.getCena());
                    }
                } else {
                    System.out.println("Nema dostupnih pretplata.");
                }
            } else {
                System.out.println("Greška prilikom dohvacanja svih pretplata: " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    private static void request24() {
        System.out.println("Unesite id videa(broj): ");
        String video = s.nextLine();
        ShowGledanjaForVideoService service = retrofit.create(ShowGledanjaForVideoService.class);
        Call<List<Gledanje>> call = service.getAllGledanjaForVideo(video);
        try {
            retrofit2.Response<List<Gledanje>> response = call.execute();
            if (response.isSuccessful()) {
                List<Gledanje> gledanja = response.body();
                if (gledanja != null) {
                    for (Gledanje gledanje : gledanja) {
                        System.out.println("ID: " + gledanje.getId() + ", Korisnik: " + gledanje.getKorisnik()+ ", Datum i Vreme: "+gledanje.getDatum_i_vreme()+ ", Sekunda Pocetka: "+gledanje.getSek_poc()+ ", Sekunda dokle se stiglo: "+gledanje.getSek_kraj());
                    }
                } else {
                    System.out.println("Nema dostupnih gledanja.");
                }
            } else {
                System.out.println("Greška prilikom dohvacanja svih gledanja: " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void request25() {
        System.out.println("Unesite id videa(broj): ");
        String video = s.nextLine();
        ShowOceneForVideoService service = retrofit.create(ShowOceneForVideoService.class);
        Call<List<Ocena>> call = service.getAllGOceneForVideo(video);
        try {
            retrofit2.Response<List<Ocena>> response = call.execute();
            if (response.isSuccessful()) {
                List<Ocena> ocene = response.body();
                if (ocene != null) {
                    for (Ocena ocena : ocene) {
                        System.out.println("ID: " + ocena.getId() + ", Korisnik: " + ocena.getKorisnik()+ ", Datum i Vreme: "+ocena.getDatum_i_vreme()+ ", Ocena: "+ocena.getOcena());
                    }
                } else {
                    System.out.println("Nema dostupnih ocena.");
                }
            } else {
                System.out.println("Greška prilikom dohvacanja svih ocena: " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    interface MestoService {
        @FormUrlEncoded
        @POST("mesto")
        Call<Void> createMesto(@Field("naziv") String naziv);
    }

    interface KorisnikService {
        @FormUrlEncoded
        @POST("korisnik")
        Call<Void> createKorisnik(@Field("ime") String ime,
                                  @Field("email") String email,
                                  @Field("godiste") String godiste,
                                  @Field("pol") String pol,
                                  @Field("idmes") String idmes);
    }
    
   
    interface KorisnikUpdateService {
        @FormUrlEncoded
        @PUT("korisnik/email")
        Call<Void> updateKorisnikEmail(@Field("trenutniEmail") String trenutniEmail,
                                       @Field("noviEmail") String noviEmail);
    }
    
    interface KorisnikUpdateMestoService {
        @FormUrlEncoded
        @PUT("korisnik/mesto")
        Call<Void> updateKorisnikMesto(@Field("email") String email,
                                       @Field("mesto") String mesto);
    }
    
    interface KategorijaService {
        @FormUrlEncoded
        @POST("kategorija")
        Call<Void> createKategorija(@Field("naziv") String naziv);
    }
    
    interface VideoSnimakService {
        @FormUrlEncoded
        @POST("videosnimak")
        Call<Void> createVideoSnimak(@Field("naziv") String naziv,
                                      @Field("trajanje") String trajanje,
                                      @Field("vlasnik") String vlasnik,
                                      @Field("datum_i_vreme") String datum_i_vreme);
    } 
    
    interface VideoSnimakUpdateService {
        @FormUrlEncoded
        @PUT("videosnimak")
        Call<Void> updateVideoSnimak(@Field("video") String video,
                                       @Field("naziv") String naziv);
    }
    interface VideoSnimakAddKategorijaService {
        @FormUrlEncoded
        @POST("videosnimak/kategorija")
        Call<Void> addKategorija(@Field("video") String video,
                                 @Field("kategorija") String kategorija);
    }
    
    
    interface PaketService {
        @FormUrlEncoded
        @POST("paket")
        Call<Void> createPaket(@Field("cena") String cena);
    }
    
    interface PaketUpdateService {
        @FormUrlEncoded
        @PUT("paket")
        Call<Void> updatePaket(@Field("paket") String paket,
                               @Field("cena") String cena);
    }
    
    interface PretplataService {
        @FormUrlEncoded
        @POST("pretplata")
        Call<Void> createPretplata(@Field("korisnik") String korisnik,
                                  @Field("paket") String paket);
    }
    
    interface GledanjeService {
        @FormUrlEncoded
        @POST("gledanje")
        Call<Void> createGledanje(@Field("korisnik") String korisnik,
                                  @Field("video") String video,
                                  @Field("datum_i_vreme") String datum_i_vreme,
                                  @Field("sekund_poc") String sekund_poc,
                                  @Field("sekund_stigao") String sekund_stigao);
    }
    
    interface OcenaService {
        @FormUrlEncoded
        @POST("ocena")
        Call<Void> createOcena(@Field("korisnik") String korisnik,
                                  @Field("video") String video,
                                  @Field("ocena") String ocena);
    }
    
    interface UpdateOcenaService {
        @FormUrlEncoded
        @PUT("ocena")
        Call<Void> updateOcena(@Field("korisnik") String korisnik,
                                  @Field("video") String video,
                                  @Field("nova_ocena") String nova_ocena);
    }
    
    interface DeleteOcenaService {
        @FormUrlEncoded
        @PUT("ocena/brisi")
        Call<Void> deleteOcena(@Field("korisnik") String korisnik,
                                  @Field("video") String video);
    }
    
    interface DeleteVideoSnimakService {
        @FormUrlEncoded
        @PUT("videosnimak/brisi")
        Call<Void> deleteVideoSnimak(@Field("korisnik") String korisnik,
                                  @Field("video") String video);
    }
    
    public interface ShowMestoService {
        @GET("mesto")
        Call<List<Mesto>> getAllMesta();
    }
    
    public interface ShowKorisnikService {
        @GET("korisnik")
        Call<List<Korisnik>> getAllKorisnik();
    }
    
    public interface ShowKategorijaService {
        @GET("kategorija")
        Call<List<Kategorija>> getAllKategorija();
    }
     
     
    public interface ShowVideoSnimakService {
        @GET("videosnimak")
        Call<List<VideoSnimak>> getAllVideoSnimak();
    }
    
    public interface ShowKategorijaForVideoService {
        @FormUrlEncoded
        @PUT("videosnimak/sveKategorije")
        Call<List<Kategorija>> getAllKategorijaForVideo(@Field("video") String video);
    }
    
    public interface ShowPaketService {
        @GET("paket")
        Call<List<Paket>> getAllPaket();
    }
  
    
    public interface ShowPrijaveForKorisnikService {
        @FormUrlEncoded
        @PUT("korisnik/svePrijave")
        Call<List<Pretplata>> getAllPrijaveForKorisnik(@Field("korisnik") String korisnik);
    }
    
    
    public interface ShowGledanjaForVideoService {
        @FormUrlEncoded
        @PUT("videosnimak/sveGledanja")
        Call<List<Gledanje>> getAllGledanjaForVideo(@Field("video") String video);
    }
    
    public interface ShowOceneForVideoService {
        @FormUrlEncoded
        @PUT("videosnimak/sveOcene")
        Call<List<Ocena>> getAllGOceneForVideo(@Field("video") String video);
    }
}
