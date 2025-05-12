

package com.java.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class modeloUsuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int usuario;

    @Column(name = "clave", nullable = false)
    private String clave;

    @Column(name = "permisos", nullable = false)
    private int permisos;
}
