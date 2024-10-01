/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem1;

import entities.Korisnik;
import entities.Mesto;
import java.io.Serializable;
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
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

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
    
    //kreiranje grada
    private static void request1(String naziv){
        try {
            Mesto mesto = new Mesto();
            mesto.setNaziv(naziv);
            em.getTransaction().begin(); 
            em.persist(mesto); 
            em.getTransaction().commit(); 
            System.out.println("Mesto napravljeno");
            
           
            producer = context.createProducer();
            ObjectMessage objMsg1 = context.createObjectMessage(naziv);
            objMsg1.setIntProperty("request", 26);//upis i u bazu 2
            producer.send(requestsTopic, objMsg1);
            
            ObjectMessage objMsg2 = context.createObjectMessage(naziv);
            objMsg2.setIntProperty("request", 27);//upis i u bazu 3
            producer.send(requestsTopic, objMsg2);
            
        } catch (JMSException ex) {
            Logger.getLogger(Mesto.class.getName()).log(Level.SEVERE, null, ex);
        }catch (Exception e) {
            if (em.getTransaction().isActive()) {
            em.getTransaction().rollback(); 
            }
            e.printStackTrace();
        }
    }
    
    //kreiranje korisnika
    private static void request2(String korisnik){
        try {
            String podaci[]=korisnik.split("/");
            String email=podaci[1];
            List<Korisnik> korisnici=em.createNamedQuery("Korisnik.findByEmail", Korisnik.class).setParameter("email", email).getResultList();
            if (!korisnici.isEmpty()) {
                System.out.println("Email je vec zauzet.");
                return; 
            }
            
            
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
            
           
            producer = context.createProducer();
            ObjectMessage objMsg1 = context.createObjectMessage(korisnik);
            objMsg1.setIntProperty("request", 28);//upis i u bazu 2
            producer.send(requestsTopic, objMsg1);
            
            ObjectMessage objMsg2 = context.createObjectMessage(korisnik);
            objMsg2.setIntProperty("request", 29);//upis i u bazu 3
            producer.send(requestsTopic, objMsg2);
            
        } catch (JMSException ex) {
            Logger.getLogger(Mesto.class.getName()).log(Level.SEVERE, null, ex);
        }catch (Exception e) {
            if (em.getTransaction().isActive()) {
            em.getTransaction().rollback(); 
            }
            e.printStackTrace();
        }
    }
    
    
    private static void request3(String mejlovi){
        
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
            
            
            producer = context.createProducer();
            ObjectMessage objMsg1 = context.createObjectMessage(mejlovi);
            objMsg1.setIntProperty("request", 30);//upis i u bazu 2
            producer.send(requestsTopic, objMsg1);
            
            ObjectMessage objMsg2 = context.createObjectMessage(mejlovi);
            objMsg2.setIntProperty("request", 31);//upis i u bazu 3
            producer.send(requestsTopic, objMsg2);
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }catch (Exception e) {
            if (em.getTransaction().isActive()) {
            em.getTransaction().rollback(); 
            }
            e.printStackTrace();
        }
        
        
    }
    
    //azuriranje mesta korisnika
    private static void request4(String podaci){
        
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
            
            
            producer = context.createProducer();
            ObjectMessage objMsg1 = context.createObjectMessage(podaci);
            objMsg1.setIntProperty("request", 32);//upis i u bazu 2
            producer.send(requestsTopic, objMsg1);
            
            ObjectMessage objMsg2 = context.createObjectMessage(podaci);
            objMsg2.setIntProperty("request", 33);//upis i u bazu 3
            producer.send(requestsTopic, objMsg2);
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }catch (Exception e) {
            if (em.getTransaction().isActive()) {
            em.getTransaction().rollback(); 
            }
            e.printStackTrace();
        }
        
        
    }
    
    
    private static void request17(){
        try {
            
            List<Mesto> mesta=em.createNamedQuery("Mesto.findAll").getResultList();
            String mestaS="";
            if(mesta.isEmpty()){
                System.out.println("Ne postoji ni jedno uneto mesto.");
                return;
            }
            for (Mesto mesto : mesta) {
                
                mestaS+=mesto.getIdmes();
                mestaS+="/";
                mestaS+=mesto.getNaziv();
                mestaS+="#";
            }
            mestaS=mestaS.substring(0, mestaS.length() - 1);
            producer = context.createProducer();
            ObjectMessage objMsg = context.createObjectMessage(mestaS);
            objMsg.setIntProperty("request", 17);
            producer.send(responsesTopic, objMsg);
            
            System.out.println("Dohvacena sva mesta");
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private static void request18(){
        try {
            
            List<Korisnik> korisnici=em.createNamedQuery("Korisnik.findAll").getResultList();
            String korisniciS="";
            if(korisnici.isEmpty()){
                System.out.println("Ne postoji ni jedan korisnik.");
                return;
            }
            for (Korisnik korisnik : korisnici) {
                
                korisniciS+=korisnik.getIdkor();
                korisniciS+="/";
                korisniciS+=korisnik.getIme();
                korisniciS+="/";
                korisniciS+=korisnik.getEmail();
                korisniciS+="/";
                korisniciS+=korisnik.getGodiste();
                korisniciS+="/";
                korisniciS+=korisnik.getPol();
                korisniciS+="/";
                korisniciS+=korisnik.getIdmes().getIdmes();
                korisniciS+="/";
                korisniciS+=korisnik.getIdmes().getNaziv();
                korisniciS+="#";
            }
            korisniciS=korisniciS.substring(0, korisniciS.length() - 1);
            producer = context.createProducer();
            ObjectMessage objMsg = context.createObjectMessage(korisniciS);
            objMsg.setIntProperty("request", 18);
            producer.send(responsesTopic, objMsg);
            
            System.out.println("Dohvaceni svi korisnici");
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        emf=Persistence.createEntityManagerFactory("podsistem1PU");
        em = emf.createEntityManager();
        context=cf.createContext();
        producer = context.createProducer();
        JMSConsumer consumer=context.createConsumer(requestsTopic,"request=1 OR request=2 OR request=3 OR request=4 OR request=17 OR request=18",false);
        while(true){
            try {
                ObjectMessage objMsg=(ObjectMessage) consumer.receive();
                String parameter = (String)objMsg.getObject();
                 switch(objMsg.getIntProperty("request")){
                    case 1:
                       request1(parameter);
                       break;
                    case 2:
                       request2(parameter);
                       break; 
                    case 3:
                        request3(parameter);
                        break;
                    case 4:
                        request4(parameter);
                        break;
                    case 17:
                        request17();
                        break;
                    case 18:
                        request18();
                        break;
                    default:
                        break;
                 }
            } catch (JMSException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //emf.close();
    }

    
}
