package com.integracion.balances.model.boleta;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.integracion.balances.model.Producto;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BoletaResponse {

    private String NroBoleta;
    private List<Producto> productos;
    private int montoBoleta;
    private String estadoBoleta;
    private LocalDate fechaCreacion;
    private String usuario;
}
