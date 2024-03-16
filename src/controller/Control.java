package controller;

import java.sql.SQLException;
import java.util.HashMap;
import model.Condition;
import model.Master;
import model.Student;
import model.enums.SubjectSignificance;

public class Control {

    

    // make the results of provided grade (Class,section)
    public static void makeResult(String grade, String title, int[] selectedTerms, float[] averagerValues, int outOf,
            String path)
            throws SQLException {
        Master.makeResult(grade, title, selectedTerms, averagerValues, outOf, path);
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

    public static void enterStudentAttendance(int pin, int daysAbsent) {
        Master.enterAttendance(pin, daysAbsent);
    }

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

    // pointer points to another column
    public static void addNewGradeSubjectList(int grade, int pointer) {
        Master.enterGradeSubjectList(grade, pointer);
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

    // custom query with no output
    public static void customQuery(String query) {
        Master.customQuery(query);
    }

    // query with output, prints in terminal
    public static void fetchQueryData(String query) {
        Master.fetchQueryData(query);
    }

}
