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
import java.util.Arrays;

/**
 *
 * @author smtareeq
 */
public class InnerRectangle {
    static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    public static void detect(String imagePath) {
        // Load the image
       // String imagePath = "resources/input_file/1300002.jpg";
        Mat image = Imgcodecs.imread(imagePath, Imgcodecs.IMREAD_COLOR);
        if (image.empty()) {
            System.out.println("Could not open or find the image");
            return;
        }

        // Convert to grayscale
        Mat gray = new Mat();
        Imgproc.cvtColor(image, gray, Imgproc.COLOR_BGR2GRAY);
        image.release();
        // Apply Gaussian blur
        Imgproc.GaussianBlur(gray, gray, new Size(5, 5), 0);

        // Threshold the image to create a binary image
        Mat binary = new Mat();
        Imgproc.threshold(gray, binary, 128, 255, Imgproc.THRESH_BINARY_INV);
        gray.release();
        // Detect contours
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(binary, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        //Imgcodecs.imwrite("resources/output_file/binary.jpg", binary);
        binary.release();
        hierarchy.release();
        int i = 0;
        char[] rollNumber = new char[7];
        char[] serialNumber = new char[6];
        char[] AnswerShit = new char[100];
        Arrays.fill(AnswerShit,'#');
        Arrays.fill(rollNumber, '$');
        Arrays.fill(serialNumber, '!');
        for (MatOfPoint contour : contours) {
            // Calculate contour area
            double area = Imgproc.contourArea(contour);
            if (area < 50) continue;  // Skip small contours

            // Approximate contour to a polygon
            MatOfPoint2f contour2f = new MatOfPoint2f(contour.toArray());
            MatOfPoint2f approxCurve = new MatOfPoint2f();
            Imgproc.approxPolyDP(contour2f,approxCurve, 0.02 * Imgproc.arcLength(contour2f, true), true);
            contour2f.release();  
            // Get bounding box of the contour
            Rect boundingRect = Imgproc.boundingRect(new MatOfPoint(approxCurve.toArray()));
           
           approxCurve.release();
            /*
            Mat roi1 = new Mat(gray, boundingRect);

            // Calculate the number of non-zero pixels (white pixels)
            int count1 = Core.countNonZero(roi1);

            // Define a threshold to consider the shape as marked
            boolean isMarked1 = count1 > (boundingRect.width * boundingRect.height) * 0.10;  // Example threshold: more than 50% of the bounding box area is marked

            // Draw the contour and label
            Scalar color1 = isMarked1 ? new Scalar(0, 255, 0) : new Scalar(0, 0, 255);
                
            Imgproc.drawContours(image, List.of(contour), -1, color1, 2);
                
            String xCo1= String.valueOf(boundingRect.x);
            String yCo1 = String.valueOf(boundingRect.y);
            String CoOrdinate1 = xCo1+" , "+yCo1;
            String label1 = isMarked1 ? CoOrdinate1 : " ";
            Scalar color2 = new Scalar(0, 200, 0);
            Imgproc.putText(image, label1, new Point(boundingRect.x, boundingRect.y - 40), Imgproc.FONT_HERSHEY_SIMPLEX, 0.4, color2, 1);
               */
            
           
            
            if(boundingRect.x >GblVble.OmrBox_tl_x && boundingRect.y>GblVble.OmrBox_tl_y && boundingRect.x<GblVble.OmrBox_br_x && boundingRect.y<GblVble.OmrBox_br_y){
                 //boundingRect.x = boundingRect.x+5;
                 //boundingRect.y = boundingRect.y+5;
            //  System.out.println("the height and width is :" +boundingRect.height+" "+boundingRect.width);
            //System.out.println("points:+"+boundingRect.x+" , "+boundingRect.y);
                /*
                // Extract the region of interest (ROI) around the detected shape
                Mat roi = new Mat(gray, boundingRect);

                // Calculate the number of non-zero pixels (white pixels)
                int count = Core.countNonZero(roi);
                    roi.release();
                // Define a threshold to consider the shape as marked
                boolean isMarked = count > (boundingRect.width * boundingRect.height) * 0.10;  // Example threshold: more than 50% of the bounding box area is marked

                // Draw the contour and label
                Scalar color = isMarked ? new Scalar(0, 255, 0) : new Scalar(0, 0, 255); 
                // Green for marked, Red for unmarked
                
                // double top_Left_y = 1080;
                //double top_Left_x = 145;
                //double bottom_Right_y =2181;
                //double bottom_Right_x = 953;
            
                 */
                double y_gap = (GblVble.circle_25D_y-GblVble.circle_1A_y)/24;
                
                
                //int box_size = 1000/4; //maximum value of x / 4 boxes
                //double box_size = x_gap*3;
                //int box_difference = 131;
                //int row_number = (int) (Math.round((boundingRect.y-top_Left_y)/y_gap))+1;
                int row_number = (int) (Math.round((boundingRect.y-GblVble.circle_1A_y)/y_gap))+1;
                 // int question_number = (int) (row_number+Math.floor(boundingRect.x/box_size)*25);
                int question_number = 0; 
                int letter_number = 0;
               
                if(boundingRect.x>GblVble.firstBox_lx && boundingRect.x<GblVble.firstBox_rx){
                   question_number = row_number;
                   double x_gap = ((GblVble.circle_25D_x-GblVble.circle_1A_x)/3);
                   letter_number = (int)Math.round((boundingRect.x-GblVble.circle_1A_x)/x_gap)+1;
                }
               
                    /* if(boundingRect.x<(top_Left_x+box_size)){ 
                     question_number = row_number+0*25;
                     letter_number = (int)Math.round((boundingRect.x-top_Left_x)/x_gap)+1;
                       }   */
              
                else if(boundingRect.x>GblVble.secondBox_lx && boundingRect.x<GblVble.secondBox_rx){
                   double x_gap = ((GblVble.circle_50D_x-GblVble.circle_26A_x)/3);
                   question_number = row_number+25;
                   letter_number = (int)Math.round((boundingRect.x-GblVble.circle_26A_x)/x_gap)+1;
                }
               
                /* else if(boundingRect.x<(top_Left_x+box_difference+2*box_size)){ 
                    question_number = row_number+1*25;
                    letter_number = (int)Math.round((boundingRect.x-top_Left_x-(box_difference+box_size))/x_gap)+1;
                }*/
               else if(boundingRect.x>GblVble.thirdBox_lx && boundingRect.x<GblVble.thirdBox_rx){
                   question_number = row_number+2*25;
                   double x_gap = ((GblVble.circle_75D_x-GblVble.circle_51A_x)/3);
                   letter_number = (int)Math.round((boundingRect.x-GblVble.circle_51A_x)/x_gap)+1;
               }
               /* else if(boundingRect.x<(top_Left_x+2*box_difference+3*box_size)){ 
                    question_number = row_number+2*25;
                    letter_number = (int)Math.round((boundingRect.x-top_Left_x-2*(box_difference+box_size))/x_gap)+1;
                }*/
               else if(boundingRect.x>GblVble.fourthBox_lx && boundingRect.x<GblVble.fourthBox_rx){ //can't use else only because student can mark betwen two boxes
                     question_number = row_number+3*25;  
                     double x_gap = ((GblVble.circle_100D_x-GblVble.circle_76A_x)/3);
                     letter_number = (int)Math.round((boundingRect.x-GblVble.circle_76A_x)/x_gap)+1;
                }
                //System.out.println("question number: "+question_number+" letter_number:"+letter_number);
                //int letter_number = (int)Math.round((boundingRect.x-(int)Math.floor(boundingRect.x/250)*250-60)/y_gap)+1 ;
                if(question_number!=0){
                    
                    char letter;
                    if(letter_number>=1 && letter_number<=4){
                        if(AnswerShit[question_number-1]=='#'){
                            letter = (char)(letter_number+64);
                            AnswerShit[question_number-1] = letter;
                        }
                        else
                            AnswerShit[question_number-1] = 'X';
                    }
                    else 
                        System.out.println("question number "+question_number+" has invalid answer");
                   
                  // System.out.println("question number: "+question_number+" "+letter);   
                    
                  /*  String xCo= String.valueOf(boundingRect.x);
                    String yCo = String.valueOf(boundingRect.y);
                    String CoOrdinate = xCo+" , "+yCo;
                    String label = isMarked ? CoOrdinate : " ";
                    Imgproc.putText(image, label, new Point(boundingRect.x, boundingRect.y - 10), Imgproc.FONT_HERSHEY_SIMPLEX, 0.4, color, 2);
                        //System.out.println("Bubble number:"+i+" co ordinates: " + boundingRect.x + ", " + boundingRect.y);
*/

                   // Imgproc.drawContours(image, List.of(contour), -1, color, 2);

                    //System.out.println("Bubble at: " + boundingRect.x + ", " + boundingRect.y);
                }
            }
            if(boundingRect.x>(GblVble.RollBox_lx) && boundingRect.y>(GblVble.RollBox_ly) && boundingRect.x<(GblVble.RollBox_rx) && boundingRect.y<(GblVble.RollBox_ry)){
               //System.out.println(GblVble.RollBox_lx+" "+boundingRect.x);
                boundingRect.x = boundingRect.x+10;
                boundingRect.y = boundingRect.y+10;
                    
                /*
                // Extract the region of interest (ROI) around the detected shape
                
                Mat roi = new Mat(gray, boundingRect);

                // Calculate the number of non-zero pixels (white pixels)
                int count = Core.countNonZero(roi);

                // Define a threshold to consider the shape as marked
                boolean isMarked = count > (boundingRect.width * boundingRect.height) * 0.10;  // Example threshold: more than 50% of the bounding box area is marked

                // Draw the contour and label
                Scalar color = isMarked ? new Scalar(0, 255, 0) : new Scalar(0, 0, 255); 
                // Green for marked, Red for unmarked
                 
                String xCo= String.valueOf(boundingRect.x);
                String yCo = String.valueOf(boundingRect.y);
                String CoOrdinate = xCo+" , "+yCo;
                String label = isMarked ? CoOrdinate : " ";
                //System.out.println("point :"+boundingRect.x+" "+boundingRect.y);
                Imgproc.putText(image, label, new Point(boundingRect.x, boundingRect.y - 10), Imgproc.FONT_HERSHEY_SIMPLEX, 0.6, color, 2);
                
                */
                //int y_gap =(int)Math.round(991-633)/
                double x_gap =Math.round(GblVble.Roll_mxr_x-GblVble.Roll_mnl_x)/6;
                double y_gap = Math.round(GblVble.Roll_mxr_y-GblVble.Roll_mnl_y)/9;
                //System.out.println("xgap:"+x_gap+" ygap:"+y_gap);
                //double colomn = (boundingRect.x-518)/x_gap;
                //System.out.println("exact value of coloumn:"+colomn);
                int coloumn_number = (int)Math.floor((boundingRect.x-GblVble.Roll_mnl_x)/x_gap);
                int row_number =(int) Math.floor((boundingRect.y-GblVble.Roll_mnl_y)/y_gap);
               
                //System.out.println("roll calculation: coloumn: "+coloumn_number+"row_number: "+row_number+" y value:"+boundingRect.y );
               //System.out.println("coloumn number(roll) is: "+coloumn_number+"row :"+ row_number);
               if(coloumn_number < 0)
                   coloumn_number= 0;
               if(coloumn_number>6)
                   coloumn_number=6;
               if(row_number<0)
                   row_number = 0;
               if(rollNumber[coloumn_number] == '$'){
                  rollNumber[coloumn_number] = (char)(row_number+'0');
               }
               else{
                   rollNumber[coloumn_number] = 'X';
                  // System.out.println(" repeated in "+ coloumn_number+" roll:"+rollNumber[coloumn_number]);
               }
                
            }
            if(boundingRect.x>(GblVble.SerialBox_lx-20) && boundingRect.y>(GblVble.SerialBox_ly-20) && boundingRect.x<(GblVble.SerialBox_rx+20) && boundingRect.y<(GblVble.SerialBox_ry+20)){
                // Extract the region of interest (ROI) around the detected shape
                boundingRect.x = boundingRect.x+10;
                boundingRect.y = boundingRect.y+10;
                
                /*
                Mat roi = new Mat(gray, boundingRect);

                // Calculate the number of non-zero pixels (white pixels)
                int count = Core.countNonZero(roi);

                // Define a threshold to consider the shape as marked
                boolean isMarked = count > (boundingRect.width * boundingRect.height) * 0.10;  // Example threshold: more than 50% of the bounding box area is marked

                // Draw the contour and label
                Scalar color = isMarked ? new Scalar(0, 255, 0) : new Scalar(0, 0, 255); 
                // Green for marked, Red for unmarked
              /*  
                String xCo= String.valueOf(boundingRect.x);
                String yCo = String.valueOf(boundingRect.y);
                String CoOrdinate = xCo+" , "+yCo;
                String label = isMarked ? CoOrdinate : " ";
                //System.out.println("point :"+boundingRect.x+" "+boundingRect.y);
                //Imgproc.putText(image, label, new Point(boundingRect.x, boundingRect.y - 10), Imgproc.FONT_HERSHEY_SIMPLEX, 0.6, color, 2);
                
                */
                //int y_gap =(int)Math.round(991-633)/
                double x_gap =Math.round(GblVble.Serial_mxr_x-GblVble.Serial_mnl_x)/5;
                double y_gap = Math.round(GblVble.Serial_mxr_y-GblVble.Serial_mnl_y)/9;
                //System.out.println("xgap:"+x_gap+" ygap:"+y_gap);
                //double colomn = (boundingRect.x-518)/x_gap;
                //System.out.println("exact value of coloumn:"+colomn);
                int coloumn_number = (int)Math.floor((boundingRect.x-GblVble.Serial_mnl_x)/x_gap);
                int row_number =(int) Math.floor((boundingRect.y-GblVble.Serial_mnl_y)/y_gap);
               
                //System.out.println("roll calculation: coloumn: "+coloumn_number+"row_number: "+row_number+" y value:"+boundingRect.y );
               // System.out.println("coloumn number(serial) is: "+coloumn_number+ "row number:"+row_number);
               if(coloumn_number < 0)
                   coloumn_number= 0;
               if(coloumn_number>5)
                   coloumn_number=5;
               if(row_number<0)
                   row_number = 0;
               if(serialNumber[coloumn_number] == '!'){
                  serialNumber[coloumn_number] = (char)(row_number+'0');
               }
               else{
                   serialNumber[coloumn_number] = 'X';
                   //System.out.println(" repeated in serial to  "+ coloumn_number+" roll:"+rollNumber[coloumn_number]);
               }
                
                
                
            }
        }
        System.gc();
        GblVble.roll = new String(rollNumber);
        GblVble.serial = new String(serialNumber);
        GblVble.result = new String(AnswerShit);
       
       System.out.print("roll number: ");
        for(int j=0;j<7;j++){
            System.out.print(rollNumber[j]);
        }
        System.out.println(" ");
        
        
        
        System.out.print("serial number: ");
        for(int j=0;j<6;j++){
            System.out.print(serialNumber[j]);
        }
        System.out.println(" ");
        
        
        
        for(int j=0;j<100;j++){
            System.out.println("question:"+(j+1)+" answer: "+AnswerShit[j]);
        }
        
        
        

        // Show the output image with detected and labeled shapes
        //Imgcodecs.imwrite("resources/output_file/coordinate_of_contours.jpg", image);
        //System.out.println("Output image saved in /omrDetectionProject/resources/output_file ");
        
  
    }
    
    
}
