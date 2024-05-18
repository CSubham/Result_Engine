package view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import controller.Control;
import model.Condition;
import model.DataBridge;
import model.Master;
import model.Condition_blocks.Exception;
import model.Condition_blocks.Compulsory;
import model.Condition_blocks.ConditionBlock;
import model.Condition_blocks.MixedValue;
import model.Condition_blocks.Value;
import model.enums.Operator;
import model.enums.SubjectSignificance;

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
                "setres",
                "new-gsl",
                "new-sub",
                "newc",

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
                "Options to set the needed values to make result",
                "Create a new grade subject list",
                "Add a new subject",
                "Create a new condition block"

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
                }case "new-gsl":{
                    newgsl();
                }
                case "new-sub":{
                    newSub();
                }
                case "newc":{
                    newc();
                }

                default:
                    break;
            }

            if (exit)
                break;
            renewConn();
        }

    }

    
    public static void newc() {
        Condition condition = new Condition();
        boolean exit = false;
        System.out.println("Enter Grade");
        System.out.print(">>>");
        int grade = gradeStringToInteger(sc.nextLine());

        while (!exit) {
            System.out.println("Menu:");
            System.out.println("1. Add Compulsory Condition Block");
            System.out.println("2. Add Exception Condition Block");
            System.out.println("3. Add MixedValue Condition Block");
            System.out.println("4. Add Value Condition Block");
            System.out.println("5. Display Condition Blocks");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    condition.addConditionBlock(createCompulsoryBlock());
                    break;
                case 2:
                    condition.addConditionBlock((ConditionBlock) createExceptionBlock());
                    break;
                case 3:
                    condition.addConditionBlock(createMixedValueBlock());
                    break;
                case 4:
                    condition.addConditionBlock(createValueBlock());
                    break;
                case 5:
                    displayConditionBlocks(condition);
                    break;
                case 6:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        Control.addNewCondition(condition, grade);
    }

    private static Compulsory createCompulsoryBlock() {
        System.out.print("Enter subject code (or -1 to skip): ");
        int subjectCode = sc.nextInt();
        sc.nextLine();  // Consume newline

        List<Integer> subjects = new ArrayList<>();
        if (subjectCode == -1) {
            System.out.print("Enter subject codes (comma-separated): ");
            String[] subjectCodes = sc.nextLine().split(",");
            for (String code : subjectCodes) {
                subjects.add(Integer.parseInt(code.trim()));
            }
        }

        Operator operator = chooseOperator();
        System.out.print("Enter value: ");
        int value = sc.nextInt();
        sc.nextLine();  // Consume newline

        if (subjectCode == -1) {
            return new Compulsory(new ArrayList<>(subjects), operator, value);
        } else {
            return new Compulsory(subjectCode, operator, value);
        }
    }

    private static Exception createExceptionBlock() {
        System.out.print("Enter exception subject codes (comma-separated): ");
        String[] subjectCodes = sc.nextLine().split(",");
        ArrayList<Integer> exceptions = new ArrayList<>();
        for (String code : subjectCodes) {
            exceptions.add(Integer.parseInt(code.trim()));
        }
        return new Exception(exceptions);
    }

    private static MixedValue createMixedValueBlock() {
        Value firstValue = createValueBlock();
        Value secondValue = createValueBlock();
        Exception exception = createExceptionBlock();
        return new MixedValue(firstValue, secondValue, exception);
    }

    private static Value createValueBlock() {
        SubjectSignificance significance = chooseSubjectSignificance();
        Operator valueOperator = chooseOperator();
        Operator comparisonOperator = chooseOperator();

        System.out.print("Enter value: ");
        int value = sc.nextInt();
        System.out.print("Enter comparison value: ");
        int comparisonValue = sc.nextInt();
        sc.nextLine();  // Consume newline

        return new Value(significance, valueOperator, comparisonOperator, value, comparisonValue);
    }

    private static SubjectSignificance chooseSubjectSignificance() {
        System.out.println("Choose Subject Significance:");
        for (SubjectSignificance significance : SubjectSignificance.values()) {
            System.out.println(significance.ordinal() + 1 + ". " + significance);
        }
        int choice = sc.nextInt();
        sc.nextLine();  // Consume newline
        return SubjectSignificance.values()[choice - 1];
    }

    private static Operator chooseOperator() {
        System.out.println("Choose Operator:");
        for (Operator operator : Operator.values()) {
            System.out.println(operator.ordinal() + 1 + ". " + operator);
        }
        int choice = sc.nextInt();
        sc.nextLine();  // Consume newline
        return Operator.values()[choice - 1];
    }

    private static void displayConditionBlocks(Condition condition) {
        System.out.println("Condition Blocks:");
        for (ConditionBlock block : condition.getCondition()) {
            if (block instanceof Compulsory) {
                Compulsory compulsory = (Compulsory) block;
                System.out.println("Compulsory: subjectCode=" + compulsory.getSubjectCode() + ", subjects=" + compulsory.getSubjects() + ", operator=" + compulsory.getUnaryOperator() + ", value=" + compulsory.getValue());
            } else if (block instanceof Exception) {
                Exception exception = (Exception) block;
                System.out.println("Exception: subjects=" + exception.getException());
            } else if (block instanceof MixedValue) {
                MixedValue mixedValue = (MixedValue) block;
                System.out.println("MixedValue: firstValue=" + mixedValue.getFirstValueCondition() + ", secondValue=" + mixedValue.getSecondValueCondition() + ", exception=" + mixedValue.getException());
            } else if (block instanceof Value) {
                Value value = (Value) block;
                System.out.println("Value: significance=" + value.getSignificance() + ", valueOperator=" + value.getValueOperator() + ", comparisonOperator=" + value.getComparisonOperator() + ", value=" + value.getValue() + ", comparisonValue=" + value.getComparisonValue() + ", exception=" + value.getException());
            }
        }
    }

    public static boolean newSub(){
        String sub = "";
        int code = -1;
        try{
        System.out.println("Enter Subject name");
        System.out.print(">>>");
        sub = sc.nextLine();
        System.out.println("Enter Subject code");
        System.out.print(">>>");
        code = sc.nextInt();
        Control.addNewSubject(code, sub);
        }catch(java.lang.Exception e){
            System.out.println("Subject added");
            return false;
        }
        return true; 


    }

    // data required to generate result

    private static String grade = "";
    private static String title = "";
    private static int[] selectedTerms = { -1, -1, -1 };
    private static float[] averagerValues = null;
    private static int outOf = -1;
    private static String path = "";

    public static boolean newgsl(){
        String choice = "";
        try{
        System.out.println("pgsl for pointer gsl, hgsl for hashmap gsl ");
        System.out.print("gsl ");
        choice = sc.nextLine();
        }catch(InputMismatchException e){
            System.out.println(e);
            return false;
        }

        switch(choice){
            case "pgsl" :{
                pgsl();
            }
            case "hgsl" :{
                hgsl();
            }
            default :{
                return false;
            }

        }

        
    }

    // grade to grade pointer
    public static boolean pgsl(){
        try{
        String grade ="";
        String grade2 = "";
        System.out.println("Enter Grade ");
        System.out.print(">>>");
        grade = sc.nextLine();
        System.out.println("Enter Reference Grade ");
        System.out.print(">>>");
        grade2 = sc.nextLine();
        Control.addNewGradeSubjectList(gradeStringToInteger(grade),gradeStringToInteger(grade2));
        }catch(java.lang.Exception e){
            return false;
        }
        return true;
    }

    public static boolean hgsl(){
        //grade
        String grade = "";
        System.out.println("Enter Grade ");
        System.out.print(">>>");
        grade = sc.nextLine();
        HashMap<Integer,SubjectSignificance> gsl = new HashMap<>();
        
        while(true){
        System.out.println("Enter exit to stop taking input, c to continue");
        System.out.print(">>>");
        // take input or return
        String choice  = sc.nextLine();
        switch(choice){
            case "exit":{
                Control.addNewGradeSubjectList(gradeStringToInteger(grade), gsl); 
                return true;
                
            }
            case "c" :{
                takeInput(gsl);
            }
        }
    }


    }

    public static boolean takeInput( HashMap<Integer,SubjectSignificance> gsl){
        try{
        System.out.println("Enter Subject Code ");
        System.out.print(">>>");
        int code = sc.nextInt();
        System.out.println("Enter Subject Significance M, MI, E ");

        System.out.print(">>>");
        String signi = sc.nextLine();

        SubjectSignificance ss = switch(signi){
            case "M"-> 
                SubjectSignificance.MAJOR;
            
            case "MI"
               -> SubjectSignificance.MINOR;
            
            case "E" 
               -> SubjectSignificance.EVALUATION;
            default ->
                null;
            
        };
        
        gsl.put(code, ss);
    }catch(java.lang.Exception e){
        System.out.println("Not added!");
        return false;
    }
    System.out.println("Added Succesfully");
        return true;
    }

    // this converts to 3n-2 + x;
    public static int gradeStringToInteger(String inputString) {
        StringBuilder numbers = new StringBuilder();
        StringBuilder letters = new StringBuilder();

        for (char character : inputString.toCharArray()) {
            if (Character.isDigit(character)) {
                numbers.append(character);
            } else if (Character.isLetter(character)) {
                letters.append(character);
            }
        }

        int grade = Integer.parseInt(numbers.toString());
        // addition according to sections
        int addition = (grade <= 10) ?

                switch (letters.toString()) {
                    case "a" -> 0;
                    case "b" -> 1;
                    case "c" -> 2;
                    default -> 0;

                } : switch (letters.toString()) {
                    case "s" -> 0;
                    case "c" -> 1;
                    case "a" -> 2;
                    default -> 0;
                };

        
                
        int gradeNumber = ((3 * (grade)) - 2) + addition;

        return gradeNumber;

    }

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
