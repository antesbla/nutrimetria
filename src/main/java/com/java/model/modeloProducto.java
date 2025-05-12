package com.java.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "producto")

public class modeloProducto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "peso")
	private double peso;

	@Column(name = "cat_legal")
	private String cat_legal;
	
	@Column(name = "durabilidad")
	private String durabilidad;
	
	@Column(name = "transporte")
	private String transporte;
	
	@Column(name = "cond_almac")
	private String cond_almac;
	
	@Column(name = "composicion")
	private String composicion;
	
	@Column(name = "cod_prod", unique = true)
	private String cod_prod;

	@Column(name = "unidad_caja")
	private int unidad_caja;
	
	@Column(name = "peso_caja")
	private double peso_caja;
	
	@Column(name = "cod_barras", unique = true)
	private String cod_barras;
	
}
