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

public class Podsistem3Handler 
{
    private static String URL = "http://localhost:8080/Server/resources/podsistem3";
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
    
    public static void zahtev11Handler()
    {
        URL = URL_CON;
        URL = URL + "/zahtev11";
        URL = URL + "?";
        count = 0;
        
        unesiParam("id korisnika", "idKor");
        
        komunikacija(URL, "POST");
    }
    
    public static void zahtev17Handler()
    {
        URL = URL_CON;
        URL = URL + "/zahtev17";
        URL = URL + "?";
        count = 0;
        
        unesiParam("id korisnika", "idKor");
        
        komunikacija(URL, "GET");
    }
    
    public static void zahtev18Handler()
    {
        URL = URL_CON;
        URL = URL + "/zahtev18";
        //URL = URL + "?";
        count = 0;
        
        komunikacija(URL, "GET");
    }
    
    public static void zahtev19Handler()
    {
        URL = URL_CON;
        URL = URL + "/zahtev19";
        //URL = URL + "?";
        count = 0;
        
        //unesiParam("id korisnika", "idKor");
        
        komunikacija(URL, "GET");
    }
}
