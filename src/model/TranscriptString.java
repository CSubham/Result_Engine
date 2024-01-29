package model;

import java.util.HashMap;

public class TranscriptString {
    // reserved keys : "n","rn","s","sl","tl"

    public TranscriptString() {
    }

    // generate transcript string

    // -1 represents empty
    public String generate(HashMap<Integer, Integer> subjects, String name, int roll_no) {
        String transcriptString = "";

        transcriptString += "n:" + name + ";";
        transcriptString += "rn:" + roll_no + ";";

        int[] keysArray = getKeysArray(subjects);
        int[] valuesArray = getValuesArray(subjects);

        transcriptString += "s:";
        for (int i = 0; i < keysArray.length; i++) {
            int key = keysArray[i];
            String value = (valuesArray[i] == -1) ? "#" : "" + valuesArray[i];
            transcriptString += key + "-" + value;
            if (i != keysArray.length - 1)
                transcriptString += ",";
        }
        transcriptString += ";";

        return transcriptString;

    }

    // add second and third language into transcirpt string
    public String addLanguages(String transcriptString, int secondLanguage, int secondLanguageMarks, int thirdLanguage,
            int thirdLanguageMarks) {

        if (secondLanguage > 0) {
            String marks = (secondLanguageMarks < 0) ? "#" : "" + secondLanguageMarks;
            transcriptString += "sl:" + secondLanguage + "-" + marks + ";";
        }
        if (thirdLanguage > 0) {
            String marks = (thirdLanguage < 0) ? "#" : "" + thirdLanguageMarks;
            transcriptString += "sl:" + thirdLanguage + "-" + marks + ";";

        }

        return transcriptString;

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

    // convert transcript into hashmap

    public HashMap<Integer, Integer> convertToHashMap(String str) {
        if  (str == null)return null;
        HashMap<Integer, Integer> studentHM = new HashMap<Integer, Integer>();

        String[] primaryStrings = str.split(";");

        String[] marks = (primaryStrings[2].substring(2)).split(",");

        for (int i = 0; i < marks.length; i++) {

            String[] flag = marks[i].split("-");
            int subjectCode = Integer.parseInt(flag[0]);
            int subjectMarks = (flag[1] == "#") ? -1 : Integer.parseInt(flag[1]);
            studentHM.put(subjectCode, subjectMarks);

        }

        

        return studentHM;

    }

    public String getName(String str) {
        String[] primaryStrings = str.split(";");

        String name = primaryStrings[0].substring(2);
        return name;

    }

    public int getRollNo(String str) {
        String[] primaryStrings = str.split(";");

        String roll_no = primaryStrings[1].substring(3);

        return Integer.parseInt(roll_no);

    }
    // these functions return an array where first subject code and then marks is
    // contained in them

    public int[] getSecondLanguage(String str) {
        String[] primaryStrings = str.split(";");
        int[] subjectNMark = new int[2];

        try {

            if (primaryStrings[3].substring(0, 2) == "sl") {

                String[] utilty = (primaryStrings[3].substring(3)).split("-");
                subjectNMark[0] = Integer.parseInt(utilty[0]);
                subjectNMark[1] = Integer.parseInt(utilty[1]);
            } else if (primaryStrings[4].substring(0, 2) == "sl") {
                String[] utilty = (primaryStrings[4].substring(3)).split("-");
                subjectNMark[0] = Integer.parseInt(utilty[0]);
                subjectNMark[1] = Integer.parseInt(utilty[1]);

            } else {
                throw new Exception("No second language found");
            }

        } catch (Exception e) {

            
            subjectNMark = null;

        }

        return subjectNMark;

    }

    public int[] getThirdLanguage(String str) {
        String[] primaryStrings = str.split(";");
        int[] subjectNMark = new int[2];

        try {

            if (primaryStrings[3].substring(0, 2) == "tl") {

                String[] utilty = (primaryStrings[3].substring(3)).split("-");
                subjectNMark[0] = Integer.parseInt(utilty[0]);
                subjectNMark[1] = Integer.parseInt(utilty[1]);
            } else if (primaryStrings[4].substring(0, 2) == "tl") {
                String[] utilty = (primaryStrings[4].substring(3)).split("-");
                subjectNMark[0] = Integer.parseInt(utilty[0]);
                subjectNMark[1] = Integer.parseInt(utilty[1]);

            } else {
                throw new Exception("No third language found");
            }

        } catch (Exception e) {
         
            subjectNMark = null;

        }

        return subjectNMark;

    }

}
