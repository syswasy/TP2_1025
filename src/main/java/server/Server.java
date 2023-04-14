package server;

import javafx.util.Pair;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * La classe Server est une implémentation de serveur qui accepte les connexions des clients,
 * interprète les commandes "INSCRIRE" "CHARGER" et exécute les méthodes.
 */
public class Server {

    public final static String REGISTER_COMMAND = "INSCRIRE";
    public final static String LOAD_COMMAND = "CHARGER";
    private final ServerSocket server;
    private Socket client;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private final ArrayList<EventHandler> handlers;

    /**
     * Ce constructeur déclare et initialise un serveur qui écoute sur le port et une liste d'objets EventHandler
     * qui ajoute un gestionnaire d'événement.
     * @param port Le port sur lequel le serveur doit écouter.
     * @throws IOException Une exception si il y'a une erreur pour la création du serveur.
     */
    public Server(int port) throws IOException {
        this.server = new ServerSocket(port, 1);
        this.handlers = new ArrayList<EventHandler>();
        this.addEventHandler(this::handleEvents);
    }

    /**
     * La méthode addEventHandler rajoute l'argument h à la liste handlers.
     * @param h est l'argument qui représente l'objet EeventHandler à rajouter.
     */
    public void addEventHandler(EventHandler h) {
        this.handlers.add(h);
    }

    private void alertHandlers(String cmd, String arg) {
        for (EventHandler h : this.handlers) {
            h.handle(cmd, arg);
        }
    }


    public void run() {
        while (true) {
            try {
                client = server.accept();
                System.out.println("Connecté au client: " + client);
                objectInputStream = new ObjectInputStream(client.getInputStream());
                objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                listen();
                disconnect();
                System.out.println("Client déconnecté!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void listen() throws IOException, ClassNotFoundException {
        String line;
        if ((line = this.objectInputStream.readObject().toString()) != null) {
            Pair<String, String> parts = processCommandLine(line);
            String cmd = parts.getKey();
            String arg = parts.getValue();
            this.alertHandlers(cmd, arg);
        }
    }

    public Pair<String, String> processCommandLine(String line) {
        String[] parts = line.split(" ");
        String cmd = parts[0];
        String args = String.join(" ", Arrays.asList(parts).subList(1, parts.length));
        return new Pair<>(cmd, args);
    }

    public void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        client.close();
    }

    public void handleEvents(String cmd, String arg) {
        if (cmd.equals(REGISTER_COMMAND)) {
            handleRegistration();
        } else if (cmd.equals(LOAD_COMMAND)) {
            handleLoadCourses(arg);
        }
    }

    /**
     Lire un fichier texte contenant des informations sur les cours et les transofmer en liste d'objets 'Course'.
     La méthode filtre les cours par la session spécifiée en argument.
     Ensuite, elle renvoie la liste des cours pour une session au client en utilisant l'objet 'objectOutputStream'.
     La méthode gère les exceptions si une erreur se produit lors de la lecture du fichier ou de l'écriture de l'objet dans le flux.
     @param arg la session pour laquelle on veut récupérer la liste des cours
     */
           public void handleLoadCourses (String arg) {
            ArrayList<Course> cours = new ArrayList<Course>();
            try {
                FileReader fr = new FileReader("./src/main/java/server/data/cours.txt");
                BufferedReader reader = new BufferedReader(fr);
                String coursData;
                while ((coursData = reader.readLine()) != null) {
                    String[] coursDataList = coursData.split("\t");
                    if (coursDataList.length >= 3 && arg.equals(coursDataList[2])){
                        String name = coursDataList[0];
                        String code = coursDataList[1];
                        String session = coursDataList[2];
                        Course course = new Course(name,code,session);
                        cours.add(course);
                    }
                }
                reader.close();
                objectOutputStream.writeObject(cours);
                objectOutputStream.flush();

            } catch(FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("Le fichier cours.txt n'a pas été trouvé !");
            } catch (IOException e){
                e.printStackTrace();
            }
        }


    /**
     Récupérer l'objet 'RegistrationForm' envoyé par le client en utilisant 'objectInputStream', l'enregistrer dans un fichier texte
     et renvoyer un message de confirmation au client.
     La méthode gére les exceptions si une erreur se produit lors de la lecture de l'objet, l'écriture dans un fichier ou dans le flux de sortie.
     */
    public void handleRegistration() {
        // TODO: implémenter cette méthode
    }
}

