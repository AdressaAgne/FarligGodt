package com.example.android.farliggodtapp.api;

import org.json.JSONObject;

/**
 * Created by Agne Ødegaard on 01/12/2016.
 */

public class Specie implements JSONpop{

    private String name;
    private int id;
    private String latin;
    private String risk;
    private String family;
    private boolean eatable;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLatin() {
        return latin;
    }

    public void setLatin(String latin) {
        this.latin = latin;
    }

    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public boolean isEatable() {
        return eatable;
    }

    public void setEatable(boolean eatable) {
        this.eatable = eatable;
    }

    @Override
    public void populate(JSONObject data) {

        this.id      = data.optInt("taxonID");
        this.name    = data.optString("navn");
        this.latin   = data.optString("scientificName");
        this.risk    = data.optString("risiko");
        this.family  = data.optString("family");
        this.eatable = data.optBoolean("canEat");
        this.image   = data.optString("image");

    }
}
