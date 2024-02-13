package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.awt.Dimension;
import java.awt.Toolkit;

import controller.Control;

public class UIMaster extends Application {
    private static  double screenWidth ;
    private static double screenHeight;
    // app size factor
    private static double asf = 0.9;  



    static{
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        screenWidth = screenSize.getWidth();
        screenHeight = screenSize.getHeight();
        System.out.println( screenWidth);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

         HBox root = new HBox();

        Scene scene = new Scene(root,screenWidth*asf,screenHeight *asf);
        
        
        stage.setScene(scene);
        stage.setTitle("Result Engine");
        stage.setResizable(true);
        Image logo = new Image("/view/rsrcs/Result Engine-logos.jpeg");

        stage.getIcons().add(logo);
        stage.setResizable(false);
        // stage.show();


        int[] selectedTerms = { 1, 0, 0 };
        Control.makeResult("1a", "FIRST TERM PROGRESS REPORT - 2024", selectedTerms, null, 200,"C:\\Users\\Subham Rai\\Workspace\\Test Folder");
        
      
    }

    private static void mainScreen(){
        //sidewise storing of scene display and navigation bar
        
        // base element
        AnchorPane anchorPane = new AnchorPane();
    }




}
