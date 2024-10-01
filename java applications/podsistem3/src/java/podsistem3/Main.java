/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem3;

import entities.Gledanje;
import entities.Kategorija;
import entities.Korisnik;
import entities.Mesto;
import entities.Ocena;
import entities.Paket;
import entities.Pretplata;
import entities.Videosnimak;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Nikola
 */
public class Main {

    @Resource(lookup="jms/__defaultConnectionFactory")
    public static ConnectionFactory cf;
    
    @Resource(lookup = "responsesTopic")
    public static Topic responsesTopic;
    
    @Resource(lookup = "requestsTopic")
    public static Topic requestsTopic;
    
    private static EntityManagerFactory emf;
    
    private static EntityManager em;
    
    private static JMSContext context;
    
    private static JMSProducer producer;
    
    //kreiranje paketa
    private static void request9(String cena){
        try {
            Paket paket = new Paket();
            paket.setCena(Integer.parseInt(cena));
            em.getTransaction().begin(); 
            em.persist(paket); 
            em.getTransaction().commit(); 
            System.out.println("Paket napravljen");
 
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
            em.getTransaction().rollback(); 
            }
            e.printStackTrace();
        }
    }
    
    //azuriranje paketa
    private static void request10(String podaci){
        try {
            String data[]=podaci.split("/");
            int paketid=Integer.parseInt(data[0]);
            int cena=Integer.parseInt(data[1]);
            
            List<Paket> paketi=em.createNamedQuery("Paket.findByIdpak", Paket.class).setParameter("idpak", paketid).getResultList();
            if (paketi.isEmpty()) {
                System.out.println("Ne postoji paket sa unetim id-om."); 
                return;
            }
            Paket paket=paketi.get(0);
            paket.setCena(cena);
            
            em.getTransaction().begin(); 
            em.persist(paket); 
            em.getTransaction().commit(); 
            System.out.println("Paket azuriran");
 
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
            em.getTransaction().rollback(); 
            }
            e.printStackTrace();
        }
    }
    
    
    //kreiranje pretplate
    private static void request11(String podaci){
        try {
            String data[]=podaci.split("/");
            String email=data[0];
            int paketid=Integer.parseInt(data[1]);
            
            List<Korisnik> korisnici=em.createNamedQuery("Korisnik.findByEmail",Korisnik.class).setParameter("email", email).getResultList();
            if(korisnici.isEmpty()){
                System.out.println("Ne postoji korisnik sa unetim email-om."); 
                return;
            }
            Korisnik korisnik=korisnici.get(0);
            
            if(!korisnik.getPretplataList().isEmpty()){
                System.out.println("Pretplata za korisnika vec postoji."); 
                return;
            }
            
            Paket paket=em.find(Paket.class, paketid);
            if(paket==null){
                System.out.println("Ne postoji trazeni paket."); 
                return;
            }
            
            Date currentDate = new Date();
            
            
            Pretplata pretplata=new Pretplata();
            pretplata.setKorisnik(korisnik);
            pretplata.setPaket(paket);
            pretplata.setCena(paket.getCena());
            pretplata.setDatumIVreme(currentDate);
            
            paket.getPretplataList().add(pretplata);
            korisnik.getPretplataList().add(pretplata);
            
            em.getTransaction().begin(); 
            em.persist(pretplata); 
            em.getTransaction().commit(); 
            System.out.println("Pretplata kreirana");
 
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
            em.getTransaction().rollback(); 
            }
            e.printStackTrace();
        }
    }
    //kreiranje gledanja
    private static void request12(String podaci){
        try {
            String data[]=podaci.split("/");
            
            String email=data[0];
            List<Korisnik> korisnici=em.createNamedQuery("Korisnik.findByEmail",Korisnik.class).setParameter("email", email).getResultList();
            if(korisnici.isEmpty()){
                System.out.println("Ne postoji korisnik sa unetim email-om."); 
                return;
            }
            Korisnik korisnik=korisnici.get(0);
            
            int videoid=Integer.parseInt(data[1]);
            
            Videosnimak video=em.find(Videosnimak.class, videoid);
            if(video==null){
                System.out.println("Ne postoji video snimak."); 
                return;
            }
            int sekunda_s=Integer.parseInt(data[4]);
            if(video.getTrajanje()*60<sekunda_s){
                System.out.println("Lose uneta sekunda dokle ste stigli."); 
                return;
            }
            
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parsedDateTime = dateTimeFormat.parse(data[2]);
            
            if(parsedDateTime.before(video.getDatumIVreme())){
                System.out.println("Niste mogli da odgledate video pre nego sto je izasao."); 
                return;
            }
            
            Gledanje gledanje=new Gledanje();
            gledanje.setKor(korisnik);
            gledanje.setVid(video);
            gledanje.setDatumIVreme(parsedDateTime);
            gledanje.setSekundPoc(Integer.parseInt(data[3]));
            gledanje.setSekundStigao(sekunda_s);
            
            korisnik.getGledanjeList().add(gledanje);
            video.getGledanjeList().add(gledanje);
            
            
            
            em.getTransaction().begin(); 
            em.persist(gledanje); 
            em.getTransaction().commit(); 
            System.out.println("Pretplata kreirana");
 
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
            em.getTransaction().rollback(); 
            }
            e.printStackTrace();
        }
    }
    
    
    //kreiranje ocene
    private static void request13(String podaci){
        try {
            String data[]=podaci.split("/");
            
            String email=data[0];
            List<Korisnik> korisnici=em.createNamedQuery("Korisnik.findByEmail",Korisnik.class).setParameter("email", email).getResultList();
            if(korisnici.isEmpty()){
                System.out.println("Ne postoji korisnik sa unetim email-om."); 
                return;
            }
            Korisnik korisnik=korisnici.get(0);
            
            int videoid=Integer.parseInt(data[1]);
            
            Videosnimak video=em.find(Videosnimak.class, videoid);
            if(video==null){
                System.out.println("Ne postoji video snimak."); 
                return;
            }
            Date currentDate = new Date();
            int ocenaid=Integer.parseInt(data[2]);
            
            Ocena ocena=new Ocena();
            ocena.setIdkor(korisnik);
            ocena.setIdvid(video);
            ocena.setDatumIVreme(currentDate);
            ocena.setOcena(ocenaid);
            
            korisnik.getOcenaList().add(ocena);
            video.getOcenaList().add(ocena);
            
            em.getTransaction().begin(); 
            em.persist(ocena); 
            em.getTransaction().commit(); 
            System.out.println("Ocena kreirana");
 
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
            em.getTransaction().rollback(); 
            }
            e.printStackTrace();
        }
    }
    
    private static void request14(String podaci){
        try {
            String data[]=podaci.split("/");
            
            String email=data[0];
            List<Korisnik> korisnici=em.createNamedQuery("Korisnik.findByEmail",Korisnik.class).setParameter("email", email).getResultList();
            if(korisnici.isEmpty()){
                System.out.println("Ne postoji korisnik sa unetim email-om."); 
                return;
            }
            Korisnik korisnik=korisnici.get(0);
            
            int videoid=Integer.parseInt(data[1]);
            
            Videosnimak video=em.find(Videosnimak.class, videoid);
            if(video==null){
                System.out.println("Ne postoji video snimak."); 
                return;
            }
            
            List<Ocena> ocene=korisnik.getOcenaList();
            boolean flag=false;
            Ocena o=null;
            for (Ocena ocena : ocene) {
                if(ocena.getIdvid().equals(video)){
                    flag=true;
                    o=ocena;
                    break;
                }
            }
            if(!flag){
                System.out.println("Korisnik nije ocenio uneti video."); 
                return;
            }
            
            
            Date currentDate = new Date();
            int ocenaid=Integer.parseInt(data[2]);
            
            
            
            
            o.setDatumIVreme(currentDate);
            o.setOcena(ocenaid);
            
            
            
            em.getTransaction().begin(); 
            em.persist(o); 
            em.getTransaction().commit(); 
            System.out.println("Ocena kreirana");
 
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
            em.getTransaction().rollback(); 
            }
            e.printStackTrace();
        }
    }
    
    private static void request15(String podaci){
        try {
            String data[]=podaci.split("/");
            
            String email=data[0];
            List<Korisnik> korisnici=em.createNamedQuery("Korisnik.findByEmail",Korisnik.class).setParameter("email", email).getResultList();
            if(korisnici.isEmpty()){
                System.out.println("Ne postoji korisnik sa unetim email-om."); 
                return;
            }
            Korisnik korisnik=korisnici.get(0);
            
            int videoid=Integer.parseInt(data[1]);
            
            Videosnimak video=em.find(Videosnimak.class, videoid);
            if(video==null){
                System.out.println("Ne postoji video snimak."); 
                return;
            }
            
            List<Ocena> ocene = korisnik.getOcenaList();
            Ocena ocenaToRemove = null;
            for (Ocena ocena : ocene) {
                if (ocena.getIdvid().equals(video)) {
                    ocenaToRemove = ocena;
                    break;
                }
            }
            if (ocenaToRemove == null) {
                System.out.println("Korisnik nije ocenio uneti video."); 
                return;
            }
            
            em.getTransaction().begin(); 
            em.remove(ocenaToRemove); 
            em.getTransaction().commit(); 
            System.out.println("Ocena obrisana");
 
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
            em.getTransaction().rollback(); 
            }
            e.printStackTrace();
        }
    }
    
    
    private static void request22(){
        try {
            
            List<Paket> paketi=em.createNamedQuery("Paket.findAll").getResultList();
            String paketiS="";
            if(paketi.isEmpty()){
                System.out.println("Ne postoji ni jedan paket.");
                return;
            }
            for (Paket paket : paketi) {
                
                paketiS+=paket.getIdpak();
                paketiS+="/";
                paketiS+=paket.getCena();
                paketiS+="#";
            }
            paketiS=paketiS.substring(0, paketiS.length() - 1);
            producer = context.createProducer();
            ObjectMessage objMsg = context.createObjectMessage(paketiS);
            objMsg.setIntProperty("request", 22);
            producer.send(responsesTopic, objMsg);
            
            System.out.println("Dohvaceni svi paketi");
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void request23(String podaci){
        try {
            List<Korisnik> korisnici=em.createNamedQuery("Korisnik.findByEmail",Korisnik.class).setParameter("email", podaci).getResultList();
            if(korisnici.isEmpty()){
                System.out.println("Ne postoji korisnik sa unetim email-om."); 
                return;
            }
            Korisnik korisnik=korisnici.get(0);
            List<Pretplata> pretplate=korisnik.getPretplataList();
            if(pretplate.isEmpty()){
                System.out.println("Ne postoji pretplata za korisnika."); 
                return;
            }
            String pretplateS="";
            
            
            
            for (Pretplata p : pretplate) {
                
                pretplateS+=p.getIdpret();
                pretplateS+="/";
                pretplateS+=p.getPaket().getIdpak();
                pretplateS+="/";
                pretplateS+=p.getPaket().getCena();
                pretplateS+="/";
                pretplateS+=p.getDatumIVreme();
                pretplateS+="/";
                pretplateS+=p.getCena();
                pretplateS+="#";
            }
            System.out.println(pretplateS);
            pretplateS=pretplateS.substring(0, pretplateS.length() - 1);
            producer = context.createProducer();
            ObjectMessage objMsg = context.createObjectMessage(pretplateS);
            objMsg.setIntProperty("request", 23);
            producer.send(responsesTopic, objMsg);
            
            System.out.println("Dohvacene sve pretplate");
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private static void request24(String podaci){
        try {
            int videoid=Integer.parseInt(podaci);
            Videosnimak video=em.find(Videosnimak.class, videoid);
            if(video==null){
                System.out.println("Ne postoji video snimak."); 
                return;
            }
            
            List<Gledanje> gledanja=video.getGledanjeList();
            String gledanjaS="";
            if(gledanja.isEmpty()){
                System.out.println("Ne postoji ni jedna kategorija.");
                return;
            }
            for (Gledanje g : gledanja) {
                
                gledanjaS+=g.getIdgled();
                gledanjaS+="/";
                gledanjaS+=g.getKor().getIdkor();
                gledanjaS+="/";
                gledanjaS+=g.getKor().getIme();
                gledanjaS+="/";
                gledanjaS+=g.getKor().getEmail();
                gledanjaS+="/";
                gledanjaS+=g.getKor().getGodiste();
                gledanjaS+="/";
                gledanjaS+=g.getKor().getPol();
                gledanjaS+="/";
                gledanjaS+=g.getKor().getIdmes().getIdmes();
                gledanjaS+="/";
                gledanjaS+=g.getKor().getIdmes().getNaziv();
                gledanjaS+="/";
                gledanjaS+=g.getDatumIVreme();
                gledanjaS+="/";
                gledanjaS+=g.getSekundPoc();
                gledanjaS+="/";
                gledanjaS+=g.getSekundStigao();
                gledanjaS+="#";
            }
            gledanjaS=gledanjaS.substring(0, gledanjaS.length() - 1);
            producer = context.createProducer();
            ObjectMessage objMsg = context.createObjectMessage(gledanjaS);
            objMsg.setIntProperty("request", 24);
            producer.send(responsesTopic, objMsg);
            
            System.out.println("Dohvacene sve kategorije");
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private static void request25(String podaci){
        try {
            int videoid=Integer.parseInt(podaci);
            Videosnimak video=em.find(Videosnimak.class, videoid);
            if(video==null){
                System.out.println("Ne postoji video snimak."); 
                return;
            }
            
            List<Ocena> ocene=video.getOcenaList();
            String oceneS="";
            if(ocene.isEmpty()){
                System.out.println("Ne postoji ni jedna kategorija.");
                return;
            }
            for (Ocena o : ocene) {
                
                oceneS+=o.getIdocena();
                oceneS+="/";
                oceneS+=o.getIdkor().getIdkor();
                oceneS+="/";
                oceneS+=o.getIdkor().getIme();
                oceneS+="/";
                oceneS+=o.getIdkor().getEmail();
                oceneS+="/";
                oceneS+=o.getIdkor().getGodiste();
                oceneS+="/";
                oceneS+=o.getIdkor().getPol();
                oceneS+="/";
                oceneS+=o.getIdkor().getIdmes().getIdmes();
                oceneS+="/";
                oceneS+=o.getIdkor().getIdmes().getNaziv();
                oceneS+="/";
                oceneS+=o.getDatumIVreme();
                oceneS+="/";
                oceneS+=o.getOcena();
                oceneS+="#";
            }
            oceneS=oceneS.substring(0, oceneS.length() - 1);
            producer = context.createProducer();
            ObjectMessage objMsg = context.createObjectMessage(oceneS);
            objMsg.setIntProperty("request", 25);
            producer.send(responsesTopic, objMsg);
            
            System.out.println("Dohvacene sve ocene");
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //kreiranje grada
    private static void request27(String naziv){
        try {

            Mesto mesto = new Mesto();
            mesto.setNaziv(naziv);
            em.getTransaction().begin(); 
            em.persist(mesto); 
            em.getTransaction().commit(); 
            System.out.println("Mesto napravljeno");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
            em.getTransaction().rollback(); 
            }
            e.printStackTrace();
        }
    }
    
    //kreiranje korisnika
    private static void request29(String korisnik){
        try {
            String podaci[]=korisnik.split("/");
            
            
            
            Korisnik kor=new Korisnik();
            kor.setIme(podaci[0]);
            kor.setEmail(podaci[1]);
            kor.setGodiste(Integer.parseInt(podaci[2]));
            kor.setPol(podaci[3]);
            
            Mesto mesto=em.find(Mesto.class, Integer.parseInt(podaci[4]));
            kor.setIdmes(mesto);
            em.getTransaction().begin(); 
            em.persist(kor); 
            em.getTransaction().commit(); 
            System.out.println("Korisnik napravljen");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
            em.getTransaction().rollback(); 
            }
            e.printStackTrace();
        }
    }
    
    
    //azuriranje emaila korisnika
    private static void request31(String mejlovi){
        try {
            String emails[]=mejlovi.split("/");
            String stari=emails[0];
            String novi=emails[1];
            
            List<Korisnik> korisnici=em.createNamedQuery("Korisnik.findByEmail", Korisnik.class).setParameter("email", stari).getResultList();
            if (korisnici.isEmpty()) {
                System.out.println("Ne postoji korisnik sa unetim emailom."); 
                return;
            }
            Korisnik korisnik=korisnici.get(0);
            
            korisnici=em.createNamedQuery("Korisnik.findByEmail", Korisnik.class).setParameter("email", novi).getResultList();
            if (!korisnici.isEmpty()) {
                System.out.println("Email je vec zauzet.");
                return;
            }
            
            korisnik.setEmail(novi);
            em.getTransaction().begin();
            em.persist(korisnik);
            em.getTransaction().commit();
            System.out.println("Korisnik azuriran");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
            em.getTransaction().rollback(); 
            }
            e.printStackTrace();
        }
    }
    
     //azuriranje mesta korisnika
    private static void request33(String podaci){
        try {
            String data[]=podaci.split("/");
            String email=data[0];
            int mestoid=Integer.parseInt(data[1]);
            
            List<Korisnik> korisnici=em.createNamedQuery("Korisnik.findByEmail", Korisnik.class).setParameter("email", email).getResultList();
            if (korisnici.isEmpty()) {
                System.out.println("Ne postoji korisnik sa unetim emailom."); 
                return;
            }
            Korisnik korisnik=korisnici.get(0);
            
            Mesto mesto=em.find(Mesto.class, mestoid);
            
            if(mesto==null){
                System.out.println("Ne postoji mesto."); 
                return;
            }
            
            korisnik.setIdmes(mesto);
            em.getTransaction().begin();
            em.persist(korisnik);
            em.getTransaction().commit();
            System.out.println("Korisnik azuriran");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
            em.getTransaction().rollback(); 
            }
            e.printStackTrace();
        }
    }
    
    //kreiranje kategorije
    private static void request34(String naziv){
        try {

           Kategorija kategorija = new Kategorija();
            kategorija.setNaziv(naziv);
            em.getTransaction().begin(); 
            em.persist(kategorija); 
            em.getTransaction().commit(); 
            System.out.println("Kategorija napravljena");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
            em.getTransaction().rollback(); 
            }
            e.printStackTrace();
        }
    }
    
    //kreiranje korisnika
    private static void request35(String podaci){
        try {
            String data[]=podaci.split("/");
            String email=data[2];
            List<Korisnik> korisnici=em.createNamedQuery("Korisnik.findByEmail", Korisnik.class).setParameter("email", email).getResultList();
            if (korisnici.isEmpty()) {
                System.out.println("Korisnik ne postoji.");
                return; 
            }
            Korisnik korisnik=korisnici.get(0);
            
            
            Videosnimak videosnimak=new Videosnimak();
            videosnimak.setNaziv(data[0]);
            videosnimak.setTrajanje(Integer.parseInt(data[1]));
            videosnimak.setVlasnik(korisnik);
            
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parsedDateTime = dateTimeFormat.parse(data[3]);
            videosnimak.setDatumIVreme(parsedDateTime);
            korisnik.getVideosnimakList().add(videosnimak);
            
            
            em.getTransaction().begin(); 
            em.persist(videosnimak); 
            em.getTransaction().commit(); 
            System.out.println("Video snimak napravljen");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
            em.getTransaction().rollback(); 
            }
            e.printStackTrace();
        }
    }
    
    //azuriranje naziva videa
    private static void request36(String podaci){
        try {
            String data[]=podaci.split("/");
            int  videoid=Integer.parseInt(data[0]);
            String naziv=data[1];
            
            Videosnimak video=em.find(Videosnimak.class, videoid);
            if(video==null){
                System.out.println("Video snimak ne postoji.");
                return;
            }
            video.setNaziv(naziv);
            
            em.getTransaction().begin();
            em.persist(video);
            em.getTransaction().commit();
            System.out.println("Video snimak azuriran");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
            em.getTransaction().rollback(); 
            }
            e.printStackTrace();
        }
    }
    
    //dodavanje kategorija video snimku
    private static void request37(String podaci){
        try {
            String data[]=podaci.split("/");
            int  videoid=Integer.parseInt(data[0]);
            int  kategorijaid=Integer.parseInt(data[1]);
            
            
            Videosnimak video=em.find(Videosnimak.class, videoid);
            if(video==null){
                System.out.println("Video snimak ne postoji.");
                return;
            }
            
            Kategorija kategorija=em.find(Kategorija.class, kategorijaid);
            if(kategorija==null){
                System.out.println("Kategorija ne postoji.");
                return;
            }
            video.getKategorijaList().add(kategorija);
            kategorija.getVideosnimakList().add(video);
            
            em.getTransaction().begin();
            em.persist(video);
            em.getTransaction().commit();
            System.out.println("Video snimak azuriran");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
            em.getTransaction().rollback(); 
            }
            e.printStackTrace();
        }
    }
    
    //brisanje video snimka
    private static void request38(String podaci){
        try {
            String data[]=podaci.split("/");
            
            String email=data[0];
            List<Korisnik> korisnici=em.createNamedQuery("Korisnik.findByEmail",Korisnik.class).setParameter("email", email).getResultList();
            if(korisnici.isEmpty()){
                System.out.println("Ne postoji korisnik sa unetim email-om."); 
                return;
            }
            Korisnik korisnik=korisnici.get(0);
            
            int videoid=Integer.parseInt(data[1]);
            
            Videosnimak video=em.find(Videosnimak.class, videoid);
            if(video==null){
                System.out.println("Ne postoji video snimak."); 
                return;
            }
            
            List<Videosnimak> videi=korisnik.getVideosnimakList();
            Videosnimak videoToRemove=null;
            for (Videosnimak videosnimak : videi) {
                if(videosnimak.equals(video)){
                    videoToRemove=videosnimak;
                    break;
                }
            }
            if(videoToRemove==null){
                System.out.println("Nije moguce brisanje videa ako korisnik nije njegov vlasnik."); 
                return;
            }
            
            em.getTransaction().begin(); 
            em.remove(videoToRemove); 
            em.getTransaction().commit(); 
            System.out.println("Video snimak obrisan");
            
 
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
            em.getTransaction().rollback(); 
            }
            e.printStackTrace();
        }
    }
    
   
    
    public static void main(String[] args) {
        emf=Persistence.createEntityManagerFactory("podsistem3PU");
        em = emf.createEntityManager();
        context=cf.createContext();
        producer = context.createProducer();
        JMSConsumer consumer=context.createConsumer(requestsTopic,"request=9 OR request=10 OR request=11 OR request=12 OR request=13 OR request=14 OR request=15 OR request=22 OR request=23 OR request=24 OR request=25 OR request=27 OR request=29 OR request=31 OR request=33 OR request=34 OR request=35 OR request=36 OR request=37 OR request=38",false);
        while(true){
            try {
                ObjectMessage objMsg=(ObjectMessage) consumer.receive();
                String parameter = (String)objMsg.getObject();
                 switch(objMsg.getIntProperty("request")){
                    case 9:
                       request9(parameter);
                       break;
                    case 10:
                       request10(parameter);
                       break;
                    case 11:
                       request11(parameter);
                       break;
                    case 12:
                       request12(parameter);
                       break;
                    case 13:
                       request13(parameter);
                       break;
                    case 14:
                       request14(parameter);
                       break;
                    case 15:
                       request15(parameter);
                       break;
                    case 22:
                       request22();
                       break;
                    case 23:
                       request23(parameter);
                       break;
                    case 24:
                       request24(parameter);
                       break;
                    case 25:
                       request25(parameter);
                       break;
                    case 27:
                       request27(parameter);
                       break;
                    case 29:
                       request29(parameter);
                       break;
                    case 31:
                       request31(parameter);
                       break;
                    case 33:
                       request33(parameter);
                       break;
                    case 34:
                       request34(parameter);
                       break;
                    case 35:
                       request35(parameter);
                       break;
                    case 36:
                       request36(parameter);
                       break;
                    case 37:
                       request37(parameter);
                       break;
                    case 38:
                       request38(parameter);
                       break;
                 }
            } catch (JMSException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //emf.close();
    }
    
}
