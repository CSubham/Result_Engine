package model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.stage.Stage;
import model.enums.SubjectSignificance;

public class Master extends Application {
    private static DataBridge databridge = new DataBridge();

    static {
        databridge.connectDatabase();
    }

    public static void main(String[] args) throws Exception {

        // databridge.executeQuery("create table students(pin int primary key
        // unique,class varchar(3),data varchar(250));");
        // databridge.executeQuery("create table subjects(subject_name
        // varchar(30),subject_code int primary key unique );");
        // databridge.executeQuery("create table term_data( pin int primary key
        // unique,term_one varchar(250), term_two varchar(250), term_three varchar(250)
        // );");
        // databridge.executeQuery("drop table grade_subject_list");
        // databridge.executeQuery("CREATE TABLE grade_subject_list (grade int PRIMARY
        // KEY, subject_list varchar(250));");

        // String[] subjectNames =
        // {"english_language","english_literature","spelling_dictation","general_knowledge","hindi","tibetan","bengali","nepali",
        // "mathematics","science","value_education","social_studies","computer_science","reading",
        // "recitation","art","physics","chemistry","biology","computer_application","commercial_application","physical_education",
        // "accountancy","commerce","economics","sociology","history","geography","pol_science","supw","business_studies",
        // "conduct","physical_training","singing","order_n_neatness","handwriting","attendance","co_curricular_activities",
        // "spoken_language","personal_grooming","hobby","library_reading","cooperation",
        // };

        // try {
        // String sql = "INSERT INTO subjects (subject_code, subject_name) VALUES (?,
        // ?)";
        // PreparedStatement statement =
        // databridge.getConnection().prepareStatement(sql);

        // // Loop through the array to insert data into the table
        // for (int i = 0; i < subjectNames.length; i++) {
        // statement.setInt(1, i + 1); // Assuming the subject_code starts from 1
        // statement.setString(2, subjectNames[i]);
        // statement.executeUpdate();
        // }

        // System.out.println("Data inserted successfully!");
        // } catch (SQLException e) {
        // e.printStackTrace();
        // }

        // TESTING

        // System.out.println("tString :"+ tString);

        // HashMap<Integer,String> hmap = transcriptString.convertToHashMap(tString);

        // printHashMap(hmap);
        // System.out.println(transcriptString.getName(tString));
        // System.out.println(transcriptString.getRollNo(tString));

        // // enterStudentData(student);
        // updateStudentData(student);

        // ResultSet resultSet = databridge.fetchQueryData("select * from
        // grade_subject_list;");
        // printResultSet(resultSet);
        // insertIntoTermTwo();

        // ResultSet resultSet2 = databridge.fetchQueryData("select * from subjects;");
        // printResultSet(resultSet2);

        // String query = "SELECT table_name FROM information_schema.tables WHERE
        // table_schema = 'public' AND table_type = 'BASE TABLE'";
        // ResultSet resultSet = databridge.fetchQueryData( query);

        // // Iterate through the result set and print table names
        // while (resultSet.next()) {
        // String tableName = resultSet.getString("table_name");
        // System.out.println("Table Name: " + tableName);
        // }

        // HashMap<Integer, Integer> subjects = new HashMap<>();
        // subjects.put(01, 45);
        // subjects.put(02, 45);
        // subjects.put(04,100);
        // subjects.put(05, 30);
        // subjects.put(06, 46);
        // subjects.put(07, 60);
        // subjects.put(9, 78);
        // subjects.put(10, 90);
        // subjects.put(11, 30);
        // subjects.put(8, 45);

        // Student student = new Student(45, 0401, "Subham Rai The Third", subjects,
        // "1a");

        // enterStudentData(student);
        // Result result = new Result(Master.subjects);
        // System.out.println(result.calculateResultPrimary(student));

        // databridge.executeQuery("insert into subjects values('bengali',44);");

        // HashMap<Integer, SubjectSignificance> gradeSubjectList = new HashMap<>();
        // gradeSubjectList.put(01, SubjectSignificance.EVALUATION);
        // gradeSubjectList.put(02, SubjectSignificance.EVALUATION);
        // gradeSubjectList.put(03, SubjectSignificance.EVALUATION);
        // gradeSubjectList.put(04, SubjectSignificance.EVALUATION);
        // gradeSubjectList.put(05, SubjectSignificance.EVALUATION);
        // gradeSubjectList.put(06, SubjectSignificance.EVALUATION);
        // gradeSubjectList.put(07, SubjectSignificance.EVALUATION);
        // gradeSubjectList.put(8, SubjectSignificance.EVALUATION);
        // gradeSubjectList.put(9, SubjectSignificance.EVALUATION);
        // gradeSubjectList.put(10, SubjectSignificance.EVALUATION);
        // gradeSubjectList.put(11, SubjectSignificance.EVALUATION);
        // gradeSubjectList.put(12, SubjectSignificance.EVALUATION);

        // updateGradeSubjectList(1, gradeSubjectList);
        // // String str = new Subject().generateSubjectList(gradeSubjectList);
        // System.out.println(str);

        // printHashMap(new Subject().subjectListToHashMap(str));
        // String query = "CREATE TABLE your_table_name (grade INT, condition_object
        // BYTEA ); ";

        // Condition condition = new Condition();
        // addNewCondition(condition,1);

        // printDatabaseData();

        // getGradeSubjectList(1);
        // deleteCondition(1);

        // int[] selectedTerms = { 1, 0, 0 };
        // makeResult("1a", "pain stacking", selectedTerms, null);

        // Student student = getStudentData(1);
        // System.out.println(student.getTermOne());
        // System.out.println(student.getTermTwo());
        // System.out.println(student.getTermThree());

        launch(args);

    }

