package com.java.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class RelConsejoId implements Serializable {
    private int consejo;
    private int producto;
}
