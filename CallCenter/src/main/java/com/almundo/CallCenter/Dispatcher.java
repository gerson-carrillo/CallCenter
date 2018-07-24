package com.almundo.CallCenter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Clase que distribuye todos los procesos a ejecutar
 * @author gcarrillo
 * */
public class Dispatcher {
	
	/*Se crean las variables necesarias para el proceso, estaticas para que todos los procesos compartan la misma información*/
	private static int intMaxCalls;
	private static int intCurrentCalls;
	private static Map<String, ProcessEmployeeDTO> mapProcessEmployee;
	private static Queue<CallDTO> quequeCallWait;
	
	/**
	 * Constructor que inicializa el proceso con las parametros requeridos
	 * @param mapProcessEmployee Se define la cantidad de llamadas que puede atender cada rol
	 * @param intMaxCalls Cantidad de llamadas que se pueden atender en paralelo
	 * @listCalls Lista de llamadas a procesar
	 * */
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
	
	
	/**
	 * Método que distribuye las llamadas
	 * */
	public void dispatchCall() {
		/*Se crea la cantidad de procesos que se pueden ejecutar en paralelo*/
		ExecutorService executor = Executors.newFixedThreadPool(Dispatcher.intMaxCalls);

		
		
		/*Recorre el proceso con las llamadas parametrizadas*/
		while ( !Dispatcher.quequeCallWait.isEmpty() && assignCall( Dispatcher.quequeCallWait.peek()) != null ) {
		
			/*Se obtieneuna llamada para asignarla al rol respectivo*/
			CallDTO callWhile = assignCall( Dispatcher.quequeCallWait.peek() ) ;

			boolean isAvaibleToProcess = callWhile != null ;
			
			/*Solamente se ejecuta el proceso si hay empleados disponibles para atender la llamada*/
			if( isAvaibleToProcess ) {
				
				/*Se atiende la llamada con los datos necesarios*/
				Runnable process = new Process( callWhile );
	            executor.execute(process);

	            /*Despues de procesada la llamada, se procede a asignar las variables y contadores necesarias*/          
	            Dispatcher.quequeCallWait.remove(callWhile);
				int numOfcalls = Dispatcher.mapProcessEmployee.get( callWhile.getStrRole()).getIntCurrentProcess()-1;
	            Dispatcher.mapProcessEmployee.get( callWhile.getStrRole() ).setIntCurrentProcess( numOfcalls );
	            Dispatcher.intCurrentCalls--;
			}
			//else
				//System.out.println("Ocupado esperando para procesar llamada " + callWhile.getIntCall());
				
		}

		
	}
	
	/**
	 * Método que asigna la llamada a un empleado que esté disponible
	 * @param call llamada a procesar
	 * */
	public CallDTO assignCall(CallDTO call) {
		
		/*Solo se procesa la llamada si el máximo de llamadas no ha llegado a su tope*/
		if(Dispatcher.intCurrentCalls <= Dispatcher.intMaxCalls) {
			ProcessEmployeeDTO operator = Dispatcher.mapProcessEmployee.get(RoleEnum.OPERATOR.name());
			ProcessEmployeeDTO supervidor = Dispatcher.mapProcessEmployee.get(RoleEnum.SUPERVISOR.name());
			ProcessEmployeeDTO director = Dispatcher.mapProcessEmployee.get(RoleEnum.DIRECTOR.name());
			
			/*Se pregunta si cada rol de empleado tiene la máxima cantidad de llamadas posibles a atender*/
			if(operator.getIntCurrentProcess() <= operator.getIntMaxProcess()) {
				/*Se validan los valores para un rol operador, controlando el máximo de llamadas que puede recibir*/
				int numOfcalls = operator.getIntCurrentProcess()+1;
				operator.setIntCurrentProcess(numOfcalls);
				call.setStrRole(RoleEnum.OPERATOR.name());
				Dispatcher.intCurrentCalls++;
				//System.out.println("Entra operator numOfCalls=" + numOfcalls + " intCurrentCalls="+intCurrentCalls);
				return call;
			} 
			/*Se validan los datos para el */
			else if(supervidor.getIntCurrentProcess() <= supervidor.getIntMaxProcess()) {
				
				/*Se validan los valores para un rol supervisor, controlando el máximo de llamadas que puede recibir*/
				int numOfcalls = supervidor.getIntCurrentProcess()+1;
				supervidor.setIntCurrentProcess(numOfcalls);
				call.setStrRole(RoleEnum.SUPERVISOR.name());
				Dispatcher.intCurrentCalls++;
				//System.out.println("Entra supervisor numOfCalls=" + numOfcalls + " intCurrentCalls="+intCurrentCalls);
				return call;
			} else if(director.getIntCurrentProcess() <= director.getIntMaxProcess()) {
				
				/*Se validan los valores para un rol director, controlando el máximo de llamadas que puede recibir*/
				int numOfcalls = director.getIntCurrentProcess()+1;
				director.setIntCurrentProcess(numOfcalls);
				call.setStrRole(RoleEnum.DIRECTOR.name());
				Dispatcher.intCurrentCalls++;
				//System.out.println("Entra director numOfCalls=" + numOfcalls + " intCurrentCalls="+intCurrentCalls);
				return call;
			}
			/*Se controla si no hay empleados para procesar la llamada*/
			//System.out.println("No hay asesores que puedan atender su llamada");
		}else {
			/*Se controla si el máximo de llamadas ha llegado a su tope*/
			//System.out.println("El numero de llamadas esta al maximo, esperar en cola");
		}
		return null;
			
	}

	
	
}
