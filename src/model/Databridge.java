package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Databridge {


    private Connection connection = null;

    // establishes connection with the database.

    public void connectDatabase() {
        // Database connection details

        // String url =
        // "jdbc:postgresql://oiohghwy:g_boOl3p-FtPxXCs5sEF-q4pqJvWx8IE@rosie.db.elephantsql.com/oiohghwy";
        // The above url was taken from elephant sql, which is not in right format to be
        // used by DriverManager.
        // The url below was converted using ChatGPT-3.5

        String url = "jdbc:postgresql://rosie.db.elephantsql.com:5432/oiohghwy?user=oiohghwy&password=g_boOl3p-FtPxXCs5sEF-q4pqJvWx8IE";

        String user = "oiohghwy";
        String password = "g_boOl3p-FtPxXCs5sEF-q4pqJvWx8IE";

        try {

            Class.forName("org.postgresql.Driver");

            connection = DriverManager.getConnection(url, user, password);

            if (connection != null) {
                System.out.println("Database connection status : Positive");
            }
        } catch (ClassNotFoundException e) {
            System.err.println(
                    "Could not find the PostgreSQL JDBC driver. Please include the JDBC driver in your project, \n or download it from the website.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database connection status : Negative");
            e.printStackTrace();
        }

    }

    // executes queries which will return nothing

    public void executeQuery(String query) {
        Statement statement;
        try {
            statement = getConnection().createStatement();
            statement.executeUpdate(query);
            System.out.println("Query : Executed");

        } catch (Exception e) {
            System.out.println("Query : Execution failed");

            System.out.println(e);
        }

    }

    // fetches a single result set for the given data

    public ResultSet fetchQueryData(String query) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = getConnection().createStatement();
            resultSet = statement.executeQuery(query);

        } catch (Exception e) {
            System.out.println(e);
        }
        return resultSet;

    }

 

    public Connection getConnection() {
        return connection;
    }

    

  

}
