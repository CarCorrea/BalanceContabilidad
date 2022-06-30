package com.integracion.balances.controller;


import com.integracion.balances.model.Balance;
import com.integracion.balances.model.BalanceDocumento;
import com.integracion.balances.service.BalanceService;
import com.integracion.balances.service.BoletaService;
import com.integracion.balances.service.FacturaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/balance")
public class BoletaController {

    private final BoletaService boletaService;
    private final FacturaService facturaService;
    private final BalanceService balanceService;

    public BoletaController(BoletaService boletaService, FacturaService facturaService, BalanceService balanceService) {
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

    @GetMapping
    public Balance balance(){
        return balanceService.getBalance();
    }
}
