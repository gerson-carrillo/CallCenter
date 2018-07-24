package com.almundo.CallCenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnitTestDispatcher {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		Map<String, ProcessEmployeeDTO> mapProcessEmployee = new HashMap<String, ProcessEmployeeDTO>();
		mapProcessEmployee.put(RoleEnum.OPERATOR.name(), new ProcessEmployeeDTO(3, RoleEnum.OPERATOR));
		mapProcessEmployee.put(RoleEnum.SUPERVISOR.name(), new ProcessEmployeeDTO(1, RoleEnum.SUPERVISOR));
		mapProcessEmployee.put(RoleEnum.DIRECTOR.name(), new ProcessEmployeeDTO(1, RoleEnum.DIRECTOR));
		
		List<CallDTO> listCalls=new ArrayList<>();
		listCalls.add(new CallDTO(1, 10));
		listCalls.add(new CallDTO(2, 10));
		listCalls.add(new CallDTO(3, 10));
		listCalls.add(new CallDTO(4, 10));
		listCalls.add(new CallDTO(5, 10));
		
		
		
		Dispatcher dispatcher = new Dispatcher(mapProcessEmployee, 5, listCalls);
		dispatcher.dispatchCall();

		
		
		
	}

}
