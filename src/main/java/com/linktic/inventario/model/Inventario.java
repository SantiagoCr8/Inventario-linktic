package com.linktic.inventario.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventario")
public class Inventario {
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;

	  @Column(name = "product_id", nullable = false, unique = true)
	  private Long productId;

	  @Column(nullable = false)
	  private Integer cantidad;

	public Inventario() {
		super();
	}

	public Inventario(Long id, Long productId, Integer cantidad) {
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
