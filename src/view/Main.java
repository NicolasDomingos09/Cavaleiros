package view;

import java.util.concurrent.Semaphore;

import controller.Cavaleiro;

public class Main {

	public static void main(String[] args) {
 
		Semaphore semaforo = new Semaphore(1);
		
		for(int cav = 1; cav <= 4; cav++) {
			Thread cavaleiro = new Cavaleiro(semaforo, cav);
			cavaleiro.start();
		}
	}
}