package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.Scene;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Condition_blocks.Compulsory;
import model.Condition_blocks.ConditionBlock;
import model.Condition_blocks.MixedValue;
import model.Condition_blocks.Value;
import model.enums.Operator;
import model.enums.SubjectSignificance;

public class Result {
    // ensure that the class is made to handle only the data of one student at a
    // time
    private static HashMap<Integer, Integer> major = new HashMap<Integer, Integer>();
    private static HashMap<Integer, Integer> minor = new HashMap<Integer, Integer>();
    private static HashMap<Integer, Integer> evaluation = new HashMap<Integer, Integer>();

    private static HashMap<Integer, Integer> termOne = null;
    private static HashMap<Integer, Integer> termTwo = null;
    private static HashMap<Integer, Integer> termThree = null;
    private static ArrayList<ArrayList<Integer>> averageSubjectSignifier = new ArrayList<>();

    // result set containing subject name and subject keys

    // a function which has a student object and a condition object
    // return true for pass and false for fail

    public static void setAverageSubjectSignifier(ArrayList<ArrayList<Integer>> averageSubjectSignifier) {
        Result.averageSubjectSignifier = averageSubjectSignifier;
    }

    // takes the averaged hashmap
    public static boolean hasPassed(Student student, Condition condition,
            HashMap<Integer, SubjectSignificance> subjectList) {

        // if the average subjects value stored in student is null probably term one,two
        // or three has the value

        HashMap<Integer, Integer> inUseHashMap = null;

        if (student.getSubjects() != null) {
            inUseHashMap = student.getSubjects();
        } else if (student.getTermOne() != null) {
            inUseHashMap = new TranscriptString().convertToHashMap(student.getTermOne());
        } else if (student.getTermTwo() != null) {
            inUseHashMap = new TranscriptString().convertToHashMap(student.getTermTwo());

        } else if (student.getTermThree() != null) {
            inUseHashMap = new TranscriptString().convertToHashMap(student.getTermThree());
        }
        int[] keysArray = getKeysArray(inUseHashMap);
        int[] valuesArray = getValuesArray(inUseHashMap);

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

    public static void createResultImageFile(Student student, String title, Condition condition,
            HashMap<Integer, SubjectSignificance> subjectList, HashMap<Integer, String> subjects,
            HashMap<Integer, Float> percentage, double attendance, String saveLocation) {

        // this function is called first as it sets the value of the MAJOR,MINOR,
        // EVALUATION
        boolean hasPassed = hasPassed(student, condition, subjectList);

        // generates image of the result
        ResultImageBuilder rib = new ResultImageBuilder();

        TranscriptString tString = new TranscriptString();

        // to store marks of terms, in case a term is null, blank cells are printed
        // evaluation subjects stored in term one,two,three

        // if is primary then the evaluation subjects will also contain minor subjects
        boolean isPrimary = (Subject.gradeStringToInteger(student.getGrade()) <= 15) ? true : false;

        try {
            termOne = getEvaluationSubjects(tString.convertToHashMap(student.getTermOne()), subjectList, isPrimary);

        } catch (Exception e) {
        }
        try {
            termTwo = getEvaluationSubjects(tString.convertToHashMap(student.getTermTwo()), subjectList, isPrimary);

        } catch (Exception e) {
        }
        try {
            termThree = getEvaluationSubjects(tString.convertToHashMap(student.getTermThree()), subjectList, isPrimary);

        } catch (Exception e) {
        }

        // set only once for a single grade
        rib.setSubjects(subjects);
        addCompoundSubjects(condition);
        rib.setAverageSubjectsSignifier(averageSubjectSignifier);
        // setting average subjects, which is stored in student subjects variable but if
        // it is null
        // it probably means the result being generated for a single term
        rib.setAveragedSubjectsValue(student.getSubjects());
        // set MAJOR AND MINOR subject codes
        rib.setMajorSubCodes(convertKeysToArrayList(major));
        rib.setMinorSubCodes(convertKeysToArrayList(minor));

        // set term values for petable
        rib.setTermOne(getEvaluationSubjects(termOne, subjectList, isPrimary));
        rib.setTermTwo(getEvaluationSubjects(termTwo, subjectList, isPrimary));
        rib.setTermThree(getEvaluationSubjects(termThree, subjectList, isPrimary));

        VBox PETable = rib.createPETable();

        // set term values for stable
        // set values in term one.two,three for subjects
        // also add second language and third
        termOne = tString.convertToHashMap(student.getTermOne());
        setLanguagesTermOne(student.getTermOne());
        termTwo = tString.convertToHashMap(student.getTermTwo());
        setLanguagesTermTwo(student.getTermTwo());
        termThree = tString.convertToHashMap(student.getTermThree());
        setLanguagesTermThree(student.getTermThree());

        rib.setTermOne(termOne);
        rib.setTermTwo(termTwo);
        rib.setTermThree(termThree);

        // STable and attention and division box
        VBox STable = rib.createSubjectTable(isPrimary);
        STable.getChildren()
                .add(rib.createAttendanceAndDivBox(hasPassed, percentage, student.getPin(), (float) attendance));
        // the code for attendance is missing
        VBox passStatusBox = rib.createPassStatusBox(hasPassed, percentage, student.getPin(), (float) attendance);
        PETable.getChildren().add(passStatusBox);

        // // adding a rect over ptable and pass status box

        // Image temp = ResultImageBuilder.captureVBoxImage(PETable);
        // ImageView tempo = new ImageView(temp);
        // StackPane ptableWRect =
        // ResultImageBuilder.addRectangleOverVBox(PETable,temp.getWidth(),temp.getHeight(),10);
        // System.out.println(PETable.heightProperty().doubleValue());
        // PETable = new VBox(ptableWRect);

        // the below code is actually meant to done in ResultImageBuilder

        HBox joiner = new HBox();
        VBox result = new VBox();

        VBox termRow = rib.getTermRow(title, student.getRoll_no(), student.getName(), student.getGrade());
        result.getChildren().add(termRow);
        joiner.getChildren().addAll(PETable, STable);
        result.getChildren().add(joiner);
        // remarks row
        VBox remarksRow = rib.getRemarksBox();
        result.getChildren().add(remarksRow);

        Scene scene = new Scene(result);

        // ResultImageBuilder.captureAndSaveVBoxImage((VBox) scene.getRoot(),
        // saveLocation +"/"+ student.getName() + ".png");

        // an image is obtained and then overlayed
        Image initialImg = ResultImageBuilder.captureVBoxImage((VBox) scene.getRoot());

        // VBox fnl = new VBox();
        // fnl.getChildren().addAll(new ImageView(img));
        // Scene tsc = new Scene(fnl);
        // ResultImageBuilder.captureAndSaveVBoxImage((VBox) tsc.getRoot(), saveLocation
        // +"/"+ student.getName() + ".png");
        Image finImage = removeBackgroundColor(initialImg, findMostPrevalentColor(initialImg));
        ImageView imageView = blendImages(finImage);

        VBox fnl = new VBox();
        fnl.getChildren().addAll(imageView);
        Scene tsc = new Scene(fnl);

        ResultImageBuilder.captureAndSaveVBoxImage((VBox) tsc.getRoot(), saveLocation+"/"+ student.getName() + ".png");
        System.out.println("Result created for pin :"+student.getPin());


        }

        

    public static javafx.scene.paint.Color findMostPrevalentColor(Image image) {
        PixelReader pixelReader = image.getPixelReader();
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        // Count occurrences of each color using a histogram
        Map<Integer, Integer> colorHistogram = new HashMap<>();
        int maxOccurrences = 0;
        int mostPrevalentColor = 0; // Default color (black)

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int color = pixelReader.getArgb(x, y);
                colorHistogram.put(color, colorHistogram.getOrDefault(color, 0) + 1);

                // Update most prevalent color
                if (colorHistogram.get(color) > maxOccurrences) {
                    maxOccurrences = colorHistogram.get(color);
                    mostPrevalentColor = color;
                }
            }
        }

