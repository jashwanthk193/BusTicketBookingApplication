package com.redbus.user.service;

import com.redbus.operator.entity.BusOperator;
import com.redbus.operator.entity.TicketCost;
import com.redbus.operator.repository.BusOperatorRepository;
import com.redbus.operator.repository.TicketCostRepository;
import com.redbus.user.Repository.BookingRepository;
import com.redbus.user.entity.Booking;
import com.redbus.user.payload.BookingDetailsDto;
import com.redbus.user.payload.passengerDetails;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class BookingService {
    @Value("${stripe.api.key}")
    private String stripeApiKey;
    private BusOperatorRepository busoperatorrepository;
    private TicketCostRepository ticketcostrepository;
    private BookingRepository bookingrepository;

    public BookingService(BusOperatorRepository busoperatorrepository, TicketCostRepository ticketcostrepository, BookingRepository bookingrepository) {
        this.busoperatorrepository = busoperatorrepository;
        this.ticketcostrepository = ticketcostrepository;
        this.bookingrepository = bookingrepository;
    }

    public BookingDetailsDto createBooking(String busId,String ticketId, passengerDetails passengerdetails) {
        BusOperator bus = busoperatorrepository.findById(busId).orElse(null);
        TicketCost ticketCost = ticketcostrepository.findById(ticketId).orElse(null);

        if (bus == null || ticketCost == null) {
            System.out.println("Bus or Ticket not found");
            return null;
        }

        // Validate booking date
        LocalDate bookingDate = LocalDate.now(); // Get current date
        LocalDate travelDate = bus.getTravelDate();

        if (travelDate.isBefore(bookingDate)) {
            System.out.println("Cannot book for past dates");
            return null;
        }

        String paymentIntent = createPaymentIntent((int) ticketCost.getCost());
        if (paymentIntent != null) {
            Booking booking = new Booking();
            String bookingid = UUID.randomUUID().toString();
            booking.setBookingId(bookingid);
            booking.setToCity(bus.getArrivalCity());
            booking.setFromCity(bus.getDepartureCity());
            booking.setBusCompany(bus.getBusOperatorCompanyName());
            booking.setPrice(ticketCost.getCost());
            booking.setFirstName(passengerdetails.getFirstName());
            booking.setLastName(passengerdetails.getLastName());
            booking.setEmail(passengerdetails.getEmail());
            booking.setMobile(passengerdetails.getMobile());
            Booking ticketCreateDetails = bookingrepository.save(booking);
            BookingDetailsDto dto = new BookingDetailsDto();
            dto.setBookingId(ticketCreateDetails.getBookingId());
            dto.setFirstName(ticketCreateDetails.getFirstName());
            dto.setLastName(ticketCreateDetails.getLastName());
            dto.setPrice(ticketCreateDetails.getPrice());
            dto.setEmail(ticketCreateDetails.getEmail());
            dto.setMobile(ticketCreateDetails.getMobile());
            dto.setBusCompany(ticketCreateDetails.getBusCompany());
            dto.setFromCity(ticketCreateDetails.getFromCity());
            dto.setToCity(ticketCreateDetails.getToCity());
            dto.setMessage("Booking confirmed");
            return dto;
        } else {
            System.out.println("Error creating payment intent");
        }
        return null;
    }

    public String createPaymentIntent(Integer amount) {
        Stripe.apiKey = stripeApiKey;
        try {
            PaymentIntent intent = PaymentIntent.create(
                    new PaymentIntentCreateParams.Builder()
                            .setCurrency("usd")
                            .setAmount((long) amount)
                            .build());

            return generateResponse(intent.getClientSecret());
        } catch (StripeException e) {
            System.out.println("Payment failed: " + e.getMessage());
            return null;
        }
    }

    private String generateResponse(String clientSecret) {
        return "{\"clientSecret\":\"" + clientSecret + "\"}";
    }
}
