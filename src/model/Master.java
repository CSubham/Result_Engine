package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Master {
    private static Databridge databridge = new Databridge();
    private static ResultSet subjects = null;

    // static {
    //     databridge.connectDatabase();
    //     subjects = databridge.fetchQueryData("select * from subjects");
    // }

    public static void main(String[] args) throws Exception {

        // databridge.executeQuery("create table students(pin int primary key unique
        // ,class varchar(3),data varchar(250));");
        // databridge.executeQuery("create table subjects(subject_name
        // varchar(30),subject_code int primary key unique );");
        // databridge.executeQuery("create table term_data( pin int primary key
        // unique,term_one varchar(250), term_two varchar(250), term_three varchar(250)
        // );");
        // databridge.executeQuery("drop table subjects");

        // String[] array =
        // {"english_language","english_literature","spelling_dictation","general_knowledge","second_language",
        // "mathematics","science","value_education","social_studies","computer_science","third_language","reading",
        // "recitation","art","physics","chemistry","biology","computer_application","commercial_application","physical_education",
        // "accountancy","commerce","economics","sociology","history","geography","pol_science","supw","business_studies",
        // "conduct","physical_training","singing","order_n_neatness","handwriting","attendance","co_curricular_activities",
        // "spoken_language","personal_grooming","hobby","library_reading","supw","cooperation"
        // };

        // TESTING

        // System.out.println("tString :"+ tString);

        // HashMap<Integer,String> hmap = transcriptString.convertToHashMap(tString);

        // printHashMap(hmap);
        // System.out.println(transcriptString.getName(tString));
        // System.out.println(transcriptString.getRollNo(tString));

        // // enterStudentData(student);
        // updateStudentData(student);

        // ResultSet resultSet = databridge.fetchQueryData("select * from students;");
        // printResultSet(resultSet);
        // insertIntoTermTwo();

        // ResultSet resultSet2 = databridge.fetchQueryData("select * from subjects;");
        // printResultSet(resultSet2);

        HashMap<Integer, String> subjects = new HashMap<>();
        subjects.put(01, "45");
        subjects.put(02, "45");
        subjects.put(04, "30");
        subjects.put(05, "30");
        subjects.put(06, "30");
        subjects.put(07, "40");
        subjects.put(9, "78");
        subjects.put(10, "45");
        subjects.put(11, "30");
        subjects.put(8, "45");



        Student student = new Student(30, 8361, "Subham Rai", subjects, "11s");

        Result result = new Result(Master.subjects);
        System.out.println(result.calculateResultPrimary(student));


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

    // student and its data into the database
    public static void enterStudentData(Student student) {
        TranscriptString transcriptString = new TranscriptString();
        String tString = transcriptString.generate(student.getSubjects(), student.getName(), student.getRoll_no());

        databridge.executeQuery(
                "insert into students values(" + (student.getPin()) + "," + "'" + (student.getGrade()) + "'" + ","
                        + "'" + tString + "'" + ");");

        databridge.executeQuery("INSERT INTO term_data (pin) VALUES (" + student.getPin() + ");");

    }

    // when there is a change in its pin no, the previous pin is required
    public static void updateStudentData(Student student, int pin) {
        TranscriptString transcriptString = new TranscriptString();
        String tString = transcriptString.generate(student.getSubjects(), student.getName(), student.getRoll_no());

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

        databridge.executeQuery("update students set class ='" + student.getGrade() + "', data ='" + tString
                + "' where pin =" + student.getPin() + ";");

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

    // make result code

    public static void makeResult() {
        Result result = new Result(subjects);

        // code
    }

}
