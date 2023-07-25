package model;

import java.util.HashMap;



public class Student {

    private int roll_no;
    private int pin;
    private String name;
    private HashMap<Integer, String> subjects;

    // class basically, as we cannot use it being reserved
    private String grade;

    public Student(int roll_no, int pin, String name, HashMap<Integer, String> subjects, String grade) {
        this.roll_no = roll_no;
        this.pin = pin;
        this.name = name;
        this.subjects = subjects;
        this.grade = grade;
    }

    public int getRoll_no() {
        return roll_no;
    }

    public void setRoll_no(int roll_no) {
        this.roll_no = roll_no;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<Integer, String> getSubjects() {
        return subjects;
    }

    public void setSubjects(HashMap<Integer, String> subjects) {
        this.subjects = subjects;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

  


    

}
