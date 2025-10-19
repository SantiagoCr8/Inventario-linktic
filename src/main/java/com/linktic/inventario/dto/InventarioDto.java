package com.linktic.inventario.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = false)
public class InventarioDto {

	 private Long id;
	 
	@NotNull(message = "El campo productId es obligatorio")
	 private Long productId;
	
	@NotNull(message = "El campo cantidad es obligatorio")
	 private Integer cantidad;
	
	public InventarioDto() {
		super();
	}
	
	public InventarioDto(Long id, @NotNull(message = "El campo productId es obligatorio") Long productId,
			@NotNull(message = "El campo cantidad es obligatorio") Integer cantidad) {
		super();
		this.id = id;
		this.productId = productId;
		this.cantidad = cantidad;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	  
	  
}
