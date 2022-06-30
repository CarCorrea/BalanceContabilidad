package com.integracion.balances.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Balance {

    private BalanceDocumento boletas;
    private BalanceDocumento facturas;
    private int balanceFinal;
}