    @Override
    public void start(Stage arg0) throws Exception {
        int[] selectedTerms = { 1, 0, 0 };
        makeResult("1a", "pain stacking", selectedTerms, null);
        
    }

    public static void printHashMap(Map<Integer, SubjectSignificance> hashMap) {
        for (Map.Entry<Integer, SubjectSignificance> entry : hashMap.entrySet()) {
            int key = entry.getKey();
            SubjectSignificance value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + value);
        }
    }

    public static void printResultSet(ResultSet resultSet) {
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Print column names
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(metaData.getColumnName(i) + "\t");
            }
            System.out.println();

            // Print rows
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(resultSet.getString(i) + "\t");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // print the data of the database
    public static void printDatabaseData() throws SQLException {

        // Step 1: Establish the connection
        Connection connection = databridge.getConnection();

        // Step 2: Specify the tables you want to print
        String[] tablesToPrint = { "students", "subjects", "term_data", "grade_subject_list", "conditions" };

        for (String tableName : tablesToPrint) {
            System.out.println("Table: " + tableName);

            // Step 3: Get data from the specified table
            PreparedStatement dataStatement = connection.prepareStatement("SELECT * FROM " + tableName);
            ResultSet data = dataStatement.executeQuery();
            ResultSetMetaData columnMetaData = data.getMetaData();

            int columnCount = columnMetaData.getColumnCount();

            // Print column names
            for (int i = 1; i <= columnCount; i++) {
                String columnName = columnMetaData.getColumnName(i);
                System.out.println("  Column: " + columnName);
            }

            // Print data
            while (data.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = columnMetaData.getColumnName(i);
                    Object columnValue = data.getObject(columnName);
                    System.out.println("    " + columnName + ": " + columnValue);
                }
            }

            System.out.println();
        }

        // Step 4: Close the connection
        connection.close();

    }

    // student and its data into the database
    public static void enterStudentData(Student student) {
        TranscriptString transcriptString = new TranscriptString();
        String tString = transcriptString.generate(student.getSubjects(), student.getName(), student.getRoll_no());

        if (student.getSecondLanguage() != null)
            tString = transcriptString.addLanguages(tString,
                    student.getSecondLanguage()[0],
                    student.getSecondLanguage()[1], -1, -1);
        if (student.getThirdLanguage() != null)
            tString = transcriptString.addLanguages(tString, -1, -1,
                    student.getThirdLanguage()[0], student.getThirdLanguage()[1]);
        databridge.executeQuery(
                "insert into students values(" + (student.getPin()) + "," + "'" + (student.getGrade()) + "'" + ","
                        + "'" + tString + "'" + ");");

        databridge.executeQuery("INSERT INTO term_data (pin) VALUES (" + student.getPin() + ");");

    }

    // when there is a change in its pin no, the previous pin is required
    public static void updateStudentData(Student student, int pin) {
        TranscriptString transcriptString = new TranscriptString();
        String tString = transcriptString.generate(student.getSubjects(), student.getName(), student.getRoll_no());
        if (student.getSecondLanguage() != null)
            tString = transcriptString.addLanguages(tString,
                    student.getSecondLanguage()[0],
                    student.getSecondLanguage()[1],
                    -1, -1);
        if (student.getThirdLanguage() != null)
            tString = transcriptString.addLanguages(tString, -1, -1,
                    student.getThirdLanguage()[0],
                    student.getThirdLanguage()[1]);

        databridge.executeQuery("update students set pin =" + student.getPin() + " where pin =" + pin + ";");
        databridge.executeQuery("update term_data set pin =" + student.getPin() + " where pin =" + pin + ";");

        // databridge.executeQuery(
        // "insert into students values(" + (student.getPin()) + "," + "'" +
        // (student.getGrade()) + "'" + ","
        // + "'" + tString + "'" + ");");

    }

    // update the data of an existing student
    public static void updateStudentData(Student student) {
        TranscriptString transcriptString = new TranscriptString();
        String tString = transcriptString.generate(student.getSubjects(), student.getName(), student.getRoll_no());
        if (student.getSecondLanguage() != null)
            tString = transcriptString.addLanguages(tString,
                    student.getSecondLanguage()[0],
                    student.getSecondLanguage()[1], -1, -1);
        if (student.getThirdLanguage() != null)
            tString = transcriptString.addLanguages(tString, -1, -1,
                    student.getThirdLanguage()[0],
                    student.getThirdLanguage()[1]);

        databridge.executeQuery("update students set class ='" + student.getGrade() + "', data ='" + tString
                + "' where pin =" + student.getPin() + ";");

    }

    // delete student data
    public static void deleteStudentData(int pin) {

        String query = " DELETE FROM students WHERE pin = " + pin + ";";
        String queryTwo = " DELETE FROM term_data WHERE pin = " + pin + ";";

        databridge.executeQuery(query);
        databridge.executeQuery(queryTwo);
    }
    // increment the marks provided to the one already stored in the database
    // and store the new TStirng data

    public static void incrementStudentData(Student stu) throws SQLException {

        Student student = getStudentData(stu.getPin());
        int[] secondLanguageFinal = null;
        int[] thirdLanguageFinal = null;

        int[] valueFinal = addArrays(getValuesArray(stu.getSubjects()), getValuesArray(student.getSubjects()));
        try {
            secondLanguageFinal = addArrays(stu.getSecondLanguage(), student.getSecondLanguage());
        } catch (Exception e) {
        }
        try {
            thirdLanguageFinal = addArrays(stu.getThirdLanguage(), student.getThirdLanguage());
        } catch (Exception e) {

        }
        int[] keysArray = getKeysArray(stu.getSubjects());

        HashMap<Integer, Integer> studentHM = new HashMap<Integer, Integer>();

        for (int i = 0; i < keysArray.length; i++) {
            studentHM.put(keysArray[i], valueFinal[i]);
        }

        Student studentFinal = new Student(student.getRoll_no(), student.getPin(), student.getName(), studentHM,
                stu.getGrade());

        try {
            if (secondLanguageFinal != null)
                studentFinal.setSecondLanguage(secondLanguageFinal);
            if (thirdLanguageFinal != null)
                studentFinal.setThirdLanguage(thirdLanguageFinal);
        } catch (Exception e) {
        }

        updateStudentData(studentFinal);

    }

    private static int[] addArrays(int[] array1, int[] array2) {
        // Check if the arrays have the same length
        if (array1.length != array2.length) {
            throw new IllegalArgumentException("Arrays must have the same length");
        }

        int length = array1.length;
        int[] result = new int[length];

        // Add corresponding elements
        for (int i = 0; i < length; i++) {
            result[i] = array1[i] + array2[i];
        }

        return result;
    }

    private static int[] getKeysArray(HashMap<Integer, Integer> hashMap) {
        int[] keysArray = new int[hashMap.size()];
        int index = 0;
        for (int key : hashMap.keySet()) {
            keysArray[index] = key;
            index++;
        }
        return keysArray;
    }

    private static int[] getValuesArray(HashMap<Integer, Integer> hashMap) {
        int[] valuesArray = new int[hashMap.size()];
        int index = 0;
        for (Integer value : hashMap.values()) {
            valuesArray[index] = value;
            index++;
        }
        return valuesArray;
    }

    // fetch a student's data from the database

    // changes here have not been tested due to no internet connection 11 December.

    public static Student getStudentData(int pin) throws SQLException {
        String query = "SELECT * FROM students WHERE pin =" + pin + ";";

        String tString = "";
        String grade = "";

        ResultSet resultSet = databridge.fetchQueryData(query);
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                switch (i) {
                    case 1 -> {
                        break;
                    }
                    case 2 -> {
                        grade = resultSet.getString(i);
                        break;
                    }
                    case 3 -> {
                        tString = resultSet.getString(i);
                        break;
                    }

                }
            }
            System.out.println();
        }

        TranscriptString tStringObj = new TranscriptString();

        int rollNo = tStringObj.getRollNo(tString);
        String name = tStringObj.getName(tString);
        HashMap<Integer, Integer> subjects = tStringObj.convertToHashMap(tString);

        Student student = new Student(rollNo, pin, name, subjects, grade);
        try {
            student.setSecondLanguage(tStringObj.getSecondLanguage(tString));
        } catch (Exception e) {
        }
        try {
            student.setSecondLanguage(tStringObj.getThirdLanguage(tString));

        } catch (Exception e) {
        }

        // get term one to three
        String queryTwo = "SELECT * FROM term_data WHERE pin =" + pin + ";";
        ResultSet termData = databridge.fetchQueryData(queryTwo);
        ResultSetMetaData metaDataTwo = termData.getMetaData();
        int columnCountTwo = metaDataTwo.getColumnCount();

        String termOne = null;
        String termTwo = null;
        String termThree = null;

        while (termData.next()) {
            for (int i = 1; i <= columnCountTwo; i++) {
                switch (i) {
                    case 1 -> {
                        break;
                    }
                    case 2 -> {
                        termOne = termData.getString(i);
                        break;
                    }
                    case 3 -> {
                        termTwo = termData.getString(i);
                        break;
                    }
                    case 4 -> {
                        termThree = termData.getString(i);
                        break;
                    }

                }
            }
            System.out.println();
        }

        if (termOne != null)
            student.setTermOne(termOne);
        if (termTwo != null)
            student.setTermTwo(termTwo);
        if (termThree != null)
            student.setTermThree(termThree);

        return student;

    }

    // note : the data in the students table will be pushed into the term data
    // table, there are three term columns,
    // the respective function should be called for pushing into the table columns.
    // the students data should all be correct because not much manipulation will be
    // allowed on term_data through code

    public static void insertIntoTermOne() {
        insertTermData("term_one");
    }

    public static void insertIntoTermTwo() {
        insertTermData("term_two");

    }

    public static void insertIntoTermThree() {
        insertTermData("term_three");

    }

    // takes the column name as parameter
    private static void insertTermData(String term) {
        String grade = "";

        // set grade value
        for (int i = 1; i <= 12; i++) {
            grade += i;
            for (int j = 1; j <= 3; j++) {

                if (i != 11 && i != 12) {
                    switch (j) {
                        case 1: {
                            grade += 'a';
                            break;
                        }
                        case 2: {
                            grade += 'b';
                            break;
                        }
                        case 3: {
                            grade += 'c';
                            break;
                        }

                    }
                } else {
                    switch (j) {
                        case 1: {
                            grade += 's'; // science
                            break;
                        }
                        case 2: {
                            grade += 'c'; // commerce
                            break;
                        }
                        case 3: {
                            grade += 'a'; // arts
                            break;
                        }

                    }

                }

                // fetch data acc to grade
                ResultSet rs = databridge
                        .fetchQueryData("select pin,data from students where class='" + grade + "';");
                // System.out.println(" result set from insertTermData() :");
                // printResultSet(rs);
                insert(term, rs);
                grade = "";
                grade += i;

            }
            grade = "";

        }

    }

    private static void insert(String term, ResultSet resultSet) {
        Connection connection = databridge.getConnection();

        try {
            connection.setAutoCommit(false);
            String query = " update term_data set " + term + " = ? where pin = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            while (resultSet.next()) {
                preparedStatement.setString(1, resultSet.getString(2));
                preparedStatement.setInt(2, resultSet.getInt(1));
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void enterGradeSubjectList(int grade, HashMap<Integer, SubjectSignificance> subjects) {
        model.Subject subject = new model.Subject();
        String subjectString = subject.generateSubjectList(subjects);

        databridge.executeQuery(
                "INSERT INTO grade_subject_list (grade, subject_list) VALUES ( " + grade + ",'" + subjectString
                        + "');");

    }

    public static void updateGradeSubjectList(int grade, HashMap<Integer, SubjectSignificance> subjects) {
        model.Subject subject = new model.Subject();
        String subjectString = subject.generateSubjectList(subjects);
        databridge.executeQuery(
                "UPDATE grade_subject_list SET subject_list = '" + subjectString + "' WHERE grade = " + grade + ";");

    }

    public static HashMap<Integer, SubjectSignificance> getGradeSubjectList(int n) throws SQLException {
        String query = "SELECT * FROM grade_subject_list WHERE grade =" + n + ";";

        String gradeSubjectList = "";

        ResultSet resultSet = databridge.fetchQueryData(query);
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                switch (i) {
                    case 1 -> {
                        break;
                    }
                    case 2 -> {
                        gradeSubjectList = resultSet.getString(i);

                        break;
                    }

                }
            }
            System.out.println();
        }

        Subject subject = new Subject();
        return subject.subjectListToHashMap(gradeSubjectList);

    }

    public static void deleteGradeSubjectList(int grade) {

        String query = " DELETE FROM grade_subject_list WHERE grade = " + grade + ";";
        databridge.executeQuery(query);

    }

    public static void deleteSubject(int subject_code) {
        String query = " DELETE FROM subjects WHERE subject_code = " + subject_code + ";";
        databridge.executeQuery(query);
    }

    public static void addNewSubject(int subjectCode, String subjectName) {

        String query = "INSERT INTO subjects (subject_code, subject_name) VALUES (" + subjectCode + ",'" + subjectName
                + "')";
        databridge.executeQuery(query);

    }

    public static void deleteCondition(int grade) {
        String query = " DELETE FROM conditions WHERE grade = " + grade + ";";
        databridge.executeQuery(query);
    }

    public static void addNewCondition(Condition condition, int grade) {
        byte[] serialized = serialize(condition);
        String hexString = bytesToHex(serialized);
        String query = "INSERT INTO conditions (grade, data_bytea) VALUES (" + grade + ", E'\\\\x" + hexString + "');";
        databridge.executeQuery(query);

    }

    public static Condition getConditionBlock(int grade) throws SQLException {

        byte[] bytes = retrieveByteArray(databridge.getConnection(), 1);

        Condition condition = deserialize(bytes);
        return condition;

    }

    private static byte[] retrieveByteArray(Connection connection, int grade) throws SQLException {
        String sql = "SELECT data_bytea FROM conditions WHERE grade = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, grade);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBytes("data_bytea");
                } else {
                    return null; // No data found
                }
            }
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xff & aByte);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private static byte[] serialize(Condition condition) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(condition);
            System.out.println("Object has been serialized");
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Condition deserialize(byte[] data) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
                ObjectInputStream ois = new ObjectInputStream(bis)) {
            Object obj = ois.readObject();
            System.out.println("Object has been deserialized");
            return (Condition) obj;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // custom query
    // note : you need a way to authorise else misconducts are inevitable

    public static void customQuery(String query) {
        databridge.executeQuery(query);
    }

    public static void fetchQueryData(String query) {
        ResultSet resultSet = databridge.fetchQueryData(query);
        printResultSet(resultSet);
    }

    // make result code

    // selected terms = (position+1) represents terms if value at that pos > 0 it is
    // selected
    public static void makeResult(String grade, String title, int[] selectedTerms, float[] averagerValues)
            throws SQLException {

        ArrayList<Integer> pins = retrievePinsByGrade(grade);
        // a tweak here so below class 10 the number of grades and conditions saved are
        // minimal, basically same grades
        // will have same grade subjectlist till 10 so below 11 we can store only for
        // grade section a

        HashMap<Integer, SubjectSignificance> gradeSubjectList = getGradeSubjectList(
                Subject.gradeStringToInteger(grade));

        HashMap<Integer, String> subjects = retrieveSubjects();
        Condition condition = getConditionBlock(Subject.gradeStringToInteger(grade));

        for (int i : pins) {

            ArrayList<String> termData = new ArrayList<>();

            Student student = getStudentData(i);

            for (int j = 0; j < selectedTerms.length; j++) {
                if (selectedTerms[j] > 0) {
                    termData.add(

                            switch (j) {
                                case 0 -> student.getTermOne();
                                case 1 -> student.getTermTwo();
                                case 2 -> student.getTermThree();
                                default -> throw new IllegalArgumentException("Unexpected value: " + j);
                            }

                    );

                } else {
                    switch (j) {
                        case 0 -> student.setTermOne(null);
                        case 1 -> student.setTermTwo(null);
                        case 2 -> student.setTermThree(null);
                    }
                }

            }

            AdvancedTermAverager advancedTermAverager = new AdvancedTermAverager();
            // subject list is grade subject list
            if (termData.size() == 2) {
                student.setSubjects(new TranscriptString().convertToHashMap(advancedTermAverager.averageofTwo(
                        gradeSubjectList, termData.get(0), termData.get(1), averagerValues[0],
                        averagerValues[1], averagerValues[2], averagerValues[3])));

            } else if (termData.size() == 3) {

                student.setSubjects(new TranscriptString().convertToHashMap(advancedTermAverager.averageofThree(
                        gradeSubjectList, termData.get(0), termData.get(1), termData.get(2), averagerValues[0],
                        averagerValues[1], averagerValues[2], averagerValues[3], averagerValues[4])));

            } else {
                student.setSubjects(new TranscriptString().convertToHashMap(termData.get(0)));
            }

            // store file and store in created folder
            Result.createResultImageFile(student, title, condition, gradeSubjectList, subjects);

        }

    }

    public static void makeResult(int pin) {

    }

    private static HashMap<Integer, String> retrieveSubjects() throws SQLException {
        String sql = "SELECT subject_code, subject_name FROM subjects";
        Connection connection = databridge.getConnection();
        HashMap<Integer, String> subjectMap = new HashMap<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int subjectCode = resultSet.getInt("subject_code");
                    String subjectName = resultSet.getString("subject_name");

                    // Store in the HashMap
                    subjectMap.put(subjectCode, subjectName);
                }
            }
        }

        return subjectMap;
    }

    private static ArrayList<Integer> retrievePinsByGrade(String grade) throws SQLException {

        Connection connection = databridge.getConnection();
        ArrayList<Integer> pins = new ArrayList<>();

        String sql = "SELECT pin FROM students WHERE class = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, grade);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    // Access the 'pin' column
                    int pin = resultSet.getInt("pin");
                    pins.add(pin);

                }
            }
        }

        return pins;

    }

    

}
