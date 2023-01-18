package com.example.poke_pedia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.poke_pedia.model.Pokemon;

public class PokemonStats extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_stats);

        Pokemon pokemon = (Pokemon) getIntent().getSerializableExtra("POKEMON_STAT");

        ImageView pokemonImage = findViewById(R.id.pokemon_image_pokemon_stats);
        pokemonImage.setImageBitmap(pokemon.getImage().getBitmap());

        TextView pokemonName = findViewById(R.id.pokemon_name_pokemon_stats);
        pokemonName.setText(pokemon.getName());
    }
}