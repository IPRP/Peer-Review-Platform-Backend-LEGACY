package at.peer.platform.back.review;

public class ProzentKriterium extends Kriterium{

    private int bewertung;

    public ProzentKriterium(String name, String bewertungsText, int bewertung) {
        super(name, bewertungsText);
        this.bewertung = bewertung;
    }

    @Override
    public int getBewertung() {
        return bewertung;
    }

    @Override
    public void setBewertung(int bewertung) {
        this.bewertung = bewertung;
    }
}
