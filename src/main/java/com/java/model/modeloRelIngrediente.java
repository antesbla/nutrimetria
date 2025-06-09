package com.java.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "rel_ingrediente")
public class modeloRelIngrediente {

    @EmbeddedId
    private RelIngredienteId id;

    @ManyToOne
    @MapsId("ingrediente")
    @JoinColumn(name = "ID_ingrediente", nullable = false)
    private modeloIngredientes ingrediente;

    @ManyToOne
    @MapsId("materiaPrima")
    @JoinColumn(name = "ID_materia", nullable = false)
    private modeloMateriasPrimas materiaPrima;

	public RelIngredienteId getId() {
		return id;
	}

	public void setId(RelIngredienteId id) {
		this.id = id;
	}

	public modeloIngredientes getIngrediente() {
		return ingrediente;
	}

	public void setIngrediente(modeloIngredientes ingrediente) {
		this.ingrediente = ingrediente;
	}

	public modeloMateriasPrimas getMateriaPrima() {
		return materiaPrima;
	}

	public void setMateriaPrima(modeloMateriasPrimas materiaPrima) {
		this.materiaPrima = materiaPrima;
	}
}
