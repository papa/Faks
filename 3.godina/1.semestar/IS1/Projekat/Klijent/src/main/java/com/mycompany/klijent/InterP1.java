/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.klijent;

import entiteti.Grad;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
/**
 *
 * @author Jelena
 */
public interface InterP1 {
  @GET("/resources/podsistem1")
  Call<Grad> getGrad();
}