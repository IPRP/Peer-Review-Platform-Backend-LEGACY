package com.iprp.backend.review;


import com.iprp.backend.data.user.Student;

import java.util.ArrayList;

public class Review {
    private ArrayList<Kriterium> kriteriums;
    private Student abgeber, reviewer;
    private int gesamtBewertung;
    private String gesamtBewertungsText;
    private int id;

    public Review(ArrayList<Kriterium> kriteriums, Student abgeber, Student reviewer, String gesamtBewertungsText) {
        this.kriteriums = kriteriums;
        this.abgeber = abgeber;
        this.reviewer = reviewer;
        this.gesamtBewertungsText = gesamtBewertungsText;
        this.gesamtBewertung = 0;
        for (int i = 0; i <= this.kriteriums.size(); i++){
            if (this.kriteriums.get(i).getClass().getName().equals("JaNeinKriterium")){
                System.out.println("Found Ja Nein");
                this.gesamtBewertung += this.kriteriums.get(i).getBewertung();
            }else if (this.kriteriums.get(i).getClass().getName().equals("ProzentKriterium")){
                System.out.println("Found Prozent");
                this.gesamtBewertung += this.kriteriums.get(i).getBewertung();
            }
        }
    }

    public void setGesamtBewertung(int gesamtBewertung) {
        this.gesamtBewertung = gesamtBewertung;
    }

    public ArrayList<Kriterium> getKriteriums() {
        return kriteriums;
    }

    public Student getAbgeber() {
        return abgeber;
    }

    public Student getReviewer() {
        return reviewer;
    }

    public int getGesamtBewertung() {
        return gesamtBewertung;
    }

    public String getGesamtBewertungsText() {
        return gesamtBewertungsText;
    }
}
