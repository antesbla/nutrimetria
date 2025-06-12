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
	private int cod_prod;

	@Column(name = "unidad_caja")
	private int unidad_caja;
	
	@Column(name = "peso_caja")
	private double peso_caja;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public String getCat_legal() {
		return cat_legal;
	}

	public void setCat_legal(String cat_legal) {
		this.cat_legal = cat_legal;
	}

	public String getDurabilidad() {
		return durabilidad;
	}

	public void setDurabilidad(String durabilidad) {
		this.durabilidad = durabilidad;
	}

	public String getTransporte() {
		return transporte;
	}

	public void setTransporte(String transporte) {
		this.transporte = transporte;
	}

	public String getCond_almac() {
		return cond_almac;
	}

	public void setCond_almac(String cond_almac) {
		this.cond_almac = cond_almac;
	}

	public String getComposicion() {
		return composicion;
	}

	public void setComposicion(String composicion) {
		this.composicion = composicion;
	}

	public int getCod_prod() {
		return cod_prod;
	}

	public void setCod_prod(int cod_prod) {
		this.cod_prod = cod_prod;
	}

	public int getUnidad_caja() {
		return unidad_caja;
	}

	public void setUnidad_caja(int unidad_caja) {
		this.unidad_caja = unidad_caja;
	}

	public double getPeso_caja() {
		return peso_caja;
	}

	public void setPeso_caja(double peso_caja) {
		this.peso_caja = peso_caja;
	}

	@Override
	public String toString() {
		return nombre;
	}
	
	
	
}
