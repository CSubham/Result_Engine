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
import model.Condition_blocks.Compulsory;
import model.Condition_blocks.ConditionBlock;
import model.enums.Operator;
import model.enums.SubjectSignificance;

public class Master {
    private static DataBridge databridge = new DataBridge();

    static {
        databridge.connectDatabase();
    }

    private static int getDaysAbsentForPin(int pin) {
        String query = "SELECT days_absent FROM attendance WHERE pin = ?";

        try (PreparedStatement preparedStatement = databridge.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, pin);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("days_absent");
                } else {
                    // Handle the case where the pin is not found
                    return -1; // Or any other appropriate value
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately based on your application's needs
            return -1; // Or any other appropriate value
        }
    }
    // enters the attendance for pin, if it is already present it updates the value
    // for that pin

    public static void enterAttendance(int pin, int daysAbsent) {

        String query = "INSERT INTO attendance (pin, days_absent)" +
                "VALUES (" + pin + ", " + daysAbsent + ")" +
                "ON CONFLICT (pin)" +
                "DO UPDATE SET days_absent = EXCLUDED.days_absent;";

        databridge.executeQuery(query);

    }

    private static void printResultSet(ResultSet resultSet) {
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
        String[] tablesToPrint = { "students", "subjects", "term_data", "grade_subject_list", "conditions",
                "attendance" };

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

    // no need to use n
    private static int[] getKeysArray(HashMap<Integer, Float> hashMap, int n) {

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

    private static float[] getValuesArray(HashMap<Integer, Float> hashMap, int n) {

        float[] valuesArray = new float[hashMap.size()];
        int index = 0;
        for (float value : hashMap.values()) {
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
        System.out.println("inserted");
    }

    public static void insertIntoTermTwo() {
        insertTermData("term_two");
        System.out.println("inserted");


    }

    public static void insertIntoTermThree() {
        insertTermData("term_three");
        System.out.println("inserted");


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

    // pointer points to another column
    public static void enterGradeSubjectList(int grade, int pointer) {
        databridge.executeQuery(
                "INSERT INTO grade_subject_list (grade, subject_list) VALUES ( " + grade + ",'" + pointer
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
        // it probably implies that it is a pointer pointing to another column because
        // pointers are integers stored as strings whose max value is 36
        try {
            if (gradeSubjectList.length() < 5)
                return getGradeSubjectList(Integer.parseInt(gradeSubjectList));
        } catch (Exception e) {
        }
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

    // stores in use term data
    private static ArrayList<String> termData = new ArrayList<>();

    // the provided string path here creates a folder at that destination and saves
    // the images at that destination
    public static void makeResult(String grade, String title, int[] selectedTerms, float[] averagerValues, int outOf,
            String path)
            throws SQLException {

        // creating grade dir at that file path , and it returns a saveLocation where we
        // can genrated image files for that specific grade

        String saveLocation = Printer.createDirAt(path, grade.toUpperCase());

        ArrayList<Integer> pins = retrievePinsByGrade(grade);

        // a tweak here so below class 10 the number of grades and conditions saved are
        // minimal, basically same grades
        // will have same grade subjectlist till 10 so below 11 we can store only for
        // grade section a

        HashMap<Integer, SubjectSignificance> gradeSubjectList = getGradeSubjectList(
                Subject.gradeStringToInteger(grade));

        HashMap<Integer, String> subjects = retrieveSubjects();
        Condition condition = getConditionBlock(Subject.gradeStringToInteger(grade));

        // getting compound subject codes
        ArrayList<ArrayList<Integer>> averageSubjectSignifier = getCompoundSubjects(condition);
        Result.setAverageSubjectSignifier(averageSubjectSignifier);

        // fetches a sorted arrayList largest to smallest which contains percentage of
        // students

        HashMap<Integer, Float> percentage = getRankByPercentage(pins, averageSubjectSignifier, selectedTerms,
                averagerValues, gradeSubjectList);

        for (int i : pins) {

            Student student = getStudentData(i);
            // setting the termData values which will be used for this sub operation
            student = setRequiredTerms(selectedTerms, student);

            // setting the subjects of the student by averaging the terms if required
            student = setAverageSubject(student, gradeSubjectList, averagerValues);

            // attendance percentage
            int daysAbsent = getDaysAbsentForPin(i);

            double attendance = 100.00 - ((((double) daysAbsent / (double) outOf)) * 100.00);

            // store file and store in created folder
            Result.createResultImageFile(student, title, condition, gradeSubjectList, subjects, percentage, attendance,
                    saveLocation);

            // term data needs to be cleared after every sub operation for next use
            termData.clear();

        }

    }

    private static Student setAverageSubject(Student student, HashMap<Integer, SubjectSignificance> gradeSubjectList,
            float[] averagerValues) {
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

        return student;

    }

    // list of compound subjects

    private static ArrayList<ArrayList<Integer>> getCompoundSubjects(Condition condition) {

        ArrayList<ArrayList<Integer>> compoundSubjects = new ArrayList<>();
        for (int i = 0; i < condition.getCondition().size(); i++) {

            ConditionBlock block = condition.getCondition().get(i);
            Class<?> clazz = block.getClass();
            if (clazz.getSimpleName() == "Compulsory") {
                Compulsory compulsory = (Compulsory) block;
                if (compulsory.getUnaryOperator() != Operator.NULL)
                    continue;
                // if the operator is null it usually contains multiple subjects.

                compoundSubjects.add(compulsory.getSubjects());

            }

        }

        return compoundSubjects;
    }

    // only keeps the values of the terms which will be used
    private static Student setRequiredTerms(int[] selectedTerms, Student student) {

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

        return student;

    }

    // private static void makeResult(int pin, String title, int[] selectedTerms,
    // float[] averagerValues) {

    // }

    // the percentage for every student is calculated and stored and then sorted to
    // estimate rank
    // which will be then (i+1) and itll be displayed only if the student has passed
    private static HashMap<Integer, Float> getRankByPercentage(ArrayList<Integer> pins,
            ArrayList<ArrayList<Integer>> averageSubjectSignifier, int[] selectedTerms, float[] averagerValues,
            HashMap<Integer, SubjectSignificance> gradeSubjectList) throws SQLException {

        // Hashmap to store percentage by key pin
        HashMap<Integer, Float> average = new HashMap<>();

        int value = 0;
        int divisorFact = 0;
        for (int i : pins) {
            ArrayList<Integer> addedSubjects = new ArrayList<>();
            Student student = getStudentData(i);

            // setting the termData values which will be used for this sub operation
            student = setRequiredTerms(selectedTerms, student);

            // setting the subjects of the student by averaging the terms if required
            student = setAverageSubject(student, gradeSubjectList, averagerValues);
            // clear termdata
            termData.clear();

            // iterating through the avg signifier and adding the calculated average values
            for (ArrayList<Integer> compoundSubject : averageSubjectSignifier) {

                value += calculateAverage(compoundSubject, student.getSubjects());
                addedSubjects.addAll(compoundSubject);
                divisorFact++;

            }

            // after we have iterated through compound subjects there must be subjects left
            // in the major and minor category

            int[] keysArray = getKeysArray(student.getSubjects());
            for (int key : keysArray) {

                if (addedSubjects.contains(key))
                    continue;

                if (gradeSubjectList.get(key) == SubjectSignificance.EVALUATION)
                    continue;

                value += switch (gradeSubjectList.get(key)) {
                    case MAJOR, MINOR -> student.getSubjects().get(key);

                    default -> 0;
                };

                divisorFact++;

            }

            // putting pin and percentage of student in hm

            average.put(student.getPin(), (float) (value / divisorFact));

            // resetting variables
            value = 0;
            divisorFact = 0;

        }

        return sortHashMap(average);

    }

    private static HashMap<Integer, Float> sortHashMap(HashMap<Integer, Float> average) {

        HashMap<Integer, Float> sortedHashMap = new HashMap<>();
        int[] keysArray = getKeysArray(average, -1);
        float[] valuesArray = getValuesArray(average, -1);

        for (int i = 0; i < valuesArray.length; i++) {
            for (int j = 0; j < valuesArray.length - 1 - i; j++) {

                if (valuesArray[j] < valuesArray[j + 1]) {
                    // swapping values
                    float temp = valuesArray[j];
                    valuesArray[j] = valuesArray[j + 1];
                    valuesArray[j + 1] = temp;

                    // swapping pins
                    int flag = keysArray[j];
                    keysArray[j] = keysArray[j + 1];
                    keysArray[j + 1] = flag;

                }
            }
        }

        for (int i = 0; i < keysArray.length; i++) {
            sortedHashMap.put(keysArray[i], valuesArray[i]);

        }

        return sortedHashMap;

    }

    // calculate average of subjects in ArrayList<Integer> which is part of
    // ArrayList<ArrayList<Integer>>
    private static int calculateAverage(ArrayList<Integer> list, HashMap<Integer, Integer> subjects) {

        if (list.size() == 0) {
            return -1; // Return -1 if the list is empty
        }

        int divFact = 0;
        int sum = 0;
        for (int number : list) {

            sum += subjects.get(number);

            divFact++;
        }

        return sum / divFact; // Calculate and return the average
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
