package com.redbus.user.service;


    import com.redbus.operator.entity.BusOperator;
import com.redbus.operator.repository.BusOperatorRepository;
import com.redbus.user.payload.BuslistDto;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

    @Service
    public class SearchBusesService {
        private BusOperatorRepository busoperatorrepository;

        public SearchBusesService(BusOperatorRepository busoperatorrepository) {
            this.busoperatorrepository = busoperatorrepository;
        }
        public List<BuslistDto> searchBusBy(String departureCity, String arrivalCity, Date departureDate ){
            List<BusOperator> busesAvailable = busoperatorrepository.findByDepartureCityAndArrivalCityAndDepartureDate(departureCity, arrivalCity, departureDate);
            List<BuslistDto> dtos = busesAvailable.stream().map(bus -> mapToDto(bus)).collect(Collectors.toList());

            return dtos ;
        }
        BuslistDto mapToDto(BusOperator busoperator){
            BuslistDto buslistdto = new BuslistDto();
            buslistdto.setBusId(busoperator.getBusId());
            buslistdto.setBusName(busoperator.getBusName());
            buslistdto.setBusType(busoperator.getBusType());
            buslistdto.setDepartureDate(busoperator.getDepartureDate());
            buslistdto.setArrivalDate(busoperator.getArrivalDate());
            buslistdto.setDepartureCity(busoperator.getDepartureCity());
            buslistdto.setArrivalCity(busoperator.getArrivalCity());
            buslistdto.setBusOperatorCompanyName(busoperator.getBusOperatorCompanyName());
            buslistdto.setTotalnumberOfSeats(busoperator.getTotalnumberOfSeats());
            buslistdto.setAvailableSeats(busoperator.getAvailableSeats());
            buslistdto.setTotalTravelTime(busoperator.getTotalTravelTime());
            buslistdto.setAmenities(busoperator.getAmenities());
            buslistdto.setDepartureTime(busoperator.getDepartureTime());
            buslistdto.setArrivalTime(busoperator.getArrivalTime());
            return buslistdto;

        }
    }


