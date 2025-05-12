package com.java.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class RelMateriaId implements Serializable {
    private int producto;
    private int materiaPrima;
}
