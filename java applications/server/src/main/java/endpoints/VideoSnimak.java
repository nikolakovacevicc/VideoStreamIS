/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoints;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import parampetri.GledanjeParam;
import parampetri.KategorijaParam;
import parampetri.KorisnikParam;
import parampetri.MestoParam;
import parampetri.OcenaParam;
import parampetri.VideoSnimakParam;

/**
 *
 * @author Nikola
 */

@Path("videosnimak")
public class VideoSnimak {
     @Resource(lookup = "jms/__defaultConnectionFactory")
    public ConnectionFactory cf;
    
    @Resource(lookup = "requestsTopic")
    public Topic requestsTopic;
    
    @Resource(lookup = "responsesTopic")
    public Topic responsesTopic;
    
    @Inject
    public JMSContext context;
    
    @POST
    public Response createVideoSnimak(@FormParam("naziv") String naziv,
                                   @FormParam("trajanje") String trajanje,
                                   @FormParam("vlasnik") String vlasnik,
                                   @FormParam("datum_i_vreme") String datum_i_vreme) {
        try {
            
            String korisnik = naziv + "/" +trajanje + "/" + vlasnik + "/" + datum_i_vreme;
            JMSProducer producer = context.createProducer();
            ObjectMessage objMsg = context.createObjectMessage(korisnik);
            objMsg.setIntProperty("request", 6);
            producer.send(requestsTopic, objMsg);
        } catch (JMSException ex) {
            Logger.getLogger(Mesto.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Greška prilikom slanja zahteva").build();
        }
        return Response.status(Response.Status.OK).entity("Uspešno poslat zahtev 6").build();
    }
    
    @PUT
    public Response updateVideoSnimak(@FormParam("video") String video, 
                                         @FormParam("naziv") String naziv) {
        try {
            String podaci = video+ "/" + naziv;
            JMSProducer producer = context.createProducer();
            ObjectMessage objMsg = context.createObjectMessage(podaci);
            objMsg.setIntProperty("request", 7);
            producer.send(requestsTopic, objMsg);
        } catch (JMSException ex) {
            Logger.getLogger(Korisnik.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Greška prilikom slanja zahteva").build();
        }
        return Response.status(Response.Status.OK).entity("Uspešno poslat zahtev 7").build();
    }
    
    @POST
    @Path("/kategorija")
    public Response addKategorija(@FormParam("video") String video, 
                                         @FormParam("kategorija") String kategorija) {
        try {
            String podaci = video+ "/" + kategorija;
            JMSProducer producer = context.createProducer();
            ObjectMessage objMsg = context.createObjectMessage(podaci);
            objMsg.setIntProperty("request", 8);
            producer.send(requestsTopic, objMsg);
        } catch (JMSException ex) {
            Logger.getLogger(Korisnik.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Greška prilikom slanja zahteva").build();
        }
        return Response.status(Response.Status.OK).entity("Uspešno poslat zahtev 8").build();
    }
    
    @PUT
    @Path("brisi")
    public Response deleteVideoSnimak(@FormParam("korisnik") String korisnik,
                                 @FormParam("video") String video) {
        try {
            String podaci = korisnik + "/" + video;
            JMSProducer producer = context.createProducer();
            ObjectMessage objMsg = context.createObjectMessage(podaci);
            objMsg.setIntProperty("request", 16); 
            producer.send(requestsTopic, objMsg);
        } catch (JMSException ex) {
            Logger.getLogger(Mesto.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Greška prilikom slanja zahteva").build();
        }
        return Response.status(Response.Status.OK)
                       .entity("Uspešno poslat zahtev za brisanje video snimka").build();
    }
    
    
    @GET
    public Response allVideoSnimak() {
        try {
            
            JMSProducer producer = context.createProducer();
            ObjectMessage objMsg = context.createObjectMessage("");
            objMsg.setIntProperty("request", 20);
            producer.send(requestsTopic, objMsg);

            JMSConsumer consumer = context.createConsumer(responsesTopic, "request=20", false);
            ObjectMessage objMsg1;
            while (true) {
                objMsg1 = (ObjectMessage) consumer.receive();
                if (objMsg1 != null) {
                    break; 
                }


            }

            String videiS = (String) objMsg1.getObject();
            String videi[]=videiS.split("#");
            List<VideoSnimakParam> lista=new ArrayList<VideoSnimakParam>(); 
            for (String string : videi) {
                String data[]=string.split("/");
                MestoParam m =new MestoParam(Integer.parseInt(data[8]), data[9]);
                KorisnikParam k =new KorisnikParam(Integer.parseInt(data[3]), data[4], data[5], Integer.parseInt(data[6]), data[7], m);
                
                SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss zzz yyyy");
                //Date dateTime = dateFormat.parse(data[10]);
                VideoSnimakParam vs=new VideoSnimakParam(Integer.parseInt(data[0]), data[1], Integer.parseInt(data[2]), k, new Date());
                lista.add(vs);
            }
            
            
            return Response.status(Response.Status.OK).entity(new GenericEntity<List<VideoSnimakParam>>(lista){}).build();
            
        } catch (JMSException ex) {
            Logger.getLogger(Mesto.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Greška prilikom slanja zahteva").build();
        } 
//        catch (ParseException ex) {
//             Logger.getLogger(VideoSnimak.class.getName()).log(Level.SEVERE, null, ex);
//             return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Greška!!!").build();
//         }
        //return Response.status(Response.Status.OK).entity("Odgovor").build();
        
    }
    
    
    @PUT
    @Path("sveKategorije")
    public Response allKategorije(@FormParam("video") String video) {
        try {
            
            JMSProducer producer = context.createProducer();
            ObjectMessage objMsg = context.createObjectMessage(video);
            objMsg.setIntProperty("request", 21);
            producer.send(requestsTopic, objMsg);
            

            JMSConsumer consumer = context.createConsumer(responsesTopic, "request=21", false);
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
    
    
    @PUT
    @Path("sveGledanja")
    public Response allGledanja(@FormParam("video") String video) {
        try {
            
            JMSProducer producer = context.createProducer();
            ObjectMessage objMsg = context.createObjectMessage(video);
            objMsg.setIntProperty("request", 24);
            producer.send(requestsTopic, objMsg);
            

            JMSConsumer consumer = context.createConsumer(responsesTopic, "request=24", false);
            ObjectMessage objMsg1;
            while (true) {
                objMsg1 = (ObjectMessage) consumer.receive();
                if (objMsg1 != null) {
                    break; 
                }


            }

            String gledanjaS = (String) objMsg1.getObject();
            String gledanja[]=gledanjaS.split("#");
            List<GledanjeParam> lista=new ArrayList<GledanjeParam>(); 
            for (String string : gledanja) {
                String data[]=string.split("/");
                MestoParam m = new MestoParam(Integer.parseInt(data[6]), data[7]);
                KorisnikParam k = new KorisnikParam(Integer.parseInt(data[1]), data[2], data[3], Integer.parseInt(data[4]), data[5], m);
                GledanjeParam g = new GledanjeParam(Integer.parseInt(data[0]), k, new Date(), Integer.parseInt(data[9]), Integer.parseInt(data[10]));
                lista.add(g);
            }
            return Response.status(Response.Status.OK).entity(new GenericEntity<List<GledanjeParam>>(lista){}).build();
            
        } catch (JMSException ex) {
            Logger.getLogger(Mesto.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Greška prilikom slanja zahteva").build();
        }
        //return Response.status(Response.Status.OK).entity("Odgovor").build();
        
    }
    
    
    @PUT
    @Path("sveOcene")
    public Response allOcene(@FormParam("video") String video) {
        try {
            
            JMSProducer producer = context.createProducer();
            ObjectMessage objMsg = context.createObjectMessage(video);
            objMsg.setIntProperty("request", 25);
            producer.send(requestsTopic, objMsg);
            

            JMSConsumer consumer = context.createConsumer(responsesTopic, "request=25", false);
            ObjectMessage objMsg1;
            while (true) {
                objMsg1 = (ObjectMessage) consumer.receive();
                if (objMsg1 != null) {
                    break; 
                }


            }

            String oceneS = (String) objMsg1.getObject();
            String ocene[]=oceneS.split("#");
            List<OcenaParam> lista=new ArrayList<OcenaParam>(); 
            for (String string : ocene) {
                String data[]=string.split("/");
                MestoParam m = new MestoParam(Integer.parseInt(data[6]), data[7]);
                KorisnikParam k = new KorisnikParam(Integer.parseInt(data[1]), data[2], data[3], Integer.parseInt(data[4]), data[5], m);
                OcenaParam o = new OcenaParam(Integer.parseInt(data[0]), k, new Date(), Integer.parseInt(data[9]));
                lista.add(o);
            }
            return Response.status(Response.Status.OK).entity(new GenericEntity<List<OcenaParam>>(lista){}).build();
            
        } catch (JMSException ex) {
            Logger.getLogger(Mesto.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Greška prilikom slanja zahteva").build();
        }
        //return Response.status(Response.Status.OK).entity("Odgovor").build();
        
    }
}
