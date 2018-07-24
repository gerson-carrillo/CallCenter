package com.almundo.CallCenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnitTestDispatcher {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/*Se parametriza la aplicación para procesar 10 llamadas
		 * Operador procesa máximo 5 llamadas
		 * Supervisor procesa máximo 3 llamadas
		 * Director procesa máximo 2 llamadas
		 * */
		Map<String, ProcessEmployeeDTO> mapProcessEmployee = new HashMap<String, ProcessEmployeeDTO>();
		mapProcessEmployee.put(RoleEnum.OPERATOR.name(), new ProcessEmployeeDTO(5, RoleEnum.OPERATOR));
		mapProcessEmployee.put(RoleEnum.SUPERVISOR.name(), new ProcessEmployeeDTO(3, RoleEnum.SUPERVISOR));
		mapProcessEmployee.put(RoleEnum.DIRECTOR.name(), new ProcessEmployeeDTO(2, RoleEnum.DIRECTOR));
		
		List<CallDTO> listCalls=new ArrayList<>();
		int numOfCalls = 10;
		for(int i=1; i<=numOfCalls ; i++) {
			listCalls.add(new CallDTO(i));
		}
		
		/*Se procesan 10 llamadas, parametrizadas en la variable numOfCalls*/
		Dispatcher dispatcher = new Dispatcher(mapProcessEmployee, numOfCalls, listCalls);
		dispatcher.dispatchCall();

		/*Se procesan 15 llamadas, parametrizando que la aplicación solo tiene disponibilidad para atender 10 llamadas*/
		/*Este escenerio aplica para cuendo hay mas llamadas de las que el sistema puede procesar, o cuando no hay empleados para atender la llamada*/
		listCalls=new ArrayList<>();
		numOfCalls = 15;
		for(int i=1; i<=numOfCalls ; i++) {
			listCalls.add(new CallDTO(i));
		}
		dispatcher = new Dispatcher(mapProcessEmployee, numOfCalls, listCalls);
		dispatcher.dispatchCall();
		
		
	}

}
