package omrdetectionproject;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BarCodeReader {
    public static void main(String imagePath) {
        try {
            File file = new File(imagePath);
            BufferedImage bufferedImage = ImageIO.read(file);

            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
                    new BufferedImageLuminanceSource(bufferedImage)));

            Result result = new MultiFormatReader().decode(binaryBitmap);
            GblVble.barCode = result.getText();
            
            bufferedImage = null;
            //System.out.println("Barcode Text: " + result.getText());
        } catch (IOException e) {
            System.err.println("Could not read the image: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error decoding barcode: " + e.getMessage());
            GblVble.barCode = "Errorrrrrr";
        }
    }
}
