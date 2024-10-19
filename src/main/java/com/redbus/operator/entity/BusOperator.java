package com.redbus.operator.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
@Entity
@Table(name="bus_operator")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusOperator {
    @Id
@Column(name="bus_id")
    private String busId;
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="bus_id")
    private TicketCost ticketCost;
    @Column(name="bus_name")
    private String busName;
    @Column(name="busOperator_CompanyName")
    private String busOperatorCompanyName;
    @Column(name="driver_Name")
    private String driverName;
    @Column(name="support_Staff")
    private String supportStaff;
    @Column(name="totalnumber_Of_Seats")
    private int totalnumberOfSeats;
    @Column(name="departure_City")
    private String departureCity;
    @Column(name="arrival_City")
    private String arrivalCity;
    @Column(name="departure_Time")
    private LocalTime departureTime;
    @Column(name="arrival_Time")
    private String arrivalTime;
    @Column(name="departure_Date")
    @Temporal(TemporalType.DATE)
    private Date departureDate;
    @Column(name="arrival_Date")
    @Temporal(TemporalType.DATE)
    private Date arrivalDate;
    @Column(name="total_Travel_Time")
    private double totalTravelTime;
    @Column(name="bus_Type")
    private String busType;
    @Column(name="amenities")
    private String amenities;
    @Column(name="available_seats")
    private int availableSeats;

    // Assuming departureDate is a java.sql.Date
    public LocalDate getTravelDate() {
        if (departureDate != null) {
            return new java.util.Date(departureDate.getTime())
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        } else {
            return null; // Handle null case appropriately in your application
        }
    }


}




