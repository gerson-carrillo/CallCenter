package com.almundo.CallCenter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Clase que distribuye todos los procesos a ejecutar
 * @author gcarrillo
 * */
public class Dispatcher implements Runnable  {
	
	/*Se crean las variables necesarias para el proceso, estaticas para que todos los procesos compartan la misma información*/
	private static int intMaxCalls;
	private static int intTryCalls;
	private static int intMinCallTime;
	private static int intMaxCallTime;
	private static Map<String, ProcessEmployeeDTO> mapProcessEmployee;
	private static Queue<CallDTO> quequeCallWait;
	
	private CallDTO call;
	
	public Dispatcher(CallDTO call) {
		this.call = call;
	}
	
	/**
	 * Constructor que inicializa el proceso con las parametros requeridos
	 * @param mapProcessEmployee Se define la cantidad de llamadas que puede atender cada rol
	 * @param intMaxCalls Cantidad de llamadas que se pueden atender en paralelo
	 * @param intMinCallTime Tiempo minimo en segundos que dura una llamada
	 * @param intMaxCallTime Tiempo minimo en segundos que dura una llamada 
	 * @listCalls Lista de llamadas a procesar
	 * */
	public Dispatcher(Map<String, ProcessEmployeeDTO> mapProcessEmployee, int intMaxCalls, List<CallDTO> listCalls, int intMinCallTime, int intMaxCallTime){
		Dispatcher.mapProcessEmployee = mapProcessEmployee;
		Dispatcher.intMaxCalls = intMaxCalls;
		Dispatcher.quequeCallWait=new LinkedList<CallDTO>();
		Dispatcher.intMinCallTime = intMinCallTime;
		Dispatcher.intMaxCallTime = intMaxCallTime;
		addCallsToQueque(listCalls);
	}
	
	/**
	 * Método que asigna las llamadas entrantes a una cola, para procesarlas de forma ordenada
	 * @author gcarrillo
	 * @listCalls Lista de llamadas a procesar
	 * */
	public void addCallsToQueque(List<CallDTO> listCalls) {
			quequeCallWait.addAll(listCalls);
	}
	
	@Override
	public void run() {
		dispatchCall();
	}
	
	public void dispatchCall() {
		try {
		
		/*Se parametriza el tiempo de atencion de la llamada, tiempo aleatorio de 5 y 10 segundos*/
		Random rn = new Random();
		int intDuration = rn.nextInt(intMinCallTime) + (intMaxCallTime - intMinCallTime);
		System.out.println("---------- Inicia llamada " + call.getIntCall() + " --  atiende usuario de rol "+ call.getStrRole());
        this.waitSeconds(intDuration);
		System.out.println("-------------------- Finaliza llamada " + call.getIntCall() + " -- duracion " + intDuration +" segundos");
		}catch(Exception e) {
			System.out.println("Error");
			e.getMessage();
		}
		
	}
	
