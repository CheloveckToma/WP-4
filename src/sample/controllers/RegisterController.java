package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.classes.Database;
import sample.interfaces.DATABASE_CONSTANTS;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class RegisterController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField login_field;

    @FXML
    private Button registration_button;

    @FXML
    private PasswordField password1_field;

    @FXML
    private PasswordField password2_field;


    Database database;
    Connection connection;
    Statement statement;
    Stage stage;

    public void setStage(Stage stage){
        this.stage = stage;
    }

    @FXML
    void initialize() {
        registration_button.setOnAction(regEvent -> {
            try {
                database = DATABASE_CONSTANTS.DATABASE;
                connection = database.connectDataBase();
                statement = connection.createStatement();
                registration();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void registration() throws SQLException {
        String login = login_field.getText();
        String password1 = password1_field.getText();
        String password2 = password2_field.getText();
        if (password1.equals(password2)) {
            statement.execute("INSERT INTO users(login, password) VALUES ('" + login + "', '" + password1 + "')");
            alert("Вы успешно зарегистрировались!");
            stage.close();
        } else {
            alert("Пароли не совпадают!");
        }

    }

    private void alert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
