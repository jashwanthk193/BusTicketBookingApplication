package com.redbus.operator.repository;

import com.redbus.operator.entity.BusOperator;
import com.redbus.operator.payload.BusOperatorDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface BusOperatorRepository extends JpaRepository<BusOperator,String> {
public List<BusOperator> findBybusOperatorCompanyName(String busOperatorCompanyName);
List<BusOperator> findByDepartureCityAndArrivalCityAndDepartureDate(String departureCity, String arrivalCity, Date departureDate);
    @Query("SELECT bo FROM BusOperator bo WHERE bo.departureCity = :departureCity AND bo.arrivalCity = :arrivalCity AND bo.departureDate = :departureDate")
    List<BusOperator> searchByDepartureCityAndArrivalCityAndDepartureDate(
            @Param("departureCity") String departureCity,
            @Param("arrivalCity") String arrivalCity,
            @Param("departureDate") LocalDate departureDate
    );// Inside BusOperatorRepository
    @Modifying
    @Query("UPDATE BusOperator b SET b.availableSeats = :availableSeats WHERE b.id = :busId")
    void updateAvailableSeats(@Param("busId") String busId, @Param("availableSeats") int availableSeats);


}
