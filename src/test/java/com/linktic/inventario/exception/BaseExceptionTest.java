package com.linktic.inventario.exception;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.linktic.inventario.controller.InventarioController;
import com.linktic.inventario.dto.InventarioDto;
import com.linktic.inventario.service.InventarioService;

@WebMvcTest(controllers = InventarioController.class)
@Import(BaseExcepcion.class)
public class BaseExceptionTest {

	@MockBean
	private InventarioService inventarioService;

	@Autowired
	private MockMvc mockMvc;

//	@Test
//	void recursoNoEncontradoTest() throws Exception {
//	    String json = "{\"cantidad\": 1,\"productId\": 10,\"id\": 10}";
//	    when(inventarioService.actualizar(any(InventarioDto.class)))
//	        .thenThrow(new NotFoundException("El stock actual en menor que la cantidad a comprar. Actual Stock: 1"));
//	    mockMvc.perform(post("/inventario/actualizarr")
//	            .contentType("application/json")
//	            .content(json))
//	        .andExpect(status().isBadRequest())
//	        .andExpect(jsonPath("$.severity").value("ERROR"))
//	        .andExpect(jsonPath("$.userMessage").value("El stock actual en menor que la cantidad a comprar. Actual Stock: 1"));
//	}

	@Test 
	void ArgumantosNoValidosTest() throws Exception {
		   String json = "{\"cantidad\": 1}";
		   mockMvc.perform(post("/inventario/actualizar")
	                .contentType("application/json")
	                .content(json))
	            .andExpect(status().isBadRequest())
	            .andExpect(jsonPath("$.severity").value("ERROR"))
	            .andExpect(jsonPath("$.userMessage").value("El campo productId es obligatorio"));

	}

	@Test
	void ServerErrorTest() throws Exception {
		when(inventarioService.actualizar(any(InventarioDto.class)))
	    .thenThrow(new RuntimeException("Error inesperado"));
		String json = "{\"cantidad\": 1,\"productId\": 1,\"id\": 1}";
		 mockMvc.perform(post("/inventario/actualizar")
		            .contentType("application/json")
		            .content(json))
				.andExpect(jsonPath("$.severity").value("ERROR"))
				.andExpect(jsonPath("$.userMessage").value("Error inesperado, comuniquese con el administrador"));
	}

}
