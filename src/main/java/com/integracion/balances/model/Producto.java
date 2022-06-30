package com.integracion.balances.model;

import lombok.Data;

@Data
public class Producto {

    private String idProducto;
    private String nombreProducto;
    private int precio;
    private int cantidad;
}
