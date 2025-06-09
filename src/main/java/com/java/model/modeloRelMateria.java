package com.java.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "rel_materia")
public class modeloRelMateria {

    @EmbeddedId
    private RelMateriaId id;

    @ManyToOne
    @MapsId("producto")
    @JoinColumn(name = "ID_producto", nullable = false)
    private modeloProducto producto;

    @ManyToOne
    @MapsId("materiaPrima")
    @JoinColumn(name = "ID_materia", nullable = false)
    private modeloMateriasPrimas materiaPrima;
    
    @ManyToOne
    @JoinColumn(name = "id_proveedor")
    private modeloProveedor proveedor;


    @Column(name = "cantidad")
    private double cantidad;

	public RelMateriaId getId() {
		return id;
	}

	public void setId(RelMateriaId id) {
		this.id = id;
	}

	public modeloProducto getProducto() {
		return producto;
	}

	public void setProducto(modeloProducto producto) {
		this.producto = producto;
	}

	public modeloMateriasPrimas getMateriaPrima() {
		return materiaPrima;
	}

	public void setMateriaPrima(modeloMateriasPrimas materiaPrima) {
		this.materiaPrima = materiaPrima;
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public modeloProveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(modeloProveedor proveedor) {
		this.proveedor = proveedor;
	}
    
    
}
