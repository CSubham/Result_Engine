package model;

import java.util.ArrayList;
import java.util.HashMap;

import model.Condition_blocks.Compulsory;
import model.Condition_blocks.ConditionBlock;
import model.Condition_blocks.Exception;
import model.Condition_blocks.MixedValue;
import model.Condition_blocks.Value;
import model.enums.Operator;
import model.enums.SubjectSignificance;

public class ConditionReader {

    private HashMap<Integer, Integer> major;
    private HashMap<Integer, Integer> minor;
    private HashMap<Integer, Integer> evaluation;
    private ArrayList<ArrayList<Integer>> compoundSubjects;

    public ConditionReader(HashMap<Integer, Integer> major, HashMap<Integer, Integer> minor,
            HashMap<Integer, Integer> evaluation) {
        this.major = major;
        this.minor = minor;
        this.evaluation = evaluation;

    }

    // needs to be called after initialisation
    public void addCompoundSubjects(Condition condition) {
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
    }

    public boolean compulsoryReader(Compulsory compulosry, HashMap<Integer, SubjectSignificance> subjectList) {

        // checking whether the value will be a single data or an array
        int value = -1;

        if (compulosry.getSubjects() != null) {

            ArrayList<Integer> subjects = compulosry.getSubjects();
            ArrayList<Integer> marks = new ArrayList<Integer>();

            for (int i = 0; i < subjects.size(); i++) {
                marks.add(getMarks(compulosry.getSubjectCode(), subjectList));
            }

            value = (int) (Math.round(calculateAverage(marks)));

        } else {
            value = getMarks(compulosry.getSubjectCode(), subjectList);
        }

        return vOperatorV2(value, compulosry.getUnaryOperator(), compulosry.getValue());

    }

    public boolean valueReader(Value value) {
        int conditionPassCount = 0;

        switch (value.getSignificance()) {
            case MAJOR: {
                int[] keysArray = getKeysArray(major);
                int[] valuesArray = getValuesArray(major);

                for (int i = 0; i < keysArray.length; i++) {
                    int key = keysArray[i];
                    int keyValue = valuesArray[i];

                    if (isCompoundSubject(key))
                        continue;

                    if (vOperatorV2(keyValue, value.getComparisonOperator(), value.getComparisonValue()))
                        conditionPassCount++;

                }

            }
            case MINOR: {
                int[] keysArray = getKeysArray(minor);
                int[] valuesArray = getValuesArray(minor);

                for (int i = 0; i < keysArray.length; i++) {
                    int key = keysArray[i];
                    int keyValue = valuesArray[i];

                    if (isCompoundSubject(key))
                        continue;

                    if (vOperatorV2(keyValue, value.getComparisonOperator(), value.getComparisonValue()))
                        conditionPassCount++;

                }

            }
            default:
                break;
        }

        return vOperatorV2(conditionPassCount, value.getValueOperator(), value.getValue());

    }

    public boolean mixedValueReader(MixedValue mixedValue){
        return valueReader(mixedValue.getFirstValueCondition() ) && valueReader(mixedValue.getSecondValueCondition());
    }

    

    private boolean vOperatorV2(int marks, Operator operator, int value) {
        if (operator == Operator.LESS_THAN) {
            if (marks < value)
                return true;
        } else {
            if (marks > value)
                return true;
        }
        return false;

    }

    private boolean isCompoundSubject(int subjectCode) {

        for (ArrayList<Integer> compoundSubject : compoundSubjects) {
            if (compoundSubject.contains(subjectCode))
                return true;
        }
        return false;
    }

    private int[] getKeysArray(HashMap<Integer, Integer> hashMap) {
        int[] keysArray = new int[hashMap.size()];
        int index = 0;
        for (int key : hashMap.keySet()) {
            keysArray[index] = key;
            index++;
        }
        return keysArray;
    }

    private int[] getValuesArray(HashMap<Integer, Integer> hashMap) {
        int[] valuesArray = new int[hashMap.size()];
        int index = 0;
        for (Integer value : hashMap.values()) {
            valuesArray[index] = value;
            index++;
        }
        return valuesArray;
    }

    public boolean isException(int subjectCode, Exception exception) {

        if (exception.getException().contains(subjectCode))
            return true;

        return false;

    }

    public int getMarks(int subjectCode, HashMap<Integer, SubjectSignificance> subjectList) {

        switch (subjectList.get(subjectCode)) {
            case EVALUATION: {
                return evaluation.get(subjectCode);

            }
            case MAJOR: {

                return major.get(subjectCode);

            }
            case MINOR: {

                return minor.get(subjectCode);

            }
            default: {
                return -1;
            }

        }

    }

    public double calculateAverage(ArrayList<Integer> arrayList) {
        if (arrayList.isEmpty()) {
            return 0.0;
        }

        int sum = 0;
        for (int num : arrayList) {
            sum += num;
        }

        return (double) sum / arrayList.size();
    }

}
