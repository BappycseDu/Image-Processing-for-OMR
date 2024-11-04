/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package omrdetectionproject;

import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import static org.opencv.imgproc.Imgproc.boundingRect;

/**
 *
 * @author smtareeq
 */
public class finding_sections {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void box_finding(String imagePath) {
        // Load the image
        Mat src = Imgcodecs.imread(imagePath);

        if (src.empty()) {
            System.out.println("Could not open or find the image!");
            return;
        }

       
        // Convert to grayscale
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        src.release();
        // Apply GaussianBlur to reduce noise and avoid false rectangle detection
        Imgproc.GaussianBlur(gray, gray, new Size(5, 5), 0);
        

        // Apply adaptive thresholding to get binary image
        Mat binary = new Mat();      
        Imgproc.adaptiveThreshold(gray, binary, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY_INV, 11, 2);
        gray.release();
        
         
        // Find contours
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(binary, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        hierarchy.release();
        binary.release();
        // Loop through the contours to find rectangles
        
        for (MatOfPoint contour : contours) {
            MatOfPoint2f contour2f = new MatOfPoint2f(contour.toArray());

            // Approximate contour with accuracy proportional to the contour perimeter
            MatOfPoint2f approxCurve = new MatOfPoint2f();
            double approxDistance = Imgproc.arcLength(contour2f, true) * 0.02;
            Imgproc.approxPolyDP(contour2f, approxCurve, approxDistance, true);
            contour2f.release();

            // Convert back to MatOfPoint
            MatOfPoint points = new MatOfPoint(approxCurve.toArray());
            approxCurve.release();
            // If the contour has 4 vertices, it is a rectangle
            if (points.total() == 4) {
                
                Rect rect = Imgproc.boundingRect(points);
                
                // Define the expected size range for OMR circles area and roll number area
                double area = rect.area();
                double aspectRatio = (double) rect.width / rect.height;
                //System.out.println(area);
                
                if (area > 170000 && area<200000 && aspectRatio > 0.1 && aspectRatio<0.2) {
                    if(rect.x<200){ 
                        GblVble.OmrBox_tl_x= rect.x;
                        GblVble.OmrBox_tl_y= rect.y;
                        GblVble.firstBox_lx =rect.x;
                        GblVble.firstBox_rx = rect.x+rect.width;
                        
                    }
                    else if(rect.x>200 && rect.x<500){
                        GblVble.secondBox_lx = rect.x;
                        GblVble.secondBox_rx = rect.x+rect.width;
                    }
                    else if(rect.x>500 && rect.x<700){
                        GblVble.thirdBox_lx = rect.x;
                        GblVble.thirdBox_rx = rect.x+rect.width;
                    }
                    else if (rect.x>700 && rect.x<900){                    
                        GblVble.fourthBox_lx = rect.x;
                        GblVble.fourthBox_rx = rect.x+rect.width;
                        GblVble.OmrBox_br_x = rect.x+rect.width;
                        GblVble.OmrBox_br_y = rect.y+rect.height;
                    }
                   // System.out.println("area: "+area+" aspect ratio "+aspectRatio);
                    // This is likely the OMR circles area
                    
                    //System.out.println("OMR Circles Area - Top Left: " + rect.tl() + ", Bottom Right: " + rect.br()+" area is: "+area);
                   /* 
                    String xCo= String.valueOf(rect.x);
                    String yCo = String.valueOf(rect.y);
         
                    String CoOrdinate = xCo+" , "+yCo;
                   Imgproc.putText(src,CoOrdinate, new Point(rect.x, rect.y- 10), Imgproc.FONT_HERSHEY_SIMPLEX, 0.4,new Scalar(200, 255, 0), 1);
                    
                  */
                } else if (area>80000 && area<88000 && aspectRatio>.6 && aspectRatio<.69) {
                    //System.out.println("Roll box");
                    //||(area>100000 && area<130000 && aspectRatio>.5 && aspectRatio<.6)
                       // This is likely the roll number area
                    Imgproc.rectangle(src, rect, new Scalar(255, 0, 0), 3);
                   GblVble.RollBox_lx = rect.x;
                   GblVble.RollBox_ly = rect.y;
                   GblVble.RollBox_rx = rect.x+rect.width;
                   GblVble.RollBox_ry = rect.y +rect.height;
                   
                   /* 
                   // System.out.println("Roll Number Area - Top Left: " + rect.tl() + ", Bottom Right: " + rect.br()+" area is: "+area+" aspect Ratio "+aspectRatio);
                    String xCo= String.valueOf(rect.x);
                    String yCo = String.valueOf(rect.y);
                    String area_string = "--"+String.valueOf(area);
                    String CoOrdinate = xCo+" , "+yCo;
                    Imgproc.rectangle(src, rect, new Scalar(0, 255, 0), 3);
                    Imgproc.putText(src,CoOrdinate, new Point(rect.x, rect.y- 10), Imgproc.FONT_HERSHEY_SIMPLEX, 0.4,new Scalar(200, 255, 0), 1);
                   //Imgcodecs.imwrite("resources/output_file/coordinate_of_rectangular_detected.jpg", src);
                */
                }
                else if(area>71000 && area<78000 && aspectRatio>.55 && aspectRatio<.65){
                   
                   GblVble.SerialBox_lx = rect.x;
                   GblVble.SerialBox_ly = rect.y;
                   GblVble.SerialBox_rx = rect.x+rect.width;
                   GblVble.SerialBox_ry = rect.y +rect.height;
                    
                   /*
                    //System.out.println("Section Number Area - Top Left: " + rect.tl() + ", Bottom Right: " + rect.br()+" area is: "+area+" aspect Ratio "+aspectRatio);
                    String xCo= String.valueOf(rect.x);
                    String yCo = String.valueOf(rect.y);        
                    String CoOrdinate = xCo+" , "+yCo;
                    Imgproc.putText(src,CoOrdinate, new Point(rect.x, rect.y- 10), Imgproc.FONT_HERSHEY_SIMPLEX, 0.4,new Scalar(200, 255, 0), 1);
                */
                  
                }
                
            }
            points.release();
        }
        System.gc();

        // Save the result
        //Imgcodecs.imwrite("resources/output_file/coordinate_of_rectangular_detected.jpg", src);
    
    
    }
    
}
