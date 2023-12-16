package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

import model.enums.SubjectSignificance;

public class Master {
    private static DataBridge databridge = new DataBridge();
    // private static ResultSet subjects = null;

    static {
        databridge.connectDatabase();
        // subjects = databridge.fetchQueryData("select * from subjects");
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

        HashMap<Integer, Integer> subjects = new HashMap<>();
        subjects.put(01, 45);
        subjects.put(02, 45);
        subjects.put(04, 30);
        subjects.put(05, 30);
        subjects.put(06, 30);
        subjects.put(07, 40);
        subjects.put(9, 78);
        subjects.put(10, 45);
        subjects.put(11, 30);
        subjects.put(8, 45);

        // Student student = new Student(30, 8361, "Subham Rai", subjects, "11s");

        // enterStudentData(student);
        // Result result = new Result(Master.subjects);
        // System.out.println(result.calculateResultPrimary(student));

        // databridge.executeQuery("insert into subjects values('bengali',44);");

    }

    // public static void printHashMap(Map<Integer, String> hashMap) {
    // for (Map.Entry<Integer, String> entry : hashMap.entrySet()) {
    // int key = entry.getKey();
    // String value = entry.getValue();
    // System.out.println("Key: " + key + ", Value: " + value);
    // }
    // }

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
        String[] tablesToPrint = { "students", "subjects", "term_data", "grade_subject_list" };

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

        int[] valueFinal = addArrays(getValuesArray(stu.getSubjects()), getValuesArray(student.getSubjects()));
        int[] secondLanguageFinal = addArrays(stu.getSecondLanguage(), student.getSecondLanguage());
        int[] thirdLanguageFinal = addArrays(stu.getThirdLanguage(), student.getThirdLanguage());
        int[] keysArray = getKeysArray(stu.getSubjects());

        HashMap<Integer, Integer> studentHM = new HashMap<Integer, Integer>();

        for (int i = 0; i < keysArray.length; i++) {
            studentHM.put(keysArray[i], valueFinal[i]);
        }

        Student studentFinal = new Student(student.getRoll_no(), student.getPin(), student.getName(), studentHM,
                stu.getGrade());

        if (secondLanguageFinal != null)
            studentFinal.setSecondLanguage(secondLanguageFinal);
        if (thirdLanguageFinal != null)
            studentFinal.setThirdLanguage(thirdLanguageFinal);

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
        String query = "SELECT * FROM Student WHERE pin =" + pin + ";";

        ResultSet studentData = databridge.fetchQueryData(query);
        TranscriptString tStringObj = new TranscriptString();

        String tString = studentData.getString(0);
        String grade = studentData.getString(1);

        int rollNo = tStringObj.getRollNo(tString);
        String name = tStringObj.getName(tString);
        HashMap<Integer, Integer> subjects = tStringObj.convertToHashMap(tString);

        Student student = new Student(rollNo, pin, name, subjects, grade);
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
                System.out.println(" grade from insertTermData() :" + grade);
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
                "INSERT INTO grade_subject_list (grade, subject_list) VALUES ( grade,'" + subjectString + "');");

    }

    public static void updateGradeSubjectList(int grade, HashMap<Integer, SubjectSignificance> subjects) {
        model.Subject subject = new model.Subject();
        String subjectString = subject.generateSubjectList(subjects);
        databridge.executeQuery(
                "UPDATE grade_subject_list SET subject_list = '" + subjectString + "' WHERE grade = " + grade + ";");

    }

    // make result code

    public static void makeResult() {
        // Result result = new Result(subjects);

        // code
    }

}
