package com.mycompany.klijent;

import entiteti.Grad;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Scanner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Klijent {
    private static String menu = "1. Kreiranje grada\n" +"2. Kreiranje korisnika\n" +"3. Dodavanje novca korisniku\n" +
            "4. Promena adrese i grada za korisnika\n" +"5. Kreiranje kategorije\n" +"6. Kreiranje artikla\n" +"7. Menjanje cene artikla\n" +
            "8. Postavljanje popusta za artikal\n" +"9. Dodavanje artikala u određenoj količini u korpu\n" +"10. Brisanje artikla u određenoj količini iz korpe\n" +
            "11. Plaćanje, koje obuhvata kreiranje transakcije, kreiranje narudžbine sa njenim stavkama, i brisanje sadržaja iz korpe\n" +"12. Dohvatanje svih gradova\n" +
            "13. Dohvatanje svih korisnika\n" +"14. Dohvatanje svih kategorija\n" +"15. Dohvatanje svih artikala koje prodaje korisnik koji je poslao zahtev\n" +
            "16. Dohvatanje sadržaja korpe korisnika koji je poslao zahtev\n" +"17. Dohvatanje svih narudžbina korisnika koji je poslao zahtev\n" +"18. Dohvatanje svih narudžbina\n" +
            "19. Dohvatanje svih transakcija\n";
    
    private static final int KREIRAJ_GRAD = 1;
    private static final int KREIRAJ_KORISNIKA = 2;
    private static final int DODAJ_NOVAC = 3;
    private static final int PROMENA_ADRESA_GRAD = 4;
    private static final int KREIRAJ_KATEGORIJU = 5;
    private static final int KREIRAJ_ARTIKL = 6;
    private static final int MENJAJ_CENU = 7;
    private static final int POSTAVI_POPUST = 8;
    private static final int DODAJ_ARTIKL_KORPA = 9;
    private static final int BRISI_ARTIKL_KORPA = 10;
    private static final int PLACANJE = 11;
    private static final int SVI_GRADOVI = 12;
    private static final int SVI_KORISNICI = 13;
    private static final int SVE_KATEGORIJE = 14;
    private static final int SVI_ARTIKLI_KORISNIK = 15;
    private static final int KORISNIK_KORPA = 16;
    private static final int KORISNIK_NARUDZBINE = 17;
    private static final int SVE_NARUDZBINE = 18;
    private static final int SVE_TRANSKACIJE = 19;
    
    public static void main(String[] args) {
        System.out.println("Krenuo");
        while(true)
        {
            System.out.println(menu);
            System.out.println("Izaberite jedan od zahteva: ");
            int izbor = -1;
            Scanner in = new Scanner(System.in);
            izbor = in.nextInt();
            
            switch(izbor)
            {
                case KREIRAJ_GRAD:
                    Podsistem1Handler.zahtev1Handler();
                    break;
                case KREIRAJ_KORISNIKA:
                    Podsistem1Handler.zahtev2Handler();
                    break;
                case DODAJ_NOVAC:
                    Podsistem1Handler.zahtev3Handler();
                    break;
                case PROMENA_ADRESA_GRAD:
                    Podsistem1Handler.zahtev4Handler();
                    break;
                case KREIRAJ_KATEGORIJU:
                    Podsistem2Handler.zahtev5Handler();
                    break;   
                case KREIRAJ_ARTIKL:
                    Podsistem2Handler.zahtev6Handler();
                    break;
                case MENJAJ_CENU:
                    Podsistem2Handler.zahtev7Handler();
                    break;
                case POSTAVI_POPUST:
                    Podsistem2Handler.zahtev8Handler();
                    break;
                case DODAJ_ARTIKL_KORPA:
                    Podsistem2Handler.zahtev9Handler();
                    break;
                case BRISI_ARTIKL_KORPA:
                    Podsistem2Handler.zahtev10Handler();
                    break;
                case PLACANJE:
                    Podsistem3Handler.zahtev11Handler();
                case SVI_GRADOVI:
                    Podsistem1Handler.zahtev12Handler();
                    break;
                case SVI_KORISNICI:
                    Podsistem1Handler.zahtev13Handler();
                    break;
                case SVE_KATEGORIJE:
                    Podsistem2Handler.zahtev14Handler();
                    break;
                case SVI_ARTIKLI_KORISNIK:
                    Podsistem2Handler.zahtev15Handler();
                    break;
                case KORISNIK_KORPA:
                    Podsistem2Handler.zahtev16Handler();
                    break;
                case KORISNIK_NARUDZBINE:
                    Podsistem3Handler.zahtev17Handler();
                    break;
                case SVE_NARUDZBINE:
                    Podsistem3Handler.zahtev18Handler();
                    break;
                case SVE_TRANSKACIJE:
                    Podsistem3Handler.zahtev19Handler();
                    break;
            }
            
            
        }
    }
}