        return javafx.scene.paint.Color.rgb(
                (mostPrevalentColor >> 16) & 0xFF,
                (mostPrevalentColor >> 8) & 0xFF,
                mostPrevalentColor & 0xFF);
    }

    private static Image removeBackgroundColor(Image originalImage, Color backgroundColor) {
        int width = (int) originalImage.getWidth();
        int height = (int) originalImage.getHeight();

        // Create a WritableImage with a transparent background
        WritableImage processedImage = new WritableImage(width, height);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color pixelColor = originalImage.getPixelReader().getColor(x, y);

                // Check if the pixel color is similar to the background color
                if (isSimilarColor(pixelColor, backgroundColor)) {
                    processedImage.getPixelWriter().setColor(x, y, Color.TRANSPARENT);
                } else {
                    processedImage.getPixelWriter().setColor(x, y, pixelColor);
                }
            }
        }

        return processedImage;
    }

    private static boolean isSimilarColor(Color color1, Color color2) {
        // You may need to adjust the threshold based on your specific use case
        double colorDistanceThreshold = 0.1;

        double distance = Math.sqrt(
                Math.pow(color1.getRed() - color2.getRed(), 2) +
                        Math.pow(color1.getGreen() - color2.getGreen(), 2) +
                        Math.pow(color1.getBlue() - color2.getBlue(), 2));

        return distance < colorDistanceThreshold;
    }

    public static ImageView blendImages(Image overlayImage) {
        // Create ImageViews for the images
        ImageView baseImageView = new ImageView("/model/resources/result bg w title.png");
        ImageView overlayImageView = new ImageView(overlayImage);
        overlayImageView.setFitHeight(overlayImage.getHeight() * 1.5);
        overlayImageView.setFitWidth(overlayImage.getWidth() * 1.5);

        // Create a Blend effect
        Blend blend = new Blend();
        blend.setMode(BlendMode.MULTIPLY);

        // Set the blend effect to the overlay image
        overlayImageView.setEffect(blend);

        // Create a StackPane to blend images
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(baseImageView, overlayImageView);

        // Return the blended ImageView
        return new ImageView(stackPane.snapshot(null, null));
    }

    private static void setLanguagesTermOne(String str) {

        if (str == null)
            return;
        int[] sl = new TranscriptString().getSecondLanguage(str);
        int[] tl = new TranscriptString().getThirdLanguage(str);
        if (sl != null) {
            termOne.put(sl[0], sl[1]);
        }

        if (tl != null) {
            termOne.put(tl[0], tl[1]);
        }
    }

    private static void setLanguagesTermTwo(String str) {
        if (str == null)
            return;

        int[] sl = new TranscriptString().getSecondLanguage(str);
        int[] tl = new TranscriptString().getThirdLanguage(str);
        if (sl != null) {
            termTwo.put(sl[0], sl[1]);
        }

        if (tl != null) {
            termTwo.put(tl[0], tl[1]);
        }
    }

    private static void setLanguagesTermThree(String str) {
        if (str == null)
            return;

        int[] sl = new TranscriptString().getSecondLanguage(str);
        int[] tl = new TranscriptString().getThirdLanguage(str);
        if (sl != null) {
            termThree.put(sl[0], sl[1]);
        }

        if (tl != null) {
            termThree.put(tl[0], tl[1]);
        }
    }

    private static ArrayList<Integer> convertKeysToArrayList(HashMap<Integer, Integer> hashMap) {
        // Create an ArrayList from the key set of the HashMap
        return new ArrayList<>(hashMap.keySet());
    }

    // needs to be called after initialisation
    private static void addCompoundSubjects(Condition condition) {
        for (int i = 0; i < condition.getCondition().size(); i++) {

            ConditionBlock block = condition.getCondition().get(i);
            Class<?> clazz = block.getClass();
            if (clazz.getSimpleName() == "Compulsory") {
                Compulsory compulsory = (Compulsory) block;
                if (compulsory.getUnaryOperator() != Operator.NULL)
                    continue;
                // if the operator is null it usually contains multiple subjects.

                averageSubjectSignifier.add(compulsory.getSubjects());

            }

        }
    }

    private static HashMap<Integer, Integer> getEvaluationSubjects(HashMap<Integer, Integer> subjects,
            HashMap<Integer, SubjectSignificance> subjectList, boolean isPrimary) {

        if (subjects == null)
            return null;
        int[] keysArray = getKeysArray(subjects);
        int[] valuesArray = getValuesArray(subjects);

        HashMap<Integer, Integer> evaluationSubjects = new HashMap<>();

        for (int i = 0; i < keysArray.length; i++) {
            int key = keysArray[i];

            SubjectSignificance significance = subjectList.get(key);

            switch (significance) {

                case EVALUATION: {
                    evaluationSubjects.put(key, valuesArray[i]);
                    break;
                }
                default: {
                    break;
                }
            }

        }

        if (isPrimary) {
            for (int i = 0; i < keysArray.length; i++) {
                int key = keysArray[i];

                SubjectSignificance significance = subjectList.get(key);

                switch (significance) {

                    case MINOR: {
                        evaluationSubjects.put(key, valuesArray[i]);
                        break;
                    }
                    default: {
                        break;
                    }
                }

            }

        }

        return evaluationSubjects;
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
