package com.example.poke_pedia.model;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Pokemon implements Serializable {

    public Pokemon(String name, String url){
        this.name = name;
        this.url = url;
    }

    public Pokemon(int id, String name){
        this.id = id;
        this.name = name;
    }

    @SerializedName("name")
    private String name;

    @SerializedName("url")
    private String url;

    private int id;
    private SerializableBitMap image;

    @SerializedName("stats")
    private List<Stats> stats;

    public SerializableBitMap getImage() {
        return image;
    }

    public void setImage(SerializableBitMap image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Stats> getStats() {
        return stats;
    }

    public void setStats(List<Stats> stats) {
        this.stats = stats;
    }

}
