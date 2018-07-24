package com.almundo.CallCenter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Dispatcher {
	
	private static int intMaxCalls;
	private static int intCurrentCalls;
	private static Map<String, ProcessEmployeeDTO> mapProcessEmployee;
	private static Queue<CallDTO> quequeCallWait;
	
	public Dispatcher(Map<String, ProcessEmployeeDTO> mapProcessEmployee, int intMaxCalls, List<CallDTO> listCalls){
		Dispatcher.mapProcessEmployee = mapProcessEmployee;
		Dispatcher.intMaxCalls = intMaxCalls;
		Dispatcher.intCurrentCalls = 0;
		Dispatcher.quequeCallWait=new LinkedList<CallDTO>();
		addCallsToQueque(listCalls);
	}
	
	public void addCallsToQueque(List<CallDTO> listCalls) {
			quequeCallWait.addAll(listCalls);
	}
	
	public void dispatchCall() {
		ExecutorService executor = Executors.newFixedThreadPool(Dispatcher.intMaxCalls);
		
		while ( !Dispatcher.quequeCallWait.isEmpty() ) {
			CallDTO callWhile = assignCall( Dispatcher.quequeCallWait.peek() ) ;
			boolean isAvaibleToProcess = callWhile != null && !Dispatcher.quequeCallWait.isEmpty() ;
			if( isAvaibleToProcess ) {
				Runnable process = new Process( callWhile );
	            executor.execute(process);

	            	            
	            Dispatcher.quequeCallWait.remove(callWhile);
				int numOfcalls = Dispatcher.mapProcessEmployee.get( callWhile.getStrRole()).getIntCurrentProcess()-1;
	            Dispatcher.mapProcessEmployee.get( callWhile.getStrRole() ).setIntCurrentProcess( numOfcalls );
	            Dispatcher.intCurrentCalls--;
			}
			else
				System.out.println("Ocupado esperando para procesar llamada " + callWhile.getIntCall());
				
		}
        executor.shutdown();
		
	}
	
	public CallDTO assignCall(CallDTO call) {
		
		if(Dispatcher.intCurrentCalls <= Dispatcher.intMaxCalls) {
			ProcessEmployeeDTO operator = Dispatcher.mapProcessEmployee.get(RoleEnum.OPERATOR.name());
			ProcessEmployeeDTO supervidor = Dispatcher.mapProcessEmployee.get(RoleEnum.SUPERVISOR.name());
			ProcessEmployeeDTO director = Dispatcher.mapProcessEmployee.get(RoleEnum.DIRECTOR.name());
			if(operator.getIntCurrentProcess() <= operator.getIntMaxProcess()) {
				int numOfcalls = operator.getIntCurrentProcess()+1;
				operator.setIntCurrentProcess(numOfcalls);
				call.setStrRole(RoleEnum.OPERATOR.name());
				Dispatcher.intCurrentCalls++;
				//System.out.println("Entra operator" + numOfcalls);
				return call;
			} else if(supervidor.getIntCurrentProcess() <= supervidor.getIntMaxProcess()) {
				int numOfcalls = supervidor.getIntCurrentProcess()+1;
				supervidor.setIntCurrentProcess(numOfcalls);
				call.setStrRole(RoleEnum.SUPERVISOR.name());
				Dispatcher.intCurrentCalls++;
				//System.out.println("Entra supervisor" + numOfcalls);
				return call;
			} else if(director.getIntCurrentProcess() <= director.getIntMaxProcess()) {
				int numOfcalls = director.getIntCurrentProcess()+1;
				director.setIntCurrentProcess(numOfcalls);
				call.setStrRole(RoleEnum.DIRECTOR.name());
				Dispatcher.intCurrentCalls++;
				return call;
			}
			System.out.println("No hay asesores que puedan atender su llamada");
		}else {
			System.out.println("El numero de llamadas esta al maximo, esperar en cola");
		}
		return null;
			
	}

	
	
}
