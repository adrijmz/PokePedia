package com.example.poke_pedia;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.poke_pedia.model.Pokemon;
import com.example.poke_pedia.model.Stats;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PokemonStats extends AppCompatActivity {

    Pokemon pokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_stats);

        ImageView goBack = findViewById(R.id.go_back_button_pokemon_stats);
        goBack.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), MainActivity.class)));

        pokemon = (Pokemon) getIntent().getSerializableExtra("POKEMON_STAT");

        ImageView pokemonImage = findViewById(R.id.pokemon_image_pokemon_stats);
        pokemonImage.setImageBitmap(pokemon.getImage().getBitmap());

        pokemonImage.setOnClickListener(view -> {
            Bitmap image = pokemon.getImage().getBitmap();
            if(isExternalStorageWritable())
               saveImageExternal(image);
            else
                Toast.makeText(PokemonStats.this,"External storage is not writeable",Toast.LENGTH_LONG).show();
        });

        TextView pokemonName = findViewById(R.id.pokemon_name_pokemon_stats);
        pokemonName.setText(pokemon.getName());

        List<Stats> stats = pokemon.getStats();

        TextView stat0 = findViewById(R.id.stat_0);
        stat0.setText(stats.get(0).getStat().getName()+": "+stats.get(0).getBase_stat());

        TextView stat1 = findViewById(R.id.stat_1);
        stat1.setText(stats.get(1).getStat().getName()+": "+stats.get(1).getBase_stat());

        TextView stat2 = findViewById(R.id.stat_2);
        stat2.setText(stats.get(2).getStat().getName()+": "+stats.get(2).getBase_stat());

        TextView stat3 = findViewById(R.id.stat_3);
        stat3.setText(stats.get(3).getStat().getName()+": "+stats.get(3).getBase_stat());

        TextView stat4 = findViewById(R.id.stat_4);
        stat4.setText(stats.get(4).getStat().getName()+": "+stats.get(4).getBase_stat());

        TextView stat5 = findViewById(R.id.stat_5);
        stat5.setText(stats.get(5).getStat().getName()+": "+stats.get(5).getBase_stat());

        ImageView shareButton = findViewById(R.id.share_button_pokemon_stats);
        shareButton.setOnClickListener(view -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            String text = pokemon.getName()+"'s stats:\n" +
                    pokemon.getStats().get(0).getStat().getName()+": "+pokemon.getStats().get(0).getBase_stat()+"\n∑"+
                    pokemon.getStats().get(1).getStat().getName()+": "+pokemon.getStats().get(1).getBase_stat()+"\n"+
                    pokemon.getStats().get(2).getStat().getName()+": "+pokemon.getStats().get(2).getBase_stat()+"\n"+
                    pokemon.getStats().get(3).getStat().getName()+": "+pokemon.getStats().get(3).getBase_stat()+"\n"+
                    pokemon.getStats().get(4).getStat().getName()+": "+pokemon.getStats().get(4).getBase_stat()+"\n"+
                    pokemon.getStats().get(5).getStat().getName()+": "+pokemon.getStats().get(5).getBase_stat();

            sendIntent.putExtra(Intent.EXTRA_TEXT, text);
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        });
    }

    private Uri saveImageExternal(Bitmap image) {
        String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), image, "palette", "share palette");
        Uri bitmapUri = Uri.parse(bitmapPath);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/png");
        intent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
        startActivity(Intent.createChooser(intent, "Share"));

        return bitmapUri;
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
}