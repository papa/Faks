/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.klijent;

import entiteti.Grad;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Klijent {
    public static void main(String[] args) {
        Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build();

        InterP1 apiEndpoint = retrofit.create(InterP1.class);

        Call<Grad> call = apiEndpoint.getGrad();
        call.enqueue(new Callback<Grad>() {
            @Override
            public void onResponse(Call<Grad> call, Response<Grad> response) {
              Grad grad = response.body();
              System.out.println(grad.getNaziv());
            }

            @Override
            public void onFailure(Call<Grad> call, Throwable t) {
               System.out.println(t.getMessage());
             }
        });
        
    }
}
