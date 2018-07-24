package com.almundo.CallCenter;

public class CallDTO {
	
	int intCall;
	int intDuration;
	String strRole;
	
	public CallDTO(int intCall, int intDuration) {
		this.intCall = intCall;
		this.intDuration = intDuration;
	}

	public int getIntCall() {
		return intCall;
	}

	public void setIntCall(int intCall) {
		this.intCall = intCall;
	}

	public int getIntDuration() {
		return intDuration;
	}

	public void setIntDuration(int intDuration) {
		this.intDuration = intDuration;
	}

	public String getStrRole() {
		return strRole;
	}

	public void setStrRole(String strRole) {
		this.strRole = strRole;
	}
	
	
}
