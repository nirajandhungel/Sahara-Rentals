package com.sahara.service;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sahara.model.Rentals;

public class InvoiceGenerator {

    public static void generateInvoice(Rentals rental, String outputPath) {
        Document document = new Document();
        try {
            // Create a PDF writer
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));

            // Open the document
            document.open();

            // Add title
            document.add(new Paragraph("Vehicle Rental Invoice"));
            document.add(new Paragraph("\n"));

            // Add rental details
            document.add(new Paragraph("Rental Details:"));
            document.add(new Paragraph("\n"));

            // Create a table for rental details
            PdfPTable table = new PdfPTable(2); // Two columns
            table.addCell("Vehicle Name:");
            table.addCell(rental.getVehicleName());
            table.addCell("Rental Date:");
            table.addCell(rental.getRentalDate().toString());
            table.addCell("Return Date:");
            table.addCell(rental.getReturnDate() != null ? rental.getReturnDate().toString() : "N/A");
            table.addCell("Total Cost:");
            table.addCell("$" + rental.getTotalCost());
            table.addCell("Status:");
            table.addCell(rental.getStatus());

            // Add the table to the document
            document.add(table);

            // Add a footer
            document.add(new Paragraph("\nThank you for using our Vehicle Rental System!"));

            System.out.println("Invoice generated successfully at: " + outputPath);
        } catch (DocumentException | IOException e) {
            System.err.println("Error generating invoice: " + e.getMessage());
        } finally {
            // Close the document
            document.close();
        }
    }
}