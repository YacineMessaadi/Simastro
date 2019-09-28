package Lancement;

import java.util.Scanner;

public class ThreadTrajectoire extends Thread {
	
	private boolean running = true;
	
	public ThreadTrajectoire(){ 
	}
	
	public void cancel() {
		this.running = false;
	}
	
	public void run() {
		while(running) {
			System.out.println("Test thread");
			try {
				Scanner sc = new Scanner(System.in);
			    System.out.println("Enter the n value :");
			    int n = sc.nextInt();
			      
			    for (int i = 1; i <= n; i++){
			    	int x = 1;
			        for (int j = 2; j < i; j++){
			        	if (gcd(j, n) == 1){
			        		x++;
			            }
			        }
			        System.out.println(x);
			    }
			    Thread.sleep(1000);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
				cancel();
			}
		}
	}
	
	public int gcd(int a,int b){
			int i, hcf = 0;
		    for(i = 1; i <= a || i <= b; i++) {
		    	if( a%i == 0 && b%i == 0 )
		    		hcf = i;
		    }
		    return hcf;
	}
	
	/* Pour lancer le thread 
	 * 

	public static void main(String[] args) {
		ThreadTrajectoire T = new ThreadTrajectoire();
		T.start();
	}
	*/

}
