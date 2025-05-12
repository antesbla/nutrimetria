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
}
