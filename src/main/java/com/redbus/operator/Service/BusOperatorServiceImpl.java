package com.redbus.operator.Service;

import com.redbus.operator.entity.BusOperator;
import com.redbus.operator.entity.TicketCost;
import com.redbus.operator.payload.BusOperatorDto;
import com.redbus.operator.repository.BusOperatorRepository;

import com.redbus.operator.repository.TicketCostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BusOperatorServiceImpl implements BusOperatorService {
    private ModelMapper modelmapper;
    private BusOperatorRepository busoperatorrepository;
private TicketCostRepository ticketcostrepository;
    public BusOperatorServiceImpl(ModelMapper modelmapper, BusOperatorRepository busoperatorrepository, TicketCostRepository ticketcostrepository) {
        this.modelmapper = modelmapper;
        this.busoperatorrepository = busoperatorrepository;
        this.ticketcostrepository=ticketcostrepository;
    }

    @Override
    public BusOperatorDto scheduleBus(BusOperatorDto busoperatordto) {
        BusOperator busoperator=mapToEntity(busoperatordto);
        TicketCost ticketCost=new TicketCost();
        ticketCost.setTicketId(busoperatordto.getTicketCost().getTicketId());
        ticketCost.setCost(busoperatordto.getTicketCost().getCost());
        ticketCost.setCode(busoperatordto.getTicketCost().getCode());
        ticketCost.setDiscountAmount(busoperatordto.getTicketCost().getDiscountAmount());
busoperator.setTicketCost(ticketCost);
        String busId = UUID.randomUUID().toString();
        busoperator.setBusId(busId);
        BusOperator save = busoperatorrepository.save(busoperator);
        return  mapToDto(save);

    }

    @Override
    public List<BusOperatorDto> getAll() {
        List<BusOperator> buslist = busoperatorrepository.findAll();
        List<BusOperatorDto> Dto = buslist.stream()
                .map(BusOperator -> mapToDto(BusOperator))
                .collect(Collectors.toList());

return Dto;
    }

    @Override
    public List<BusOperatorDto> findBybusOperatorCompanyName(String busOperatorCompanyName) {
        List<BusOperator> busoperator = busoperatorrepository.findBybusOperatorCompanyName(busOperatorCompanyName);
        List<BusOperatorDto> collect = busoperator.stream()
                .map(BusOperator -> mapToDto(BusOperator))
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public BusOperatorDto updateBus(String busId, BusOperatorDto busOperatorDto) {
        return null;
    }

    @Override
    public void deleteBus(String busId) {

    }


    BusOperator mapToEntity(BusOperatorDto busoperatordto){
        BusOperator busoperator=modelmapper.map(busoperatordto,BusOperator.class);
        return busoperator;

   }

BusOperatorDto mapToDto(BusOperator busoperator){
    BusOperatorDto busoperatordto=modelmapper.map(busoperator,BusOperatorDto.class);
        return busoperatordto;
}

}
