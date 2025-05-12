package com.java.DTO;

public class MateriasProveedorRelProvDTO {
	private int id;
	private String nombre;
	private String proveedor;
	private double precio;
	private double kcal;
	private double hidratos;
	private double azucares;
	private double grasas;
	private double saturadas;
	private double proteinas;
	private double sal;
	private double fibra;
	
	public MateriasProveedorRelProvDTO(String nombre, String proveedor, double precio, double kcal,
			double hidratos, double azucares, double grasas, double saturadas, double proteinas, double sal,
			double fibra) {
		super();
		this.nombre = nombre;
		this.proveedor = proveedor;
		this.precio = precio;
		this.kcal = kcal;
		this.hidratos = hidratos;
		this.azucares = azucares;
		this.grasas = grasas;
		this.saturadas = saturadas;
		this.proteinas = proteinas;
		this.sal = sal;
		this.fibra = fibra;
	}

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

	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
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
	
	
	
	
}
