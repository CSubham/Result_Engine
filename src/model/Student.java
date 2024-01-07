package model;

import java.util.HashMap;

public class Student {

    private int roll_no;
    private int pin;
    private String name;
    private HashMap<Integer, Integer> subjects = null;
    private int[] secondLanguage = null;
    private int[] thirdLanguage = null;
    private String termOne = null;
    private String termTwo= null;
    private String termThree = null;
    // class basically, as we cannot use it being reserved
    private String grade;

    public Student(int roll_no, int pin, String name, HashMap<Integer, Integer> subjects, String grade) {
        this.roll_no = roll_no;
        this.pin = pin;
        this.name = name;
        this.subjects = subjects;
        this.grade = grade;
        this.secondLanguage = null;
        this.thirdLanguage = null;
    }

    public Student(int roll_no, int pin, String name, HashMap<Integer, Integer> subjects, String grade,
            int[] secondLanguage, int[] thirdLanguage) {

        this.roll_no = roll_no;
        this.pin = pin;
        this.name = name;
        this.subjects = subjects;
        this.grade = grade;
        this.secondLanguage = secondLanguage;
        this.thirdLanguage = thirdLanguage;
    }

    public String getTermOne() {
        return termOne;
    }

    public void setTermOne(String termOne) {
        this.termOne = termOne;
    }

    public String getTermTwo() {
        return termTwo;
    }

    public void setTermTwo(String termTwo) {
        this.termTwo = termTwo;
    }

    public String getTermThree() {
        return termThree;
    }

    public void setTermThree(String termThree) {
        this.termThree = termThree;
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

    public HashMap<Integer, Integer> getSubjects() {
        return subjects;
    }

    public void setSubjects(HashMap<Integer, Integer> subjects) {
        this.subjects = subjects;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int[] getSecondLanguage() {
        return secondLanguage;
    }

    public void setSecondLanguage(int[] secondLanguage) {
        this.secondLanguage = secondLanguage;
    }

    public int[] getThirdLanguage() {
        return thirdLanguage;
    }

    public void setThirdLanguage(int[] thirdLanguage) {
        this.thirdLanguage = thirdLanguage;
    }

}
