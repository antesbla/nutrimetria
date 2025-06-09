package com.java.DTO;

import java.util.List;

public class MateriaPrimaCompletaDTO {
	private int id;
	private String nombre;
	private String unidadMedida;

	private Double precio;
	private Double kcal;
	private Double hidratos;
	private Double azucares;
	private Double grasas;
	private Double saturadas;
	private Double proteinas;
	private Double sal;
	private Double fibra;

	private Integer idProveedor;

	private List<AlérgenoDTO> alergenos;

	private List<IngredienteDTO> ingredientes;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Double getKcal() {
		return kcal;
	}

	public void setKcal(Double kcal) {
		this.kcal = kcal;
	}

	public Double getHidratos() {
		return hidratos;
	}

	public void setHidratos(Double hidratos) {
		this.hidratos = hidratos;
	}

	public Double getAzucares() {
		return azucares;
	}

	public void setAzucares(Double azucares) {
		this.azucares = azucares;
	}

	public Double getGrasas() {
		return grasas;
	}

	public void setGrasas(Double grasas) {
		this.grasas = grasas;
	}

	public Double getSaturadas() {
		return saturadas;
	}

	public void setSaturadas(Double saturadas) {
		this.saturadas = saturadas;
	}

	public Double getProteinas() {
		return proteinas;
	}

	public void setProteinas(Double proteinas) {
		this.proteinas = proteinas;
	}

	public Double getSal() {
		return sal;
	}

	public void setSal(Double sal) {
		this.sal = sal;
	}

	public Double getFibra() {
		return fibra;
	}

	public void setFibra(Double fibra) {
		this.fibra = fibra;
	}

	public Integer getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(Integer idProveedor) {
		this.idProveedor = idProveedor;
	}

	public List<AlérgenoDTO> getAlergenos() {
		return alergenos;
	}

	public void setAlergenos(List<AlérgenoDTO> alergenos) {
		this.alergenos = alergenos;
	}

	public List<IngredienteDTO> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(List<IngredienteDTO> ingredientes) {
		this.ingredientes = ingredientes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static class AlérgenoDTO {
		private String nombre;
		private int tipo;

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public int getTipo() {
			return tipo;
		}

		public void setTipo(int tipo) {
			this.tipo = tipo;
		}
	}

	public static class IngredienteDTO {
		private String nombre;
		private Double porcentaje;

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public Double getPorcentaje() {
			return porcentaje;
		}

		public void setPorcentaje(Double porcentaje) {
			this.porcentaje = porcentaje;
		}
	}
}
