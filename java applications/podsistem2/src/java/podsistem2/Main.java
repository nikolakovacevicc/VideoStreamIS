/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem2;

import entities.Kategorija;
import entities.Korisnik;
import entities.Mesto;
import entities.Videosnimak;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    
    
    //kreiranje kategorije
    private static void request5(String naziv){
        try {
            Kategorija kategorija = new Kategorija();
            kategorija.setNaziv(naziv);
            em.getTransaction().begin(); 
            em.persist(kategorija); 
            em.getTransaction().commit(); 
            System.out.println("Kategorija napravljena");
            
           
            producer = context.createProducer();
            ObjectMessage objMsg1 = context.createObjectMessage(naziv);
            objMsg1.setIntProperty("request", 34);//upis i u bazu 3
            producer.send(requestsTopic, objMsg1);
            
            
        } catch (JMSException ex) {
            Logger.getLogger(Mesto.class.getName()).log(Level.SEVERE, null, ex);
        }catch (Exception e) {
            if (em.getTransaction().isActive()) {
            em.getTransaction().rollback(); 
            }
            e.printStackTrace();
        }
    }
    
    //kreiranje video snimka
    private static void request6(String podaci){
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
            
           
            producer = context.createProducer();
            ObjectMessage objMsg1 = context.createObjectMessage(podaci);
            objMsg1.setIntProperty("request", 35);//upis i u bazu 3
            producer.send(requestsTopic, objMsg1);
            
            
        } catch (JMSException ex) {
            Logger.getLogger(Mesto.class.getName()).log(Level.SEVERE, null, ex);
        }catch (Exception e) {
            if (em.getTransaction().isActive()) {
            em.getTransaction().rollback(); 
            }
            e.printStackTrace();
        }
    }
    
    //azuriranje naziva video snimka
    private static void request7(String podaci){
        
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
            
            
            producer = context.createProducer();
            ObjectMessage objMsg1 = context.createObjectMessage(podaci);
            objMsg1.setIntProperty("request", 36);//upis i u bazu 3
            producer.send(requestsTopic, objMsg1);
            
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }catch (Exception e) {
            if (em.getTransaction().isActive()) {
            em.getTransaction().rollback(); 
            }
            e.printStackTrace();
        }
        
        
    }
    
    //dodavanje kategorije video snimku
    private static void request8(String podaci){
        
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
            
            
            producer = context.createProducer();
            ObjectMessage objMsg1 = context.createObjectMessage(podaci);
            objMsg1.setIntProperty("request", 37);//upis i u bazu 3
            producer.send(requestsTopic, objMsg1);
            
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }catch (Exception e) {
            if (em.getTransaction().isActive()) {
            em.getTransaction().rollback(); 
            }
            e.printStackTrace();
        }
        
        
    }
    //brisanje video snimka
    private static void request16(String podaci){
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
            
            producer = context.createProducer();
            ObjectMessage objMsg1 = context.createObjectMessage(podaci);
            objMsg1.setIntProperty("request", 38);//upis i u bazu 3
            producer.send(requestsTopic, objMsg1);
 
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
            em.getTransaction().rollback(); 
            }
            e.printStackTrace();
        }
    }
    
    
    private static void request19(){
        try {
            
            List<Kategorija> kategorije=em.createNamedQuery("Kategorija.findAll").getResultList();
            String kategorijeS="";
            if(kategorije.isEmpty()){
                System.out.println("Ne postoji ni jedna kategorija.");
                return;
            }
            for (Kategorija k : kategorije) {
                
                kategorijeS+=k.getIdkat();
                kategorijeS+="/";
                kategorijeS+=k.getNaziv();
                kategorijeS+="#";
            }
            kategorijeS=kategorijeS.substring(0, kategorijeS.length() - 1);
            producer = context.createProducer();
            ObjectMessage objMsg = context.createObjectMessage(kategorijeS);
            objMsg.setIntProperty("request", 19);
            producer.send(responsesTopic, objMsg);
            
            System.out.println("Dohvacene sve kategorije");
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void request20(){
        try {
            
            List<Videosnimak> videi=em.createNamedQuery("Videosnimak.findAll").getResultList();
            String videiS="";
            if(videi.isEmpty()){
                System.out.println("Ne postoji ni jedan korisnik.");
                return;
            }
            for (Videosnimak video : videi) {
                
                videiS+=video.getIdvid();
                videiS+="/";
                videiS+=video.getNaziv();
                videiS+="/";
                videiS+=video.getTrajanje();
                videiS+="/";
                videiS+=video.getVlasnik().getIdkor();
                videiS+="/";
                videiS+=video.getVlasnik().getIme();
                videiS+="/";
                videiS+=video.getVlasnik().getEmail();
                videiS+="/";
                videiS+=video.getVlasnik().getGodiste();
                videiS+="/";
                videiS+=video.getVlasnik().getPol();
                videiS+="/";
                videiS+=video.getVlasnik().getIdmes().getIdmes();
                videiS+="/";
                videiS+=video.getVlasnik().getIdmes().getNaziv();
                videiS+="/";
                videiS+=video.getDatumIVreme().toString();
                System.out.println(video.getDatumIVreme());
                videiS+="#";
            }
            videiS=videiS.substring(0, videiS.length() - 1);
            producer = context.createProducer();
            ObjectMessage objMsg = context.createObjectMessage(videiS);
            objMsg.setIntProperty("request", 20);
            producer.send(responsesTopic, objMsg);
            
            System.out.println("Dohvaceni svi videi");
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void request21(String podaci){
        try {
            int videoid=Integer.parseInt(podaci);
            Videosnimak video=em.find(Videosnimak.class, videoid);
            if(video==null){
                System.out.println("Ne postoji video snimak."); 
                return;
            }
            
            List<Kategorija> kategorije=video.getKategorijaList();
            String kategorijeS="";
            if(kategorije.isEmpty()){
                System.out.println("Ne postoji ni jedna kategorija.");
                return;
            }
            for (Kategorija k : kategorije) {
                
                kategorijeS+=k.getIdkat();
                kategorijeS+="/";
                kategorijeS+=k.getNaziv();
                kategorijeS+="#";
            }
            kategorijeS=kategorijeS.substring(0, kategorijeS.length() - 1);
            producer = context.createProducer();
            ObjectMessage objMsg = context.createObjectMessage(kategorijeS);
            objMsg.setIntProperty("request", 21);
            producer.send(responsesTopic, objMsg);
            
            System.out.println("Dohvacene sve kategorije");
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //kreiranje grada
    private static void request26(String naziv){
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
    private static void request28(String korisnik){
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
    private static void request30(String mejlovi){
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
    private static void request32(String podaci){
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
    
   
    
    public static void main(String[] args) {
        emf=Persistence.createEntityManagerFactory("podsistem2PU");
        em = emf.createEntityManager();
        context=cf.createContext();
        producer = context.createProducer();
        JMSConsumer consumer=context.createConsumer(requestsTopic,"request=5 OR request=6 OR request=7 OR request=8 OR request=16 OR request=19 OR request=20 OR request=21 OR request=26 OR request=28 OR request=30 OR request=32",false);
        while(true){
            try {
                ObjectMessage objMsg=(ObjectMessage) consumer.receive();
                String parameter = (String)objMsg.getObject();
                 switch(objMsg.getIntProperty("request")){
                    case 5:
                       request5(parameter);
                       break;
                    case 6:
                       request6(parameter);
                       break;
                    case 7:
                       request7(parameter);
                       break;
                    case 8:
                       request8(parameter);
                       break;
                    case 16:
                       request16(parameter);
                       break;
                    case 19:
                       request19();
                       break;
                    case 20:
                       request20();
                       break;
                    case 21:
                       request21(parameter);
                       break;
                    case 26:
                       request26(parameter);
                       break;
                    case 28:
                       request28(parameter);
                       break;
                    case 30:
                       request30(parameter);
                       break;
                    case 32:
                       request32(parameter);
                       break;
                 }
            } catch (JMSException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //emf.close();
    }
    
}
