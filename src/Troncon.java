import java.time.Duration;

public class Troncon {
    private int numeroDeLigne;
    private String depart;
    private String arrivee;
    private int duree;

    public Troncon(int numeroDeLigne, String depart, String arrivee, int duree) {
        this.numeroDeLigne = numeroDeLigne;
        this.depart = depart;
        this.arrivee = arrivee;
        this.duree = duree;
    }

    public int getNumeroDeLigne() {
        return numeroDeLigne;
    }

    public String getDepart() {
        return depart;
    }

    public String getArrivee() {
        return arrivee;
    }

    public int getDuree() {
        return duree;
    }
}
