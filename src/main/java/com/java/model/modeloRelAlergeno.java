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
    private double cantidad;
}
