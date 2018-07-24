package com.almundo.CallCenter;

import java.util.Random;

/**
 * Clase que ejecuta que procesa las llamadas
 * */
public class Process implements Runnable {
	
	private CallDTO call;
	
	public Process(CallDTO call) {
		this.call = call;
	}

	@Override
	public void run() {
		try {
		
		/*Se parametriza el tiempo de espera de la llamada*/
		Random rn = new Random();
		int intDuration = rn.nextInt(6) + 5;
		System.out.println("Inicia El usuario con role " + call.getStrRole() + " procesa la llamada numero " + call.getIntCall() );

        this.waitSeconds(intDuration);
        
		System.out.println("Finaliza El usuario con role " + call.getStrRole() +  
								 " procesa la llamada numero " + call.getIntCall() + " con duracion en segundos de " + intDuration);
		}catch(Exception e) {
			System.out.println("Error");
			e.getMessage();
		}
		
	}
	

	private void waitSeconds(int intDuration) {
		try {
			
			Thread.sleep(intDuration * 1000);
			//System.out.println("Llamada demora en segundos = " + intDuration);  
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}
	
	
}
