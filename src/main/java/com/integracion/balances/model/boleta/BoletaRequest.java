package com.integracion.balances.model.boleta;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BoletaRequest {

    private List<String> productos;
    private int montoBoleta;
    private String estadoBoleta;
    private LocalDate fechaCreacion;
    private String usuario;

}
