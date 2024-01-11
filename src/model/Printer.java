package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;

public class Printer {

    // this function takes a string path leading to an image and then looks for
    // connected printers and prints 1 copy
    
    public static void printImage(String imagePath) {
        try {
            // Create a PrintService
            PrintService printService = choosePrintService();

            if (printService != null) {
                // Load the image
                DocFlavor flavor = DocFlavor.INPUT_STREAM.PNG;
                Doc doc = new SimpleDoc(new FileInputStream(imagePath), flavor, null);

                // Create a PrintJob
                DocPrintJob printJob = printService.createPrintJob();

                // Specify print request attributes
                PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
                attributes.add(new Copies(1)); // Number of copies
                attributes.add(MediaSizeName.ISO_A4); // Paper size

                // Print the document
                printJob.print(doc, attributes);
            }
        } catch (PrintException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static PrintService choosePrintService() {
        // Use the default print service for simplicity
        PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();
        if (defaultPrintService != null) {
            return defaultPrintService;
        } else {
            System.out.println("No default print service found.");
            return null;
        }
    }

}
