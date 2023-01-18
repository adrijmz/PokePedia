package com.example.poke_pedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.poke_pedia.model.Pokemon;
import com.example.poke_pedia.model.PokemonAdapter;
import com.example.poke_pedia.model.Stats;
import com.example.poke_pedia.util.GetDataThread;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Pokemon> listPokemons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button misPokemonButton = findViewById(R.id.mis_pokemon_button_main);
        misPokemonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MisPokemon.class);
                startActivity(intent);
            }
        });

        Button buscarPokemonButton = findViewById(R.id.buscar_pokemon_button_id);
        buscarPokemonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BuscarPokemon.class);
                startActivity(intent);
            }
        });

        ListView allPokemons = findViewById(R.id.all_pokemons_list);
        if(listPokemons.size()!=0)
            allPokemons.setAdapter(new PokemonAdapter(listPokemons));
        else
            allPokemons.setAdapter(new PokemonAdapter(new ArrayList<Pokemon>()));
        Button downloadButton = findViewById(R.id.download_button);
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetDataThread downloadTh = new GetDataThread(MainActivity.this);
                Thread th = new Thread(downloadTh);
                th.start();
            }
        });

        allPokemons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),PokemonStats.class);
                intent.putExtra("POKEMON_STAT",listPokemons.get(i));
                startActivity(intent);
            }
        });
    }

    public void updatePokemons(List<Pokemon> pokemons){
        this.listPokemons.addAll(pokemons);
        ListView listView = findViewById(R.id.all_pokemons_list);
        ((PokemonAdapter) listView.getAdapter()).updatePokemons(pokemons);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updatePokemons(listPokemons);
        Toast.makeText(MainActivity.this, "onResume", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(MainActivity.this, "onPause", Toast.LENGTH_LONG).show();
    }
}