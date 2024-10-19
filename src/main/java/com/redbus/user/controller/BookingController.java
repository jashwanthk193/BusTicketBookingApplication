package com.redbus.user.controller;

import com.redbus.user.payload.BookingDetailsDto;
import com.redbus.user.payload.passengerDetails;
import com.redbus.user.service.BookingService;
import com.redbus.util.PdfService;
import com.redbus.util.mailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking")
public class BookingController {
    private BookingService bookingservice;
    private mailService mailservice;
    private PdfService pdfservice;

    public BookingController(BookingService bookingservice, mailService mailservice, PdfService pdfservice) {
        this.bookingservice = bookingservice;
        this.mailservice = mailservice;
        this.pdfservice = pdfservice; // Now it's correctly initialized
    }




    //http://localhostt:8080/api/booking?busId=&ticketId=
    @PostMapping
    public ResponseEntity<BookingDetailsDto> bookingbus(@RequestParam("busId")String busId,
                                                        @RequestParam("ticketId")String ticketId,
                                                        @RequestBody passengerDetails passengerdetails) {
        BookingDetailsDto dto = bookingservice.createBooking(busId, ticketId, passengerdetails);
        if (dto != null) {
            byte[] pdfBytes = pdfservice.generatePdf(dto);
            sendBookingConfirmationEmailWithAttachment(passengerdetails, dto, pdfBytes);
        }

//            mailservice.sendEmail(
//                    passengerdetails.getEmail(),
//                    "Booking confirmed. Booking Id: " + dto.getBookingId(),
//                    "Your booking is confirmed.\n" +
//                            "Email: " + passengerdetails.getEmail() + "\n" +
//                            "Name: " + passengerdetails.getFirstName() + " " + passengerdetails.getLastName()+ "\n" +"mobile:"+passengerdetails.getMobile()
//            );

        return new ResponseEntity<>(dto, HttpStatus.CREATED);

    }
    private void  sendBookingConfirmationEmailWithAttachment(
            passengerDetails passengerdetails,BookingDetailsDto bookigdetailsdto,byte[] pdfbytes){
        String emailsubject="Booking confirmed.Bookind Id: "+bookigdetailsdto.getBookingId();
        String emailBody=String.format("your Booking is Confirmed\nNam:%s %s",passengerdetails.getFirstName(),passengerdetails.getLastName());
        mailservice.sendEmailWithAttachment(
                passengerdetails.getEmail(),emailsubject,emailBody,pdfbytes,"Booking is confirmed.pdf"
        );
    }


}

