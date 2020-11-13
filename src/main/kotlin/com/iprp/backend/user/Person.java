package com.iprp.backend.user;

import com.iprp.backend.workshop.Workshop;

import java.util.ArrayList;

abstract public class Person {
    private String id, vorname, nachname;
    private ArrayList<Workshop> workshops;

    public Person(String id, String vorname, String nachname) {
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
        this.workshops = new ArrayList<Workshop>();
    }

    public String getId() {
        return id;
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public ArrayList<Workshop> getWorkshops() {
        return workshops;
    }

    public Workshop getWorkshop(String id){
        for (int i = 0; this.workshops.size() >= i; i++){
            if(this.workshops.get(i).getId().equals(id)){
                return this.workshops.get(i);
            }
        }
        return null;
    }

    public void setWorkshops(ArrayList<Workshop> workshops) {
        this.workshops = workshops;
    }

    public void addWorkshop(Workshop workshop){
        this.workshops.add(workshop);
    }

    public void delWorkshop(Workshop workshop){
        this.workshops.remove(workshop);
    }

    public void editWorkshop(Workshop workshopOld, Workshop workshopNeu){
        this.delWorkshop(workshopOld);
        this.addWorkshop(workshopNeu);
    }
}
