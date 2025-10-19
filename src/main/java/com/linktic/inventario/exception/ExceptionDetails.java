package com.linktic.inventario.exception;

public class ExceptionDetails {
	private String severity;
	private String userMessage;
	
	public String getUserMessage() {
		return userMessage;
	}
	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	
}
