package model;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class Result {
    // ensure that the class is made to handle only the data of one student at a
    // time

    private ResultSet subjectKeys;
    // result set containing subject name and subject keys

    public Result(ResultSet subjectKeys) {
        this.subjectKeys = subjectKeys;
    }

    // String[] array =
    // {"english_language","english_literature","spelling_dictation","general_knowledge","second_language",
    // "mathematics","science","value_education","social_studies","computer_science","third_language","reading",
    // "recitation","art","physics","chemistry","biology","computer_application","commercial_application","physical_education",
    // "accountancy","commerce","economics","sociology","history","geography","pol_science","supw","business_studies",
    // "conduct","physical_training","singing","order_n_neatness","handwriting","attendance","co_curricular_activities",
    // "spoken_language","personal_grooming","hobby","library_reading","supw","cooperation"
    // };

    public boolean calculateResultPrimary(Student student) {
        HashMap<Integer, String> subjects = student.getSubjects();

        double English = Math.round((Integer.parseInt(subjects.get(01)) + Integer.parseInt(subjects.get(02))) / 2.0);
        if (English < 40) {
            System.out.println("Failed in English :" + English);
            return false;// failing in english is fail

        }
        int failCount = 0; // failing in four or more is fail

        for (Map.Entry<Integer, String> entry : subjects.entrySet()) {
            int key = entry.getKey();
            String value = entry.getValue();

            // add third language keys in the database and in this if condition
            if (key != 11 && Integer.parseInt(value) < 25) {
                System.out.println("Less than 25% in " + key + " :" + value);
                return false; // a student should score minimum 25% in all subjects
            }
            if (key != 11 && Integer.parseInt(value) < 40)
                failCount++;

            if (failCount == 4) {
                System.out.println("Failed in four subjects");
                return false;
            }

        }

        return true;

    }


}
