package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import controller.Control;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.enums.Operator;
import model.enums.SubjectSignificance;

public class TestZone extends Application {

    // Create condition blocks for every class and keep them stored while checking
    // it for every class

    public static void main(String[] args) throws SQLException {
        launch(args);

    }

    @Override
    public void start(Stage stage) throws Exception {
        
        Group root = new Group();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }

}
