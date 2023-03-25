import java.time.Duration;

public class Troncon {
    private Ligne ligne;
    private Station stationDepart;
    private Station stationArrivee;
    private int duree;

    public Troncon(Ligne ligne, Station stationDepart, Station stationArrivee, int duree) {
        this.ligne = ligne;
        this.stationDepart = stationDepart;
        this.stationArrivee = stationArrivee;
        this.duree = duree;
    }

    public Ligne getLigne() {
        return ligne;
    }

    public Station getStationDepart() {
        return stationDepart;
    }

    public Station getStationArrivee() {
        return stationArrivee;
    }

    public int getDuree() {
        return duree;
    }

    @Override
    public String toString() {
        return "Troncon [" +
            "depart=" + stationDepart +
            ", arrivee=" + stationArrivee +
            ", duree=" + duree +
            ", ligne=" + ligne
            + "]";
    }
}
