
package server;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import java.awt.*;
import javafx.scene.control.MenuItem;
import server.models.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class ClientFX extends Application {
    private final static String HOST = "localhost";
    private final static int PORT = 1337;

    @Override
    public void start(Stage primaryStage) throws IOException {

        // Create buttons
        Button button1 = new Button("Button 1");
        Button button2 = new Button("Button 2");
        Button button3 = new Button("Button 3");
        Button button5 = new Button("Charger");

        // Create SplitMenuButton for button 4 with three options
        SplitMenuButton button4 = new SplitMenuButton();
        button4.setText("Hiver");
        MenuItem option1 = new MenuItem("Hiver");
        MenuItem option2 = new MenuItem("Automne");
        MenuItem option3 = new MenuItem("Ete");
        button4.getItems().addAll(option1, option2, option3);


        // Event handler for option1
        option1.setOnAction(event -> {
            button4.setText("Hiver");
        });

        // Event handler for option2
        option2.setOnAction (event -> {
            button4.setText("Automne");
        });

        // Event handler for option3
        option3.setOnAction(event -> {
            button4.setText("Ete");
        });



        // Hbox for button 4 and 5
        HBox hbox3 = new HBox(30);
        hbox3.getChildren().addAll(button4, button5);
        hbox3.setPadding(new Insets(30, 0 , 30  , 30));

        // Liste de cours
        TableView tableView = new TableView();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn< Server,  String> column1 =
                new TableColumn<>("Code");

        column1.setCellValueFactory(
                new PropertyValueFactory<>("code"));


        TableColumn<Server, String> column2 =
                new TableColumn<>("Cours");

        column2.setCellValueFactory(
                new PropertyValueFactory<>("cours"));
        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);

        VBox vbox3 = new VBox(30);
        Label label = new Label("Liste des cours");
        vbox3.getChildren().addAll(label,tableView);
        vbox3.setSpacing(10);
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

    public static void main(String[] args) {
        launch(args);
    }
}