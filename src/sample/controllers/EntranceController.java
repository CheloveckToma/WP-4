package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.classes.Database;
import sample.interfaces.DATABASE_CONSTANTS;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class EntranceController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField login_field;

    @FXML
    private Button regisrtration_button;

    @FXML
    private Button entracne_button;

    @FXML
    private PasswordField password_filed;

    private Stage enterStage;
    private Database database;
    private Connection connection;
    private Statement statement;

    public void setStage(Stage stage) {
        this.enterStage = stage;
    }

    @FXML
    void initialize() {
        try {
            database = DATABASE_CONSTANTS.DATABASE;
            connection = database.connectDataBase();
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        entracne_button.setOnAction(enterEvent -> {
            try {
                ResultSet rs1 = statement.executeQuery(("SELECT password FROM users where login='" + login_field.getText() + "'"));
                Statement statement1 = connection.createStatement();
                ResultSet rs2 = statement1.executeQuery(("SELECT login FROM users where password='" + password_filed.getText() + "'"));
                if (rs1.next()&&rs2.next()) {
                    if ((rs1.getString("password").equals(password_filed.getText()))&&(rs2.getString("login").equals(login_field.getText()))) {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/fxml/sample.fxml"));
                        Parent root = null;
                        root = fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setTitle("Вход");
                        stage.setResizable(false);
                        stage.initModality(Modality.WINDOW_MODAL);
                        stage.centerOnScreen();
                        stage.setScene(new Scene(root));
                        stage.show();
                        alert("Вы успешно вошли");
                        enterStage.close();
                    }
                } else {
                    alert("Неправильный пароль или логин");
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });

        regisrtration_button.setOnAction(regEvent -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/fxml/registration.fxml"));
                Parent root = null;
                root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Вход");
                stage.setResizable(false);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.centerOnScreen();
                stage.setScene(new Scene(root));
                stage.show();

                RegisterController registerController = fxmlLoader.getController();
                registerController.setStage(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });


    }

    private void alert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
