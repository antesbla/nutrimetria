package com.java.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class RelProveedorId implements Serializable {

    private int proveedorId;
    private int materiaPrimaId;

    public RelProveedorId() {}

    public RelProveedorId(int proveedorId, int materiaPrimaId) {
        this.proveedorId = proveedorId;
        this.materiaPrimaId = materiaPrimaId;
    }

    public int getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(int proveedorId) {
        this.proveedorId = proveedorId;
    }

    public int getMateriaPrimaId() {
        return materiaPrimaId;
    }

    public void setMateriaPrimaId(int materiaPrimaId) {
        this.materiaPrimaId = materiaPrimaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RelProveedorId)) return false;
        RelProveedorId that = (RelProveedorId) o;
        return proveedorId == that.proveedorId &&
               materiaPrimaId == that.materiaPrimaId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(proveedorId, materiaPrimaId);
    }
}
