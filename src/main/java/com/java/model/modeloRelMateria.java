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

    @Column(name = "cantidad")
    private double cantidad;
}
