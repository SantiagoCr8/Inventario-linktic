package com.linktic.inventario.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.linktic.inventario.dto.InventarioDto;
import com.linktic.inventario.dto.ProductoDto;
import com.linktic.inventario.exception.NotFoundException;
import com.linktic.inventario.model.Inventario;
import com.linktic.inventario.repository.InventarioRepository;
@Service
public class InventarioService {
	
    private final InventarioRepository inventoryRepo;
    private final RestTemplate restTemplate;
    
    @Value("${spring.external.service.base-url}")
    private String basePath;
    
    @Autowired
    public InventarioService(InventarioRepository inventoryRepo, RestTemplate restTemplate) {
        this.inventoryRepo = inventoryRepo;
        this.restTemplate = restTemplate;
    }

    public Inventario obtenerInventario(Long productId) {
    	String url = basePath+"{id}";
    	Inventario inventario = null;
    	System.err.println("asda");
    		ProductoDto producto = restTemplate.getForObject(url,ProductoDto.class,productId);
    		System.err.println(producto.getDescripcion());
    	    if (producto != null)  {
	    	    Optional<Inventario> inventarioOp = inventoryRepo.findFirstByProductId(producto.getId());
	    		if (inventarioOp.isPresent()) {
	    			inventario = inventarioOp.get();
	        	}else {
	        		throw new NotFoundException("No se ha encontrado el producto con el ID: " + productId);
				}
    	    }
    	return inventario;	
    }
    
    public InventarioDto purchase(InventarioDto req) {
    	Inventario inv = obtenerInventario(req.getProductId());
        InventarioDto inventarioDto = new InventarioDto();
        if(inv.getCantidad() < req.getCantidad()) {
        	throw new NotFoundException("El stock actual en menor que la cantidad a comprar. Actual Stock: " + inv.getCantidad());
        }
        inv.setCantidad(inv.getCantidad() - req.getCantidad());
        inv = inventoryRepo.save(inv);
        inventarioDto.setCantidad(inv.getCantidad());
        inventarioDto.setId(inv.getId());
        inventarioDto.setProductId(inv.getProductId());
        return inventarioDto;
    }
    
    public InventarioDto actualizar(InventarioDto req) {
    	Inventario inv = obtenerInventario(req.getProductId());
        InventarioDto inventarioDto = new InventarioDto();
        inv.setCantidad(req.getCantidad());
        inv = inventoryRepo.save(inv);
        inventarioDto.setCantidad(inv.getCantidad());
        inventarioDto.setId(inv.getId());
        inventarioDto.setProductId(inv.getProductId());
        return inventarioDto;
    }
    

}
