/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package omrdetectionproject;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.*;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.xml.parsers.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import org.opencv.utils.Converters;
//import javax.swing.text.Document;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
       
     
        new HoughCirclesRun().run(args);
        CircleFromContour.detect(args);


/*//loading the image
      String imagePath ="/home/smtareeq/Pictures/paper1/omr.jpg";
      Mat src = Imgcodecs.imread(imagePath);
      
      if(src.empty()){
           System.out.println("Could not open or find the image");
           return;
      }
       
      
      Mat gray = new Mat();
      Imgproc.cvtColor(src,gray,Imgproc.COLOR_BGR2GRAY);
      Imgproc.blur(gray, gray, new Size(3, 3));
      Imgcodecs.imwrite("/home/smtareeq/Pictures/paper1/gray.jpg", gray);
     // Imgcodecs.imwrite("/home/smtareeq/Pictures/paper1/gray_image_2.jpg", gray);
     /*
     Mat blurred = new Mat();
     Imgproc.GaussianBlur(src, blurred, new Size(5, 5), 0);
      
      //Thresholding
       Mat binary = new Mat();
       Imgproc.threshold(gray,binary, 0, 255, Imgproc.THRESH_BINARY_INV+Imgproc.THRESH_OTSU);
      // Imgproc.threshold(blurred, binary, 0, 255, Imgproc.THRESH_BINARY_INV + Imgproc.THRESH_OTSU);
       Mat horizontal = binary.clone();
       
       int horizontalSize = horizontal.cols();
       //System.out.println(horizontalSize);
       
       Mat vertical = binary.clone();
        int verticalSize = vertical.rows();
       // System.out.println(verticalSize);
        
        
        
       Imgcodecs.imwrite("/home/smtareeq/Pictures/paper1/binaryImage.jpg", binary);
       
       // Find the circles
      // List<MatOfPoint> contours = new ArrayList<>();
       
        //Mat hierarchy = new Mat();

        // Find contours
        //Imgproc.findContours(binary, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        
       /* Collections.sort(contours, new Comparator<MatOfPoint>() {
        @Override
        public int compare(MatOfPoint c1, MatOfPoint c2) {
            double area1 = Imgproc.contourArea(c1);
            double area2 = Imgproc.contourArea(c2);
            return Double.compare(area2, area1);  // For descending order
        }
       });
        int numContoursToCrop = Math.min(contours.size(), 15);
        for (int i = 0; i < numContoursToCrop; i++) {
            MatOfPoint contour = contours.get(i);

            // Get the bounding box of the contour
            Rect boundingRect = Imgproc.boundingRect(contour);

            // Crop the image using the bounding box
            Mat croppedImage = new Mat(binary, boundingRect);

            // Save the cropped image
            String outputFileName = "/home/smtareeq/Pictures/paper1/cropped_image_" + (i + 1) + ".jpg";
            Imgcodecs.imwrite(outputFileName, croppedImage);

            System.out.println("Cropped image saved as '" + outputFileName + "'.");
        }
        */
     //Mat binary = Imgcodecs.imread("/home/smtareeq/Pictures/paper1/binaryImage.jpg");
      
     /*
        double minDist = 60;
    // higher threshold of Canny Edge detector, lower threshold is twice smaller
        double p1UpperThreshold = 200;
    // the smaller it is, the more false circles may be detected
        double p2AccumulatorThreshold = 20;
            int minRadius = 10;
        int maxRadius = 0;
        
        Mat circles = new Mat();
        Imgproc.HoughCircles(gray, circles, Imgproc.CV_HOUGH_GRADIENT, 1, minDist, p1UpperThreshold, p2AccumulatorThreshold, minRadius, maxRadius);
        /*Imgproc.HoughCircles(
                edges, circles, Imgproc.HOUGH_GRADIENT, 
                3.0, // dp
                (double)src.rows() / 10, // minDist
                50.0, // param1
                30.0, // param2
                1, // minRadius
                100 // maxRadius
        );*/
        
     
     /*
        Mat colorSrc = Imgcodecs.imread("/home/smtareeq/Pictures/paper1/omr.jpg");
        
// Check if any circles were detected
         for (int i = 0; i < circles.cols(); i++) {
            double[] circleParams = circles.get(0, i);
            Point center = new Point(Math.round(circleParams[0]), Math.round(circleParams[1]));
            int radius = (int) Math.round(circleParams[2]);

            // Draw the circle center
            Imgproc.circle(colorSrc, center, 3, new Scalar(0, 255, 0), -1);
            // Draw the circle outline
            Imgproc.circle(colorSrc, center, radius, new Scalar(0, 0, 255), 3);
        }
         System.out.println("Total circles: " + circles.cols());

        // Save the result
        Imgcodecs.imwrite("/home/smtareeq/Pictures/paper1/detected_circles.jpg", colorSrc);
        
        
       

   
        
        
        
        
        
        



        List<Bubble> bubbles = new ArrayList<>();
 
    /*    double maxArea = 0;
        int maxAreaIndex = -1;
        MatOfPoint largestContour = null;
        for (int i = 0; i < contours.size(); i++) {
            double area = Imgproc.contourArea(contours.get(i));
            if (area > maxArea) {
                maxArea = area;
                maxAreaIndex = i;
                largestContour = contours.get(i);
            }
        }

        // Draw the largest contour
        if (maxAreaIndex >= 0) {
            Imgproc.drawContours(src, contours, maxAreaIndex, new Scalar(0, 255, 0), 2);
        }

        // Save or display the result
        Imgcodecs.imwrite("/home/smtareeq/Pictures/paper1/biggestContour.jpg", src);
        
        System.out.println("The biggest contour has an area of: " + maxArea);
       
        Rect boundingRect = Imgproc.boundingRect(largestContour);

            // Crop the image using the bounding box
            Mat croppedImage = new Mat(binary, boundingRect);

            // Save the cropped image
            Imgcodecs.imwrite("/home/smtareeq/Pictures/paper1/cropped_image.jpg", croppedImage);
            
            
            
            
            

        
        
        
        for (MatOfPoint contour : contours){
            Rect rect = Imgproc.boundingRect(contour);
            
            if(isBubble(rect)){
                Mat bubble = binary.submat(rect);
                if (isFilled(bubble)){
                    bubbles.add(new Bubble(rect.tl().x,rect.tl().y));
                }
            }
        }
        
        
        
        Mat blurred = new Mat();
        Imgproc.GaussianBlur(gray, blurred, new Size(5, 5), 0);

        // Apply Canny edge detector
        Mat edges = new Mat();
        Imgproc.Canny(blurred, edges, 50, 150, 3, false);

        // Apply Hough Line Transform
        Mat lines = new Mat();
        Imgproc.HoughLinesP(edges, lines, 1, Math.PI / 180, 50, 50, 10);

        // Draw the lines on the image
        for (int i = 0; i < lines.rows(); i++) {
            double[] line = lines.get(i, 0);
            Point pt1 = new Point(line[0], line[1]);
            Point pt2 = new Point(line[2], line[3]);
            Imgproc.line(src, pt1, pt2, new Scalar(0, 0, 255), 2);
        }

        // Save the result
        String outputPath = "/home/smtareeq/Pictures/paper1/line.jpg";
        Imgcodecs.imwrite(outputPath, src);

        System.out.println("Line detection complete. Result saved at " + outputPath);
    
        
      int count = 0;
        
       for (int i = 0; i < contours.size(); i++) {
            Rect rect = Imgproc.boundingRect(contours.get(i));
    
            double x = rect.x;
            double y = rect.y;
            count++;
            System.out.println("Marked area at: (" + (x/1118)*100 + ", " + (y/2337)*100 + ")");
    
            // Optionally, draw rectangles around detected marks for visualization
             Imgproc.rectangle(src, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0), 2);
        }
       System.out.println("total marked area : "+count);

        // Save the result image with marked areas
               Imgcodecs.imwrite("/home/smtareeq/Pictures/paper1/marked.jpg", src);
        
        
       
        
        
        
        try{
            generateXML(bubbles,"/home/smtareeq/Pictures/paper1/result.xml");
        }catch(ParserConfigurationException | TransformerException e){
            e.printStackTrace();
        }

        // Draw the contours on the original image
       /* Mat drawing = Mat.zeros(binary.size(), CvType.CV_8UC3);
        
         Imgcodecs.imwrite("/home/smtareeq/Pictures/paper1/contourOutputZeros.jpg", drawing);
        
        for (int i = 0; i < contours.size(); i++) {
            Imgproc.drawContours(drawing, contours, i, new Scalar(0,255, 0), 2);
            System.out.println(i);
        }

        // Save the output image
        Imgcodecs.imwrite("/home/smtareeq/Pictures/paper1/contourOutput.jpg", drawing);*/
       
      
       
      
      
    }//main method ends
    
    
    
     private static boolean isBubble(Rect rect) {
        return rect.width > 10 && rect.height > 10 && Math.abs(rect.width - rect.height) < 10;
    }
     
     private static boolean isFilled(Mat bubble) {
        int nonZeroCount = Core.countNonZero(bubble);
        double area = bubble.rows() * bubble.cols();
        double filledPercentage = (nonZeroCount / area) * 100;
        return filledPercentage > 50;
    }
    
    private static void generateXML(List<Bubble> bubbles, String filePath) throws ParserConfigurationException,TransformerException{
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        Element root = document.createElement("OmrResults");
        document.appendChild(root);
        
        for(Bubble bubble:bubbles){
            Element bubbleElement = document.createElement("Bubble");
            bubbleElement.setAttribute("x", String.valueOf(bubble.getX()));
            bubbleElement.setAttribute("y", String.valueOf(bubble.getY()));
            root.appendChild(bubbleElement);
        }
        
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "10"); // Adj
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(filePath));

        transformer.transform(domSource, streamResult);

        System.out.println("XML file generated: " + filePath);
    }
   
}//class
    
class Bubble {
    private double x;
    private double y;

    public Bubble(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
 }
