package controller;

import java.util.concurrent.Semaphore;
import java.util.Random;

public class Cavaleiro extends Thread{
	
	int cavaleiro;
	
	final int tamanho = 2000;
	Semaphore semaforo;
	Semaphore controleItens = new Semaphore(1);
	
	static boolean tocha = false;
	static boolean pedra = false;
	boolean temItem = false;
	
	static int minPorta = 1;
	static int maxPorta = 4;
	
	private int item = 0;
	static int portas[] = {0, 0, 1, 0};
	
	public Cavaleiro(Semaphore semaforo, int cavaleiro) {
		this.semaforo = semaforo;
		this.cavaleiro = cavaleiro;
	}
	
	private void caminhar() {
		Random rand = new Random();
		
		int totalCaminhado = 0;
		while(totalCaminhado < tamanho) {
			try {
				sleep(50);
				int passo = (rand.nextInt(2,4)+item);
				totalCaminhado += passo;
				//System.out.println("Cavaleiro " + cavaleiro + " caminhou " + passo + " metros." + " Total percorrido " + totalCaminhado);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			if(tocha == false && totalCaminhado >= 500 && temItem == false) {
				try {
					controleItens.acquire();
					tocha();
				}catch(Exception e) {
					e.printStackTrace();
				}finally {
					controleItens.release();
				}

			}
			
			if(pedra == false && totalCaminhado >= 1500 && temItem == false) {
				try {
					controleItens.acquire();
					pedra();
				}catch(Exception e) {
					e.printStackTrace();
				}finally {
					controleItens.release();
				}
			}
		}
		System.out.println("O cavaleiro " + cavaleiro + " chegou ao fim do corredor");
	}
	
	public void saida() {
		Random rand = new Random();
		
		boolean saiu = false;
		while(!saiu) {
			int escolha = rand.nextInt(1,4);
			if(portas[(escolha-1)] == (-1)) {
				escolha = rand.nextInt(1,4);
			}else {
				if(portas[(escolha-1)] == 1){
					System.out.println("O cavaleiro " + cavaleiro + " conseguiu sair pela porta ");
				}else {
					System.out.println("O cavaleiro " + cavaleiro + " foi devorado pelo monstro da porta ");
				}
				portas[(escolha-1)] = -1;
				saiu = true;
			}
		}
	}
	
	@Override
	public void run() {
		caminhar();
		try {
			semaforo.acquire();
			saida();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			semaforo.release();
		}
	}
	
	private void tocha() {
		if(tocha == false && temItem == false) {
			tocha = true;
			temItem = true;
			item = 2;
			System.out.println("Cavaleiro " + cavaleiro + " pegou a tocha");
		}
	}
	
	private void pedra() {
		if(pedra == false && temItem == false) {
			pedra = true;
			temItem = true;
			item = 2;
			System.out.println("Cavaleiro " + cavaleiro + " pegou a pedra");
		}
	}
}
