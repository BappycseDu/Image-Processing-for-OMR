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
 
    public void run(String imagePath) {
        //String default_file = "resources/input_file/omr.jpg";
       String filename =  imagePath;
     // Load an image
        Mat src = Imgcodecs.imread(filename, Imgcodecs.IMREAD_COLOR);
      //  Mat src_cloned = Imgcodecs.imread(filename, Imgcodecs.IMREAD_COLOR);

    // Check if image is loaded fine
        if( src.empty() ) {
        System.out.println("Error opening image!");
        System.out.println("Program Arguments: [image_name -- default "
            + imagePath +"] \n");
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
         src.release();
         double minimum_x = 99999;
         double minimum_y =999999;
         double maximum_x = 0;
         double maximum_y = 0;
         double minimum_rollX=99999;
         double minimum_rollY = 99999;
         double maximum_rollX = 0;
         double maximum_rollY = 0;
        
     for (int x = 0; x < circles.cols(); x++) {
            double[] c = circles.get(0, x);
            Point center = new Point(Math.round(c[0]), Math.round(c[1]));
     // circle center
            Imgproc.circle(cloned_image, center, 1, new Scalar(0,100,100), 3, 8, 0 );
     // circle outline
            int radius = (int) Math.round(c[2]);
            Imgproc.circle(cloned_image, center, radius, new Scalar(255,0,255), 3, 8, 0 );
            //System.out.println(center);
           // System.out.println(x+" circles detected")
         
           /*
           String xCo= String.valueOf(center.x-radius);
           String yCo = String.valueOf(center.y-radius);
           
           String CoOrdinate = xCo+" , "+yCo;
           Imgproc.putText(src,CoOrdinate, new Point(center.x, center.y- 10), Imgproc.FONT_HERSHEY_SIMPLEX, 0.4,new Scalar(0, 255, 0), 2);
           
           */
            if(center.x >GblVble.OmrBox_tl_x && center.y>GblVble.OmrBox_tl_y && center.x<GblVble.OmrBox_br_x && center.y<GblVble.OmrBox_br_y){
                if(center.x>GblVble.firstBox_lx && center.x<GblVble.firstBox_rx){
                     if(center.x<GblVble.circle_1A_x)
                         GblVble.circle_1A_x = center.x-radius;
                     if(center.y<GblVble.circle_1A_y)
                         GblVble.circle_1A_y = center.y-radius;

                     if(center.x>GblVble.circle_25D_x)
                         GblVble.circle_25D_x = center.x-radius;
                     if(center.y>GblVble.circle_25D_y)
                         GblVble.circle_25D_y = center.y-radius;
                 }
                if(center.x>GblVble.secondBox_lx && center.x<GblVble.secondBox_rx){
                    if(center.x<GblVble.circle_26A_x)
                         GblVble.circle_26A_x = center.x-radius;
                     if(center.y<GblVble.circle_26A_y)
                         GblVble.circle_26A_y = center.y-radius;

                     if(center.x>GblVble.circle_50D_x)
                         GblVble.circle_50D_x = center.x-radius;
                     if(center.y>GblVble.circle_50D_y)
                         GblVble.circle_50D_y = center.y-radius;
                 }
                 if(center.x>GblVble.thirdBox_lx && center.x<GblVble.thirdBox_rx){ 
                     if(center.x<GblVble.circle_51A_x)
                         GblVble.circle_51A_x = center.x-radius;
                     if(center.y<GblVble.circle_51A_y)
                         GblVble.circle_51A_y = center.y-radius;

                     if(center.x>GblVble.circle_75D_x)
                         GblVble.circle_75D_x = center.x-radius;
                     if(center.y>GblVble.circle_75D_y)
                         GblVble.circle_75D_y = center.y-radius;            
                 }
                 if(center.x>GblVble.fourthBox_lx && center.x<GblVble.fourthBox_rx){ 
                     if(center.x<GblVble.circle_76A_x)
                         GblVble.circle_76A_x = center.x-radius;
                     if(center.y<GblVble.circle_76A_y)
                         GblVble.circle_76A_y = center.y-radius;

                     if(center.x>GblVble.circle_100D_x)
                         GblVble.circle_100D_x = center.x-radius;
                     if(center.y>GblVble.circle_100D_y)
                         GblVble.circle_100D_y = center.y-radius;            
                 }
           }
          if(center.x> GblVble.RollBox_lx && center.y>GblVble.RollBox_ly && center.x<GblVble.RollBox_rx && center.y<GblVble.RollBox_ry){
                if(center.x<GblVble.Roll_mnl_x)
                    GblVble.Roll_mnl_x = (int)center.x-radius;
                if(center.y<GblVble.Roll_mnl_y)
                    GblVble.Roll_mnl_y = (int)center.y-radius;
                if(center.x>GblVble.Roll_mxr_x)
                    GblVble.Roll_mxr_x = (int)center.x-radius;
                if(center.y>GblVble.Roll_mxr_y)
                    GblVble.Roll_mxr_y = (int)center.y-radius;
           }
          if(center.x>GblVble.SerialBox_lx && center.y>GblVble.SerialBox_ly && center.x<GblVble.SerialBox_rx && center.y<GblVble.SerialBox_ry){
                if(center.x<GblVble.Serial_mnl_x)
                    GblVble.Serial_mnl_x = (int)center.x-radius;
                if(center.y<GblVble.Serial_mnl_y)
                    GblVble.Serial_mnl_y = (int)center.y-radius;
                if(center.x>GblVble.Serial_mxr_x)
                    GblVble.Serial_mxr_x = (int)center.x-radius;
                if(center.y>GblVble.Serial_mxr_y)
                    GblVble.Serial_mxr_y = (int)center.y-radius;          
          }
     }
           // Imgcodecs.imwrite("resources/output_file/detected_Hough_circle.jpg", cloned_image);
            // HighGui.imshow("detected circles", cloned_image);
           // HighGui.waitKey();
            //System.exit(0);
        
     /*
     Mat cloned_image2 = src.clone();
     //int j=1;

*/
    // System.out.println("Minimum x:"+minimum_x+" Minimum y:"+minimum_y+" Maximum x:"+maximum_x+"Maximum y"+maximum_y);
     //System.out.println("Roll box: Minimum x:"+minimum_rollX+" Minimum y:"+minimum_rollY+" Maximum x:"+maximum_rollX+"Maximum y"+maximum_rollY);
     circles.release();
    }
    
}

