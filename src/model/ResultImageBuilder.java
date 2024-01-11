package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class ResultImageBuilder {

    // public static void main(String[] args) {
    // launch(args);
    // }

    // @Override
    // public void start(Stage stage) throws Exception {
    // HashMap<Integer, SubjectSignificance> gradeSubjectList = new HashMap<>();
    // gradeSubjectList.put(01, SubjectSignificance.EVALUATION);
    // gradeSubjectList.put(02, SubjectSignificance.EVALUATION);

    // HashMap<Integer, String> subjects = new HashMap<>();
    // subjects.put(01, "SomeSubject");
    // subjects.put(02, "Some");

    // HashMap<Integer, Integer> marks = new HashMap<>();
    // marks.put(01, 45);
    // marks.put(02, 45);

    // Scene scene = new Scene(createPETable(marks, null, null, subjects));
    // captureAndSaveVBoxImage((VBox) scene.getRoot(), "image.png");

    // stage.setScene(scene);
    // stage.show();
    // stage.setResizable(true);

    // }

    /* prior function call variables */

    // the variables here need to be set everytime for create subject table or
    // create personal evaluation table
    // they are just value holders and need respective data

    // term keys and term marks holder for PE AND ST values
    private HashMap<Integer, Integer> termOne = null;
    private HashMap<Integer, Integer> termTwo = null;
    private HashMap<Integer, Integer> termThree = null;

    // keys and their respective subject names
    private HashMap<Integer, String> subjects = null;

    // a list of subjects which are compound, they can be major or minor(Subject
    // codes)
    private ArrayList<ArrayList<Integer>> averageSubjectsSignifier = null;

    // this hashmap stores the value as per advanced term averager, if it is null it
    // probably means that the result file is being generated for only one term
    private HashMap<Integer, Integer> averagedSubjectsValue = null;

    // major, minor subject codes
    private ArrayList<Integer> majorSubCodes = null;
    private ArrayList<Integer> minorSubCodes = null;

    // This arraylist holds the data for ST table regarding which subjects have
    // already been added to the table
    // it is necessary as multiple subject codes corresponding to same compund
    // subject might get added as separate subjects in the table

    private ArrayList<Integer> addedSubjectCodes = new ArrayList<>();

    private String remarksRow = "/model/ResultImageFxmls/remarks.fxml";
    private String titleRow = "/model/ResultImageFxmls/term.fxml";

    /* PETable variables */

    private String displayRowPE = "/model/ResultImageFxmls/personal_evaluation.fxml";

    private Font subjectFontPE = new Font("Cantarell", 30);
    private Font marksFontPE = new Font("Cantarell", 30);

    private int cellStrokePE = 1;

    // length of rectangle strings are displayed within
    private int subjectNameRectHeightPE = -1;
    private int subjectNameRectLenPE = 318;

    private int marksRectHeightPE = -1;
    private int marksRectLenPE = 89;

    // label lengths

    // note : if the value is -1 the label is centered as its size depends on the
    // text it is holding, but if the size
    // is equals to marks rect length the text in it will be left aligned

    private int subjectLabelLengthPE = subjectNameRectLenPE;
    private int marksLabelLengthPE = -1;

    /* STable variables */

    private String displayRowST = "/model/ResultImageFxmls/subjects.fxml";

    private Font subjectFontST = new Font("Cantarell", 30);
    private Font marksFontST = new Font("Cantarell", 30);

    private int cellStrokeST = 1;

    // length of rectangle strings are displayed within
    private int subjectNameRectHeightST = -1;
    private int subjectNameRectLenST = 318;

    private int marksRectHeightST = -1;
    private int marksRectLenST = 89;

    // label lengths

    private int subjectLabelLengthST = subjectNameRectLenPE;
    private int marksLabelLengthST = -1;

    // avg column dimensions

    private int avgCellRectLength = 90;
    private int avgCellRectHeight = -1;

    // grade on average dimensions
    private int govCellRectLength = 92;
    private int govCellRectHeight = -1;

    // Major, Minor display row
    private int displayCellRectLength = 772;
    private int displayCellRectHeight = -1;
    private Font displayCellFont = new Font("Cantarell", 30);
    private String majorString = "MAJOR";
    private String minorString = "MINOR";
    private int displayCellStroke = 4;
    private int displayCellLabelLength = displayCellRectLength;

    private HBox createPERow(String value, int marksOne, int marksTwo, int marksThree) {

        HBox row = new HBox();

        // subject name

        // no need to pad the string as by default the texts in the labels will be left
        // aligned in case label length is provided, but if we do not provide label
        // length it will be centered in the
        // cell so we only need to add leading zero

        StackPane cell = createCell(value,
                subjectFontPE,
                cellStrokePE,
                subjectNameRectHeightPE,
                subjectNameRectLenPE,
                subjectLabelLengthPE);
        addChild(row, cell);

        // term one marks
        if (marksOne != -1) {
            StackPane termOneCell = createCell(addLeadingZero(marksOne),
                    marksFontPE,
                    cellStrokePE,
                    marksRectHeightPE,
                    marksRectLenPE,
                    marksLabelLengthPE);

            addChild(row, termOneCell);

        } else {

            StackPane termOneCell = emptyMarksCellST();
            addChild(row, termOneCell);

        }

        // term two marks

        if (marksTwo != -1) {
            StackPane termTwoCell = createCell(addLeadingZero(marksTwo),
                    marksFontPE,
                    cellStrokePE,
                    marksRectHeightPE,
                    marksRectLenPE,
                    marksLabelLengthPE);

            addChild(row, termTwoCell);

        } else {

            StackPane termTwoCell = emptyMarksCellST();
            addChild(row, termTwoCell);

        }

        // term three cell

        if (marksThree != -1) {
            StackPane termThreeCell = createCell(addLeadingZero(marksThree),
                    marksFontPE,
                    cellStrokePE,
                    marksRectHeightPE,
                    marksRectLenPE,
                    marksLabelLengthPE);

            addChild(row, termThreeCell);

        } else {

            StackPane termThreeCell = emptyMarksCellST();
            addChild(row, termThreeCell);

        }

        return row;

    }

    private HBox createSTRow(String value, int marksOne, int marksTwo, int marksThree) {

        HBox row = new HBox();

        // subject name

        // no need to pad the string as by default the texts in the labels will be left
        // aligned in case label length is provided, but if we do not provide label
        // length it will be centered in the
        // cell so we only need to add leading zero

        StackPane cell = createCell(value,
                subjectFontST,
                cellStrokeST,
                subjectNameRectHeightST,
                subjectNameRectLenST,
                subjectLabelLengthST);
        addChild(row, cell);

        // term one marks
        if (marksOne != -1) {
            StackPane termOneCell = createCell(addLeadingZero(marksOne),
                    marksFontST,
                    cellStrokeST,
                    marksRectHeightST,
                    marksRectLenST,
                    marksLabelLengthST);

            addChild(row, termOneCell);

        } else {

            StackPane termOneCell = emptyMarksCellPE();
            addChild(row, termOneCell);

        }

        // term two marks

        if (marksTwo != -1) {
            StackPane termTwoCell = createCell(addLeadingZero(marksTwo),
                    marksFontST,
                    cellStrokeST,
                    marksRectHeightST,
                    marksRectLenST,
                    marksLabelLengthST);

            addChild(row, termTwoCell);

        } else {

            StackPane termTwoCell = emptyMarksCellPE();
            addChild(row, termTwoCell);

        }

        // term three cell

        if (marksThree != -1) {
            StackPane termThreeCell = createCell(addLeadingZero(marksThree),
                    marksFontST,
                    cellStrokeST,
                    marksRectHeightST,
                    marksRectLenST,
                    marksLabelLengthST);

            addChild(row, termThreeCell);

        } else {

            StackPane termThreeCell = emptyMarksCellPE();
            addChild(row, termThreeCell);

        }

        return row;

    }

    private StackPane emptyMarksCellPE() {
        // the functions addLeadingZero returns an empty string for -1
        return createCell(addLeadingZero(-1),
                marksFontPE,
                cellStrokePE,
                marksRectHeightPE,
                marksRectLenPE,
                marksLabelLengthPE);
    }

    public VBox getRemarksBox() {

        HBox row = getDisplayRow(remarksRow);
        VBox rectRow = new VBox();

        rectRow.getChildren().add(row);

        VBox finalRemarks = new VBox();
        finalRemarks.getChildren().add(addRectangleOverVBox(rectRow, (double) 1358, (double) 250, (double) 3));

        return finalRemarks;

    }

    private StackPane emptyMarksCellST() {
        // the functions addLeadingZero returns an empty string for -1
        return createCell(addLeadingZero(-1),
                marksFontST,
                cellStrokeST,
                marksRectHeightST,
                marksRectLenST,
                marksLabelLengthST);
    }

    public VBox getTermRow(String title, int roll, String name, String grade) {

        Font titleFont = new Font("Arial", 50);

        cellLabelAlignment = TextAlignment.CENTER;
        labelAlignment = Pos.CENTER;
        StackPane titleCell = createCell(title,
                titleFont,
                0,
                100,
                1300,
                1300);

        cellLabelAlignment = TextAlignment.LEFT;
        labelAlignment = Pos.CENTER_LEFT;
        Font subFont = new Font("Arial", 30);
        StackPane rollGradeCell = createCell("ROLL :" + roll + "   " + "CLASS :" + grade.toUpperCase(),
                subFont,
                0,
                100,
                1300,
                1300);

        StackPane nameCell = createCell("NAME :" + name,
                subFont,
                0,
                100,
                1300,
                1300);

        HBox termRow = new HBox();
        termRow.getChildren().add(titleCell);

        VBox joiner = new VBox();
        joiner.getChildren().addAll(rollGradeCell, nameCell);

        VBox finalRow = new VBox();
        finalRow.getChildren().addAll(termRow, joiner);

        VBox rectRow = new VBox();
        rectRow.getChildren().add(addRectangleOverVBox(finalRow, (double) 1360, (double) 300, (double) 4));
        return rectRow;

    }

    private HBox getDisplayRow(String path) {

        HBox displayRow = new HBox();
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(path));
            Parent loadedContent = loader.load();

            // Add the loaded content to the "table" VBox
            displayRow.getChildren().add(loadedContent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return displayRow;

    }

    public VBox createPassStatusBox(boolean hasPassed, HashMap<Integer, Float> percentage, int pin, float attendance) {

        // passed or fail
        String result = "RESULT : " + ((hasPassed == true) ? "PASSED" : "FAILED");

        Font font = new Font("Arial", 30);
        StackPane resultLabel = createCell(result, font, 0, subjectNameRectHeightPE, -1, -1);

        // position
        int rank = -1;
        if (hasPassed) {
            rank = findPosition(percentage, pin);

        }

        String position = "POSITION : " + ((rank == -1) ? "-- " : rank) + "/ " + percentage.keySet().size();
        StackPane positionLabel = createCell(position, font, 0, subjectNameRectHeightPE, -1, -1);

        // percentage
        String percentageOfMarks = "PERCENTAGE OF MARKS : " + percentage.get(pin) + "%";
        StackPane percentageLabel = createCell(percentageOfMarks, font, 0, subjectNameRectHeightPE, -1, -1);

        // divison
        String divison = "DIVISON : ";
        if (hasPassed) {
            divison += getDiv(percentage.get(pin));
        }
        StackPane divisonLabel = createCell(divison, font, 0, subjectNameRectHeightPE, -1, -1);

        // attendance
        String present = "ATTENDANCE : " + attendance + "%";
        StackPane presentLabel = createCell(present, font, 0, subjectNameRectHeightPE, -1, -1);

        HBox resultRow = new HBox();
        addChild(resultRow, resultLabel);
        HBox positionRow = new HBox();
        addChild(positionRow, positionLabel);
        HBox percentageRow = new HBox();
        addChild(percentageRow, percentageLabel);
        HBox divisonRow = new HBox();
        addChild(divisonRow, divisonLabel);
        HBox presentRow = new HBox();
        addChild(presentRow, presentLabel);

        VBox rowHolder = new VBox();
        addChild(rowHolder, resultRow);
        addChild(rowHolder, positionRow);
        addChild(rowHolder, percentageRow);
        addChild(rowHolder, divisonRow);
        addChild(rowHolder, presentRow);

        StackPane statusBox = addRectangleOverVBox(rowHolder, (double) 585, (double) 200, 3);
        VBox rectStatusBox = new VBox();
        rectStatusBox.getChildren().add(statusBox);
        return rectStatusBox;

    }

    

    private static String getDiv(float percentage) {
        if (percentage >= 81 && percentage <= 100) {
            return "DISTINCTION DIV";
        } else if (percentage >= 60 && percentage <= 80) {
            return "1st DIV";
        } else if (percentage >= 50 && percentage <= 59) {
            return "2nd DIV";
        } else if (percentage >= 45 && percentage <= 49) {
            return "3rd DIV";
        } else if (percentage >= 40 && percentage <= 44) {
            return "PASS DIV";
        } else {
            return "----";
        }

    }

    public static StackPane addRectangleOverVBox(VBox vBox, double length, double height, double stroke) {
        StackPane stackPane = new StackPane();

        // Create a rectangle with the specified width, height, and stroke
        Rectangle rectangle = new Rectangle(length, height);

        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(stroke);

        // Add the rectangle as a child to the StackPane
        stackPane.getChildren().add(rectangle);

        // Add the VBox as a child to the StackPane (it will be positioned over the
        // rectangle)
        stackPane.getChildren().add(vBox);

        return stackPane;
    }

    public static int findPosition(HashMap<Integer, Float> hashMap, int target) {
        List<Integer> keyList = hashMap.keySet().stream().collect(Collectors.toList());

        for (int i = 0; i < keyList.size(); i++) {
            if (keyList.get(i) == target) {
                return i + 1; // Adding 1 to make it a 1-based index
            }
        }

        return -1; // Return -1 if the target is not found
    }

    public VBox createPETable() {

        VBox table = new VBox();
        HBox displayRow = getDisplayRow(displayRowPE);
        addChild(table, displayRow);

        // the function can recieve null in any two parameters so we need to determine
        // which variable has the values and only add the values into the cell which it
        // belongs to keeping the other term cells empty

        int[] usableKeys = getUsableKeys();

        // iterating and adding cells, the cells will still be added even if there is,
        // no value provided for the corresponding subject in a term
        for (int i = 0; i < usableKeys.length; i++) {

            int key = usableKeys[i];
            // get the values

            // subject name
            String subjectName = subjects.get(key);

            // term one value
            int marksOne = getMarksOne(key);

            // term two value
            int marksTwo = getMarksTwo(key);

            // term three value

            int marksThree = getMarksThree(key);

            HBox row = createPERow(subjectName, marksOne, marksTwo, marksThree);
            addChild(table, row);

        }

        return table;
    }

    private int[] getUsableKeys() {

        int[] usableKeys = null;
        try {
            usableKeys = getKeysArray(termOne);
        } catch (Exception e) {
        }
        try {
            usableKeys = getKeysArray(termTwo);
        } catch (Exception e) {
        }
        try {
            usableKeys = getKeysArray(termThree);
        } catch (Exception e) {
        }
        return usableKeys;
    }

    public VBox createSubjectTable(boolean isPrimary) {

        VBox table = new VBox();
        HBox displayRow = getDisplayRow(displayRowST);
        addChild(table, displayRow);

        // add major title to the result

        cellLabelAlignment = TextAlignment.CENTER;
        labelAlignment = Pos.CENTER;
        StackPane majorCell = createCell(majorString,
                displayCellFont,
                displayCellStroke,
                displayCellRectHeight,
                displayCellRectLength,
                displayCellLabelLength);

        addChild(table, addChild(new HBox(), majorCell));

        cellLabelAlignment = TextAlignment.LEFT;
        labelAlignment = Pos.CENTER_LEFT;
        // iterating and adding cells, the cells will still be added even if there is,
        // no value provided for the corresponding subject in a term
        for (int i : majorSubCodes) {

            int key = i;

            // confirming if it is added or not

            if (addedSubjectCodes.contains(key) == true)
                continue;

            // checking whether the key is part of any compound subject

            ArrayList<Integer> compoundSubject = findArrayList(averageSubjectsSignifier, key);

            if (compoundSubject != null) {
                HBox compoundRow = createCompoundSubjectRow(compoundSubject);
                addChild(table, compoundRow);
                continue;

            }

            // get the values

            // subject name
            String subjectName = subjects.get(key);

            // term one value
            int marksOne = getMarksOne(key);

            // term two value
            int marksTwo = getMarksTwo(key);

            // term three value

            int marksThree = getMarksThree(key);

            HBox row = createSTRow(subjectName, marksOne, marksTwo, marksThree);

            int avg = -1;
            if (averagedSubjectsValue != null) {
                averagedSubjectsValue.get(key);
            }
            row = addColToRow(row, avg);

            addChild(table, row);

        }

        if (!isPrimary) {
            // add minor title to the result
            if (minorSubCodes.size() != 0) {
                labelAlignment = Pos.CENTER;

                cellLabelAlignment = TextAlignment.CENTER;
                StackPane minorCell = createCell(minorString,
                        displayCellFont,
                        displayCellStroke,
                        displayCellRectHeight,
                        displayCellRectLength,
                        displayCellLabelLength);

                addChild(table, addChild(new HBox(), minorCell));

            }

            labelAlignment = Pos.CENTER_LEFT;
            cellLabelAlignment = TextAlignment.LEFT;

            for (int i : minorSubCodes) {

                int key = i;

                // confirming if it is added or not

                if (addedSubjectCodes.contains(key) == true)
                    continue;

                // checking whether the key is part of any compound subject

                ArrayList<Integer> compoundSubject = findArrayList(averageSubjectsSignifier, key);
                if (compoundSubject != null) {
                    HBox compoundRow = createCompoundSubjectRow(compoundSubject);
                    addChild(table, compoundRow);
                    continue;

                }

                // get the values

                // subject name
                String subjectName = subjects.get(key);

                // term one value
                int marksOne = getMarksOne(key);

                // term two value
                int marksTwo = getMarksTwo(key);

                // term three value

                int marksThree = getMarksThree(key);

                HBox row = createSTRow(subjectName, marksOne, marksTwo, marksThree);
                int avg = -1;
                if (averagedSubjectsValue != null) {
                    averagedSubjectsValue.get(key);
                }
                row = addColToRow(row, avg);

                addChild(table, row);

            }
        }

        return table;
    }

    private HBox createCompoundSubjectRow(ArrayList<Integer> compoundSubject) {

        // stores overall term average marks
        ArrayList<Integer> marks = new ArrayList<>();

        // initial compound row holder

        VBox compoundRow = new VBox();
        int rectHeightFact = 0;

        for (int i : compoundSubject) {
            if (averagedSubjectsValue != null) {

                marks.add(averagedSubjectsValue.get(i));
            }

            // subject name
            String subjectName = subjects.get(i);

            // term one value
            int marksOne = getMarksOne(i);

            // term two value
            int marksTwo = getMarksTwo(i);

            // term three value
            int marksThree = getMarksThree(i);

            HBox row = createSTRow(subjectName, marksOne, marksTwo, marksThree);

            addChild(compoundRow, row);
            rectHeightFact++;
            addedSubjectCodes.add(i);

        }

        // generally if its a compound subject and its 2nd or 3rd term we have the
        // required data in averageSubjectsValue
        // in case it is null we only need to print hollow boxes

        HBox row = new HBox();
        row = addColToCompoundRow(compoundRow, calculateAverage(marks), rectHeightFact);

        return row;
    }

    public void setTermOne(HashMap<Integer, Integer> termOne) {
        this.termOne = termOne;
    }

    public void setTermTwo(HashMap<Integer, Integer> termTwo) {
        this.termTwo = termTwo;
    }

    public void setTermThree(HashMap<Integer, Integer> termThree) {
        this.termThree = termThree;
    }

    public void setSubjects(HashMap<Integer, String> subjects) {
        this.subjects = subjects;
    }

    public void setAverageSubjectsSignifier(ArrayList<ArrayList<Integer>> averageSubjectsSignifier) {
        this.averageSubjectsSignifier = averageSubjectsSignifier;
    }

    public void setAveragedSubjectsValue(HashMap<Integer, Integer> averagedSubjectsValue) {
        this.averagedSubjectsValue = averagedSubjectsValue;
    }

    public void setMajorSubCodes(ArrayList<Integer> majorSubCodes) {
        this.majorSubCodes = majorSubCodes;
    }

    public void setMinorSubCodes(ArrayList<Integer> minorSubCodes) {
        this.minorSubCodes = minorSubCodes;
    }

    private int calculateAverage(ArrayList<Integer> list) {
        if (list.size() == 0) {
            return -1; // Return -1 if the list is empty
        }

        int sum = 0;
        for (int number : list) {
            sum += averagedSubjectsValue.get(number);
        }

        return sum / list.size(); // Calculate and return the average
    }

    private HBox addColToCompoundRow(VBox compoundRow, int average, int multiplyFactor) {

        HBox row = new HBox();
        row.getChildren().add(compoundRow);

        StackPane avg = createCell(addLeadingZero(average),
                marksFontST,
                cellStrokeST,
                avgCellRectHeight * multiplyFactor,
                avgCellRectLength,
                marksLabelLengthST);

        StackPane gov = createCell(addLeadingZero(calculateGrade(average)),
                marksFontST,
                cellStrokeST,
                govCellRectHeight * multiplyFactor,
                govCellRectLength,
                marksLabelLengthST);

        addChild(row, avg);
        addChild(row, gov);
        return row;

    }

    private HBox addColToRow(HBox row, int average) {

        StackPane avg = createCell(addLeadingZero(average),
                marksFontST,
                cellStrokeST,
                avgCellRectHeight,
                avgCellRectLength,
                marksLabelLengthST);

        StackPane gov = createCell(addLeadingZero(calculateGrade(average)),
                marksFontST,
                cellStrokeST,
                govCellRectHeight,
                govCellRectLength,
                marksLabelLengthST);

        addChild(row, avg);
        addChild(row, gov);
        return row;

    }

    // INCOMPLETE
    private int calculateGrade(int value) {

        return value;
    }

    public ArrayList<Integer> findArrayList(ArrayList<ArrayList<Integer>> averageSubjects, int target) {
        for (int i = 0; i < averageSubjects.size(); i++) {
            ArrayList<Integer> innerList = averageSubjects.get(i);
            if (innerList.contains(target)) {

                return innerList;
            }
        }
        return null; // Return null if the target is not found in any ArrayList
    }

    private int getMarksOne(int key) {

        int marksOne = -1;
        try {
            marksOne = termOne.get(key);

        } catch (Exception e) {
        }

        return marksOne;

    }

    private int getMarksTwo(int key) {
        int marksTwo = -1;
        try {
            marksTwo = termTwo.get(key);

        } catch (Exception e) {
        }

        return marksTwo;
    }

    private int getMarksThree(int key) {
        int marksThree = -1;
        try {
            marksThree = termThree.get(key);

        } catch (Exception e) {
        }

        return marksThree;
    }

    public static HBox createRow() {
        HBox row = new HBox();
        return row;
    }

    // a cell is nothing but a square which holds an empty label inside it(Stack
    // pane)

    private TextAlignment cellLabelAlignment = TextAlignment.LEFT;
    private Pos labelAlignment = Pos.CENTER_LEFT;

    public StackPane createCell(String text, Font font, int stroke, int rectHeight, int rectLength, int labelLength) {
        Label label = new Label();
        label.setText(text);
        label.setAlignment(labelAlignment); // Align label to the left
        label.setFont(font);

        if (labelLength > 0) {
            label.setPrefWidth(labelLength);
        }

        label.textAlignmentProperty().set(cellLabelAlignment);

        // Create a rectangle with the specified width and some padding
        Rectangle rectangle = new Rectangle();
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(stroke);

        // Set the width of the rectangle to the provided length
        rectangle.setWidth(rectLength);

        if (rectHeight != -1) {
            // Set the height of the rectangle to the provided height
            rectangle.setHeight(rectHeight);
        } else {
            // Bind the height of the rectangle to the height of the label with additional
            // padding
            rectangle.heightProperty().bind(label.heightProperty().add(10));
        }

        // Create a StackPane and add the rectangle and label to it
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(rectangle, label);

        return stackPane;
    }

    private String addLeadingZero(int digit) {
        if (digit == -1)
            return "";

        if (digit >= 0 && digit < 10) {
            // Single-digit case: Add leading zero
            return String.format("%02d", digit);
        } else {
            // Multi-digit case: No changes
            return String.valueOf(digit);
        }
    }

    public static VBox addChild(VBox table, HBox row) {
        table.getChildren().add(row);
        return table;
    }

    public static VBox mergeRow(HBox... row) {
        VBox mergedRow = new VBox();

        for (int i = 0; i < row.length; i++) {
            mergedRow.getChildren().add(row[i]);
        }
        return mergedRow;
    }

    // Table is a Vbox

    public static HBox addChild(HBox row, StackPane cell) {
        row.getChildren().add(cell);
        return row;
    }

    public static void captureAndSaveVBoxImage(VBox vbox, String filePath) {
        // Create a SnapshotParameters instance
        SnapshotParameters params = new SnapshotParameters();

        // Set the background fill to white (optional)
        params.setFill(javafx.scene.paint.Color.WHITE);

        // Capture the snapshot of the VBox
        WritableImage snapshot = vbox.snapshot(params, null);

        // Save the image to a file
        File file = new File(filePath);

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);
        } catch (IOException e) {
            e.printStackTrace();
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

    
}
