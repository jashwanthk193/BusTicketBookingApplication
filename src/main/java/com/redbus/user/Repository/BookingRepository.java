package com.redbus.user.Repository;

import com.redbus.user.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookingRepository  extends JpaRepository<Booking,String> {


}
