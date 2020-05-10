package com.example.firstproject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokeApi {
    @GET("data.json")
    Call<RestPokemonResponse> getPokemonResponse();
}
