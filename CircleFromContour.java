/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package omrdetectionproject;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author smtareeq
 */
public class CircleFromContour {
    static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    public static void detect(String[] args) {
        // Load the image
        String imagePath = "/home/smtareeq/Pictures/paper1/input_file/mbbs19q.png";
        Mat image = Imgcodecs.imread(imagePath, Imgcodecs.IMREAD_COLOR);
        if (image.empty()) {
            System.out.println("Could not open or find the image");
            return;
        }

        // Convert to grayscale
        Mat gray = new Mat();
        Imgproc.cvtColor(image, gray, Imgproc.COLOR_BGR2GRAY);

        // Apply Gaussian blur
        Imgproc.GaussianBlur(gray, gray, new Size(5, 5), 0);

        // Threshold the image to create a binary image
        Mat binary = new Mat();
        Imgproc.threshold(gray, binary, 128, 255, Imgproc.THRESH_BINARY_INV);

        // Detect contours
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(binary, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        for (MatOfPoint contour : contours) {
            // Calculate contour area
            double area = Imgproc.contourArea(contour);
            if (area < 100) continue;  // Skip small contours

            // Approximate contour to a polygon
            MatOfPoint2f contour2f = new MatOfPoint2f(contour.toArray());
            MatOfPoint2f approxCurve = new MatOfPoint2f();
            Imgproc.approxPolyDP(contour2f, approxCurve, 0.02 * Imgproc.arcLength(contour2f, true), true);

            // Get bounding box of the contour
            Rect boundingRect = Imgproc.boundingRect(new MatOfPoint(approxCurve.toArray()));

            
            
            // Extract the region of interest (ROI) around the detected shape
            Mat roi = new Mat(gray, boundingRect);

            // Calculate the number of non-zero pixels (white pixels)
            int count = Core.countNonZero(roi);

            // Define a threshold to consider the shape as marked
            boolean isMarked = count > (boundingRect.width * boundingRect.height) * 0.5;  // Example threshold: more than 50% of the bounding box area is marked

            // Draw the contour and label
            Scalar color = isMarked ? new Scalar(0, 255, 0) : new Scalar(0, 0, 255);  // Green for marked, Red for unmarked
           // String label = isMarked ? "Marked" : "Unmarked";
            Imgproc.drawContours(image, List.of(contour), -1, color, 2);
           // Imgproc.putText(image, label, new Point(boundingRect.x, boundingRect.y - 10), Imgproc.FONT_HERSHEY_SIMPLEX, 0.5, color, 2);
        }

        // Show the output image with detected and labeled shapes
        Imgcodecs.imwrite("/home/smtareeq/Pictures/paper1/output_file/output_by_contour.jpg", image);
        System.out.println("Output image saved as output_by_contour.jpg");
    }
}
