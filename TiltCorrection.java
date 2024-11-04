/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package omrdetectionproject;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Point;
import org.opencv.core.Mat;

public class TiltCorrection {
    public static void main(String imagePath) {
        // Load the image
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
       // String imagePath = "path_to_omr_image.jpg";
        Mat src = Imgcodecs.imread(imagePath);

        // Convert the image to grayscale
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);

        // Detect edges using Canny
        Mat edges = new Mat();
        Imgproc.Canny(gray, edges, 50, 150, 3, false);

        // Detect lines using Hough Transform
        Mat lines = new Mat();
        Imgproc.HoughLines(edges, lines, 1, Math.PI/180, 200);
        
        // Calculate the tilt angle based on detected lines
        double angle = 0;
        for (int i = 0; i < lines.rows(); i++) {
            double[] rhoTheta = lines.get(i, 0);
            double rho = rhoTheta[0], theta = rhoTheta[1];
            double degrees = Math.toDegrees(theta);
            System.out.println(degrees);
            // Get the angle from vertical/horizontal lines
            if (degrees > 45 && degrees < 135) {
                angle = degrees - 90;
                break;
            }
        }
        System.out.println(angle);

        // Rotate the image based on the calculated angle
        Mat rotationMatrix = Imgproc.getRotationMatrix2D(new Point(src.width()/2, src.height()/2), angle, 1);
        Mat rotated = new Mat();
        Imgproc.warpAffine(src, rotated, rotationMatrix, new Size(src.width(), src.height()));

        // Save the rotated image
       Imgcodecs.imwrite(imagePath, rotated);
        //return rotated;

        // The rotated image is now aligned, and you can further process it for detecting marks
    }
}