	/**
	 * Método que distribuye las llamadas según las reglas de negocio definidas
	 * Se asignan las llamadas a los operadores si estan disponibles
	 * en caso de que los asesores no esten disponibles, se escala a un supervisor
	 * en caso de que un supervisor no este disponible, se asigna a un operados.
	 * Se muestran mensajes, en los escenarios cuando ingresen mas llamadas de las permitidas concurrentemente y cuando no hay personas para atender
	 * @author gcarrillo 
	 * */
	public void receiveCalls() {
		
		System.out.println("--------  Inicia atencion de llamadas...");
		
		/*Se crea un executor service para cada rol (operador, supervisor, director), 
		 * con el máximo de hilos permitidos que se parametrizan en el constructor de esta clase, de esta forma nunca se van a asignar mas llamadas que las permitidas a las personas o roles*/
		ExecutorService executorOperator = Executors.newFixedThreadPool( Dispatcher.mapProcessEmployee.get(RoleEnum.OPERATOR.name()).getIntMaxConcurrentCalls() );
		ExecutorService executorSupervisor = Executors.newFixedThreadPool( Dispatcher.mapProcessEmployee.get(RoleEnum.SUPERVISOR.name()).getIntMaxConcurrentCalls() );
		ExecutorService executorDirector = Executors.newFixedThreadPool( Dispatcher.mapProcessEmployee.get(RoleEnum.DIRECTOR.name()).getIntMaxConcurrentCalls() );
		
		/*Al iniciar el proceso de atención de llamadas, se hace una advertencia en caso de que se exceda el maximo de llamadas permitidas.
		 * De igual forma, las llamadas se procesan por las personas, quedan en espera hasta que una persona, respetando la asignación según roles.
		 * Se muestra una sola ves al iniciar el proceso, a modo informativo, de igual forma todas las llamadas se atienden, con un tiempo de espera.
		 * */
		if( Dispatcher.quequeCallWait.size() > Dispatcher.intMaxCalls) {
			System.out.println("!!ADVERTENCIA¡¡ Llamadas entrantes("+Dispatcher.quequeCallWait.size()+") vs Llamadas permitidas("+Dispatcher.intMaxCalls+"). Concurrentemente estan ingresando mas llamadas de las que se pueden atender, asigne mas personal... Todas se atenderán... !!ADVERTENCIA¡¡ ");
		}
		
		/*Recorre el proceso con las llamadas parametrizadas, las llamadas se recorren en una cola, para asegurar el orden de atencion
		 * primera llamada en entrar, primera en atender
		 * */
		while ( !Dispatcher.quequeCallWait.isEmpty() ) {
		
			/*Se obtieneuna llamada para asignarla al rol respectivo*/
			CallDTO callWhile =  Dispatcher.quequeCallWait.peek() ;

			/*Se crean las variables respectivas para validacion de disponibilidad de las personas y sus roles.*/
			
			/*La variable intTotalActiveCount guarda todos los procesos que se estan ejecutando (llamadas en curso), agrupando todos los roles*/
			int intTotalActiveCount = ((ThreadPoolExecutor) executorOperator).getActiveCount() + ((ThreadPoolExecutor) executorSupervisor).getActiveCount() + ((ThreadPoolExecutor) executorDirector).getActiveCount();
			
			/*Se crean variables de tipo booleano, para definir si hay o no personas disponibles para atencion de llamadas*/
			boolean isAvaibleOperator = ((ThreadPoolExecutor) executorOperator).getActiveCount() < ((ThreadPoolExecutor) executorOperator).getMaximumPoolSize();
			boolean isAvaibleSupervisor = ((ThreadPoolExecutor) executorSupervisor).getActiveCount() < ((ThreadPoolExecutor) executorSupervisor).getMaximumPoolSize();
			boolean isAvaibleDirector = ((ThreadPoolExecutor) executorDirector).getActiveCount() < ((ThreadPoolExecutor) executorDirector).getMaximumPoolSize();
			
			/*Se imprime a modo informativo la ocupación de las personas y sus roles, para validar que se este asignando correctamente las llamadas*/
			System.out.println(
					"OPERADORES(Total="+((ThreadPoolExecutor) executorOperator).getMaximumPoolSize()+", ocupados="+((ThreadPoolExecutor) executorOperator).getActiveCount()+") - "
					+
					"SUPERVISORES(Total="+((ThreadPoolExecutor) executorSupervisor).getMaximumPoolSize()+", ocupados="+((ThreadPoolExecutor) executorSupervisor).getActiveCount()+") - "
					+
					"DIRECTORES(Total="+((ThreadPoolExecutor) executorDirector).getMaximumPoolSize()+", ocupados="+((ThreadPoolExecutor) executorDirector).getActiveCount()+") - "
					);
			
			/*Se asigna una llamada a una persona, validando la regla de negocio definida, tomando en cuenta disponibilidad de atención y jerarquía de roles*/
			/*Si alguien responde, se inicializa intTryCalls=0, porque estan respondiendo llamadas, para que el sistema no termine el proceso con llamadas pendientes*/
			if( isAvaibleOperator ) {
				intTryCalls=0;
				callWhile.setStrRole(RoleEnum.OPERATOR.name());
				Runnable process = new Dispatcher( callWhile );
				executorOperator.execute(process);
			}else if( isAvaibleSupervisor ){
				intTryCalls=0;
				callWhile.setStrRole(RoleEnum.SUPERVISOR.name());
				Runnable process = new Dispatcher( callWhile );
				executorSupervisor.execute(process);
			}else if( isAvaibleDirector ){
				intTryCalls=0;
				callWhile.setStrRole(RoleEnum.DIRECTOR.name());
				Runnable process = new Dispatcher( callWhile );
				executorDirector.execute(process);
			}else{
				System.out.println("No hay asesores que puedan atender su llamada, por favor espere en un momento lo atendemos...");
				/*Se pone un tiempo de espera, para continuar con el proceso
				 * es probable que en esos segundos ya hubiese terminado una llamada.
				 * Esto con el objetivo de no saturar el sistema con mensajes.
				 * */
		        this.waitSeconds(5);
		        Dispatcher.intTryCalls++;
			}
			
			if(isAvaibleOperator || isAvaibleSupervisor || isAvaibleDirector) {
	            /*Despues de procesada la llamada, se procede a asignar las variables y contadores necesarias*/          
	            Dispatcher.quequeCallWait.remove(callWhile);
			}
			
			
			/*Si se intenta 10 veces y no se responde una llamada, se asume las personas no estan respondiendo. Se termina el proceso*/
			if(Dispatcher.intTryCalls == 5) {
				System.out.println("Se identifica que en 20 intentos no se responden llamadas, no se atienden mas llamadas, el sistema espera llamadas en curso y finaliza...");
				break;
			}
				
		}
		
		/*Se deja en espera, en caso de que no termine un proceso muy largo (por ejemplo el caso de enviar por parametro 100 segundos de duracion de llamada, el sistema espere correctamente
		 * y no queden procesos en background, el while espera a que todos los procesos terminen
		 * )*/
		while(
					((ThreadPoolExecutor) executorOperator).getActiveCount() > 0 || 
					((ThreadPoolExecutor) executorSupervisor).getActiveCount() > 0 ||
					((ThreadPoolExecutor) executorDirector).getActiveCount() > 0
		);
		
		/*Se espera que se finalicen las llamadas en curso, con un tiempo de espera*/
        this.waitSeconds(15);
		System.out.println("--------  Finaliza atencion de llamadas...");

		
	}	
	
	
	/**
	 * Se crea un método para poner en espera la aplicación.
	 * @param intDuration Duración en segundos, para pausar el sistema durante ese tiempo
	 * @author gcarrillo 
	 * */
	private void waitSeconds(int intDuration) {
		try {
			
			Thread.sleep(intDuration * 1000);  
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}
	
	
}
