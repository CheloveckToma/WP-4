package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.interfaces.DATABASE_CONSTANTS;
import sample.classes.Database;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MainController implements DATABASE_CONSTANTS {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<HashMap> tableView;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private Button choose_button;

    @FXML
    private ComboBox<String> comboBox2;

    @FXML
    private Button choose_button2;

    @FXML
    private Button add;

    @FXML
    private Button del;


    Database database;
    Statement statement;

    @FXML
    void initialize() {

        add.setOnAction(addEvent -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/fxml/addRecord.fxml"));
                Parent root = null;
                root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Добавление записи");
                stage.setResizable(false);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.centerOnScreen();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        try {
            database = DATABASE_CONSTANTS.DATABASE;
            database.connectDataBase();
            statement = database.createStatement();

            ArrayList columnNames = removeSpaces(database.getNamesColumns(DATABASE_CONSTANTS.TABLE_SUBJECT_NAME));
            ArrayList<TableColumn> tableColumnsArray = fillTableColumnArray(columnNames, DATABASE_CONSTANTS.TABLE_SUBJECT_NAME);
            fillColumns(columnNames, tableColumnsArray, DATABASE_CONSTANTS.TABLE_SUBJECT_NAME);

            comboBox.setPromptText("предметы");
            ArrayList<String> tableNames = DATABASE_CONSTANTS.DATABASE.getNamesTables(DATABASE_CONSTANTS.DATABASE_NAME);
            comboBox.setItems(FXCollections.observableArrayList(tableNames));


            ArrayList<String> tasksNames = new ArrayList<String>();
            comboBox2.setPromptText("Задание 1");
            tasksNames.add("Задание 1");
            tasksNames.add("Задание 2");
            tasksNames.add("Задание 3");
            tasksNames.add("Задание 4");
            comboBox2.setItems(FXCollections.observableArrayList(tasksNames));

            choose_button2.setOnAction(chooseButtonEvent -> {
                try {
                    if (comboBox2.getValue().equals("Задание 1")) {
                        tableView.getColumns().clear();
                        ArrayList columnsNames = new ArrayList();
                        columnsNames.add("Наименование специальностей");
                        ArrayList<String> columnSpecialtyNames = removeSpaces(columnsNames);
                        ArrayList<TableColumn> tableSpecialityColumnsArray = fillTableColumnManually(columnSpecialtyNames, 1);
                        fillColumnsManually(columnSpecialtyNames, tableSpecialityColumnsArray, "SELECT наименование_специальности FROM специальности ORDER BY поданные_заявления DESC", 1);
                    }
                    if (comboBox2.getValue().equals("Задание 2")) {
                        tableView.getColumns().clear();
                        ArrayList columnsNames = new ArrayList();
                        columnsNames.add("ФИО абитуриента");
                        columnsNames.add("год рождения");
                        columnsNames.add("код группы");
                        ArrayList<String> columnSpecialtyNames = removeSpaces(columnsNames);
                        ArrayList<TableColumn> tableSpecialityColumnsArray = fillTableColumnManually(columnSpecialtyNames, 3);
                        fillColumnsManually(columnSpecialtyNames, tableSpecialityColumnsArray, "SELECT ФИО_абитуриента,год_рождения,код_группы FROM абитуриенты WHERE полученная_оценка=2", 3);
                    }
                    if (comboBox2.getValue().equals("Задание 3")) {
                        tableView.getColumns().clear();
                        ArrayList columnsNames = new ArrayList();
                        columnsNames.add("ФИО абитуриента");
                        columnsNames.add("год рождения");
                        columnsNames.add("код специальности");
                        columnsNames.add("код группы");
                        ArrayList<String> columnSpecialtyNames = removeSpaces(columnsNames);
                        ArrayList<TableColumn> tableSpecialityColumnsArray = fillTableColumnManually(columnSpecialtyNames, 4);
                        fillColumnsManually(columnSpecialtyNames, tableSpecialityColumnsArray, "SELECT ФИО_абитуриента,год_рождения,код_специальности,код_группы FROM абитуриенты WHERE полученная_оценка=2", 4);
                    }
                    if (comboBox2.getValue().equals("Задание 4")) {
                        tableView.getColumns().clear();
                        ArrayList columnsNames = new ArrayList();
                        int points = 0;
                        int i = 0;
                        ResultSet rs = statement.executeQuery("SELECT баллы FROM loosers");
                        while (rs.next()) {
                            i++;
                            points = points + rs.getInt("баллы");
                        }
                        int result = points / i;

                        columnsNames.add("ФИО абитуриента");
                        ArrayList<String> columnSpecialtyNames = removeSpaces(columnsNames);
                        ArrayList<TableColumn> tableSpecialityColumnsArray = fillTableColumnManually(columnSpecialtyNames, 1);
                        fillColumnsManually(columnSpecialtyNames, tableSpecialityColumnsArray, "SELECT ФИО_абитуриента FROM loosers WHERE баллы>" + result, 1);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });


            choose_button.setOnAction(chooseButtonEvent -> {
                try {
                    if (comboBox.getValue().equals(DATABASE_CONSTANTS.TABLE_SUBJECT_NAME)) {
                        tableView.getColumns().clear();
                        ArrayList columnSubNames = removeSpaces(database.getNamesColumns(DATABASE_CONSTANTS.TABLE_SUBJECT_NAME));
                        ArrayList<TableColumn> tableSubjectColumnsArray = fillTableColumnArray(columnSubNames, DATABASE_CONSTANTS.TABLE_SUBJECT_NAME);
                        fillColumns(database.getNamesColumns(DATABASE_CONSTANTS.TABLE_SUBJECT_NAME), tableSubjectColumnsArray, DATABASE_CONSTANTS.TABLE_SUBJECT_NAME);
                    }
                    if (comboBox.getValue().equals(DATABASE_CONSTANTS.TABLE_APPLICATNSTS_NAME)) {
                        tableView.getColumns().clear();
                        ArrayList columnApplicantsNames = removeSpaces(database.getNamesColumns(DATABASE_CONSTANTS.TABLE_APPLICATNSTS_NAME));
                        ArrayList<TableColumn> tableAPPLICATNSTSColumnsArray = fillTableColumnArray(columnApplicantsNames, DATABASE_CONSTANTS.TABLE_APPLICATNSTS_NAME);
                        fillColumns(database.getNamesColumns(DATABASE_CONSTANTS.TABLE_APPLICATNSTS_NAME), tableAPPLICATNSTSColumnsArray, DATABASE_CONSTANTS.TABLE_APPLICATNSTS_NAME);
                    }
                    if (comboBox.getValue().equals("вступительные испытания")) {
                        tableView.getColumns().clear();
                        ArrayList columnEntranceNames = removeSpaces(database.getNamesColumns(DATABASE_CONSTANTS.TABLE_ENTRANCE_TESTS_NAME));
                        ArrayList<TableColumn> tableEntranceColumnsArray = fillTableColumnArray(columnEntranceNames, DATABASE_CONSTANTS.TABLE_ENTRANCE_TESTS_NAME);
                        fillColumns(database.getNamesColumns(DATABASE_CONSTANTS.TABLE_ENTRANCE_TESTS_NAME), tableEntranceColumnsArray, DATABASE_CONSTANTS.TABLE_ENTRANCE_TESTS_NAME);
                    }
                    if (comboBox.getValue().equals(DATABASE_CONSTANTS.TABLE_GROUPS_NAME)) {
                        tableView.getColumns().clear();
                        ArrayList columnGroupNames = removeSpaces(database.getNamesColumns(DATABASE_CONSTANTS.TABLE_GROUPS_NAME));
                        ArrayList<TableColumn> tableGroupsColumnsArray = fillTableColumnArray(columnGroupNames, DATABASE_CONSTANTS.TABLE_GROUPS_NAME);
                        fillColumns(database.getNamesColumns(DATABASE_CONSTANTS.TABLE_GROUPS_NAME), tableGroupsColumnsArray, DATABASE_CONSTANTS.TABLE_GROUPS_NAME);
                    }
                    if (comboBox.getValue().equals(DATABASE_CONSTANTS.TABLE_SPECIALTY_NAME)) {
                        tableView.getColumns().clear();
                        ArrayList columnSpecialtyNames = removeSpaces(database.getNamesColumns(DATABASE_CONSTANTS.TABLE_SPECIALTY_NAME));
                        ArrayList<TableColumn> tableSpecialityColumnsArray = fillTableColumnArray(columnSpecialtyNames, DATABASE_CONSTANTS.TABLE_SPECIALTY_NAME);
                        fillColumns(database.getNamesColumns(DATABASE_CONSTANTS.TABLE_SPECIALTY_NAME), tableSpecialityColumnsArray, DATABASE_CONSTANTS.TABLE_SPECIALTY_NAME);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                comboBox.getValue();
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private ArrayList<TableColumn> fillTableColumnArray(ArrayList<String> columnsName, String tableName) throws SQLException {
        ArrayList<TableColumn> tableColumnsArray = new ArrayList<TableColumn>();
        for (int i = 0; i < database.getColumnNumber(tableName); i++) {
            tableColumnsArray.add(new TableColumn(columnsName.get(i)));
        }
        return tableColumnsArray;
    }

    private ArrayList<TableColumn> fillTableColumnManually(ArrayList<String> columnsName, int columnNumber) throws SQLException {
        ArrayList<TableColumn> tableColumnsArray = new ArrayList<TableColumn>();
        for (int i = 0; i < columnNumber; i++) {
            tableColumnsArray.add(new TableColumn(columnsName.get(i)));
        }
        return tableColumnsArray;
    }

    private void fillColumns(ArrayList<String> columnsName, ArrayList<TableColumn> tableColumnsArray, String tableName) throws SQLException {
        ObservableList<HashMap> hashMaps = database.getInfoListFromTable(tableName, database.getColumnNumber(tableName), columnsName);
        tableView.setItems(hashMaps);

        for (int i = 0; i < database.getColumnNumber(tableName); i++) {
            tableColumnsArray.get(i).setCellValueFactory(new MapValueFactory<String>(columnsName.get(i)));
        }

        for (int i = 0; i < database.getColumnNumber(tableName); i++) {
            tableColumnsArray.get(i).setStyle("-fx-alignment: CENTER;");
            tableView.getColumns().add(tableColumnsArray.get(i));
        }
    }

    private void fillColumnsManually(ArrayList<String> columnsName, ArrayList<TableColumn> tableColumnsArray, String sql, int columnNumber) throws SQLException {
        ObservableList<HashMap> hashMaps = database.getInfoList(columnNumber, columnsName, sql);
        tableView.setItems(hashMaps);

        for (int i = 0; i < columnNumber; i++) {
            tableColumnsArray.get(i).setCellValueFactory(new MapValueFactory<String>(columnsName.get(i)));
        }

        for (int i = 0; i < columnNumber; i++) {
            tableColumnsArray.get(i).setStyle("-fx-alignment: CENTER;");
            tableView.getColumns().add(tableColumnsArray.get(i));
        }
    }

    private ArrayList<String> removeSpaces(ArrayList<String> arrayList) {
        ArrayList<String> noSpacings = new ArrayList<>();
        for (String elem : arrayList) {
            noSpacings.add(elem.replaceAll("_+", " "));
        }
        return noSpacings;
    }
}
