package model.Condition_blocks;

import java.io.Serializable;
import java.util.ArrayList;

import model.enums.Operator;

public class Compulsory implements ConditionBlock,Serializable {

    // a compulsory should only have a single subject but, if it has more than one,
    // it implies that the condition is being applied on the average of these
    // subjects

    // If the operator is Operator.NULL, then that compulsory block exists to
    // specify the,
    // what subjects form a combined subject

    private int subjectCode;
    private ArrayList<Integer> subjects;

    // > or < to be appplied on the value mentioned
    private Operator unaryOperator;

    // if value being used for comparison is 'unaryOperator' value, the condtion is
    // passed

    private int value;

    public Compulsory(int subjectCode, Operator unaryOperator, int value) {
        this.subjectCode = subjectCode;
        this.subjects = null;
        this.unaryOperator = unaryOperator;
        this.value = value;
    }

    // subject code is not null because subjects to be checked first;
    public Compulsory(ArrayList<Integer> subjects, Operator unaryOperator, int value) {
        this.subjects = subjects; 

        this.unaryOperator = unaryOperator;
        this.value = value;
    }

    public int getSubjectCode() {
        return subjectCode;
    }

    public ArrayList<Integer> getSubjects() {
        return subjects;
    }

    public Operator getUnaryOperator() {
        return unaryOperator;
    }

    public int getValue() {
        return value;
    }

}
