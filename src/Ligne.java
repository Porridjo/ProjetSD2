import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Ligne {
    private int identifiant;
    private String numero;
    private String premierStation;
    private String destination;
    private String typeDeTransport;
    private int tempsDAttente;

    public Ligne(int identifiant, String numero, String premierStation, String destination, String typeDeTransport, int tempsDAttente) {
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

    public String getNumero() {
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ligne ligne = (Ligne) o;
        return identifiant == ligne.identifiant;
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifiant);
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
