package server;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import server.models.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client_simple {
    private final static String HOST = "localhost";
    private final static int PORT = 1337;

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        System.out.println("Bienvenue au portail d'inscription de cours de l'UDEM ");

        Scanner scanner = new Scanner(System.in);

        Socket socket = new Socket(HOST, PORT);
        ObjectOutputStream ous = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

        int command = 0;
        String session = "";
        chargerCours(command, session, ous, ois);

        ous.close();
        ois.close();
        socket.close();

        while (true) {
            System.out.println("1. Consulter les cours offerts pour une autre session \n" +
                    "2. Inscription à un cours \n ");
            System.out.println("Ou voulez-vous quitter?(répondre 'oui' pour sortir)");
            String response = scanner.nextLine();
            if (response.equals("oui")) {
                System.out.println("Au revoir");
                scanner.close();
                break;
            }
            System.out.println("> Choix: ");
            int choix = scanner.nextInt();

            while (choix < 1 || choix > 2) {
                System.out.println("Prenez un choix valide s'il vous plaît.");
                System.out.print("> Choix: ");
                choix = scanner.nextInt();
            }
            if (choix == 1) {
                Socket clientSocket = new Socket(HOST, PORT);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

                chargerCours(command, session, objectOutputStream, objectInputStream);

                objectOutputStream.close();
                objectInputStream.close();
                clientSocket.close();
                System.out.println("Client déconnecté!");
            } else if (choix == 2) {
                Socket clientSocket = new Socket(HOST, PORT);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

                inscrire(objectOutputStream);

                objectOutputStream.close();
                objectInputStream.close();
                clientSocket.close();
                System.out.println("Client déconnecté!");
            }
            scanner.nextLine();
        }

    }


    public static void chargerCours(int command, String session, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
        try {
            System.out.print("Veuillez choisir la session pour laquelle vous voulez consulter la liste des cours:" +
                    "\n" + "1. Automne" + "\n" + "2. Hiver" + "\n" + "3. Ete" + "\n" + "> Choix: ");
            Scanner scanner = new Scanner(System.in);
            command = scanner.nextInt();

            while (command < 1 || command > 3){
                System.out.println("Choix invalide. Veuillez choisir une session existante.");
                System.out.print("> Choix: ");
                command = scanner.nextInt();
            }
            if (command == 1) {
                objectOutputStream.writeObject("CHARGER Automne");
                session = "automne";
            } else if (command == 2) {
                objectOutputStream.writeObject("CHARGER Hiver");
                session = "hiver";

            } else if (command == 3) {
                objectOutputStream.writeObject("CHARGER Ete");
                session = "ete";

            } else {
                System.out.println("Choix invalide !");
            }


            ArrayList<Course> courses = (ArrayList<Course>) objectInputStream.readObject();
            System.out.println("Les cours offerts pendant la session d'" + session + " sont:");
            int compteur = 1;
            for (Course course : courses) {
                System.out.println(compteur + ". " + course.getName() + "\t" + "\t" + course.getCode());
                compteur++;
            }
        } catch (IOException e) {

        } catch (ClassNotFoundException e) {
        }
    }
    public static void inscrire(ObjectOutputStream objectOutputStream){
        try {
            objectOutputStream.writeObject("INSCRIRE");
            Scanner scanner = new Scanner(System.in);
            System.out.print("Veuillez saisir votre prénom: ");
            String prenom = scanner.nextLine();
            System.out.print("Veuillez saisir votre nom: ");
            String nom = scanner.nextLine();
            System.out.print("Veuillez saisir votre email: ");
            String email = scanner.nextLine();
            System.out.print("Veuillez saisir votre matricule: ");
            String matricule = scanner.nextLine();
            System.out.print("Veuillez saisir le nom du cours: ");
            String courseName = scanner.nextLine();
            System.out.print("Veuillez saisir le code du cours: ");
            String courseCode = scanner.nextLine();
            System.out.print("Veuillez saisir la session du cours: ");
            String courseSession = scanner.nextLine();

            Course course = new Course(courseName, courseCode, courseSession);

            RegistrationForm form = new RegistrationForm(prenom, nom, email, matricule, course);

            objectOutputStream.writeObject(form);
            System.out.println("Félicitations! L'inscription de " + prenom + " au cour " + courseCode +
                    " a été réussi");
        }
        catch (IOException e){

        }
    }
}
