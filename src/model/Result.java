package model;

import java.sql.ResultSet;
import java.util.HashMap;

import model.Condition_blocks.Compulsory;
import model.Condition_blocks.ConditionBlock;
import model.Condition_blocks.MixedValue;
import model.Condition_blocks.Value;
import model.enums.Operator;
import model.enums.SubjectSignificance;

public class Result {
    // ensure that the class is made to handle only the data of one student at a
    // time

    private ResultSet subjectKeys;
    // result set containing subject name and subject keys

    public Result(ResultSet subjectKeys) {
        this.subjectKeys = subjectKeys;
    }

    // a function which has a student object and a condition object
    // return true for pass and false for fail

    public boolean hasPassed(Student student, Condition condition, HashMap<Integer, SubjectSignificance> subjectList) {

        HashMap<Integer, Integer> major = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> minor = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> evaluation = new HashMap<Integer, Integer>();

        int[] keysArray = getKeysArray(student.getSubjects());
        int[] valuesArray = getValuesArray(student.getSubjects());

        // putting subjects into hashmap wrt significance

        for (int i = 0; i < keysArray.length; i++) {
            int key = keysArray[i];

            SubjectSignificance significance = subjectList.get(key);

            switch (significance) {
                case MAJOR: {
                    major.put(key, valuesArray[i]);
                    break;
                }
                case MINOR: {
                    minor.put(key, valuesArray[i]);
                    break;
                }
                case EVALUATION: {
                    evaluation.put(key, valuesArray[i]);
                    break;
                }
            }

        }

        // second and third language subjects

        int[] tStringSl = (student.getSecondLanguage() != null) ? student.getSecondLanguage() : null;
        int[] tStringTl = (student.getThirdLanguage() != null) ? student.getThirdLanguage() : null;

        if (tStringSl != null) {

            switch (subjectList.get(tStringSl[0])) {
                case MAJOR: {
                    major.put(tStringSl[0], tStringSl[1]);
                    break;
                }
                case MINOR: {
                    minor.put(tStringSl[0], tStringSl[1]);
                    break;
                }
                case EVALUATION: {
                    evaluation.put(tStringSl[0], tStringSl[1]);
                    break;
                }

            }
        }

        if (tStringTl != null) {

            switch (subjectList.get(tStringTl[0])) {
                case MAJOR: {
                    major.put(tStringTl[0], tStringTl[1]);
                    break;
                }
                case MINOR: {
                    minor.put(tStringTl[0], tStringTl[1]);
                    break;
                }
                case EVALUATION: {
                    evaluation.put(tStringTl[0], tStringTl[1]);
                    break;
                }

            }

        }

        ConditionReader conditionReader = new ConditionReader(major, minor, evaluation);
        conditionReader.addCompoundSubjects(condition);

        // how many passing conditions the student has failed
        int parameterFailureCount = 0;

        for (int i = 0; i < condition.getCondition().size(); i++) {
            ConditionBlock block = condition.getCondition().get(i);
            Class<?> clazz = block.getClass();

            switch (clazz.getSimpleName()) {
                case "Compulsory": {
                    if (((Compulsory) block).getUnaryOperator() == Operator.NULL)
                        continue;
                    if (conditionReader.compulsoryReader((Compulsory) block, subjectList))
                        parameterFailureCount++;

                    break;
                }
                case "MixedValue": {
                    if (conditionReader.mixedValueReader((MixedValue) block))
                        parameterFailureCount++;

                    break;
                }
                
                case "Value": {

                    if (conditionReader.valueReader((Value) block))
                        parameterFailureCount++;

                    break;
                }
            }

        }

        if (parameterFailureCount > 0) {
            return false;
        } else {
            return true;
        }

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

}
