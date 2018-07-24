package com.almundo.CallCenter;

/**
 * Clase que guarda los datos de cada rol
 * */
public class ProcessEmployeeDTO {
	
	/*Se crean las variables necesarias para cada rol, numero de llamadas que puede procesar y llamadas actuales*/
	private int intMaxProcess;
	private int intCurrentProcess;
	private RoleEnum roleEnum;
	
	public ProcessEmployeeDTO(int intMaxProcess, RoleEnum roleEnum) {
		this.intMaxProcess = intMaxProcess;
		this.intCurrentProcess = 1;
		this.roleEnum= roleEnum;
	}

	public int getIntMaxProcess() {
		return intMaxProcess;
	}

	public void setIntMaxProcess(int intMaxProcess) {
		this.intMaxProcess = intMaxProcess;
	}

	public int getIntCurrentProcess() {
		return intCurrentProcess;
	}

	public void setIntCurrentProcess(int intCurrentProcess) {
		this.intCurrentProcess = intCurrentProcess;
	}

	public RoleEnum getRoleEnum() {
		return roleEnum;
	}

	public void setRoleEnum(RoleEnum roleEnum) {
		this.roleEnum = roleEnum;
	}
	
}
