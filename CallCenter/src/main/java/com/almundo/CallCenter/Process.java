package com.almundo.CallCenter;

public class Process implements Runnable {
	
	private CallDTO call;
	
	public Process(CallDTO call) {
		this.call = call;
	}

	@Override
	public void run() {
		try {
		System.out.println("Inicia El usuario con role " + call.getStrRole() + " procesa la llamada numero " + call.getIntCall() + " con duración " + this.call.getIntDuration());

        this.waitSeconds(this.call.getIntDuration());
        
		System.out.println("Finaliza El usuario con role " + call.getStrRole() +  
								 " procesa la llamada numero " + call.getIntCall() + " con duración " + this.call.getIntDuration());
		}catch(Exception e) {
			System.out.println("Error");
			e.getMessage();
		}
		
	}
	

	private void waitSeconds(int intDuration) {
		try {
			Thread.sleep(intDuration * 1000);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}
	
	
}
