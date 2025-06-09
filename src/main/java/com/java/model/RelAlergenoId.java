package com.java.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class RelAlergenoId implements Serializable {
    private int alergeno;
    private int materia;
    
	public int getAlergeno() {
		return alergeno;
	}
	public void setAlergeno(int alergeno) {
		this.alergeno = alergeno;
	}
	public int getMateria() {
		return materia;
	}
	public void setMateria(int materia) {
		this.materia = materia;
	}
	public RelAlergenoId(int alergeno, int materia) {
		super();
		this.alergeno = alergeno;
		this.materia = materia;
	}
	public RelAlergenoId() {
		super();
	}   
}