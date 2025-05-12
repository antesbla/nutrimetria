package com.java.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class RelIngredienteId implements Serializable {
    private int ingrediente;
    private int materiaPrima;
}
