package com.example.firstproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.core.content.ContextCompat.startActivity;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private MediaPlayer mediaPlayer;

    private static final String BASE_URL = "https://raw.githubusercontent.com/Taymard/FirstProject/master/";
    private ArFragment arFragment;
    private String ASSET_3D = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("application_esiea", Context.MODE_PRIVATE);
        gson = new GsonBuilder()
                .setLenient()
                .create();

        List<Pokemon> pokemonList = getDataFromCache();
        if(pokemonList != null)
        {
            showList(pokemonList);
        }
        else {
            makeApiCall();
        }


        mediaPlayer = MediaPlayer.create(this,R.raw.pokemon_fireredleafgreen_route_11);
        if (mediaPlayer.isPlaying() == false) {
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }


        /*
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);
        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            placeModel(hitResult.createAnchor());
        });*/
    }

    private void releaseMediaPlayer() {
        if(mediaPlayer != null)
        {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    /*private void placeModel(Anchor anchor) {
        ModelRenderable
                .builder()
                .setSource(
                        this,
                        RenderableSource
                                .builder()
                                .setSource(this,Uri.parse(ASSET_3D),RenderableSource.SourceType.GLTF2)
                                .setScale(0.75f)
                                .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                                .build()
                )
                .setRegistryId(ASSET_3D)
                .build()
                .thenAccept(modelRenderable -> addNodeToScene(modelRenderable,anchor))
                .exceptionally(throwable -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(throwable.getMessage()).show();
                    return null;
                });
    }*/

    /*private void addNodeToScene(ModelRenderable modelRenderable, Anchor anchor) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setRenderable(modelRenderable);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
    }*/

    private List<Pokemon> getDataFromCache() {
        String jsonPokemon = sharedPreferences.getString("jsonPokemonList",null);
        if (jsonPokemon == null) {
            return null;
        }
        else{
            Type listType = new TypeToken<List<Pokemon>>(){}.getType();
            return gson.fromJson(jsonPokemon,listType);
        }

    }

    private void showList(List<Pokemon> pokemonList) {

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        mAdapter = new ListAdapter(pokemonList,this);
        recyclerView.setAdapter(mAdapter);
    }



    private void makeApiCall()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        PokeApi pokeApiAPI = retrofit.create(PokeApi.class);

        Call<RestPokemonResponse> call = pokeApiAPI.getPokemonResponse();
        call.enqueue(new Callback<RestPokemonResponse>() {
            @Override
            public void onResponse(Call<RestPokemonResponse> call, Response<RestPokemonResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    List<Pokemon> pokemonList = response.body().getResults();
                    saveList(pokemonList);
                    showList(pokemonList);
                }
                else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<RestPokemonResponse> call, Throwable t) {
                showError();
            }
        });


    }

    private void saveList(List<Pokemon> pokemonList) {
        String jsonString = gson.toJson(pokemonList);

        sharedPreferences
                .edit()
                .putString("jsonPokemonList", jsonString)
                .apply();
        Toast.makeText(getApplicationContext(), "List Saved", Toast.LENGTH_SHORT).show();
    }

    private void showError() {
        Toast.makeText(getApplicationContext(), "API ERROR ", Toast.LENGTH_SHORT).show();

    }



}
