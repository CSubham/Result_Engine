package model;

import java.util.HashMap;

public class TranscriptString {
    // reserved keys : "n","rn","s",

    public TranscriptString() {
    }

    // generate transcript string

    public String generate(HashMap<Integer, String> subjects, String name, int roll_no) {
        String transcriptString = "";

        transcriptString += "n:" + name + ";";
        transcriptString += "rn:" + roll_no + ";";

        int[] keysArray = getKeysArray(subjects);
        String[] valuesArray = getValuesArray(subjects);

        transcriptString += "s:";
        for (int i = 0; i < keysArray.length; i++) {
            transcriptString += keysArray[i] + "-" + valuesArray[i];
            if (i != keysArray.length - 1)
                transcriptString += ",";
        }
        transcriptString += ";";

        return transcriptString;

    }

    private static int[] getKeysArray(HashMap<Integer, String> hashMap) {
        int[] keysArray = new int[hashMap.size()];
        int index = 0;
        for (int key : hashMap.keySet()) {
            keysArray[index] = key;
            index++;
        }
        return keysArray;
    }

    private static String[] getValuesArray(HashMap<Integer, String> hashMap) {
        String[] valuesArray = new String[hashMap.size()];
        int index = 0;
        for (String value : hashMap.values()) {
            valuesArray[index] = value;
            index++;
        }
        return valuesArray;
    }

    // convert transcript into hashmap

    public HashMap<Integer, String> convertToHashMap(String str) {
        HashMap<Integer, String> studentHM = new HashMap<>();

        String[] primaryStrings = str.split(";");

        // not required
        // String name = primaryStrings[0].substring(2);
        // String roll_no = primaryStrings[1].substring(3);

        String[] marks = (primaryStrings[2].substring(2)).split(",");

        for (int i = 0; i < marks.length; i++) {

            String[] flag = marks[i].split("-");
            studentHM.put(Integer.parseInt(flag[0]), flag[1]);

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

}
