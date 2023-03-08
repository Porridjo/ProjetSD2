import java.time.Duration;

public class Troncon {
    private Ligne ligne;
    private String depart;
    private String arrivee;
    private int duree;

    public Troncon(Ligne ligne, String depart, String arrivee, int duree) {
        this.ligne = ligne;
        this.depart = depart;
        this.arrivee = arrivee;
        this.duree = duree;
    }

    public Ligne getLigne() {
        return ligne;
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

    @Override
    public String toString() {
        return "Troncon [" +
            "depart='" + depart + '\'' +
            ", arrivee='" + arrivee + '\'' +
            ", duree=" + duree +
            "ligne=" + ligne
            + "]";
    }
}
