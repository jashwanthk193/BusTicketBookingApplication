package com.redbus.operator.payload;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.redbus.operator.entity.TicketCost;
import com.redbus.operator.util.CustomDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusOperatorDto {
    private String busId;

    private String busName;

    private String busOperatorCompanyName;

    private String driverName;

    private String supportStaff;

    private int totalnumberOfSeats;

    private String departureCity;

    private String arrivalCity;
@JsonFormat(pattern="HH:mm:ss")
    private LocalTime departureTime;
    @JsonFormat(pattern="HH:mm:ss")
    private String arrivalTime;
    @JsonFormat(pattern="dd:MM:yyyy")
    //@JsonDeserialize(using= CustomDateDeserializer.class)
    private Date departureDate;
    @JsonFormat(pattern="dd:MM:yyyy")
    //@JsonDeserialize(using=CustomDateDeserializer.class)
    private Date arrivalDate;

    private double totalTravelTime;

    private String busType;

    private String amenities;
    private TicketCost ticketCost;
    private int availableSeats;
}
