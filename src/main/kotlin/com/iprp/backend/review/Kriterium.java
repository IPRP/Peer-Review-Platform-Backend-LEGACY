package com.iprp.backend.review;

abstract public class Kriterium {
    private String name, id, bewertungsText;
    private boolean done;


    public Kriterium(String name, String bewertungsText) {
        this.name = name;
        this.bewertungsText = bewertungsText;
        this.id = name;
        this.done = false;
    }

    public int getBewertung(){
        return 0;
    }

    public void setBewertung(int bw){

    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBewertungsText() {
        return bewertungsText;
    }

    public void setBewertungsText(String bewertungsText) {
        this.bewertungsText = bewertungsText;
    }
}
