/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package omrdetectionproject;
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

class HoughCirclesRun {
 
    public void run(String[] args) {
        String default_file = "/home/smtareeq/Pictures/paper1/input_file/s2.png";
        String filename = ((args.length > 0) ? args[0] : default_file);
     // Load an image
        Mat src = Imgcodecs.imread(filename, Imgcodecs.IMREAD_COLOR);
      //  Mat src_cloned = Imgcodecs.imread(filename, Imgcodecs.IMREAD_COLOR);

    // Check if image is loaded fine
        if( src.empty() ) {
        System.out.println("Error opening image!");
        System.out.println("Program Arguments: [image_name -- default "
            + default_file +"] \n");
        System.exit(-1);
         }
 
    
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);

    //Imgproc.medianBlur(gray, gray, 5);
        Imgproc.GaussianBlur(gray, gray, new Size(9, 9), 2, 2);
        //Mat edges = new Mat();
        //Imgproc.Canny(gray,edges, 100, 200);
        Mat circles = new Mat();
        Imgproc.HoughCircles(gray, circles, Imgproc.HOUGH_GRADIENT, 1.0,
            (double)gray.rows()/100, // change this value to detect circles with different distances to each other
            50, 30, 10,20); // change the last two parameters
     // (min_radius & max_radius) to detect larger circles
       
     Mat cloned_image = src.clone();
     for (int x = 0; x < circles.cols(); x++) {
            double[] c = circles.get(0, x);
            Point center = new Point(Math.round(c[0]), Math.round(c[1]));
     // circle center
            Imgproc.circle(cloned_image, center, 1, new Scalar(0,100,100), 3, 8, 0 );
     // circle outline
            int radius = (int) Math.round(c[2]);
            Imgproc.circle(cloned_image, center, radius, new Scalar(255,0,255), 3, 8, 0 );
            System.out.println(center);
            System.out.println(x+" circles detected");
        }
            Imgcodecs.imwrite("/home/smtareeq/Pictures/paper1/output_file/detected_circle.jpg", cloned_image);
            HighGui.imshow("detected circles", cloned_image);
           // HighGui.waitKey();
            //System.exit(0);
        
     
     Mat cloned_image2 = src.clone();
     if (circles.cols() > 0) {
            for (int i = 0; i < circles.cols(); i++) {
                double[] circle = circles.get(0, i);
                int x = (int) circle[0];
                int y = (int) circle[1];
                int r = (int) circle[2];

                // Extract the region of interest (ROI) around the detected circle
                Rect roiRect = new Rect(x - r, y - r, 2 * r, 2 * r);
                Mat roi = new Mat(gray, roiRect);

                // Threshold the ROI to get binary image
                Mat binary = new Mat();
                Imgproc.threshold(roi, binary, 128, 255, Imgproc.THRESH_BINARY_INV);

                // Calculate the number of non-zero pixels (white pixels)
                int count = Core.countNonZero(binary);

                // Define a threshold to consider the circle as marked
                boolean isMarked = count > (Math.PI * r * r) * 0.5;  // Example threshold: more than 50% of the circle area is marked

                // Draw the circle and label
                Scalar color = isMarked ? new Scalar(0, 255, 0) : new Scalar(0, 0, 255);  // Green for marked, Red for unmarked
               // String label = isMarked ? "M" : "U";
                Imgproc.circle(cloned_image2, new Point(x, y), r, color, 2);
               // Imgproc.putText(cloned_image2, label, new Point(x - r, y - r - 10), Imgproc.FONT_HERSHEY_SIMPLEX, 0.5, color, 2);
            }

            // Show the output image with detected and labeled circles
            Imgcodecs.imwrite("/home/smtareeq/Pictures/paper1/output_file/marked_output.jpg",cloned_image2);
            System.out.println("Output image saved as output file");
        } else {
            System.out.println("No circles were found");
        }
    
   }
    
}

