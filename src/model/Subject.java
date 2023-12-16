package model;


import java.util.HashMap;

import model.enums.SubjectSignificance;

public class Subject {

    // result set containing subject name and subject keys

    public Subject() {
    }
    

    // code for grade assignment 

    // the hashmap here will contain "subject_code-minor;subject_code2-major;"
    public String generateSubjectList(HashMap<Integer, SubjectSignificance> subjects){
        int[] keysArray = getKeysArray(subjects);
        SubjectSignificance[] valuesArray = getValuesArray(subjects);
        String subjectList = "";


        for (int i = 0; i < keysArray.length; i++) {
            subjectList += keysArray[i] + "-" + valuesArray[i];
            if (i != keysArray.length - 1)
                subjectList += ",";
        }


        return subjectList;

    }

    public HashMap<Integer,SubjectSignificance> subjectListToHashMap(String subjectList){
        String[] subjectListElements = subjectList.split(",");
        HashMap<Integer,SubjectSignificance> subjectListMap = new HashMap<Integer, SubjectSignificance>();

        for(int i =0 ; i< subjectListElements.length; i++){
            String[] subjectNSignificance = subjectListElements[i].split("-") ;

            SubjectSignificance significance = null;
            switch(subjectNSignificance[1]){
                case "major":{
                    significance = SubjectSignificance.MAJOR;
                    break;
                }
                case "minor":{
                    significance = SubjectSignificance.MINOR;
                    break;
                }
                case "evaluation":{
                    significance = SubjectSignificance.EVALUATION;
                    break;
                }
            }
            subjectListMap.put(Integer.parseInt(subjectNSignificance[0]),significance);
            significance = null;

        }

        return subjectListMap;
    }



    private static int[] getKeysArray(HashMap<Integer, SubjectSignificance> hashMap) {
        int[] keysArray = new int[hashMap.size()];
        int index = 0;
        for (int key : hashMap.keySet()) {
            keysArray[index] = key;
            index++;
        }
        return keysArray;
    }

    private static SubjectSignificance[] getValuesArray(HashMap<Integer, SubjectSignificance> hashMap) {
        SubjectSignificance[] valuesArray = new SubjectSignificance[hashMap.size()];
        int index = 0;
        for (SubjectSignificance value : hashMap.values()) {
            valuesArray[index] = value;
            index++;
        }
        return valuesArray;
    }
}
