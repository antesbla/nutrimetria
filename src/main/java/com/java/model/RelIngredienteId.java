package com.java.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class RelIngredienteId implements Serializable {
    private int ingrediente;
    private int materiaPrima;
    
	public int getIngrediente() {
		return ingrediente;
	}
	public void setIngrediente(int ingrediente) {
		this.ingrediente = ingrediente;
	}
	public int getMateriaPrima() {
		return materiaPrima;
	}
	public void setMateriaPrima(int materiaPrima) {
		this.materiaPrima = materiaPrima;
	}
	
	public RelIngredienteId(int ingrediente, int materiaPrima) {
		super();
		this.ingrediente = ingrediente;
		this.materiaPrima = materiaPrima;
	}
	public RelIngredienteId() {
		super();
	}
}
