package com.example.poke_pedia.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Stats implements Serializable {

    public Stats(int base_stat, Stat stat){
        this.base_stat = base_stat;
        this.stat = stat;
    }

    public int getBase_stat() {
        return base_stat;
    }

    public int getEffort() {
        return effort;
    }

    public Stat getStat() {
        return stat;
    }

    @SerializedName("base_stat")
    private int base_stat;

    @SerializedName("effort")
    private int effort;

    @SerializedName("stat")
    private Stat stat;
}
