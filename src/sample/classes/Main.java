package sample.classes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controllers.EntranceController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/fxml/entrance.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("WP-4");
        primaryStage.setScene(new Scene(root, 600, 307));
        primaryStage.show();

        EntranceController entranceController = loader.getController();
        entranceController.setStage(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
