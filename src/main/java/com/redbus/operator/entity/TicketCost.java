package com.redbus.operator.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;

@Entity
@Table(name="ticket_costs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketCost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column(name="ticket_id",unique=true)
    private String ticketId;
    @OneToOne(mappedBy="ticketCost")
    @JoinColumn(name="bus_id")
    private BusOperator busoperator;
    private double cost;
    private String code;
    @Column(name="discount_amount",unique=true)
    private double discountAmount;


}
