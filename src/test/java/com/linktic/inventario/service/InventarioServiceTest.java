package com.linktic.inventario.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.linktic.inventario.dto.InventarioDto;
import com.linktic.inventario.dto.ProductoDto;
import com.linktic.inventario.model.Inventario;
import com.linktic.inventario.repository.InventarioRepository;
import com.linktic.inventario.service.InventarioService;

import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class) 
public class InventarioServiceTest {

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private InventarioRepository inventoryRepo;

	@InjectMocks
	private InventarioService inventarioService;

	@BeforeEach
    void setup() {
        assertNotNull(inventarioService);
        ReflectionTestUtils.setField(inventarioService, "basePath", "http://localhost:8080/productos/");
    }

	@Test
	void inventarioCuandoProductoExiste() {
		BigDecimal precio = new BigDecimal("1000");
		ProductoDto productoMock = new ProductoDto(1L, "Chocolate", precio, "Chocolate en pastillas");
		Inventario inventarioMock = new Inventario(Long.valueOf(1L), Long.valueOf(1L), Integer.valueOf(10));
		when(restTemplate.getForObject("http://localhost:8080/productos/{id}", ProductoDto.class, 1L)).thenReturn(productoMock);
		when(inventoryRepo.findFirstByProductId(1L)).thenReturn(Optional.of(inventarioMock));
		Inventario resultado = inventarioService.obtenerInventario(1L);
		assertEquals(Long.valueOf(1L), resultado.getId());
		assertEquals(Long.valueOf(1L), resultado.getProductId());
		assertEquals(Integer.valueOf(10), resultado.getCantidad());
		assertNotNull(resultado);
		verify(restTemplate).getForObject(anyString(), eq(ProductoDto.class), eq(1L));
		verify(inventoryRepo).findFirstByProductId(1L);
	}
	
	@Test
	void compraExitosaCuandoHayStock() {
	    InventarioDto req = new InventarioDto();
	    req.setProductId(1L);
	    req.setCantidad(5);
	    Inventario inventarioSimulado = new Inventario(Long.valueOf(1L), Long.valueOf(1L), Integer.valueOf(10));
	    InventarioService spyService = spy(inventarioService);
	    doReturn(inventarioSimulado).when(spyService).obtenerInventario(1L);
	    Inventario inventarioActualizado = new Inventario(Long.valueOf(1L),Long.valueOf(1L), Integer.valueOf(5));
	    when(inventoryRepo.save(any(Inventario.class))).thenReturn(inventarioActualizado);
	    InventarioDto resultado = spyService.purchase(req);
	    assertEquals(5, resultado.getCantidad());
	    assertEquals(1L, resultado.getProductId());
	    assertEquals(1L, resultado.getId());
	    verify(inventoryRepo).save(any(Inventario.class));
	}
	
    @Test
    void verificarCantidadInventario() {
        InventarioDto req = new InventarioDto(Long.valueOf(1L),Long.valueOf(1L),Integer.valueOf(15));
        Inventario inventarioExistente = new Inventario(Long.valueOf(1L), Long.valueOf(1L),Integer.valueOf(10));
        Inventario inventarioActualizado = new Inventario(Long.valueOf(1L), Long.valueOf(1L), Integer.valueOf(50)); 
        InventarioService spyService = spy(inventarioService);
        doReturn(inventarioExistente).when(spyService).obtenerInventario(1L);
        when(inventoryRepo.save(any(Inventario.class))).thenReturn(inventarioActualizado);
        InventarioDto resultado = spyService.actualizar(req);
        assertNotNull(resultado);
        assertEquals(50, resultado.getCantidad());
        assertEquals(1L, resultado.getProductId());
        assertEquals(1L, resultado.getId());
        verify(inventoryRepo).save(any(Inventario.class));
    }

}
