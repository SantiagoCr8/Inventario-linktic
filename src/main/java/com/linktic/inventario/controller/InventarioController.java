package com.linktic.inventario.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linktic.inventario.dto.InventarioDto;
import com.linktic.inventario.model.Inventario;
import com.linktic.inventario.service.InventarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/inventario")
public class InventarioController {
	
	private final InventarioService svc;
	
	public InventarioController(InventarioService svc) {
        this.svc = svc;
    }
	  @PostMapping("/comprar/crear")
	    public ResponseEntity<InventarioDto> crear(@Valid @RequestBody InventarioDto payload) {
		  System.err.println(payload);
		  InventarioDto created = svc.purchase(payload);
	        return ResponseEntity.ok(created);
	    }
	  
	  @PostMapping("/actualizar")
	    public ResponseEntity<InventarioDto> actualizarInventario(@Valid @RequestBody InventarioDto payload) {
		  System.err.println(payload);
		  InventarioDto created = svc.actualizar(payload);
	        return ResponseEntity.ok(created);
	    }
	  
	  @GetMapping("/buscar/{id}")
	    public ResponseEntity<Inventario> buscarPorId(@Valid @PathVariable Long id) {
		  Inventario created = svc.obtenerInventario(id);
	        return ResponseEntity.ok(created);
	    }
	
}
