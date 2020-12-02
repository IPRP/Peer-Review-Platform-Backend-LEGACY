package com.iprp.backend.user;

import com.fasterxml.jackson.annotation.*;

@JsonAutoDetect
public class Kriterium {

    @JsonProperty
    private int id;
    @JsonProperty
    private String name, beschreibung;
    @JsonProperty
    private double prozent, punkte;
    @JsonProperty
    private boolean janein;

    @JsonCreator
    public Kriterium(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("beschreibung") String beschreibung, @JsonProperty("prozent") double prozent, @JsonProperty("punkte") double punkte, @JsonProperty("janein") boolean janein) {
        this.id = id;
        this.name = name;
        this.beschreibung = beschreibung;
        this.prozent = prozent;
        this.punkte = punkte;
        this.janein = janein;
    }

    @JsonGetter
    public int getId() {
        return id;
    }

    @JsonSetter
    public void setId(int id) {
        this.id = id;
    }
    @JsonGetter
    public String getName() {
        return name;
    }
    @JsonSetter
    public void setName(String name) {
        this.name = name;
    }
    @JsonGetter
    public String getBeschreibung() {
        return beschreibung;
    }
    @JsonSetter
    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }
    @JsonGetter
    public double getProzent() {
        return prozent;
    }
    @JsonSetter
    public void setProzent(double prozent) {
        this.prozent = prozent;
    }
    @JsonGetter
    public double getPunkte() {
        return punkte;
    }
    @JsonSetter
    public void setPunkte(double punkte) {
        this.punkte = punkte;
    }
    @JsonGetter
    public boolean isJanein() {
        return janein;
    }
    @JsonSetter
    public void setJanein(boolean janein) {
        this.janein = janein;
    }
}
