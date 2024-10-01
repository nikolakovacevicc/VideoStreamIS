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
import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import parampetri.KategorijaParam;
import parampetri.MestoParam;

/**
 *
 * @author Nikola
 */

@Path("kategorija")
public class Kategorija {
    @Resource(lookup = "jms/__defaultConnectionFactory")
    public ConnectionFactory cf;
    
    @Resource(lookup = "requestsTopic")
    public Topic requestsTopic;
    
    @Resource(lookup = "responsesTopic")
    public Topic responsesTopic;
    
    @Inject
    public JMSContext context;
    
    @POST
    public Response createKategorija(@FormParam("naziv") String naziv) {
        try {
            JMSProducer producer = context.createProducer();
            ObjectMessage objMsg = context.createObjectMessage(naziv);
            objMsg.setIntProperty("request", 5);
            producer.send(requestsTopic, objMsg);
        } catch (JMSException ex) {
            Logger.getLogger(Mesto.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Greška prilikom slanja zahteva").build();
        }
        return Response.status(Response.Status.OK).entity("Uspešno poslat zahtev 5").build();
    }
    
    
    @GET
    public Response allKategorija() {
        try {
            
            JMSProducer producer = context.createProducer();
            ObjectMessage objMsg = context.createObjectMessage("");
            objMsg.setIntProperty("request", 19);
            producer.send(requestsTopic, objMsg);
            

            JMSConsumer consumer = context.createConsumer(responsesTopic, "request=19", false);
            ObjectMessage objMsg1;
            while (true) {
                objMsg1 = (ObjectMessage) consumer.receive();
                if (objMsg1 != null) {
                    break; 
                }


            }

            String mestaS = (String) objMsg1.getObject();
            String mesta[]=mestaS.split("#");
            List<KategorijaParam> lista=new ArrayList<KategorijaParam>(); 
            for (String string : mesta) {
                String data[]=string.split("/");
                KategorijaParam k =new KategorijaParam(Integer.parseInt(data[0]), data[1]);
                lista.add(k);
            }
            return Response.status(Response.Status.OK).entity(new GenericEntity<List<KategorijaParam>>(lista){}).build();
            
        } catch (JMSException ex) {
            Logger.getLogger(Mesto.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Greška prilikom slanja zahteva").build();
        }
        //return Response.status(Response.Status.OK).entity("Odgovor").build();
        
    }
}
