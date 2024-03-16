package model;

import java.util.HashMap;

import model.enums.SubjectSignificance;

public class AdvancedTermAverager {

    public AdvancedTermAverager() {
    }

    // the terms are expected to be passed in correct order
    // note : add funcitonality for a student being able to drop or pick a subject
    // during the terms

    // subject list here is grade subject list
    public String averageofTwo(HashMap<Integer, SubjectSignificance> subjectList, String tString, String tString2,
            Float k, Float l, Float m, Float n) {
        TranscriptString transcriptObj = new TranscriptString();

        // tString
        int[] tStringSl = null;
        int[] tStringTl = null;

        HashMap<Integer, Integer> subjects = transcriptObj.convertToHashMap(tString);
        try {
            tStringSl = transcriptObj.getSecondLanguage(tString);
        } catch (Exception e) {
        }

        try {
            tStringTl = transcriptObj.getSecondLanguage(tString);
        } catch (Exception e) {
        }

        // tString2

        int[] tStringSl2 = null;
        int[] tStringTl2 = null;

        HashMap<Integer, Integer> subjects2 = transcriptObj.convertToHashMap(tString);
        try {
            tStringSl2 = transcriptObj.getSecondLanguage(tString2);
        } catch (Exception e) {
        }

        try {
            tStringTl2 = transcriptObj.getSecondLanguage(tString2);
        } catch (Exception e) {
        }

        // Average

        // we wil use the keys of second TString assuming its for the later term

        int[] keysArray = getKeysArray(subjects2);

        HashMap<Integer, Integer> average = new HashMap<Integer, Integer>();
        for (int i = 0; i < keysArray.length; i++) {

            int key = keysArray[i];

            if (subjectList.get(key) != SubjectSignificance.EVALUATION) {

                Integer value = 
                         Math.round((((subjects.get(key) * k)
                                + (subjects2.get(key) * l)) * m)
                                * n);

                average.put(key, value);

            } else {
                average.put(key, subjects2.get(key));

            }

        }

        String averageTString = transcriptObj.generate(average, transcriptObj.getName(tString2),
                transcriptObj.getRollNo(tString2));

        if (tStringSl2 != null) {

            Float value = (((tStringSl[1] * k) + (tStringSl2[1] * l)) * m)
                    * n;

            averageTString = transcriptObj.addLanguages(averageTString, tStringSl2[0], Math.round(value), 0, 0);

        }
        if (tStringTl2 != null) {
            Float value = (((tStringTl[1] * k) + (tStringTl2[1] * l)) * m)
                    * n;

            averageTString = transcriptObj.addLanguages(averageTString, 0, 0, tStringTl2[0], Math.round(value));
        }

        return averageTString;

    }

    public String averageofThree(HashMap<Integer, SubjectSignificance> subjectList, String tString, String tString2,
            String tString3,
            Float k, Float l, Float m, Float n, Float o) {

        TranscriptString transcriptObj = new TranscriptString();

        // tString

        int[] tStringSl = null;
        int[] tStringTl = null;

        HashMap<Integer, Integer> subjects = transcriptObj.convertToHashMap(tString);
        try {
            tStringSl = transcriptObj.getSecondLanguage(tString);
        } catch (Exception e) {
        }

        try {
            tStringTl = transcriptObj.getSecondLanguage(tString);
        } catch (Exception e) {
        }

        // tString2

        int[] tStringSl2 = null;
        int[] tStringTl2 = null;

        HashMap<Integer, Integer> subjects2 = transcriptObj.convertToHashMap(tString);
        try {
            tStringSl2 = transcriptObj.getSecondLanguage(tString2);
        } catch (Exception e) {
        }

        try {
            tStringTl2 = transcriptObj.getSecondLanguage(tString2);
        } catch (Exception e) {
        }

        // tString3

        int[] tStringSl3 = null;
        int[] tStringTl3 = null;

        HashMap<Integer, Integer> subjects3 = transcriptObj.convertToHashMap(tString);
        try {
            tStringSl3 = transcriptObj.getSecondLanguage(tString3);
        } catch (Exception e) {
        }

        try {
            tStringTl3 = transcriptObj.getSecondLanguage(tString3);
        } catch (Exception e) {
        }

        // Average

        int[] keysArray = getKeysArray(subjects2);

        HashMap<Integer, Integer> average = new HashMap<Integer, Integer>();
        for (int i = 0; i < keysArray.length; i++) {

            int key = keysArray[i];

            if (subjectList.get(key) != SubjectSignificance.EVALUATION) {

                int value =  Math.round(
                        +(((subjects.get(key) * k) + (subjects2.get(key) * l)
                                + ((subjects3.get(key)) * m))
                                * n) * o);

                average.put(key, value);

            } else {
                average.put(key, subjects3.get(key));

            }

        }


        String averageTString = transcriptObj.generate(average, transcriptObj.getName(tString3),
                transcriptObj.getRollNo(tString3));

        if (tStringSl3 != null) {

            Float value = (((tStringSl[1] * k) + (tStringSl2[1] * l) + (tStringSl3[1] * m))
                    * n) * o;

            averageTString = transcriptObj.addLanguages(averageTString, tStringSl2[0], Math.round(value), 0, 0);

        }
        if (tStringTl3 != null) {
            Float value = (((tStringTl[1] * k) + (tStringTl2[1] * l) + (tStringTl3[1] * m))
                    * n) * o;

            averageTString = transcriptObj.addLanguages(averageTString, 0, 0, tStringTl2[0], Math.round(value));
        }

        return averageTString;

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

}
