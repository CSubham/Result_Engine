package model;

import java.sql.ResultSet;

public class Master {
    private static Databridge databridge = new Databridge();
    private static ResultSet subjects = null;

    static {
        databridge.connectDatabase();
        subjects = databridge.fetchQueryData("select * from subjects");
    }

    public static void main(String[] args) throws Exception {
        //databridge.executeQuery("create table students(pin int primary key unique ,class varchar(3),data varchar(250));");
        //databridge.executeQuery("create table subjects(subject_name varchar(30),subject_code int primary key unique );");
        //databridge.executeQuery("create table term_data( pin int primary key unique,term_one varchar(250), term_two varchar(250), term_three varchar(250) );");
        // databridge.executeQuery("drop table subjects");





        // String[] array = {"english_language","english_literature","spelling_dictation","general_knowledge","second_language",
        //     "mathematics","science","value_education","social_studies","computer_science","third_language","reading",
        //     "recitation","art","physics","chemistry","biology","computer_application","commercial_application","physical_education",
        //     "accountancy","commerce","economics","sociology","history","geography","pol_science","supw","business_studies",
        //     "conduct","physical_training","singing","order_n_neatness","handwriting","attendance","co_curricular_activities",
        //     "spoken_language","personal_grooming","hobby","library_reading","supw","cooperation"
        // };
        


    }
}
