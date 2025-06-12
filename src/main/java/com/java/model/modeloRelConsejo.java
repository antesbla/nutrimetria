package com.java.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "rel_consejo")
public class modeloRelConsejo {

    @EmbeddedId
    private RelConsejoId id;

    @ManyToOne
    @MapsId("consejo")
    @JoinColumn(name = "ID_consejo", nullable = false)
    private modeloConsejo consejo;

    @ManyToOne
    @MapsId("producto")
    @JoinColumn(name = "ID_producto", nullable = false)
    private modeloProducto producto;

	public RelConsejoId getId() {
		return id;
	}

	public void setId(RelConsejoId id) {
		this.id = id;
	}

	public modeloConsejo getConsejo() {
		return consejo;
	}

	public void setConsejo(modeloConsejo consejo) {
		this.consejo = consejo;
	}

	public modeloProducto getProducto() {
		return producto;
	}

	public void setProducto(modeloProducto producto) {
		this.producto = producto;
	}
    
    
}
