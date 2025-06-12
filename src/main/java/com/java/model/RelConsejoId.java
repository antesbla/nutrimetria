package com.java.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class RelConsejoId implements Serializable {
    private int consejo;
    private int producto;
	public int getConsejo() {
		return consejo;
	}
	public void setConsejo(int consejo) {
		this.consejo = consejo;
	}
	public int getProducto() {
		return producto;
	}
	public void setProducto(int producto) {
		this.producto = producto;
	}
    
    
}
