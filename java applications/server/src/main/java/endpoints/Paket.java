/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoints;

import java.util.ArrayList;
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
import parampetri.MestoParam;
import parampetri.PaketParam;

/**
 *
 * @author Nikola
 */

@Path("paket")
public class Paket {
    
    @Resource(lookup = "jms/__defaultConnectionFactory")
    public ConnectionFactory cf;
    
    @Resource(lookup = "requestsTopic")
    public Topic requestsTopic;
    
    @Resource(lookup = "responsesTopic")
    public Topic responsesTopic;
    
    @Inject
    public JMSContext context;
    
    @POST
    public Response createPaket(@FormParam("cena") String cena) {
        try {
            JMSProducer producer = context.createProducer();
            ObjectMessage objMsg = context.createObjectMessage(cena);
            objMsg.setIntProperty("request", 9);
            producer.send(requestsTopic, objMsg);
        } catch (JMSException ex) {
            Logger.getLogger(Mesto.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Greška prilikom slanja zahteva").build();
        }
        return Response.status(Response.Status.OK).entity("Uspešno poslat zahtev 9").build();
    }
    
    @PUT
    public Response updatePaket(@FormParam("paket") String paket, 
                                         @FormParam("cena") String cena) {
        try {
            String podaci = paket+ "/" + cena;
            JMSProducer producer = context.createProducer();
            ObjectMessage objMsg = context.createObjectMessage(podaci);
            objMsg.setIntProperty("request", 10);
            producer.send(requestsTopic, objMsg);
        } catch (JMSException ex) {
            Logger.getLogger(Korisnik.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Greška prilikom slanja zahteva").build();
        }
        return Response.status(Response.Status.OK).entity("Uspešno poslat zahtev 10").build();
    }
    
    
    @GET
    public Response allPaketi() {
        try {
            
            JMSProducer producer = context.createProducer();
            ObjectMessage objMsg = context.createObjectMessage("");
            objMsg.setIntProperty("request", 22);
            producer.send(requestsTopic, objMsg);
            
            

            JMSConsumer consumer = context.createConsumer(responsesTopic, "request=22", false);
            ObjectMessage objMsg1;
            while (true) {
                objMsg1 = (ObjectMessage) consumer.receive();
                if (objMsg1 != null) {
                    break; 
                }


            }

            String paketiS = (String) objMsg1.getObject();
            String paketi[]=paketiS.split("#");
            List<PaketParam> lista=new ArrayList<PaketParam>(); 
            for (String string :paketi) {
                String data[]=string.split("/");
                PaketParam m =new PaketParam(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
                lista.add(m);
            }
            return Response.status(Response.Status.OK).entity(new GenericEntity<List<PaketParam>>(lista){}).build();
            
        } catch (JMSException ex) {
            Logger.getLogger(Mesto.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Greška prilikom slanja zahteva").build();
        }
        //return Response.status(Response.Status.OK).entity("Odgovor").build();
        
    }
    
}
