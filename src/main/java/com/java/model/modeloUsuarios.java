

package com.java.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class modeloUsuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="usuario", nullable = false)
    private String usuario;
    
    @Column(name = "clave", nullable = false)
    private String clave;

    @Column(name = "permisos", nullable = false)
    private int permisos;

	public int getId() {
		return id;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public int getPermisos() {
		return permisos;
	}

	public void setPermisos(int permisos) {
		this.permisos = permisos;
	}

	public modeloUsuarios(String usuario, String clave, int permisos) {
		super();
		this.usuario = usuario;
		this.clave = clave;
		this.permisos = permisos;
	}

	public modeloUsuarios() {
	}
	
	
    
    
}
