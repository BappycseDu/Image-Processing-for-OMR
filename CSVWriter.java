/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package omrdetectionproject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author smtareeq
 */
public class CSVWriter {
    public static void main(String[] args) {
        // Define the CSV file path
        String csvFile = "resources/output.csv";

        // Sample data to write to CSV
        String[] headers = {"QRCode","BarCode","Roll","serial","result"};
        String[][] data = {
            {"1", "Alice", "23"},
            {"2", "Bob", "34"},
            {"3", "Charlie", "29"}
        };

        // Create a File object
        File file = new File(csvFile);

        // Try-with-resources to ensure the writer is closed after the operation
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Write headers
            writer.write(String.join(",", headers));
            writer.newLine();

            // Write data rows
            for (String[] row : data) {
                writer.write(String.join(",", row));
                writer.newLine();
            }
            

            System.out.println("CSV file written successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
