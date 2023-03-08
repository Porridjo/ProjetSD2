import java.io.*;
import java.util.*;

public class Graph {

  private Set<Ligne> ensembleLignes = new HashSet<>();
  private Map<String, Set<Troncon>> listeDAdjacence = new HashMap<>();

  public Graph(File ligne, File troncon) {
    creerEnsembleLignes(ligne);
    creerMapTronconStation(troncon);
  }

  private void creerEnsembleLignes(File ligne) {
    try (FileReader lignes = new FileReader(ligne)) {
      BufferedReader readerLigne = new BufferedReader(lignes);

      String line;
      try {
        line = readerLigne.readLine();
        while (line != null) {
          String[] array = line.split(",");
          Ligne ligneCree = new Ligne(Integer.parseInt(array[0]), array[1],
              array[2], array[3], array[4], Integer.parseInt(array[5]));
          ensembleLignes.add(ligneCree);
          line = readerLigne.readLine();
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void creerMapTronconStation(File troncon) {
    try (FileReader troncons = new FileReader(troncon)) {
      BufferedReader reader = new BufferedReader(troncons);
      String line;
      try {
        line = reader.readLine();
        while (line != null) {
          String[] array = line.split(",");

          for (Ligne ligne : ensembleLignes) {
            Ligne ligneDuTroncon;
            if (ligne.getIdentifiant() == (Integer.parseInt(array[0]))) {
              ligneDuTroncon = ligne;
              Troncon tronCree = new Troncon(ligneDuTroncon, array[1],
                  array[2], Integer.parseInt(array[3]));
              if (listeDAdjacence.get(tronCree.getDepart()) == null) {
                Set<Troncon> tronconsSet = new HashSet<>();
                tronconsSet.add(tronCree);
                listeDAdjacence.put(tronCree.getDepart(), tronconsSet);

              } else {
                listeDAdjacence.get(tronCree.getDepart()).add(tronCree);
              }
              line = reader.readLine();
            }

          }

        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void calculerCheminMinimisantNombreTroncons(String station1, String station2) {
    Deque<String> file = new ArrayDeque<>();
    Map<String, Troncon> stationsDejaAtteintes = new HashMap<>();
    stationsDejaAtteintes.put(station1, null);
    String sommetCourant = station1;

    while (!stationsDejaAtteintes.containsKey(station2)) {
      Set<Troncon> ensembleTroncons = listeDAdjacence.get(sommetCourant);
      for (Troncon troncon : ensembleTroncons) {
        if (stationsDejaAtteintes.containsKey(troncon.getArrivee())) {
          continue;
        }
        file.add(troncon.getArrivee());
        stationsDejaAtteintes.put(troncon.getArrivee(), troncon);
      }
      sommetCourant = file.pop();
    }

    List<Troncon> cheminPlusCourt = new ArrayList<>();
    String parcouru = station2;
    while (!stationsDejaAtteintes.get(parcouru).getDepart().equals(station1)) {
      cheminPlusCourt.add(stationsDejaAtteintes.get(parcouru));
      parcouru = stationsDejaAtteintes.get(parcouru).getDepart();
    }
    cheminPlusCourt.add(stationsDejaAtteintes.get(parcouru));

    printCheminMinimisantNombreTroncons(cheminPlusCourt);
  }

  public void printCheminMinimisantNombreTroncons(List<Troncon> cheminPlusCourt) {
    int dureeTransport = 0;
    int dureeAttente = cheminPlusCourt.get(cheminPlusCourt.size() - 1).getLigne()
        .getTempsDAttente();
    for (int i = cheminPlusCourt.size() - 1; i >= 0; i--) {
      dureeTransport += cheminPlusCourt.get(i).getDuree();
      if (i < cheminPlusCourt.size() - 1 && !cheminPlusCourt.get(i).getLigne()
          .equals(cheminPlusCourt.get(i + 1).getLigne())) {
        dureeAttente += cheminPlusCourt.get(i).getLigne().getTempsDAttente();
      }
      System.out.println(cheminPlusCourt.get(i));
    }
    System.out.println("nbTroncons=" + cheminPlusCourt.size());
    System.out.println(
        "dureeTransport=" + dureeTransport + " dureeTotale=" + (dureeAttente + dureeTransport));
  }

  public void calculerCheminMinimisantTempsTransport(String station1, String station2) {
    // TODO

  }
}
