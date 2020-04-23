package sample.classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Database {


    private final static String DB_URL = "jdbc:mysql://localhost/wp-4?serverTimezone=UTC";
    private final static String DB_USER = "mysql";
    private final static String DB_PASSWORD = "mysql";
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public Database() {

    }

    public Connection connectDataBase() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public Statement createStatement() {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

    public ArrayList<Double> getOSInformation(String nameOS, String tableName) throws SQLException {
        ArrayList<Double> result = new ArrayList<Double>();
        ResultSet rs = statement.executeQuery("select * from " + tableName);
        while (rs.next()) {
            if (rs.getString(1).equals(nameOS)) {
                result.add(rs.getDouble(2));
                result.add(rs.getDouble(3));
            }
        }
        return result;
    }

    public ArrayList<String> getNamesColumns(String tableName) throws SQLException {
        ArrayList<String> result = new ArrayList<String>();
        ResultSetMetaData resultSetMetaData = statement.executeQuery("select * from " + tableName).getMetaData();
        for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
            result.add(resultSetMetaData.getColumnName(i));
        }
        return result;
    }

    public ArrayList<String> getNamesTables(String databaseName) throws SQLException {
        ArrayList<String> result = new ArrayList<String>();
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        String type[] = {"TABLE"};
        ResultSet rs = databaseMetaData.getTables("wp-4", null, "%", type);
        while (rs.next()) {
            result.add(rs.getString("TABLE_NAME"));
        }
        return result;
    }


    public int getColumnNumber(String tableName) throws SQLException {
        ResultSetMetaData resultSetMetaData = statement.executeQuery("select * from " + tableName).getMetaData();
        return resultSetMetaData.getColumnCount();
    }

    public ObservableList<HashMap> getInfoListFromTable(String tableName, int columnCount, ArrayList<String> columnsNames) throws SQLException {
        ArrayList<HashMap> hashMaps = new ArrayList<>();
        ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName);
        while (rs.next()) {
            HashMap<String, String> databaseInfo = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                databaseInfo.put(columnsNames.get(i - 1), rs.getString(i));
            }
            hashMaps.add(databaseInfo);
        }
        return FXCollections.observableArrayList(hashMaps);
    }

    public ObservableList<HashMap> getInfoList(int columnCount, ArrayList<String> columnsNames, String sql) throws SQLException {
        ArrayList<HashMap> hashMaps = new ArrayList<>();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            HashMap<String, String> databaseInfo = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                databaseInfo.put(columnsNames.get(i - 1), rs.getString(i));
            }
            hashMaps.add(databaseInfo);
        }
        return FXCollections.observableArrayList(hashMaps);
    }

    public void addRow(String tableName, ArrayList<String> values) throws SQLException {
        ResultSetMetaData rsm = statement.executeQuery("SELECT * FROM " + tableName + "").getMetaData();
        ArrayList<String> columnNames = getNamesColumns(tableName);

        String query = "INSERT INTO " + tableName + " (" + genColumnNames(columnNames) + ") VALUES (" + genValues(values) + ");";
        requestForData(query);
    }

    private String genColumnNames(ArrayList<String> columnNames) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < columnNames.size(); i++) {
            if (i != columnNames.size() - 1) {
                stringBuilder.append("").append(columnNames.get(i)).append(", ");

            } else {
                stringBuilder.append("").append(columnNames.get(i)).append("");
            }
        }
        return stringBuilder.toString();
    }

    public void requestForData(String query) throws SQLException {
        statement.executeUpdate(query);
    }

    private String genValues(ArrayList<String> values) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {

            if (i != values.size() - 1) {
                stringBuilder.append("'").append(values.get(i)).append("', ");

            } else {
                stringBuilder.append("'").append(values.get(i)).append("'");
            }
        }

        return stringBuilder.toString();
    }

}



