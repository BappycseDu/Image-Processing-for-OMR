/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package omrdetectionproject;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author smtareeq
 */
public class QRCodeReader {
    public static void main(String imagePath) {
        try {
            File file = new File(imagePath);
            BufferedImage bufferedImage = ImageIO.read(file);

            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
                    new BufferedImageLuminanceSource(bufferedImage)));

            Result result = new MultiFormatReader().decode(binaryBitmap);
            GblVble.qrCode = result.getText();
            bufferedImage = null;
           
           // System.out.println("QR Code Text: " + result.getText());
        } catch (IOException e) {
            System.err.println("Could not read the image: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error decoding QR code: " + e.getMessage());
            GblVble.qrCode = "Errorrrrrr";
        }
       
    }
    
}
