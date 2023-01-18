package com.example.poke_pedia.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Stat implements Serializable {

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
