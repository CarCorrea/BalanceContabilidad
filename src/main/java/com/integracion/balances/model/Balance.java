package com.integracion.balances.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Balance {

    private int cantidadBoletas;
    private int montoTotalBoletas;
    private int cantidadFacturas;
    private int montoTotalFacturas;
    private int balanceFinal;
}
