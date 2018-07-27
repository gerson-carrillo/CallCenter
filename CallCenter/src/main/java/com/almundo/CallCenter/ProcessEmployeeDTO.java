package com.almundo.CallCenter;

/**
 * Clase que guarda los datos de cada rol y las personas asignadas al rol
 * */
public class ProcessEmployeeDTO {
	
	/*Se crean las variables necesarias para cada rol, numero de llamadas que puede procesar y llamadas actuales*/
	private int intMaxConcurrentCalls;
	private RoleEnum roleEnum;
	
	/**
	 * Constructor que inicializa el numero de personas que estan asignadas a un rol, 
	 * de esta forma se sabe cuantas personas pueden atender llamadas de forma concurrente
	 * @param intMaxProcess Número de llamadas que pueden ser atendidas de forma concurrente para el rol indicado
	 * @param roleEnum Rol asignado
	 * */
	public ProcessEmployeeDTO(int intMaxConcurrentCalls, RoleEnum roleEnum) {
		this.intMaxConcurrentCalls = intMaxConcurrentCalls;
		this.roleEnum= roleEnum;
	}

	public RoleEnum getRoleEnum() {
		return roleEnum;
	}

	public void setRoleEnum(RoleEnum roleEnum) {
		this.roleEnum = roleEnum;
	}

	public int getIntMaxConcurrentCalls() {
		return intMaxConcurrentCalls;
	}

	public void setIntMaxConcurrentCalls(int intMaxConcurrentCalls) {
		this.intMaxConcurrentCalls = intMaxConcurrentCalls;
	}
	
}
