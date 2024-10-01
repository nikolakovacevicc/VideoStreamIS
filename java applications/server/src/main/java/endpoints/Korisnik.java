/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoints;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import parampetri.KorisnikParam;
import parampetri.MestoParam;
import parampetri.PaketParam;
import parampetri.PretplataParam;

/**
 *
 * @author Nikola
 */

@Path("korisnik")
public class Korisnik {
    @Resource(lookup = "jms/__defaultConnectionFactory")
    public ConnectionFactory cf;
    
    @Resource(lookup = "requestsTopic")
    public Topic requestsTopic;
    
    @Resource(lookup = "responsesTopic")
    public Topic responsesTopic;
    
    @Inject
    public JMSContext context;
    
    @POST
    public Response createKorisnik(@FormParam("ime") String ime,
                                   @FormParam("email") String email,
                                   @FormParam("godiste") String godiste,
                                   @FormParam("pol") String pol,
                                   @FormParam("idmes") String idmes) {
        try {
            
            String korisnik = ime + "/" + email + "/" + godiste + "/" + pol + "/" + idmes;
            JMSProducer producer = context.createProducer();
            ObjectMessage objMsg = context.createObjectMessage(korisnik);
            objMsg.setIntProperty("request", 2);
            producer.send(requestsTopic, objMsg);
        } catch (JMSException ex) {
            Logger.getLogger(Mesto.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Greška prilikom slanja zahteva").build();
        }
        return Response.status(Response.Status.OK).entity("Uspešno poslat zahtev 2").build();
    }
    
    @PUT
    @Path("/email")
    public Response updateKorisnikEmail(@FormParam("trenutniEmail") String trenutniEmail, 
                                         @FormParam("noviEmail") String noviEmail) {
        try {
            String mejlovi = trenutniEmail+ "/" + noviEmail;
            JMSProducer producer = context.createProducer();
            ObjectMessage objMsg = context.createObjectMessage(mejlovi);
            objMsg.setIntProperty("request", 3);
            producer.send(requestsTopic, objMsg);
        } catch (JMSException ex) {
            Logger.getLogger(Korisnik.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Greška prilikom slanja zahteva").build();
        }
        return Response.status(Response.Status.OK).entity("Uspešno poslat zahtev 3").build();
    }
    
    @PUT
    @Path("/mesto")
    public Response updateKorisnikMesto(@FormParam("email") String email, 
                                         @FormParam("mesto") String mesto) {
        try {
            String podaci = email+ "/" + mesto;
            JMSProducer producer = context.createProducer();
            ObjectMessage objMsg = context.createObjectMessage(podaci);
            objMsg.setIntProperty("request", 4);
            producer.send(requestsTopic, objMsg);
        } catch (JMSException ex) {
            Logger.getLogger(Korisnik.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Greška prilikom slanja zahteva").build();
        }
        return Response.status(Response.Status.OK).entity("Uspešno poslat zahtev 4").build();
    }
    
    
    @GET
    public Response allKorisnici() {
        try {
            
            JMSProducer producer = context.createProducer();
            ObjectMessage objMsg = context.createObjectMessage("");
            objMsg.setIntProperty("request", 18);
            producer.send(requestsTopic, objMsg);

            JMSConsumer consumer = context.createConsumer(responsesTopic, "request=18", false);
            ObjectMessage objMsg1;
            while (true) {
                objMsg1 = (ObjectMessage) consumer.receive();
                if (objMsg1 != null) {
                    break; 
                }


            }

            String korisniciS = (String) objMsg1.getObject();
            String korisnici[]=korisniciS.split("#");
            List<KorisnikParam> lista=new ArrayList<KorisnikParam>(); 
            for (String string : korisnici) {
                String data[]=string.split("/");
                MestoParam m =new MestoParam(Integer.parseInt(data[5]), data[6]);
                KorisnikParam k =new KorisnikParam(Integer.parseInt(data[0]), data[1], data[2], Integer.parseInt(data[3]), data[4], m);
                lista.add(k);
            }
            return Response.status(Response.Status.OK).entity(new GenericEntity<List<KorisnikParam>>(lista){}).build();
            
        } catch (JMSException ex) {
            Logger.getLogger(Mesto.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Greška prilikom slanja zahteva").build();
        }
        //return Response.status(Response.Status.OK).entity("Odgovor").build();
        
    }
    
    @PUT
    @Path("svePrijave")
    public Response allMesto(@FormParam("korisnik") String korisnik) {
        try {
            
            JMSProducer producer = context.createProducer();
            ObjectMessage objMsg = context.createObjectMessage(korisnik);
            objMsg.setIntProperty("request", 23);
            producer.send(requestsTopic, objMsg);
            

            JMSConsumer consumer = context.createConsumer(responsesTopic, "request=23", false);
            ObjectMessage objMsg1;
            while (true) {
                objMsg1 = (ObjectMessage) consumer.receive();
                if (objMsg1 != null) {
                    break; 
                }


            }

            String pretplateS = (String) objMsg1.getObject();
            String pretplate[]=pretplateS.split("#");
            List<PretplataParam> lista=new ArrayList<PretplataParam>(); 
            for (String string : pretplate) {
                String data[]=string.split("/");
                PaketParam p=new PaketParam(Integer.parseInt(data[1]), Integer.parseInt(data[2]));
                PretplataParam pret=new PretplataParam(Integer.parseInt(data[0]), p, new Date(), Integer.parseInt(data[4]));
                lista.add(pret);
            }
            return Response.status(Response.Status.OK).entity(new GenericEntity<List<PretplataParam>>(lista){}).build();
            
        } catch (JMSException ex) {
            Logger.getLogger(Mesto.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Greška prilikom slanja zahteva").build();
        }
        //return Response.status(Response.Status.OK).entity("Odgovor").build();
        
    }
}
