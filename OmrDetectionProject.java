/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package omrdetectionproject;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import org.opencv.imgcodecs.Imgcodecs;
/**
 *
 * @author smtareeq
 */
public class OmrDetectionProject {
        static{System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       //  CSVWriter.main(args);
        File directory= new File("resources/T1/C13-1027");
               String csvFile = "resources/T1/C13-1027/output_afterchanging191LIne.csv";
       // File directory= new File("resources/T1/filtered");
                //String csvFile = "resources/T1/filtered/output.csv";
        //File directory= new File("resources/T1/selectedfiles");
          //      String csvFile = "resources/T1/selectedfiles/output.csv";
        
         File[] allFiles = directory.listFiles();
         

         if (allFiles == null || allFiles.length == 0) {
            throw new RuntimeException("No files present in the directory: " + directory.getAbsolutePath());
         }
        Arrays.sort(allFiles, (f1, f2) -> f1.getName().compareToIgnoreCase(f2.getName()));
        List<String> supportedImageExtensions = Arrays.asList("jpg", "png", "gif", "webp");
        List<File> acceptedImages = new ArrayList<>();
        
        
        for (File file : allFiles) {
            //Parse the file extension
             String fileExtension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            //Check if the extension is listed in the supportedImageExtensions
             if (supportedImageExtensions.stream().anyMatch(fileExtension::equalsIgnoreCase)) {
                //Add the image to the filtered list
                
                acceptedImages.add(file);
                
             }
        }
        //String templateImage = "/home/smtareeq/Pictures/paper1/T1/C13-1027/C13-1027-0000025A.jpg";
      //  finding_sections.box_finding(templateImage);
       
        
        // Sample data to write to CSV
        String[] headers = {"QRCode","BarCode","file name", "Roll","serial","result"};
        File fileCsv = new File(csvFile);
        int batchSize = 1;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileCsv))) { 
            writer.write(String.join(",", headers));
            writer.newLine();
            for (int i = 0; i < acceptedImages.size(); i += batchSize){
                List<File> batch = acceptedImages.subList(i, Math.min(i + batchSize, acceptedImages.size()));
               
                for (File file : batch) {
                    GblVble.reset();
                    String imagePath = file.getAbsolutePath();

                    Mat image = Imgcodecs.imread(imagePath);
                    if (image.empty()) {
                        System.out.println("Failed to load image: " + file.getName());
                        continue;  // Skip this file and move to the next one
                    }


                    try{
                        System.out.println();
                        System.out.println(file.getName());

                       // TiltCorrection.main(imagePath);
                        finding_sections.box_finding(imagePath);
                        QRCodeReader.main(imagePath);
                        BarCodeReader.main(imagePath);        
                        new HoughCirclesRun().run(imagePath);
                        InnerRectangle.detect(imagePath);
                        //CircleFromContour.detect(imagePath);
                        writer.write(String.join(",",GblVble.qrCode,GblVble.barCode,file.getName(),GblVble.roll,GblVble.serial,GblVble.result));
                        writer.newLine();
                    }catch (Exception e) {
                        System.out.println("Error processing file: " + file.getName());
                        e.printStackTrace();
                    } finally {
                        image.release();  // Release the image resource here
                       
                    }

                }
                System.gc();
               
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    
     
    }
 }
