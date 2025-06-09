package com.java.DTO;

import com.java.model.modeloMateriasPrimas;
import com.java.model.modeloProveedor;

public class MateriaCantidadDTO {
    private modeloMateriasPrimas materia;
    private double cantidad;
    private modeloProveedor proveedor;

    public modeloProveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(modeloProveedor proveedor) {
		this.proveedor = proveedor;
	}

	public void setMateria(modeloMateriasPrimas materia) {
		this.materia = materia;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public MateriaCantidadDTO(modeloMateriasPrimas materia, double cantidad) {
        this.materia = materia;
        this.cantidad = cantidad;
    }

    public modeloMateriasPrimas getMateria() { return materia; }
    public double getCantidad() { return cantidad; }

    @Override
    public String toString() {
        return cantidad + " kilogramos de " + materia.getNombre();
    }
}
