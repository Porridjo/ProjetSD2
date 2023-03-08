import java.io.*;
import java.util.*;

public class Graph {

    private Set<Ligne> ensembleLignes = new HashSet<>();
    private Map<String, Set<Troncon>> mapStationTroncon = new HashMap<>();

    public Graph(File ligne, File troncon) {
        creerEnsembleLignes(ligne);
        creerMapTronconStation(troncon);
    }

    public void creerEnsembleLignes(File ligne){
        try (FileReader lignes = new FileReader(ligne)){
            BufferedReader readerLigne = new BufferedReader (lignes);

            String line;
            try {
                line = readerLigne.readLine();
                while (line != null) {
                    String[] array = line.split(",");
                    Ligne ligneCree = new Ligne(Integer.parseInt(array[0]), Integer.parseInt(array[1]),
                        array[2], array[3], array[4], Integer.parseInt(array[5]));
                    ensembleLignes.add(ligneCree);

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void creerMapTronconStation(File troncon){
        try (FileReader troncons = new FileReader(troncon)) {
            BufferedReader reader = new BufferedReader (troncons);
            String line;
            try {
                line = reader.readLine();
                while (line != null) {
                    String[] array = line.split(",");
                    Troncon tronCree = new Troncon(Integer.parseInt(array[0]),array[1],
                        array[2], Integer.parseInt(array[3]));
                    if (mapStationTroncon.get(tronCree.getDepart()) == null) {
                        Set<Troncon> tronconsSet = new HashSet<>();
                        tronconsSet.add(tronCree);
                        mapStationTroncon.put(tronCree.getDepart(), tronconsSet);

                    } else {
                        mapStationTroncon.get(tronCree.getDepart()).add(tronCree);
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
        Set<Troncon> ensembleTroncons = mapStationTroncon.get(station1);
        for (Troncon troncon : ensembleTroncons) {
            file.add(troncon.getArrivee());
        }
        file.pop();



    }

    public void calculerCheminMinimisantTempsTransport(String station1, String station2) {

    }
}
