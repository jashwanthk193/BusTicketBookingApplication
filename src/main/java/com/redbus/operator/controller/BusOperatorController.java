package com.redbus.operator.controller;

import com.redbus.operator.Service.BusOperatorService;
import com.redbus.operator.payload.BusOperatorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/api/bus-operator")
public class BusOperatorController {
    private BusOperatorService busOperatorService;

    public BusOperatorController(BusOperatorService busoperatorservice) {
        this.busOperatorService = busoperatorservice;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<BusOperatorDto> scheduleBus(@RequestBody BusOperatorDto busOperatorDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        System.out.println("User roles: " + authorities);

        if (!SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new AccessDeniedException("You do not have permission to perform this action");
        }

        // Your scheduling logic here

        BusOperatorDto dto = busOperatorService.scheduleBus(busOperatorDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BusOperatorDto>> findAllData() {
        List<BusOperatorDto> dto = busOperatorService.getAll();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BusOperatorDto>> findByName(@RequestParam String busOperatorCompanyName) {
        List<BusOperatorDto> dto = busOperatorService.findBybusOperatorCompanyName(busOperatorCompanyName);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{busId}")
    public ResponseEntity<BusOperatorDto> updateBus(
            @PathVariable String busId,
            @RequestBody BusOperatorDto busOperatorDto) {
        BusOperatorDto updatedBus = busOperatorService.updateBus(busId, busOperatorDto);
        return new ResponseEntity<>(updatedBus, HttpStatus.OK);
    }

    // **New Delete Endpoint**
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{busId}")
    public ResponseEntity<Void> deleteBus(@PathVariable String busId) {
        busOperatorService.deleteBus(busId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
