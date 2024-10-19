package com.redbus.user.controller;

import com.redbus.operator.entity.BusOperator;
import com.redbus.user.payload.BuslistDto;
import com.redbus.user.service.SearchBusesService;
import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class FindBusesController {
    //http://localhost:8080/api/user/searchBuses?departureCity=&arrivalCity=&departureDate
    private SearchBusesService searchbusesservice;

    public FindBusesController(SearchBusesService searchbusesservice) {
        this.searchbusesservice = searchbusesservice;
    }

    @GetMapping("/searchBuses")
   public List<BuslistDto> searchBuses(
           @RequestParam("departureCity")String departureCity,
           @RequestParam("arrivalCity")String arrivalCity,
           @RequestParam("departureDate")@DateTimeFormat(pattern = "yyyy-MM-dd") Date departureDate){

        List<BuslistDto> buslistDtos = searchbusesservice.searchBusBy(departureCity, arrivalCity, departureDate);
        return buslistDtos;
   }
}
