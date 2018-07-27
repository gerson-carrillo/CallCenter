package com.almundo.CallCenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnitTestDispatcher {

	public static void main(String[] args) {

		// TODO Descomentarear el código para ejecutar la prueba unitaria requerida
		

		/**Se prueba el escerario 1:
			La clase Dispatcher debe tener la capacidad de poder procesar 10
			llamadas al mismo tiempo (de modo concurrente). 
		 Se crea el test para cumplir con: Debe tener un test unitario donde lleguen 10 llamadas.
		 
		 El rango de duracion de una llamada es parametrizable, se envía en este método
		 
		 */
		unitTestOneReceiveTenCalls();
		
		/*prueba unitaria para:
			Dar alguna solución sobre qué pasa con una llamada cuando no hay
			ningún empleado libre. 
		 * */
		//unitTestTwoNoAttendCalls();
		
		
		/**
		Dar alguna solución sobre qué pasa con una llamada cuando entran
		más de 10 llamadas concurrentes.
		
		SOLUCION DADA: El sistema encola todas las llamadas entrantes, 
		procesa 10 concurrentes que son las permitidas, genera un mensaje que hay mas llamadas concurrentes de las permitidas
		de igual forma, el sistema encola las llamadas y va atendiendo según se liberen las personas 
		y respetando la ssignación de llamadas por jerarquia de rol
		 
		 * */
		//unitTestOneReceiveMoreThanTenCalls();
		
	}
	
	public static void unitTestOneReceiveTenCalls() {
		// TODO Auto-generated method stub

		/*El número de personas por rol, se puede enviar por parámetro, para hacer mas dinámica la aplicación*/
		/*Se parametriza la aplicación para procesar 10 llamadas
		 * Operador: Hay 5 personas con rol Operador para atender llamadas
		 * Supervisor: Hay 3 personas con rol supervisor para atender llamadas
		 * Director: Hay 2 personas con rol Director para atender llamadas
		 * */
		Map<String, ProcessEmployeeDTO> mapProcessEmployee = new HashMap<String, ProcessEmployeeDTO>();
		mapProcessEmployee.put(RoleEnum.OPERATOR.name(), new ProcessEmployeeDTO(5, RoleEnum.OPERATOR));
		mapProcessEmployee.put(RoleEnum.SUPERVISOR.name(), new ProcessEmployeeDTO(3, RoleEnum.SUPERVISOR));
		mapProcessEmployee.put(RoleEnum.DIRECTOR.name(), new ProcessEmployeeDTO(2, RoleEnum.DIRECTOR));
		
		/*Se asigna una lista de 10 llamadas, identificandolas con un id único, para poder llevar la traza*/
		List<CallDTO> listCalls=new ArrayList<>();
		int numOfCalls = 10;
		for(int i=1; i<=numOfCalls ; i++) {
			listCalls.add(new CallDTO(i));
		}
		
		/*Se procesan 10 llamadas concurrentes, parametrizadas en la variable numOfCalls*/
		Dispatcher dispatcher = new Dispatcher(mapProcessEmployee, numOfCalls, listCalls, 5, 10);
		dispatcher.receiveCalls();
		
	}
	
	/**prueba unitaria para:
	Dar alguna solución sobre qué pasa con una llamada cuando no hay
	ningún empleado libre.
	@author gcarrillo 
	 */
	public static void unitTestTwoNoAttendCalls() {
		// TODO Auto-generated method stub

		/*Se parametrizan el numero de empleados para cada rol en cero, de tal forma que no se atiendan las llamadas
		 * */
		Map<String, ProcessEmployeeDTO> mapProcessEmployee = new HashMap<String, ProcessEmployeeDTO>();
		mapProcessEmployee.put(RoleEnum.OPERATOR.name(), new ProcessEmployeeDTO(1, RoleEnum.OPERATOR));
		mapProcessEmployee.put(RoleEnum.SUPERVISOR.name(), new ProcessEmployeeDTO(1, RoleEnum.SUPERVISOR));
		mapProcessEmployee.put(RoleEnum.DIRECTOR.name(), new ProcessEmployeeDTO(1, RoleEnum.DIRECTOR));
		
		/*Se envía una llamada*/
		List<CallDTO> listCalls=new ArrayList<>();
		int numOfCalls = 4;
		for(int i=1; i<=numOfCalls ; i++) {
			listCalls.add(new CallDTO(i));
		}
		
		/*Se procesan 10 llamadas concurrentes, parametrizadas en la variable numOfCalls*/
		/*Se envía un rango de 40 a 60 segundos, 
		 * con ese tiempo el sistema detecta inactividad del sistema, no contesta mas llamadas y espera que finalicen las llamadas en curso*/
		Dispatcher dispatcher = new Dispatcher(mapProcessEmployee, numOfCalls, listCalls, 40, 60);
		dispatcher.receiveCalls();
		
	}

	
	/**
	Dar alguna solución sobre qué pasa con una llamada cuando entran
	más de 10 llamadas concurrentes.
	
	SOLUCION DADA: El sistema encola todas las llamadas entrantes, 
	procesa 10 concurrentes que son las permitidas, genera un mensaje que hay mas llamadas concurrentes de las permitidas
	de igual forma, el sistema encola las llamadas y va atendiendo según se liberen las personas 
	y respetando la ssignación de llamadas por jerarquia de rol
	 
	 * */
	public static void unitTestOneReceiveMoreThanTenCalls() {
		// TODO Auto-generated method stub

		/*El número de personas por rol, se puede enviar por parámetro, para hacer mas dinámica la aplicación*/
		/*Se parametriza la aplicación para procesar 10 llamadas
		 * Operador: Hay 5 personas con rol Operador para atender llamadas
		 * Supervisor: Hay 3 personas con rol supervisor para atender llamadas
		 * Director: Hay 2 personas con rol Director para atender llamadas
		 * */
		Map<String, ProcessEmployeeDTO> mapProcessEmployee = new HashMap<String, ProcessEmployeeDTO>();
		mapProcessEmployee.put(RoleEnum.OPERATOR.name(), new ProcessEmployeeDTO(5, RoleEnum.OPERATOR));
		mapProcessEmployee.put(RoleEnum.SUPERVISOR.name(), new ProcessEmployeeDTO(3, RoleEnum.SUPERVISOR));
		mapProcessEmployee.put(RoleEnum.DIRECTOR.name(), new ProcessEmployeeDTO(2, RoleEnum.DIRECTOR));
		
		/*Se asigna una lista de 15 llamadas, identificandolas con un id único, para poder llevar la traza*/
		List<CallDTO> listCalls=new ArrayList<>();
		int numOfCalls = 12;
		for(int i=1; i<=numOfCalls ; i++) {
			listCalls.add(new CallDTO(i));
		}
		
		/*Se procesan 10 llamadas concurrentes, parametrizadas en la variable numOfCalls, rango de segundos de duracion de llamada 5-10*/
		Dispatcher dispatcher = new Dispatcher(mapProcessEmployee, 10, listCalls, 5, 10);
		dispatcher.receiveCalls();
		
	}
	

}
