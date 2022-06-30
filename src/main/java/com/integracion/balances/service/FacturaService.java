package com.integracion.balances.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.integracion.balances.client.FacturaClient;
import com.integracion.balances.model.BalanceDocumento;
import com.integracion.balances.model.factura.FacturaResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacturaService {

    private final FacturaClient facturaClient;

    public FacturaService(FacturaClient facturaClient) {
        this.facturaClient = facturaClient;
    }

    public BalanceDocumento getBalance(){

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        List<FacturaResponse> facturaResponse = mapper.convertValue(facturaClient.getFacturas(), new TypeReference<List<FacturaResponse>>() {});

        BalanceDocumento balanceDocumento = new BalanceDocumento();

        List<Integer> monto = facturaResponse.stream().map(
                response ->
                        response.getMontoFactura()).collect(Collectors.toList());

        int cantidad = facturaClient.getFacturas().size();
        int suma = monto.stream().mapToInt(Integer::intValue).sum();

        balanceDocumento.setCantidadDocumentos(cantidad);
        balanceDocumento.setMontoTotal(suma);

        return balanceDocumento;
    }
}
