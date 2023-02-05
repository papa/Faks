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

public class Podsistem1Handler 
{
    private static String URL = "http://localhost:8080/Server/resources/podsistem1";
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
    
    public static void zahtev1Handler()
    {
        URL = URL_CON;
        System.out.println("Unesite ime grada: ");
        String s = null;
        Scanner in = new Scanner(System.in);
        s = in.nextLine();
        URL = URL + "/zahtev1";
        URL = URL + "?";
        count = 0;
        dodajNaLink("nazivGrada", s);
        komunikacija(URL, "POST");
    }
    
    public static void zahtev2Handler()
    {
        
    }
}
