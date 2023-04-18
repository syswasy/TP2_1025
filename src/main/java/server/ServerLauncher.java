package server;

/**
 * Classe qui démarre le serveur.
 */
public class ServerLauncher {
    public final static int PORT = 1337;

    /**
     * Cette méthode lance le serveur en créant une instance de la classe Server
     * avec le port spécifié, puis en exécutant le serveur. Elle gère également les exceptions
     * qui pourraient survenir lors de la création et de l'exécution du serveur.
     * @param args Les arguments de ligne de commande .
     */

    public static void main(String[] args) {
        Server server;
        try {
            server = new Server(PORT);
            System.out.println("Server is running...");
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}