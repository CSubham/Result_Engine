package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Databridge {

    public static void main(String[] args) {
        connectDatabase();
    }

    public static void connectDatabase() {
        // Database connection details

        // String url =
        // "jdbc:postgresql://oiohghwy:g_boOl3p-FtPxXCs5sEF-q4pqJvWx8IE@rosie.db.elephantsql.com/oiohghwy";
        // The above url was taken from elephant sql, which is not in right format to be
        // used by DriverManager.
        // The url below was converted using ChatGPT-3.5

        String url = "jdbc:postgresql://rosie.db.elephantsql.com:5432/oiohghwy?user=oiohghwy&password=g_boOl3p-FtPxXCs5sEF-q4pqJvWx8IE";

        String user = "oiohghwy";
        String password = "g_boOl3p-FtPxXCs5sEF-q4pqJvWx8IE";

        Connection connection = null;

        try {

            Class.forName("org.postgresql.Driver");

            connection = DriverManager.getConnection(url, user, password);

            if (connection != null) {
                System.out.println("Connected to the database successfully!");
            }
        } catch (ClassNotFoundException e) {
            System.err.println(
                    "Could not find the PostgreSQL JDBC driver. Please include the JDBC driver in your project, \n or download it from the website.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error occurred while connecting to the database.");
            e.printStackTrace();
        }

    }

}
