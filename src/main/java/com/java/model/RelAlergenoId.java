package com.java.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class RelAlergenoId implements Serializable {
    private int alergeno;
    private int materia;
}