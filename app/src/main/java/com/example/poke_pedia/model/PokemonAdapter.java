package com.example.poke_pedia.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.poke_pedia.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PokemonAdapter extends BaseAdapter {

    private List<Pokemon> data = new ArrayList<>();

    public PokemonAdapter(List<Pokemon> data) {
        this.data.addAll(data);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Pokemon getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) viewGroup.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.pokemon_row,null);
        }
        System.err.println("");
        TextView textView = view.findViewById(R.id.pokemon_name_text);
        textView.setText(data.get(i).getName().toUpperCase(Locale.ROOT));
        ImageView imageView = view.findViewById(R.id.pokemon_image);
        imageView.setImageBitmap(data.get(i).getImage().getBitmap());
        return view;
    }

    public void updatePokemons(List<Pokemon> pokemons){
        this.data.addAll(pokemons);
        super.notifyDataSetChanged();
    }
}
