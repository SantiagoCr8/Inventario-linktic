package com.linktic.inventario.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BaseExcepcion {

	@ExceptionHandler(value = {HttpMessageNotReadableException.class})
	public ResponseEntity<ExceptionDetails> manejoExecpcionArgumentosInesperados(HttpMessageNotReadableException ex)
	{
		ExceptionDetails excepcion = new ExceptionDetails();
		excepcion.setSeverity("ERROR");
		excepcion.setUserMessage("Se enviaron mas argumentos de los esperados");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(excepcion);
	}
	
	@ExceptionHandler(value = {MethodArgumentNotValidException.class})
	public ResponseEntity<ExceptionDetails> manejoExepcionArgumantosNoValidos(MethodArgumentNotValidException ex)
	{
		ExceptionDetails excepcion = new ExceptionDetails();
		 for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
				excepcion.setSeverity("ERROR");
				excepcion.setUserMessage(error.getDefaultMessage());
	     }
	
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(excepcion);
	}
	
	@ExceptionHandler(value = {Exception.class, RuntimeException.class})
	public ResponseEntity<ExceptionDetails> majoneExepcionServerError(Throwable ex)
	{	
		ex.printStackTrace();
		ExceptionDetails excepcion = new ExceptionDetails();
		excepcion.setSeverity("ERROR");
		excepcion.setUserMessage("Error inesperado, comuniquese con el administrador");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(excepcion);
	}
	
	@ExceptionHandler(NotFoundException.class)
	  public ResponseEntity<ExceptionDetails> manejoExepcionRecursoNoEncotrado(NotFoundException ex) {
		ExceptionDetails excepcion = new ExceptionDetails();
		excepcion.setSeverity("WARNING");
		excepcion.setUserMessage(ex.getMessage());
	    return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(excepcion);
	  }
	
	
}