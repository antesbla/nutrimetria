package com.java.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class RelMateriaId implements Serializable {
    private int producto;
    private int materiaPrima;

    public RelMateriaId() {}

    public RelMateriaId(int producto, int materiaPrima) {
        this.producto = producto;
        this.materiaPrima = materiaPrima;
    }
    

    public int getProducto() {
        return producto;
    }

    public void setProducto(int producto) {
        this.producto = producto;
    }

    public int getMateriaPrima() {
        return materiaPrima;
    }

    public void setMateriaPrima(int materiaPrima) {
        this.materiaPrima = materiaPrima;
    }

    // equals() y hashCode()

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RelMateriaId)) return false;
        RelMateriaId that = (RelMateriaId) o;
        return Objects.equals(producto, that.producto) &&
               Objects.equals(materiaPrima, that.materiaPrima);
    }

    @Override
    public int hashCode() {
        return Objects.hash(producto, materiaPrima);
    }
}
