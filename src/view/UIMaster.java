package view;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class UIMaster extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

         Group root = new Group() ;
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Result Engine");
        stage.setResizable(true);
        Image logo = new Image("/view/rsrcs/Result Engine-logos.jpeg");
        stage.getIcons().add(logo);
        stage.show();
        
      
    }

    



    
}
