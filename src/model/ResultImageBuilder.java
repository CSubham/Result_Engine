package model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Stage;
import model.enums.SubjectSignificance;

public class ResultImageBuilder {

    // public static void main(String[] args) {
    //     launch(args);
    // }

    // @Override
    // public void start(Stage stage) throws Exception {
    //     HashMap<Integer, SubjectSignificance> gradeSubjectList = new HashMap<>();
    //     gradeSubjectList.put(01, SubjectSignificance.EVALUATION);
    //     gradeSubjectList.put(02, SubjectSignificance.EVALUATION);

    //     HashMap<Integer, String> subjects = new HashMap<>();
    //     subjects.put(01, "SomeSubject");
    //     subjects.put(02, "Some");

    //     HashMap<Integer, Integer> marks = new HashMap<>();
    //     marks.put(01, 45);
    //     marks.put(02, 45);

    //     Scene scene = new Scene(createPETable(marks, null, null, subjects));
    //     captureAndSaveVBoxImage((VBox) scene.getRoot(), "image.png");

    //     stage.setScene(scene);
    //     stage.show();
    //     stage.setResizable(true);

    // }

    // length of string
    private int stringLength = 18;
    private Font font = new Font("Cantarell", 30);

    // length of the string
    private int marksLength = 3;
    private int cellStroke = 1;

    // length of rectangle strings are displayed within
    private int subjectNameRectLen = 318;
    private int marksDisplayRectLen = 89;

    public VBox createPETable(HashMap<Integer, Integer> termOne, HashMap<Integer, Integer> termTwo,
            HashMap<Integer, Integer> termThree, HashMap<Integer, String> subjects) {

        VBox table = new VBox();
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/model/ResultImageFxmls/personal_evaluation.fxml"));
            Parent loadedContent = loader.load();

            // Add the loaded content to the "table" VBox
            table.getChildren().add(loadedContent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // adding rows to the table

        int[] usableKeys = getUsableKeys(termOne, termTwo, termThree);

        // iterating and adding cells, the cells will still be added even if there is,
        // no value provided for the corresponding subject in a term
        for (int i = 0; i < usableKeys.length; i++) {

            HBox row = new HBox();

            System.out.println(padString(subjects.get(usableKeys[i]), stringLength));
            // subject name
            StackPane cell = createCell(subjects.get(usableKeys[i]), font, cellStroke,
                    subjectNameRectLen, subjectNameRectLen);
            addChild(row, cell);

            // term one marks
            if (termOne != null) {
                StackPane termOneCell = createCell(
                        padString(addLeadingZero(termOne.get(usableKeys[i])), marksLength),
                        font,
                        cellStroke, marksDisplayRectLen, -1);
                addChild(row, termOneCell);

            } else {

                StackPane termOneCell = emptyMarksCell();
                addChild(row, termOneCell);

            }

            // term two marks

            if (termTwo != null) {
                StackPane termTwoCell = createCell(
                        padString(addLeadingZero(termTwo.get(usableKeys[i])), marksLength),
                        font,
                        cellStroke, marksDisplayRectLen, -1);
                addChild(row, termTwoCell);

            } else {

                StackPane termTwoCell = emptyMarksCell();
                addChild(row, termTwoCell);

            }

            // term three cell

            if (termThree != null) {
                StackPane termThreeCell = createCell(
                        padString(addLeadingZero(termThree.get(usableKeys[i])), marksLength),
                        font,
                        cellStroke, marksDisplayRectLen, -1);
                addChild(row, termThreeCell);

            } else {

                StackPane termThreeCell = emptyMarksCell();
                addChild(row, termThreeCell);

            }

            addChild(table, row);
        }

        return table;
    }

    private StackPane emptyMarksCell() {
        return createCell(
                padString(addLeadingZero(-1), marksLength),
                font,
                cellStroke, marksDisplayRectLen, -1);
    }

    private int[] getUsableKeys(HashMap<Integer, Integer> termOne, HashMap<Integer, Integer> termTwo,
            HashMap<Integer, Integer> termThree) {

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

    public VBox createSubjectTable() {

        VBox table = new VBox();
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/model/ResultImageFxmls/subjects.fxml"));
            Parent loadedContent = loader.load();

            // Add the loaded content to the "table" VBox
            table.getChildren().add(loadedContent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return table;
    }

    public static HBox createRow() {
        HBox row = new HBox();
        return row;
    }

    // a cell is nothing but a square which holds an empty label inside it(Stack
    // pane)

    public StackPane createCell(String text, Font font, int stroke, int rectLength, int labelLength) {
        Label label = new Label();
        label.setText(text);
        label.setAlignment(Pos.CENTER_LEFT); // Align label to the left
        label.setFont(font);
        if (labelLength > 0) {
            label.setPrefWidth(labelLength);
        }
        label.textAlignmentProperty().set(TextAlignment.LEFT);

        // Create a rectangle with the specified width and some padding
        Rectangle rectangle = new Rectangle();
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(stroke);

        // Set the width of the rectangle to the provided length
        rectangle.setWidth(rectLength);

        // Bind the height of the rectangle to the height of the label with additional
        // padding
        rectangle.heightProperty().bind(label.heightProperty().add(10));

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

    private static String padString(String input, int length) {
        if (input.length() < length) {
            int requiredPadding = length - input.length();
            return input + " ".repeat(requiredPadding);
        } else if (input.length() > length) {
            return input.substring(0, length);
        } else {
            return input;
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
