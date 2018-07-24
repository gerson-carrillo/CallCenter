package com.almundo.CallCenter;

/**
 * Clase que guarda los datos de la llamada
 * */
public class CallDTO {
	
	int intCall;
	String strRole;
	
	public CallDTO(int intCall) {
		this.intCall = intCall;
	}

	public int getIntCall() {
		return intCall;
	}

	public void setIntCall(int intCall) {
		this.intCall = intCall;
	}

	public String getStrRole() {
		return strRole;
	}

	public void setStrRole(String strRole) {
		this.strRole = strRole;
	}
	
	
}
