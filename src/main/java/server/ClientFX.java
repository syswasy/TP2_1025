
package server;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
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
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;



public class ClientFX extends Application {
    private final static String HOST = "localhost";
    private final static int PORT = 1337;
    private String optionChoisi = "Hiver";



    @Override
    public void start(Stage primaryStage) throws IOException {


        Button button3 = new Button("Button 3");
        Button button5 = new Button("Charger");

        // Create SplitMenuButton for button 4 with three options
        SplitMenuButton button4 = new SplitMenuButton();
        button4.setText("Hiver");
        MenuItem option1 = new MenuItem("Hiver");
        MenuItem option2 = new MenuItem("Automne");
        MenuItem option3 = new MenuItem("Ete");
        button4.getItems().addAll(option1, option2, option3);



        option1.setOnAction(event -> {
            button4.setText("Hiver");
            optionChoisi = "Hiver";
        });

        option2.setOnAction(event -> {
            button4.setText("Automne");
            optionChoisi = "Automne";
        });

        option3.setOnAction(event -> {
            button4.setText("Ete");
            optionChoisi = "Ete";
        });

        ObservableList<Course> courseList = FXCollections.observableArrayList();


        button5.setOnAction(event -> {
            courseList.clear();
            String session = optionChoisi;
            List<Course> filteredCourses = filterParSession(session);
            courseList.addAll(filteredCourses);
        });



        // Hbox for button 4 and 5
        HBox hbox3 = new HBox(30);
        hbox3.getChildren().addAll(button4, button5);
        hbox3.setPadding(new Insets(30, 0 , 30  , 30));

        // Liste de cours

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
        VBox vbox3 = new VBox(10);
        vbox3.getChildren().add(label);
        vbox3.getChildren().add(tableView);
        vbox3.setPadding(new Insets(30, 30 , 0  , 30));




        // Create VBox for buttons 1 and 2
        VBox vbox1 = new VBox();
        vbox1.getChildren().addAll(vbox3, hbox3);
        vbox1.setSpacing(10);
        vbox1.setBackground(new Background(new BackgroundFill(Color.rgb(225,198,153), CornerRadii.EMPTY,
                Insets.EMPTY)));

        // Set alignment for VBox 1
        vbox1.setAlignment(Pos.TOP_LEFT);

        // Create HBox for buttons 1, 2, and 3
        HBox hbox2 = new HBox();
        hbox2.getChildren().addAll(vbox1, button3);
        hbox2.setSpacing(10);

        // Set alignment for HBox 2
        hbox2.setAlignment(Pos.TOP_LEFT);

        // Set border for HBox 2
        hbox2.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        // Set the HBox 2 as the root of the scene
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