package com.integracion.balances.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.integracion.balances.client.BoletaClient;
import com.integracion.balances.client.FacturaClient;
import com.integracion.balances.model.Balance;
import com.integracion.balances.model.BalanceDocumento;
import com.integracion.balances.model.boleta.BoletaResponse;
import com.integracion.balances.model.factura.FacturaResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BalanceService {

    private final BoletaClient boletaClient;
    private final FacturaClient facturaClient;

    public BalanceService(BoletaClient boletaClient, FacturaClient facturaClient) {
        this.boletaClient = boletaClient;
        this.facturaClient = facturaClient;
    }

    public Balance getBalance(){

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        List<BoletaResponse> boletaResponse = mapper.convertValue(boletaClient.getBoletas(),
                new TypeReference<List<BoletaResponse>>() {});

        List<FacturaResponse> facturaResponse = mapper.convertValue(facturaClient.getFacturas(),
                new TypeReference<List<FacturaResponse>>() {});

        Balance balance = new Balance();

        List<Integer> montoBoletas = boletaResponse.stream().map(boletaResponse1 ->
                boletaResponse1.getMontoBoleta()).collect(Collectors.toList());

        int cantidadBoletas = boletaClient.getBoletas().size();
        int sumaBoletas = montoBoletas.stream().mapToInt(Integer::intValue).sum();

        List<Integer> montoFacturas = facturaResponse.stream().map(facturaResponse1 ->
                facturaResponse1.getMontoFactura()).collect(Collectors.toList());

        int cantidadFacturas = facturaClient.getFacturas().size();
        int sumaFacturas = montoFacturas.stream().mapToInt(Integer::intValue).sum();

        int montoTotalBalance = sumaBoletas - sumaFacturas;

        //---------------Balance documento boletas-------------------------

        BalanceDocumento balanceBoletas = new BalanceDocumento();
        balanceBoletas.setMontoTotal(sumaBoletas);
        balanceBoletas.setCantidadDocumentos(cantidadBoletas);

        //---------------Balance documento facturas-------------------------

        BalanceDocumento balanceFacturas = new BalanceDocumento();

        balanceFacturas.setCantidadDocumentos(cantidadFacturas);
        balanceFacturas.setMontoTotal(sumaFacturas);

        //-------------Balance Final ----------------------------------------

        balance.setBoletas(balanceBoletas);
        balance.setFacturas(balanceFacturas);
        balance.setBalanceFinal(montoTotalBalance);

        return balance;
    }

    public Balance getFilteredBalance(LocalDate initialDate, LocalDate finalDate){

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        List<BoletaResponse> boletaResponse = mapper.convertValue(boletaClient.getBoletas(),
                new TypeReference<List<BoletaResponse>>() {});

        List<FacturaResponse> facturaResponse = mapper.convertValue(facturaClient.getFacturas(),
                new TypeReference<List<FacturaResponse>>() {});

        List<BoletaResponse> filteredBoletaResponse = boletaResponse.stream().filter(boletaResponse1 ->
                boletaResponse1.getFechaCreacion().isAfter(initialDate)
                        && boletaResponse1.getFechaCreacion().isBefore(finalDate)).collect(Collectors.toList());

        List<Integer> montoBoletas = filteredBoletaResponse.stream().map(boletaResponse1 ->
                boletaResponse1.getMontoBoleta()).collect(Collectors.toList());

        int cantidadBoletas = filteredBoletaResponse.size();
        int sumaBoletas = montoBoletas.stream().mapToInt(Integer::intValue).sum();

        List<FacturaResponse> filteredFacturaResponse = facturaResponse.stream().filter(facturaResponse1 ->
                facturaResponse1.getFechaCreacion().isAfter(initialDate)
                        && facturaResponse1.getFechaCreacion().isBefore(finalDate)).collect(Collectors.toList());

        List<Integer> montoFacturas = filteredFacturaResponse.stream().map(facturaResponse1 ->
                facturaResponse1.getMontoFactura()).collect(Collectors.toList());

        int cantidadFacturas = filteredFacturaResponse.size();
        int sumaFacturas = montoFacturas.stream().mapToInt(Integer::intValue).sum();

        int montoTotalBalance = sumaBoletas - sumaFacturas;

        //---------------Balance documento boletas-------------------------

        BalanceDocumento balanceBoletas = new BalanceDocumento();
        balanceBoletas.setMontoTotal(sumaBoletas);
        balanceBoletas.setCantidadDocumentos(cantidadBoletas);

        //---------------Balance documento facturas-------------------------

        BalanceDocumento balanceFacturas = new BalanceDocumento();
        balanceFacturas.setCantidadDocumentos(cantidadFacturas);
        balanceFacturas.setMontoTotal(sumaFacturas);



        Balance filteredBalance = new Balance();

        filteredBalance.setBoletas(balanceBoletas);
        filteredBalance.setFacturas(balanceFacturas);
        filteredBalance.setBalanceFinal(montoTotalBalance);

        return filteredBalance;
    }

    public Balance getDailyBalance(LocalDate date){

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        List<BoletaResponse> boletaResponse = mapper.convertValue(boletaClient.getBoletas(),
                new TypeReference<List<BoletaResponse>>() {});

        List<FacturaResponse> facturaResponse = mapper.convertValue(facturaClient.getFacturas(),
                new TypeReference<List<FacturaResponse>>() {});

        List<BoletaResponse> filteredBoletaResponse = boletaResponse.stream().filter(boletaResponse1 ->
                boletaResponse1.getFechaCreacion().equals(date)).collect(Collectors.toList());

        List<Integer> montoBoletas = filteredBoletaResponse.stream().map(boletaResponse1 ->
                boletaResponse1.getMontoBoleta()).collect(Collectors.toList());

        int cantidadBoletas = filteredBoletaResponse.size();
        int sumaBoletas = montoBoletas.stream().mapToInt(Integer::intValue).sum();

        List<FacturaResponse> filteredFacturaResponse = facturaResponse.stream().filter(facturaResponse1 ->
                facturaResponse1.getFechaCreacion().equals(date)).collect(Collectors.toList());

        List<Integer> montoFacturas = filteredFacturaResponse.stream().map(facturaResponse1 ->
                facturaResponse1.getMontoFactura()).collect(Collectors.toList());

        int cantidadFacturas = filteredFacturaResponse.size();
        int sumaFacturas = montoFacturas.stream().mapToInt(Integer::intValue).sum();

        int montoTotalBalance = sumaBoletas - sumaFacturas;

        //---------------Balance documento boletas-------------------------

        BalanceDocumento balanceBoletas = new BalanceDocumento();
        balanceBoletas.setMontoTotal(sumaBoletas);
        balanceBoletas.setCantidadDocumentos(cantidadBoletas);

        //---------------Balance documento facturas-------------------------

        BalanceDocumento balanceFacturas = new BalanceDocumento();
        balanceFacturas.setCantidadDocumentos(cantidadFacturas);
        balanceFacturas.setMontoTotal(sumaFacturas);



        Balance filteredBalance = new Balance();

        filteredBalance.setBoletas(balanceBoletas);
        filteredBalance.setFacturas(balanceFacturas);
        filteredBalance.setBalanceFinal(montoTotalBalance);

        return filteredBalance;
    }
}
