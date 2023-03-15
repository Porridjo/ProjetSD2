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
              Troncon tronconCree = new Troncon(ligneDuTroncon, array[1],
                  array[2], Integer.parseInt(array[3]));

              if (listeDAdjacence.get(tronconCree.getDepart()) == null) {
                Set<Troncon> tronconsSet = new HashSet<>();
                tronconsSet.add(tronconCree);
                listeDAdjacence.put(tronconCree.getDepart(), tronconsSet);
              } else {
                listeDAdjacence.get(tronconCree.getDepart()).add(tronconCree);
              }
              if (listeDAdjacence.get(tronconCree.getArrivee()) == null) {
                Set<Troncon> tronconsSet = new HashSet<>();
                tronconsSet.add(tronconCree);
                listeDAdjacence.put(tronconCree.getArrivee(), tronconsSet);
              } else {
                listeDAdjacence.get(tronconCree.getArrivee()).add(tronconCree);
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

  private void creerMap(File troncon) {
    try (FileReader troncons = new FileReader(troncon)) {
      BufferedReader reader = new BufferedReader(troncons);
      String line;
      try {
        line = reader.readLine();
        while (line != null) {
          String[] array = line.split(",");


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
    Map<String, Integer> mapEtiquetteProvisoire = new HashMap<>();
    Map<String, Integer> mapEtiquetteDefinitive = new HashMap<>();
    Map<String, Troncon> mapStationPrecedente = new HashMap<>();
    // Set<String> stationsParcourues = new HashSet<>();    -> nécessaire?

    // On met 0 dans les étiquettes définitives pr la station de départ
    mapEtiquetteDefinitive.put(station1, 0);

    String stationCourante = station1;
    while (mapEtiquetteDefinitive.get(station2) == null) {
      // -1 = croix
      mapEtiquetteProvisoire.put(stationCourante, -1);

      Set<Troncon> ensembleTronconsDepuisStationCourante = listeDAdjacence.get(stationCourante);
      if (ensembleTronconsDepuisStationCourante == null) {
        break;
      }
      // stationsParcourues.add(stationCourante);
      for (Troncon troncon : ensembleTronconsDepuisStationCourante) {
        if (mapEtiquetteProvisoire.get(troncon.getArrivee()) == null) {
          mapEtiquetteProvisoire.put(troncon.getArrivee(), troncon.getDuree());
        }
      }

      int coutMin = Integer.MAX_VALUE;
      String stationMin = "";
      for (String station : mapEtiquetteProvisoire.keySet()) {
        int cout = mapEtiquetteProvisoire.get(station);
        if (cout < coutMin) {
          coutMin = cout;
          stationMin = station;
        }
      }

      for (Troncon troncon : ensembleTronconsDepuisStationCourante) {
        if (troncon.getArrivee().equals(stationMin)) {
          mapStationPrecedente.put(stationMin, troncon);
        }
      }
      mapEtiquetteDefinitive.put(stationMin, coutMin);
      stationCourante = stationMin;
    }


    while (!stationCourante.equals(station1)) {
      System.out.println(mapStationPrecedente.get(stationCourante));
      stationCourante = mapStationPrecedente.get(stationCourante).getDepart();
    }

  }
}
