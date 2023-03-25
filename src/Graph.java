import java.io.*;
import java.util.*;

public class Graph {

  private Set<Ligne> ensembleLignes = new HashSet<>();
  private Map<String, Station> ensembleStations = new HashMap<>();
  private Map<Station, Set<Troncon>> listeDAdjacence = new HashMap<>();


  public Graph(File ligne, File troncon) {
    creerEnsembleLignes(ligne);
    creerMapTronconStation(troncon);
  }

  private void creerEnsembleLignes(File ligne) {
    try (FileReader lignes = new FileReader(ligne)) {
      BufferedReader readerLigne = new BufferedReader(lignes);

      String line = readerLigne.readLine();
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
  }

  private void creerMapTronconStation(File troncon) {

    try (FileReader troncons = new FileReader(troncon)) {
      BufferedReader reader = new BufferedReader(troncons);

      String line = reader.readLine();
      while (line != null) {
        String[] array = line.split(",");

        for (Ligne ligne : ensembleLignes) {
          Ligne ligneDuTroncon;
          if (ligne.getIdentifiant() == (Integer.parseInt(array[0]))) {
            ligneDuTroncon = ligne;
            if (!ensembleStations.containsKey(array[1])) {
              ensembleStations.put(array[1], new Station(array[1]));
            }
            if (!ensembleStations.containsKey(array[2])) {
              ensembleStations.put(array[2], new Station(array[2]));
            }

            Troncon tronconCree = new Troncon(ligneDuTroncon, ensembleStations.get(array[1]),
                ensembleStations.get(array[2]), Integer.parseInt(array[3]));

            if (listeDAdjacence.get(tronconCree.getStationDepart()) == null) {
              Set<Troncon> tronconsSet = new HashSet<>();
              tronconsSet.add(tronconCree);
              listeDAdjacence.put(tronconCree.getStationDepart(), tronconsSet);
            } else {
              listeDAdjacence.get(tronconCree.getStationDepart()).add(tronconCree);
            }
            if (listeDAdjacence.get(tronconCree.getStationArrivee()) == null) {
              Set<Troncon> tronconsSet = new HashSet<>();
              tronconsSet.add(tronconCree);
              listeDAdjacence.put(tronconCree.getStationArrivee(), tronconsSet);
            } else {
              listeDAdjacence.get(tronconCree.getStationArrivee()).add(tronconCree);
            }
            line = reader.readLine();
          }

        }

      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  public void calculerCheminMinimisantNombreTroncons(String station1, String station2) {
    Deque<Station> file = new ArrayDeque<>();
    Map<Station, Troncon> stationsDejaAtteintes = new HashMap<>();
    stationsDejaAtteintes.put(ensembleStations.get(station1), null);
    Station sommetCourant = ensembleStations.get(station1);

    while (!stationsDejaAtteintes.containsKey(ensembleStations.get(station2))) {
      Set<Troncon> ensembleTroncons = listeDAdjacence.get(sommetCourant);
      for (Troncon troncon : ensembleTroncons) {
        if (stationsDejaAtteintes.containsKey(troncon.getStationArrivee())) {
          continue;
        }
        file.add(troncon.getStationArrivee());
        stationsDejaAtteintes.put(troncon.getStationArrivee(), troncon);
      }
      sommetCourant = file.pop();
    }

    List<Troncon> cheminPlusCourt = new ArrayList<>();
    Station parcouru = ensembleStations.get(station2);
    while (!stationsDejaAtteintes.get(parcouru).getStationDepart()
        .equals(ensembleStations.get(station1))) {
      cheminPlusCourt.add(stationsDejaAtteintes.get(parcouru));
      parcouru = stationsDejaAtteintes.get(parcouru).getStationDepart();
    }
    cheminPlusCourt.add(stationsDejaAtteintes.get(parcouru));

    afficherChemin(cheminPlusCourt);
  }


  public void calculerCheminMinimisantTempsTransport(String station1, String station2) {

    long startTime = System.nanoTime();

    Set<Station> etiquettesProvisoires = new TreeSet<>(
        Comparator.comparingInt(Station::getEtiquette));
    Set<Station> etiquettesDefinitives = new HashSet<>();
    Map<Station, Troncon> mapStationPrecedente = new HashMap<>();
    Set<Station> stationsParcourues = new HashSet<>();

    Station stationDepart = ensembleStations.get(station1);
    stationDepart.setEtiquette(0);
    etiquettesDefinitives.add(stationDepart);
    Station stationArrivee = ensembleStations.get(station2);

    Station stationCourante = stationDepart;

    while (!etiquettesDefinitives.contains(stationArrivee)) {
      stationsParcourues.add(stationCourante);

      Set<Troncon> ensembleTronconsDepuisStationCourante = listeDAdjacence.get(stationCourante);

      if (ensembleTronconsDepuisStationCourante == null) {
        break;
      }

      for (Troncon troncon : ensembleTronconsDepuisStationCourante) {
        if (!stationsParcourues.contains(troncon.getStationArrivee())) {
          if (!etiquettesProvisoires.contains(troncon.getStationArrivee())) {
            Station newStation = troncon.getStationArrivee();
            if (!etiquettesDefinitives.contains(stationCourante)) {
              newStation.setEtiquette(troncon.getDuree());
            } else {
              newStation.setEtiquette(stationCourante.getEtiquette() + troncon.getDuree());
            }
            etiquettesProvisoires.add(newStation);
            mapStationPrecedente.put(newStation, troncon);
          } else if (stationCourante.getEtiquette() + troncon.getDuree()
              < troncon.getStationArrivee()
              .getEtiquette()) {
            Station newStation = troncon.getStationArrivee();
            newStation.setEtiquette(stationCourante.getEtiquette() + troncon.getDuree());
            mapStationPrecedente.put(newStation, troncon);
          }
        }
      }

      // Recherche du minimum dans les étiquettes provisoires
      int coutMin = Integer.MAX_VALUE;
      Station stationMin = new Station("placeholder");
      for (Station station : etiquettesProvisoires) {
        if (stationsParcourues.contains(station)) {
          continue;
        }
        int cout = station.getEtiquette();
        if (cout < coutMin) {
          coutMin = cout;
          stationMin = station;
        }
      }
      etiquettesProvisoires.remove(stationMin);

      for (Troncon troncon : ensembleTronconsDepuisStationCourante) {
        if (troncon.getStationArrivee().equals(stationMin) && troncon.getStationDepart()
            .equals(stationCourante)) {
          mapStationPrecedente.put(stationMin, troncon);
        }
      }

      stationMin.setEtiquette(coutMin);
      etiquettesDefinitives.add(stationMin);
      stationCourante = stationMin;
    }

    System.out.println("etiquettes contient la station arrivée : " + etiquettesDefinitives.contains(
        ensembleStations.get(station1)));
    System.out.println("taille : " + mapStationPrecedente.size() + " " + mapStationPrecedente);

    List<Troncon> parcours = new ArrayList<>();

    System.out.println(stationCourante);

    while (!stationCourante.equals(ensembleStations.get(station1))) {
      parcours.add(mapStationPrecedente.get(stationCourante));
      stationCourante = mapStationPrecedente.get(stationCourante).getStationDepart();
    }

    long endTime = System.nanoTime();

    System.out.println("temps: " + ((endTime - startTime) / 1000) + " microsecondes");
    afficherChemin(parcours);

  }


  public void afficherChemin(List<Troncon> cheminPlusCourt) {
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
}
