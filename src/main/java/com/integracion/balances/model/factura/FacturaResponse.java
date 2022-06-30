package com.integracion.balances.model.factura;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.integracion.balances.model.Producto;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FacturaResponse {

    private String NroFactura;
    private List<Producto> productos;
    private int montoFactura;
    private String estadoFactura;
    private LocalDate fechaCreacion;
    private String usuario;
}
