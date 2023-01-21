package com.example.poke_pedia.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Stat implements Serializable {

    public Stat(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @SerializedName("name")
    private String name;

    @SerializedName("url")
    private String url;
}
