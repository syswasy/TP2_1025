package server;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import server.models.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;


public class ClientFX extends Application {
    private final static String HOST = "localhost";
    private final static int PORT = 1337;
    private String optionChoisi = "Hiver";
    private ObservableList<Course> courseList = FXCollections.observableArrayList();




    @Override
    public void start(Stage primaryStage) throws IOException {


        Button boutonEnvoyer= new Button("Envoyer");
        Button bouton5 = new Button("Charger");

        /**
         * Création d'un bouton splitMenu pour le bouton 4, contenant la session d'hiver, d'automne et d'été.
         */
        SplitMenuButton bouton4 = new SplitMenuButton();
        bouton4.setText("Hiver");
        MenuItem option1 = new MenuItem("Hiver");
        MenuItem option2 = new MenuItem("Automne");
        MenuItem option3 = new MenuItem("Ete");
        bouton4.getItems().addAll(option1, option2, option3);


        /**
         * Changer la variable optionChoisi selon la session sélectionner .
         */
        option1.setOnAction(event -> {
            bouton4.setText("Hiver");
            optionChoisi = "Hiver";
        });

        option2.setOnAction(event -> {
            bouton4.setText("Automne");
            optionChoisi = "Automne";
        });

        option3.setOnAction(event -> {
            bouton4.setText("Ete");
            optionChoisi = "Ete";
        });


        /**
         * Utiliser la methode filtrerParSession pour obtenir le code et nom des cours à la session choisie.
         */
        bouton5.setOnAction(event -> {
            courseList.clear();
            String session = optionChoisi;
            List<Course> filteredCourses = filterParSession(session);
            courseList.addAll(filteredCourses);
        });


        /**
         * Hbox pour le bouton 4 et 5
         */
        HBox hbox3 = new HBox(30);
        hbox3.getChildren().addAll(bouton4, bouton5);
        hbox3.setPadding(new Insets(30, 0 , 30  , 30));

        /**
         * Liste de cours
         */

        TableView<Course> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setItems(courseList);

        TableColumn<Course, String> column1 = new TableColumn<>("Code");
        column1.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getCode()));

        TableColumn<Course, String> column2 = new TableColumn<>("Cours");
        column2.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));


        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        Label label = new Label("Liste des cours");
        label.setStyle("-fx-font-weight: bold;");
        label.setAlignment(Pos.CENTER);
        VBox tableau = new VBox(10);
        tableau.getChildren().add(label);
        tableau.getChildren().add(tableView);
        tableau.setPadding(new Insets(30, 30 , 0  , 30));


        /**
         * Vbox pour la partie chargement de cours.
         */
        VBox chargerCours = new VBox();
        chargerCours.getChildren().addAll(tableau, hbox3);
        chargerCours.setSpacing(10);
        chargerCours.setBackground(new Background(new BackgroundFill(Color.rgb(255,255,255), CornerRadii.EMPTY,
                Insets.EMPTY)));
        chargerCours.setAlignment(Pos.TOP_LEFT);

        // Création de la vBox contenant le formulaire d'inscription
        VBox vbox2 = new VBox();
        vbox2.setSpacing(10);

        Label titreInscription = new Label("Formulaire d'inscription");
        titreInscription.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        titreInscription.setAlignment(Pos.CENTER);
        titreInscription.setPadding(new Insets(0, 0, 10, 0));

        Label label1 = new Label("Prénom");
        label1.setPadding(new Insets(0, 10, 0, 10));
        TextField textField1 = new TextField();
        HBox lt1 = new HBox(label1,textField1);
        lt1.setPadding(new Insets(0, 0, 10, 0));

        Label label2 = new Label("Nom");
        label2.setPadding(new Insets(0, 10, 0, 10));
        TextField textField2 = new TextField();
        HBox lt2 = new HBox(label2,textField2);
        lt2.setPadding(new Insets(0, 0, 10, 0));

        Label label3 = new Label("Émail");
        label3.setPadding(new Insets(0, 10, 0, 10));
        TextField textField3 = new TextField();
        HBox lt3 = new HBox(label3,textField3);
        lt3.setPadding(new Insets(0, 0, 10, 0));

        Label label4 = new Label("Matricule");
        label4.setPadding(new Insets(0, 10, 0, 10));
        TextField textField4 = new TextField();
        HBox lt4 = new HBox(label4,textField4);
        lt4.setPadding(new Insets(0, 0, 10, 0));

        // Création de hbox4 pour aligner les textfield
        VBox vBox4 = new VBox(titreInscription, lt1, lt2, lt3, lt4, boutonEnvoyer);
        vBox4.setAlignment(Pos.CENTER);
        vBox4.setPadding(new Insets(30, 30 , 0  , 30));


        //Écouteur d'évenement pour le bouton "Envoyer" dans le formulaire d'inscription
        boutonEnvoyer.setOnAction(event -> {
            Course selectedCourse = tableView.getSelectionModel().getSelectedItem();
            if (selectedCourse != null) {
                try {
                    // Connection au serveur.
                    Socket clientSocket = new Socket(HOST, PORT);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());

                    //envoi de la commande inscire au serveur
                    objectOutputStream.writeObject("INSCRIRE");

                    // création de RegistrationForm et envoie de celui-ci
                    String prenom = textField1.getText();
                    String nom = textField2.getText();
                    String email = textField3.getText();
                    String matricule = textField4.getText();
                    String courseName = selectedCourse.getName();
                    String courseCode = selectedCourse.getCode();
                    String courseSession = selectedCourse.getSession();
                    Course course = new Course(courseName, courseCode, courseSession);
                    RegistrationForm form = new RegistrationForm(prenom, nom, email, matricule, course);
                    objectOutputStream.writeObject(form);

                    // fermeture des Stream
                    clientSocket.close();
                    objectOutputStream.close();
                }
                catch (IOException e) {
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Pas de cours choisi");

                alert.showAndWait();
            }
        });


        // Ajour des textfield à la vbox2
        vbox2.getChildren().addAll(vBox4);
        vbox2.setAlignment(Pos.TOP_LEFT);
        vbox2.setBackground(new Background(new BackgroundFill(Color.rgb(255,255,255), CornerRadii.EMPTY,
                Insets.EMPTY)));

        // Hbox pour tout le contenu
        HBox hbox2 = new HBox();
        hbox2.getChildren().addAll(chargerCours, vbox2);
        hbox2.setSpacing(10);
        hbox2.setAlignment(Pos.TOP_LEFT);
        hbox2.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        Scene scene = new Scene(hbox2, 300, 200);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Inscription UdeM");
        primaryStage.show();

    }
    private List<Course> filterParSession(String session) {
        List<Course> filtrer = new ArrayList<Course>();
        try {
            FileReader fr = new FileReader("./src/main/java/server/data/cours.txt");
            BufferedReader reader = new BufferedReader(fr);
            String coursData;
            while ((coursData = reader.readLine()) != null) {
                String[] coursDataList = coursData.split("\t");
                if (coursDataList.length >= 3 && session.equals(coursDataList[2])) {
                    String name = coursDataList[0];
                    String code = coursDataList[1];
                    Course course = new Course(code, name, session);
                    filtrer.add(course);
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Le fichier cours.txt n'a pas été trouvé !");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filtrer;
    }

    public static void main(String[] args) {
        launch(args);
    }
}