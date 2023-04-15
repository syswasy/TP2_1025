
package server;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

public class ClientFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create buttons
        Button button1 = new Button("Button 1");
        Button button2 = new Button("Button 2");
        Button button3 = new Button("Button 3");
        Button button4 = new Button("Button 4");
        Button button5 = new Button("Button 5");


        // Hbox for button 4 and 5
        HBox hbox3 = new HBox();
        hbox3.getChildren().addAll(button4, button5);

        // Create VBox for buttons 1 and 2
        VBox vbox1 = new VBox();
        vbox1.getChildren().addAll(button1, hbox3);
        vbox1.setSpacing(10);

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