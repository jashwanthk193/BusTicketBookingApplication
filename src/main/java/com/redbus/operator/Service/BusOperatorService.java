package com.redbus.operator.Service;

import com.redbus.operator.payload.BusOperatorDto;

import java.util.List;


public interface BusOperatorService {


    BusOperatorDto scheduleBus(BusOperatorDto busoperatordto);


    List<BusOperatorDto> getAll();


    List<BusOperatorDto> findBybusOperatorCompanyName(String busOperatorCompanyName);


    void deleteBus(String busId);

    BusOperatorDto updateBus(String busId, BusOperatorDto busOperatorDto);
}
