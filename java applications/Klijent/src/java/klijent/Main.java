/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klijent;



import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nikola
 */
public class Main {

    private static Scanner s = new Scanner(System.in);
    
    private static void request1(){
        try {
            System.out.println("Unesite naziv mesta: ");
            String naziv = s.nextLine();
            
            
            URL url = new URL("http://localhost:8080/server/api/mesto");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = naziv.getBytes("utf-8");
                os.write(input, 0, input.length);
            }


            connection.getResponseCode();
        } catch (MalformedURLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private static void request2(){
        try {
            System.out.println("Unesite ime korisnika: ");
            String ime = s.nextLine();
            System.out.println("Unesite email korisnika: ");
            String email = s.nextLine();
            System.out.println("Unesite godiste korisnika: ");
            String godiste = s.nextLine();
            System.out.println("Unesite pol korisnika (muski/zenski): ");
            String pol = s.nextLine();
            System.out.println("Unesite id mesta korisnika: ");
            String idmes = s.nextLine();
            
            String korisnik=ime+"/"+email+"/"+godiste+"/"+pol+"/"+idmes;
            
            
            URL url = new URL("http://localhost:8080/server/resources/mesto");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = korisnik.getBytes("utf-8");
                os.write(input, 0, input.length);
            }


            connection.getResponseCode();
        } catch (MalformedURLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void main(String[] args) {
        
        while(true){
            System.out.println("1. Kreiranje grada");
            System.out.println("2. Kreiranje korisnika");
            System.out.println("Izaberite jednu od ponudjenih opcija:");
            int opcija=s.nextInt();
            s.nextLine();
            switch(opcija){
                case 1:
                    request1();
                    break;
                case 2:
                    request2();
                    break;
                default:
                    System.out.println("Izabrali ste nepostojecu opciju. Pokusajte ponovo!");
                    break;
            }
        }
        
        
        
    }
    
}
