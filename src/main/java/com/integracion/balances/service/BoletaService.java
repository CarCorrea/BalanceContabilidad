package com.integracion.balances.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.integracion.balances.client.BoletaClient;
import com.integracion.balances.model.BalanceDocumento;
import com.integracion.balances.model.boleta.BoletaResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoletaService {

    private final BoletaClient boletaClient;

    public BoletaService(BoletaClient boletaClient) {
        this.boletaClient = boletaClient;
    }

    public BalanceDocumento getBalance() {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        List<BoletaResponse> boletaResponse = mapper.convertValue(boletaClient.getBoletas(), new TypeReference<List<BoletaResponse>>() {});

        BalanceDocumento balanceDocumento = new BalanceDocumento();

        List<Integer> monto = boletaResponse.stream().map(
                        response ->
                                response.getMontoBoleta()).collect(Collectors.toList());

        int cantidad = boletaClient.getBoletas().size();
        int suma = monto.stream().mapToInt(Integer::intValue).sum();

        balanceDocumento.setCantidadDocumentos(cantidad);
        balanceDocumento.setMontoTotal(suma);

        return balanceDocumento;
    }

    public List<BoletaResponse> boletas() {
        return boletaClient.getBoletas();
    }
}
