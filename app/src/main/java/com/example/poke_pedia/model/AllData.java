package com.example.poke_pedia.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AllData {

    @SerializedName("count")
    private int count;

    public List<Pokemon> getResults() {
        return results;
    }

    public void setResults(ArrayList results) {
        this.results = results;
    }

    @SerializedName("results")
    private ArrayList<Pokemon> results;

}
