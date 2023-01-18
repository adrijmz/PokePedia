package com.example.poke_pedia.model;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Ability {

    public Map<String, String> getAbility() {
        return ability;
    }

    public void setAbility(Map<String, String> ability) {
        this.ability = ability;
    }

    @SerializedName("ability")
    private Map<String,String> ability;

}
