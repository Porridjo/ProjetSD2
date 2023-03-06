import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Set;

public class Ligne {
    private int identifiant;
    private int numero;
    private String premierStation;
    private String destination;
    private String typeDeTransport;
    private int tempsDAttente;

    public Ligne(int identifiant, int numero, String premierStation, String destination, String typeDeTransport, int tempsDAttente) {
        this.identifiant = identifiant;
        this.numero = numero;
        this.premierStation = premierStation;
        this.destination = destination;
        this.typeDeTransport = typeDeTransport;
        this.tempsDAttente = tempsDAttente;
    }

    public int getIdentifiant() {
        return identifiant;
    }

    public int getNumero() {
        return numero;
    }
    public String getPremierStation() {
        return premierStation;
    }

    public String getDestination() {
        return destination;
    }

    public String getTypeDeTransport() {
        return typeDeTransport;
    }

    public int getTempsDAttente() {
        return tempsDAttente;
    }

    @Override
    public String toString() {
        return "Ligne{" +
                "identifiant=" + identifiant +
                ", numero=" + numero +
                ", premierStation='" + premierStation + '\'' +
                ", destination='" + destination + '\'' +
                ", typeDeTransport='" + typeDeTransport + '\'' +
                ", tempsDAttente=" + tempsDAttente +
                '}';
    }
}
