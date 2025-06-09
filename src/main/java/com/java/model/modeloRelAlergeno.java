package com.java.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "rel_alergeno")
public class modeloRelAlergeno {

    @EmbeddedId
    private RelAlergenoId id;

    @ManyToOne
    @MapsId("alergeno")
    @JoinColumn(name = "ID_alergeno", nullable = false)
    private modeloAlergeno alergeno;

    @ManyToOne
    @MapsId("materia")
    @JoinColumn(name = "ID_materia", nullable = false)
    private modeloMateriasPrimas materia;

    @Column(name = "cantidad")
    private int cantidad;

	public RelAlergenoId getId() {
		return id;
	}

	public void setId(RelAlergenoId id) {
		this.id = id;
	}

	public modeloAlergeno getAlergeno() {
		return alergeno;
	}

	public void setAlergeno(modeloAlergeno alergeno) {
		this.alergeno = alergeno;
	}

	public modeloMateriasPrimas getMateria() {
		return materia;
	}

	public void setMateria(modeloMateriasPrimas materia) {
		this.materia = materia;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
    
    
}
