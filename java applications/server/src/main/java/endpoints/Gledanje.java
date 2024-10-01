/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoints;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author Nikola
 */

@Path("gledanje")
public class Gledanje {
    @Resource(lookup = "jms/__defaultConnectionFactory")
    public ConnectionFactory cf;
    
    @Resource(lookup = "requestsTopic")
    public Topic requestsTopic;
    
    @Resource(lookup = "responsesTopic")
    public Topic responsesTopic;
    
    @Inject
    public JMSContext context;
    
    @POST
    public Response createPaket(@FormParam("korisnik") String korisnik,
                                @FormParam("video") String video,
                                @FormParam("datum_i_vreme") String datum_i_vreme,
                                @FormParam("sekund_poc") String sekund_poc,
                                @FormParam("sekund_stigao") String sekund_stigao) {
        try {
            
            String podaci = korisnik+"/"+video+"/"+datum_i_vreme+"/"+sekund_poc+"/"+sekund_stigao;
            JMSProducer producer = context.createProducer();
            ObjectMessage objMsg = context.createObjectMessage(podaci);
            objMsg.setIntProperty("request", 12);
            producer.send(requestsTopic, objMsg);
        } catch (JMSException ex) {
            Logger.getLogger(Mesto.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Greška prilikom slanja zahteva").build();
        }
        return Response.status(Response.Status.OK).entity("Uspešno poslat zahtev 12").build();
    }
}
