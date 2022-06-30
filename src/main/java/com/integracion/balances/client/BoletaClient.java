package com.integracion.balances.client;


import com.integracion.balances.model.boleta.BoletaResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class BoletaClient {

    private final RestTemplate template;
    private final String boletasUrl;

    public BoletaClient(RestTemplate template, @Value("${boletas.url}") String boletasUrl) {
        this.template = template;
        this.boletasUrl = boletasUrl;
    }

    public List<BoletaResponse> getBoletas(){
        return template.getForObject(boletasUrl, List.class);
    }
}
