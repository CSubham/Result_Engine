package view;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import controller.Control;
import model.DataBridge;
import model.Master;

public class CLI {
    private static DataBridge db = new DataBridge();
    private static Scanner sc;

    static {
        sc = new Scanner(System.in);
        db.connectDatabase();
        Master.setDataBridge(db);
        if (!login()) {

            System.exit(0);
        }
    }

    private static void renewConn() throws SQLException {
        if (db.getConnection().isClosed())
            db.connectDatabase();
    }

    public static void main(String[] args) throws SQLException {
        System.out.println("Type help to show all possible commands");
        // all the available commands
        final String[] cmds = { "help",
                "querynrs",
                "query",
                "printl",
                "exit",
                "insert-1",
                "insert-2",
                "insert-3",
                "makeres",
                "setres"
        };
        // definition of the commands index-index
        final String[] cmdDef = { "Show all available commands",
                "Query the database with no result set to display",
                "Query and fetch data from the database",
                "Print the complete database",
                "Exit the program",
                "Insert into term one",
                "Insert into term two",
                "Insert into term three",
                "Generates the result at specified path",
                "Options to set the needed values to make result"

        };

        while (true) {

            boolean exit = false;
            String ui;
            try {
                System.out.print(">>>");
                ui = sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(e);
                continue;
            }

            switch (ui) {
                case "help": {
                    help(cmds, cmdDef);
                    break;
                }
                case "querynrs": {
                    querynrs();
                    break;
                }
                case "query": {
                    query();
                    break;
                }
                case "printl": {
                    printl();
                    break;
                }
                case "exit": {
                    exit = true;
                    break;
                }
                case "insert-1": {
                    insertOne();
                }
                case "insert-2": {
                    insertTwo();
                }
                case "insert-3": {
                    insertThree();
                }
                case "makeres": {
                    makeresult();
                }
                case "setres": {
                    setres();
                }

                default:
                    break;
            }

            if (exit)
                break;
            renewConn();
        }

    }

    // data required to generate result

    private static String grade = "";
    private static String title = "";
    private static int[] selectedTerms = { -1, -1, -1 };
    private static float[] averagerValues = null;
    private static int outOf = -1;
    private static String path = "";

    public static boolean setres() {
        System.out.println("Type help to show all possible commands");

        final String[] cmd = {
                "help",
                "exit",
                "set-grade",
                "set-title",
                "select-terms",
                "set-outof",
                "set-path",
                "set-averager",
                "show"
        };

        while (true) {

            String ui;
            try {
                System.out.print("setres ");
                ui = sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(e);
                continue;
            }

            switch (ui) {
                case "help": {
                    help(cmd);
                    continue;
                }
                case "exit": {
                    return true;

                }
                case "set-grade": {
                    setGrade();
                    continue;

                }
                case "set-title": {
                    setTitle();
                    continue;

                }
                case "select-terms": {
                    selectTerms();
                    continue;

                }
                case "set-outof": {
                    setOutOf();
                    continue;

                }
                case "set-path": {
                    setPath();
                    continue;

                }
                case "set-averager": {
                    setAverager();
                    continue;

                }
                case "show": {
                    show();
                    continue;

                }
            }

        }

    }

    public static void show() {
        System.out.println("Grade :" + grade);
        System.out.println("Title :" + title);

        System.out.print("Averager :");
        printFloatArray(averagerValues);
        System.out.println();
        System.out.print("Selected Terms :");
        printIntArray(selectedTerms);
        System.out.println();
        System.out.println("Out of :" + outOf);
        System.out.println("Path :" + path);

    }

    // Function to print integer array in a single line
    public static void printIntArray(int[] array) {
        if (array == null) {
            System.out.print("null");
            return;
        }
        System.out.print("[");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            if (i < array.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.print("]");
    }

    // Function to print float array in a single line
    public static void printFloatArray(float[] array) {
        if (array == null) {
            System.out.print("null");
            return;
        }

        System.out.print("[");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            if (i < array.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.print("]");
    }

    public static boolean setGrade() {
        // grade
        System.out.println("Enter Grade ");
        System.out.print(">>>");
        grade = sc.nextLine();
        return true;

    }

    public static boolean setTitle() {
        // title
        System.out.println("Enter Title ");
        System.out.print(">>>");
        title = sc.nextLine();
        return true;

    }

    public static boolean selectTerms() {
        // selected terms
        System.out.println("Select terms, 1 for select, 0 for opposite");
        for (int i = 1; i <= selectedTerms.length; i++) {
            System.out.print("Term " + i + " >>>");
            int k = sc.nextInt();
            selectedTerms[i - 1] = k;
        }
        return true;

    }

    public static boolean setOutOf() {
        // outOf
        System.out.println("Out Of");
        System.out.print(">>>");
        outOf = sc.nextInt();
        return true;

    }

    public static boolean setPath() {
        // path
        System.out.println("Enter the path to generate the result files at");
        System.out.print(">>>");
        path = sc.nextLine();
        return true;

    }

    public static boolean setAverager() {
        // averager values
        int avg = -1;
        int count = selectedTerms[0] + selectedTerms[1] + selectedTerms[3];
        switch (count) {
            case 2 -> avg = 4;
            case 3 -> avg = 5;
        }

        // no terms selected
        if (count < 1)
            return false;
        // only one term selected no need for averager
        if (count == 1)
            return true;
        averagerValues = new float[avg];

        for (int i = 0; i < avg; i++) {
            System.out.print("Input [" + (i + 1) + "] :");
            averagerValues[i] = sc.nextFloat();
        }

        return true;

    }

    public static boolean makeresult() throws SQLException {

        try {
            Control.makeResult(grade, title, selectedTerms, averagerValues, outOf, path);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
            return false;
        }
        return true;

    }

    public static boolean insertOne() {
        Control.insertIntoTermOne();
        return true;
    }

    public static boolean insertTwo() {
        Control.insertIntoTermTwo();
        return true;
    }

    public static boolean insertThree() {
        Control.insertIntoTermThree();
        return true;
    }

    public static boolean help(String[] cmds, String[] cmDef) {
        for (int i = 0; i < cmds.length; i++) {
            System.out.println(cmds[i] + " - " + cmDef[i]);
        }
        return true;
    }

    public static boolean help(String[] cmds) {
        for (int i = 0; i < cmds.length; i++) {
            System.out.println(cmds[i]);
        }
        return true;
    }

    // to prompt the user to login, if he doesn't login he is not authorised to run
    // any other command.
    public static boolean login() {

        String username = "";
        String password = "";

        // taking name and password
        try {
            System.out.println("Enter username: ");
            System.out.print(">>>");
            username = sc.nextLine();
            System.out.println("Enter user password");
            System.out.print(">>>");
            password = sc.nextLine();
        } catch (InputMismatchException e) {
            System.out.println(e);
        }

        try {
            return Control.login(username, password);
        } catch (SQLException e) {
            System.out.println(e);
        }

        return false;

    }

    // execute a query with no resultset to diaplay from the database
    public static boolean querynrs() {
        System.out.println("Enter query:");
        try {
            String query = sc.nextLine();
            Control.customQuery(query);

        } catch (InputMismatchException e) {
            System.out.println(e);
            ;
        }

        return true;
    }

    // fetches and prints data from databse
    public static boolean query() {
        System.out.println("Enter query:");
        try {
            String query = sc.nextLine();
            Control.fetchQueryData(query);
        } catch (InputMismatchException e) {
            System.out.println(e);
        }

        return true;
    }

    // print full database
    public static boolean printl() {
        try {
            Control.printDatabaseData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

}
