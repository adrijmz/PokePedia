package com.example.poke_pedia.util;

import android.graphics.Bitmap;

import com.example.poke_pedia.MainActivity;
import com.example.poke_pedia.model.Ability;
import com.example.poke_pedia.model.AllData;
import com.example.poke_pedia.model.Pokemon;
import com.example.poke_pedia.model.SerializableBitMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GetDataThread implements Runnable {

    public MainActivity mainActivity;

    public GetDataThread(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    @Override
    public void run() {
        String pokemonsData = "";
        try {
            pokemonsData =
                    NetUtil.getURLText("https://pokeapi.co/api/v2/pokemon?limit=50&offset=0");
            System.err.println(pokemonsData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("dd/MM/yyyy hh:mm a");
        Gson gson = gsonBuilder.create();
        AllData data = gson.fromJson(pokemonsData, AllData.class);
        List<Pokemon> dataArray = data.getResults();
        //obtengo nombre y url
        for(Pokemon p : dataArray){
            String myUrl = p.getUrl();
            String[] splitted = myUrl.split("/");
            int myId = Integer.parseInt(splitted[splitted.length-1]);
            p.setId(myId);
        }

        //obtengo imagen pokemon
        for(Pokemon p : dataArray){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap imagePokemon = null;
                    try {
                        imagePokemon = NetUtil.getURLBitmap("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+p.getId()+".png");
                        SerializableBitMap serializableBitMap = new SerializableBitMap(imagePokemon);
                        p.setImage(serializableBitMap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        //obtener datos
        for(Pokemon p : dataArray){
            String infoPokemon="";
            try{
                infoPokemon = NetUtil.getURLText("https://pokeapi.co/api/v2/pokemon/"+p.getId());
            }catch (Exception e){
                e.printStackTrace();
            }

            gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("dd/MM/yyyy hh:mm a");
            gson = gsonBuilder.create();
            Pokemon myPokemon = gson.fromJson(infoPokemon, Pokemon.class);
            p.setStats(myPokemon.getStats());
        }


        mainActivity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        mainActivity.updatePokemons(dataArray);
                    }
                }
        );
    }
}
