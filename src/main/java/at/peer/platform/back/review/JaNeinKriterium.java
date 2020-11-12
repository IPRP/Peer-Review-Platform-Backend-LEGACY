package at.peer.platform.back.review;

public class JaNeinKriterium extends Kriterium{
    private boolean bewertung;

    public JaNeinKriterium(String name, String bewertungsText, boolean bewertung) {
        super(name, bewertungsText);
        this.bewertung = bewertung;
    }


    @Override
    public int getBewertung() {
        if (this.bewertung){
            return 1;
        }else {
            return 0;
        }
    }

    public void setBewertung(boolean bewertung) {
        this.bewertung = bewertung;
    }
}
