package com.java.DTO;

import com.java.model.modeloProducto;

public class IngredientesDTO {
	private modeloProducto producto;
	private String ingredientes;

	public IngredientesDTO(modeloProducto producto, String ingredientes) {
		this.producto = producto;
		this.ingredientes = ingredientes;
	}

	public int getCod_prod() { return producto.getCod_prod(); }
	public String getNombre() { return producto.getNombre(); }
	public String getDescripcion() {return producto.getDescripcion(); }
	public Double getPeso() {return producto.getPeso(); }
	public String getCat_Legal() {return producto.getCat_legal(); }
	public String getDurabilidad() {return producto.getDurabilidad(); }
	public String getTransporte() {return producto.getTransporte(); }
	public String getCond_almac() {return producto.getCond_almac(); }
	public String getComposicion() {return producto.getComposicion(); }
	public int getUnidad_Caja() {return producto.getUnidad_caja(); }
	public Double getPesoCaja() {return producto.getPeso_caja(); }
	public String getIngredientes() {return ingredientes; }	
	public int getId() {return producto.getId(); }
	
	

}
