/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.klijent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jelena
 */
public class Podsistem2Handler {
    private static String URL = "http://localhost:8080/Server/resources/podsistem2";
    private static String URL_CON = URL;
    private static int count = 0;
    private static int OK = 200;
    
    private static void komunikacija(String link, String method)
    {
        System.out.println("Komunikacija started...");
        System.out.println(link);
        System.out.println(method);
        try {
            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(method);
           
            int status = con.getResponseCode();
            if (status == OK) 
            {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                
                String response = content.toString();
                
                System.out.println(response);
            } else {
                System.out.println("Failed to get response, status code: " + status);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private static void dodajNaLink(String var, String val)
    {
        try {
            if(count == 0)
                URL = URL + var + "=" + URLEncoder.encode(val, "UTF-8");
            else
                URL = URL + "&" + var + "=" + URLEncoder.encode(val, "UTF-8");
            count++;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Podsistem1Handler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void unesiParam(String imeParam, String paramRest)
    {
        System.out.println("Unesite " + imeParam + ": ");
        String s = null;
        Scanner in = new Scanner(System.in);
        s = in.nextLine();
        dodajNaLink(paramRest, s);
    }
    
    public static void zahtev5Handler()
    {
        URL = URL_CON;
        URL = URL + "/zahtev5";
        URL = URL + "?";
        count = 0;
        
        unesiParam("naziv kategorije", "nazivKat");
        unesiParam("naziv nadkategorije", "nazivNadKat");
        
        komunikacija(URL, "POST");
    }
     
    public static void zahtev6Handler()
    {
        URL = URL_CON;
        URL = URL + "/zahtev6";
        URL = URL + "?";
        count = 0;
        
        unesiParam("id korisnika", "idKor");
        unesiParam("naziv artikla", "nazivArt");
        unesiParam("opis", "opis");
        unesiParam("cenu", "cena");
        unesiParam("popust", "popust");
        unesiParam("naziv kategorije", "nazivKat");
        
        komunikacija(URL, "POST");
    }
    
    public static void zahtev7Handler()
    {
        URL = URL_CON;
        URL = URL + "/zahtev7";
        URL = URL + "?";
        count = 0;
        
        unesiParam("id korisnika", "idKor");
        unesiParam("naziv artikla", "nazivArt");
        unesiParam("novu cenu", "novaCena");
        
        komunikacija(URL, "POST");
    }
    
    public static void zahtev8Handler()
    {
        URL = URL_CON;
        URL = URL + "/zahtev8";
        URL = URL + "?";
        count = 0;
        
        unesiParam("id korisnika", "idKor");
        unesiParam("naziv artikla", "nazivArt");
        unesiParam("popust", "popust");
        
        komunikacija(URL, "POST");
    }
    
    public static void zahtev9Handler()
    {
        URL = URL_CON;
        URL = URL + "/zahtev9";
        URL = URL + "?";
        count = 0;
        
        unesiParam("id korisnika", "idKor");
        unesiParam("id artikla", "idArt");
        unesiParam("koliko artikla zelite", "brArt");
        
        komunikacija(URL, "POST");
    }
    
    public static void zahtev10Handler()
    {
        URL = URL_CON;
        URL = URL + "/zahtev10";
        URL = URL + "?";
        count = 0;
        
        unesiParam("id korisnika", "idKor");
        unesiParam("id artikla", "idArt");
        unesiParam("koliko artikla zelite da izbacite", "brArt");
        
        komunikacija(URL, "POST");
    }
    
    public static void zahtev14Handler()
    {
        URL = URL_CON;
        URL = URL + "/zahtev14";
        //URL = URL + "?";
        count = 0;
        komunikacija(URL, "GET");
    }
    
    public static void zahtev15Handler()
    {
        URL = URL_CON;
        URL = URL + "/zahtev15";
        URL = URL + "?";
        count = 0;
        
        unesiParam("id korisnika", "idKor");
        
        komunikacija(URL, "GET");
    }
    
    public static void zahtev16Handler()
    {
        URL = URL_CON;
        URL = URL + "/zahtev16";
        URL = URL + "?";
        count = 0;
        
        unesiParam("id korisnika", "idKor");
        
        komunikacija(URL, "GET");
    }
}
