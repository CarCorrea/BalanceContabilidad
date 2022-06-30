package com.integracion.balances.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BalanceDocumento {

    private int cantidadDocumentos;
    private int montoTotal;

}
