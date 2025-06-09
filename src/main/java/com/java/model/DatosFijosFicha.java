package com.java.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "datos_fijos_ficha")
public class DatosFijosFicha {

    @Id
    private int id;

    private String nombreEmpresa;
    private String direccion;
    private String procesado;
    private String envasado;
    @Column(name = "texto_ogm", length = 1000)
    private String textoOgm;
    private String loteadoDescripcion;
    private String microbiolog;
    private String etiquetadoBase;
    private String textoOtros;
    private String clasifLegal;
    private String reglasLoteado;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombreEmpresa() {
		return nombreEmpresa;
	}
	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getProcesado() {
		return procesado;
	}
	public void setProcesado(String procesado) {
		this.procesado = procesado;
	}
	public String getEnvasado() {
		return envasado;
	}
	public void setEnvasado(String envasado) {
		this.envasado = envasado;
	}
	public String getTextoOgm() {
		return textoOgm;
	}
	public void setTextoOgm(String textoOgm) {
		this.textoOgm = textoOgm;
	}
	public String getLoteadoDescripcion() {
		return loteadoDescripcion;
	}
	public void setLoteadoDescripcion(String loteadoDescripcion) {
		this.loteadoDescripcion = loteadoDescripcion;
	}
	public String getMicrobiolog() {
		return microbiolog;
	}
	public void setMicrobiolog(String microbiolog) {
		this.microbiolog = microbiolog;
	}
	public String getEtiquetadoBase() {
		return etiquetadoBase;
	}
	public void setEtiquetadoBase(String etiquetadoBase) {
		this.etiquetadoBase = etiquetadoBase;
	}
	public String getTextoOtros() {
		return textoOtros;
	}
	public void setTextoOtros(String textoOtros) {
		this.textoOtros = textoOtros;
	}
	public String getClasifLegal() {
		return clasifLegal;
	}
	public void setClasifLegal(String clasifLegal) {
		this.clasifLegal = clasifLegal;
	}
	public String getReglasLoteado() {
		return reglasLoteado;
	}
	public void setReglasLoteado(String reglasLoteado) {
		this.reglasLoteado = reglasLoteado;
	}
}
