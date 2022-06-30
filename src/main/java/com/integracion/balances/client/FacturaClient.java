package com.integracion.balances.client;

import com.integracion.balances.model.boleta.BoletaResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class FacturaClient {

    private final RestTemplate template;
    private final String facturasUrl;

    public FacturaClient(RestTemplate template, @Value("${facturas.url}") String boletasUrl) {
        this.template = template;
        this.facturasUrl = boletasUrl;
    }

    public List<BoletaResponse> getFacturas(){
        return template.getForObject(facturasUrl, List.class);
    }
}
