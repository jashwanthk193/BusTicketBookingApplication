package com.redbus.util;
import com.redbus.user.payload.BookingDetailsDto;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    public byte[] generatePdf(BookingDetailsDto bookingDetailsDto) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             PdfWriter writer = new PdfWriter(outputStream);
             PdfDocument pdfDocument = new PdfDocument(writer)) {

            try (Document document = new Document(pdfDocument)) {
                document.add(new Paragraph("Booking Details"));
                document.add(new Paragraph("Booking ID: " + bookingDetailsDto.getBookingId()));
                document.add(new Paragraph("Bus ID: " + bookingDetailsDto.getBookingId()));

                document.add(new Paragraph("Bus Company: " + bookingDetailsDto.getBusCompany()));
                document.add(new Paragraph("From City: " + bookingDetailsDto.getFromCity()));
                document.add(new Paragraph("To City: " + bookingDetailsDto.getToCity()));
                document.add(new Paragraph("Name: " + bookingDetailsDto.getFirstName() + " " + bookingDetailsDto.getLastName()));
                document.add(new Paragraph("Email: " + bookingDetailsDto.getEmail()));
                document.add(new Paragraph("Mobile: " + bookingDetailsDto.getMobile()));
                document.add(new Paragraph("Price: " + bookingDetailsDto.getPrice()));
            }

            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
