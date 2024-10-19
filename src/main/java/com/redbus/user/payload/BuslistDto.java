package com.redbus.user.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuslistDto {
    private String busId;

    private String busName;

    private String busOperatorCompanyName;



    private int totalnumberOfSeats;
    private  int availableSeats;

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
}

