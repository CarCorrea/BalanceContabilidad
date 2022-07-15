package com.integracion.balances.controller;


import com.integracion.balances.model.Balance;
import com.integracion.balances.model.BalanceDocumento;
import com.integracion.balances.service.BalanceService;
import com.integracion.balances.service.BoletaService;
import com.integracion.balances.service.FacturaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/balance")
public class BalanceController {

    private final BoletaService boletaService;
    private final FacturaService facturaService;
    private final BalanceService balanceService;

    public BalanceController(BoletaService boletaService, FacturaService facturaService, BalanceService balanceService) {
        this.boletaService = boletaService;
        this.facturaService = facturaService;
        this.balanceService = balanceService;
    }

    @GetMapping("/balanceBoletas")
    public BalanceDocumento boletas(){
        return boletaService.getBalance();
    }

    @GetMapping("/balanceFacturas")
    public BalanceDocumento facturas(){
        return facturaService.getBalance();
    }

    @GetMapping("/balanceGeneral")
    public Balance balance(){
        return balanceService.getBalance();
    }

    @GetMapping("/balance/{initialDate}+{finalDate}")
    public Balance filteredBalance(@PathVariable("initialDate") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate initialDate,
                                   @PathVariable("finalDate") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate finalDate){
        return balanceService.getFilteredBalance(initialDate, finalDate);
    }
}
