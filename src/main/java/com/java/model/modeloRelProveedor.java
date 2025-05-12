package com.java.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "rel_proveedor")
public class modeloRelProveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "ID_proveedor", nullable = false)
    private modeloProveedor proveedor;

    @ManyToOne
    @JoinColumn(name = "ID_materia", nullable = false)
    private modeloMateriasPrimas materiaPrima;

    @Column(name = "kcal")
    private double kcal;
    
    @Column(name = "hidratos")
    private double hidratos;
    
    @Column(name = "azucares")
    private double azucares;
    
    @Column(name = "grasas")
    private double grasas;
    
    @Column(name = "saturadas")
    private double saturadas;
    
    @Column(name = "proteinas")
    private double proteinas;
    
    @Column(name = "sal")
    private double sal;
    
    @Column(name = "fibra")
    private double fibra;
    
    @Column(name = "precio")
    private double precio;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public modeloProveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(modeloProveedor proveedor) {
		this.proveedor = proveedor;
	}

	public modeloMateriasPrimas getMateriaPrima() {
		return materiaPrima;
	}

	public void setMateriaPrima(modeloMateriasPrimas materiaPrima) {
		this.materiaPrima = materiaPrima;
	}

	public double getKcal() {
		return kcal;
	}

	public void setKcal(double kcal) {
		this.kcal = kcal;
	}

	public double getHidratos() {
		return hidratos;
	}

	public void setHidratos(double hidratos) {
		this.hidratos = hidratos;
	}

	public double getAzucares() {
		return azucares;
	}

	public void setAzucares(double azucares) {
		this.azucares = azucares;
	}

	public double getGrasas() {
		return grasas;
	}

	public void setGrasas(double grasas) {
		this.grasas = grasas;
	}

	public double getSaturadas() {
		return saturadas;
	}

	public void setSaturadas(double saturadas) {
		this.saturadas = saturadas;
	}

	public double getProteinas() {
		return proteinas;
	}

	public void setProteinas(double proteinas) {
		this.proteinas = proteinas;
	}

	public double getSal() {
		return sal;
	}

	public void setSal(double sal) {
		this.sal = sal;
	}

	public double getFibra() {
		return fibra;
	}

	public void setFibra(double fibra) {
		this.fibra = fibra;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
    
    
}
