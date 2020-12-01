package com.iprp.backend.abgabe;

public class Abgabe {
    private int id;
    private boolean open, lated;
    private String to, from;

    public Abgabe(boolean open, boolean lated, String to, String from) {
        this.open = open;
        this.lated = lated;
        this.to = to;
        this.from = from;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isLated() {
        return lated;
    }

    public void setLated(boolean lated) {
        this.lated = lated;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
