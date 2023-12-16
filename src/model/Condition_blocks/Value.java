package model.Condition_blocks;

import model.enums.Operator;
import model.enums.SubjectSignificance;

// value based condtion

public class Value implements ConditionBlock {

    // eg. major subs failed
    private SubjectSignificance significance;

    // > or < to be appplied on the value mentioned
    private Operator valueOperator;
    private Operator comparisonOperator;

    // if value being used for comparison is 'unaryOperator' value, the condtion is
    // passed


    private int value;
    private int comparisonValue;


    private Exception exception;

    
    public Value(SubjectSignificance significance, Operator valueOperator, Operator comparisonOperator, int value,
            int comparisonValue, Exception exception) {
        this.significance = significance;
        this.valueOperator = valueOperator;
        this.comparisonOperator = comparisonOperator;
        this.value = value;
        this.comparisonValue = comparisonValue;
        this.exception = exception;
    }
    public Value(SubjectSignificance significance, Operator valueOperator, Operator comparisonOperator, int value,
            int comparisonValue) {
        this.significance = significance;
        this.valueOperator = valueOperator;
        this.comparisonOperator = comparisonOperator;
        this.value = value;
        this.comparisonValue = comparisonValue;
        this.exception = null;
    }
    
    public SubjectSignificance getSignificance() {
        return significance;
    }
    public Operator getValueOperator() {
        return valueOperator;
    }
    public Operator getComparisonOperator() {
        return comparisonOperator;
    }
    public int getValue() {
        return value;
    }
    public int getComparisonValue() {
        return comparisonValue;
    }
    public Exception getException() {
        return exception;
    }



    
}
