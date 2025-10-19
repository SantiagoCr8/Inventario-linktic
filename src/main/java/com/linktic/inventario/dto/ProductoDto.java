package com.linktic.inventario.dto;

import java.math.BigDecimal;

public class ProductoDto {
	  private Long id;
	  private String nombre;
	  private BigDecimal precio;
	  private String descripcion;
	  
	  
	public ProductoDto(Long id, String nombre, BigDecimal precio, String descripcion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.descripcion = descripcion;
	}
	
	
	public ProductoDto() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public BigDecimal getPrecio() {
		return precio;
	}
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	  
	  
}
