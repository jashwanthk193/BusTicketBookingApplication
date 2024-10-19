package com.redbus.user.entity;

import com.redbus.operator.entity.BusOperator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="bookings")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Booking {
    @Id

    private String bookingId;
    @Column(name="bus_id")
    private String busId;
    @Column(name="ticket_id")
    private String ticketId;
    @Column(name="bus_company")
    private String busCompany;
    @Column(name="to_city")
    private String toCity;
    @Column(name="from_city")
    private String fromCity;
    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    private String email;
    private String mobile;
    private double price;



}
