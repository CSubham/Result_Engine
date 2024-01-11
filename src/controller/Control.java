package controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import model.Condition;
import model.Master;
import model.Student;
import model.enums.SubjectSignificance;

public class Control {

    public static void main(String[] args) throws SQLException {

        // HashMap<Integer, SubjectSignificance> gradeSubjectList = new HashMap<>();
        // gradeSubjectList.put(01, SubjectSignificance.MAJOR);
        // gradeSubjectList.put(02, SubjectSignificance.MAJOR);
        // gradeSubjectList.put(03,SubjectSignificance.MAJOR);
        // gradeSubjectList.put(04, SubjectSignificance.MAJOR);
        // gradeSubjectList.put(05, SubjectSignificance.MAJOR);
        // gradeSubjectList.put(06, SubjectSignificance.MAJOR);
        // gradeSubjectList.put(07, SubjectSignificance.MAJOR);
        // gradeSubjectList.put(8, SubjectSignificance.MAJOR);
        // gradeSubjectList.put(9, SubjectSignificance.MAJOR);
        // gradeSubjectList.put(10,SubjectSignificance.MAJOR);
        // gradeSubjectList.put(11, SubjectSignificance.MAJOR);

        // gradeSubjectList.put(14, SubjectSignificance.MINOR);
        // gradeSubjectList.put(15, SubjectSignificance.MINOR);
        // gradeSubjectList.put(16, SubjectSignificance.MINOR);

        // gradeSubjectList.put(32, SubjectSignificance.EVALUATION);
        // gradeSubjectList.put(33, SubjectSignificance.EVALUATION);
        // gradeSubjectList.put(34, SubjectSignificance.EVALUATION);
        // gradeSubjectList.put(35, SubjectSignificance.EVALUATION);
        // gradeSubjectList.put(36, SubjectSignificance.EVALUATION);
        // gradeSubjectList.put(37, SubjectSignificance.EVALUATION);

        // addNewGradeSubjectList(1, gradeSubjectList);

        // HashMap<Integer, Integer> randomHashMap = new HashMap<>();

        // // Manually put keys and assign random values
        // randomHashMap.put(1, new Random().nextInt(56) + 45);
        // randomHashMap.put(2, new Random().nextInt(56) + 45);
        // randomHashMap.put(3, new Random().nextInt(56) + 45);
        // randomHashMap.put(4, new Random().nextInt(56) + 45);
        
        // randomHashMap.put(6, new Random().nextInt(56) + 45);
        // randomHashMap.put(7, new Random().nextInt(56) + 45);
        // randomHashMap.put(8, new Random().nextInt(56) + 45);
        // randomHashMap.put(9, new Random().nextInt(56) + 45);
        // randomHashMap.put(10, new Random().nextInt(56) + 45);
        // randomHashMap.put(11, new Random().nextInt(56) + 45);
        // randomHashMap.put(14, new Random().nextInt(56) + 45);
        // randomHashMap.put(15, new Random().nextInt(56) + 45);
        // randomHashMap.put(16, new Random().nextInt(56) + 45);
        // randomHashMap.put(32, new Random().nextInt(56) + 45);
        // randomHashMap.put(33, new Random().nextInt(56) + 45);
        // randomHashMap.put(34, new Random().nextInt(56) + 45);
        // randomHashMap.put(35, new Random().nextInt(56) + 45);
        // randomHashMap.put(36, new Random().nextInt(56) + 45);
        // randomHashMap.put(37, new Random().nextInt(56) + 45);

        // Student student = new Student(40, 456, "Subham Rai The Second", randomHashMap,
        //         "1a");
        // int[] sl = { 5, new Random().nextInt(56) + 45 };
        // student.setSecondLanguage(sl);

        printDatabaseData();

        // addNewStudent(student);
        
        
        

    }

    // make the results of provided grade (Class,section)
    public static void makeResult(String grade, String title, int[] selectedTerms, float[] averagerValues,int outOf,String path)
            throws SQLException {
        Master.makeResult(grade, title, selectedTerms,averagerValues,outOf,path);
    }

    // Adding a new student to the database
    public static void addNewStudent(Student student) {
        Master.enterStudentData(student);
    }

    // update pin of the student and student data, the student object consists
    // new pin and the previous pin is used to reference the new pin
    public static void updateStudentPin(Student student, int pin) {
        Master.updateStudentData(student, pin);
    }

    // update data of the student
    public static void updateStudentData(Student student) {
        Master.updateStudentData(student);
    }

    // print data from database
    public static void printDatabaseData() throws SQLException {
        Master.printDatabaseData();

    }

    // increment marks of student
    public static void incrementStudentMarks(Student student) throws SQLException {
        Master.incrementStudentData(student);
    }

    @Deprecated
    // fetch student data
    // lacks functionality of storing the term data of a student
    public static Student getStudentData(int pin) throws SQLException {
        return Master.getStudentData(pin);

    }

    // delete a student
    public static void deleteStudentData(int pin) {
        Master.deleteStudentData(pin);
    }

    // insert data present in Table: students, Column:data into term_one, term_two,
    // term_three
    // of Table : term_data

    public static void insertIntoTermOne() {
        Master.insertIntoTermOne();
    }

    public static void insertIntoTermTwo() {
        Master.insertIntoTermTwo();
    }

    public static void insertIntoTermThree() {
        Master.insertIntoTermThree();
    }

    // The grade below is obtained by 3n-2 where n is grade, this gives the value of
    // scetion a, incremented by +1 for
    // section b, +2 for section c
    public static void addNewGradeSubjectList(int grade, HashMap<Integer, SubjectSignificance> gradeSubjectList) {
        Master.enterGradeSubjectList(grade, gradeSubjectList);
    }

    public static void updateGradeSubjectList(int grade, HashMap<Integer, SubjectSignificance> gradeSubjectList) {
        Master.updateGradeSubjectList(grade, gradeSubjectList);
    }

    // delete gradesubjectlist
    public static void deleteGradeSubjectList(int grade) {
        Master.deleteGradeSubjectList(grade);
    }

    public static HashMap<Integer, SubjectSignificance> getGradeSubjectList(int n) throws SQLException {
        return Master.getGradeSubjectList(n);
    }

    // delete subject
    public static void deleteSubject(int subjectCode) {
        Master.deleteSubject(subjectCode);
    }

    // add new subject
    public static void addNewSubject(int subjectCode, String subjectName) {
        Master.addNewSubject(subjectCode, subjectName);

    }

    // custom query with no output
    public static void customQuery(String query) {
        Master.customQuery(query);
    }

    // query with output, prints in terminal
    public static void fetchQueryData(String query) {
        Master.fetchQueryData(query);
    }

    public static Student getStudentObject(int rollNo, int pin, String name, HashMap<Integer, Integer> subjects,
            String grade) {
        return new Student(rollNo, pin, name, subjects, grade);
    }

    public static void addNewCondition(Condition condition, int grade) {
        Master.addNewCondition(condition, grade);
    }

    public static Condition getConditionBlock(int grade) throws SQLException {
        return Master.getConditionBlock(grade);
    }

    public static void deleteCondition(int grade) {
        Master.deleteCondition(grade);
    }

}
