package com.example.poke_pedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.poke_pedia.db.DataBaseUsers;
import com.example.poke_pedia.model.Pokemon;
import com.example.poke_pedia.model.PokemonAdapter;
import com.example.poke_pedia.util.GetDataThread;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final List<Pokemon> listPokemons = new ArrayList<>();
    private final DataBaseUsers myDB = new DataBaseUsers(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listPokemons.addAll(myDB.getPokemons());

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

        allPokemons.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(getApplicationContext(),PokemonStats.class);
            intent.putExtra("POKEMON_STAT",listPokemons.get(i));
            startActivity(intent);
        });

        ImageView logout = findViewById(R.id.logout_button_main);
        logout.setOnClickListener(view -> {
            MySharedPreference.setUserName(MainActivity.this,"");
            startActivity(new Intent(getApplicationContext(),SignupActivity.class));
        });
    }

    public void updatePokemons(List<Pokemon> pokemons){
        for(Pokemon p : pokemons)
            myDB.insertPokemon(p);
        this.listPokemons.addAll(myDB.getPokemons());
        System.err.println("size of list "+listPokemons.size());
        ListView listView = findViewById(R.id.all_pokemons_list);
        ((PokemonAdapter) listView.getAdapter()).updatePokemons(listPokemons);
    }

}