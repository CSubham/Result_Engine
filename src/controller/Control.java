package controller;

import java.sql.SQLException;
import java.util.HashMap;

import model.Master;
import model.Student;
import model.enums.SubjectSignificance;

public class Control {

    public static void main(String[] args) throws SQLException {
        printDatabaseData();
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

    public static Student getStudentData(int pin) throws SQLException {
        return Master.getStudentData(pin);
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
    public static void newGradeSubjectList(int grade, HashMap<Integer, SubjectSignificance> gradeSubjectList) {
        Master.enterGradeSubjectList(0, null);
    }

    public static void updateGradeSubjectList(int grade, HashMap<Integer, SubjectSignificance> gradeSubjectList) {
        Master.updateGradeSubjectList(grade, gradeSubjectList);
    }

    // delete a student
    public static void deleteStudentData(int pin){
        Master.deleteStudentData(pin);
    }
}
