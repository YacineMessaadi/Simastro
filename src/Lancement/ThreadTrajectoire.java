package Lancement;

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
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
				cancel();
			}
		}
	}
	
	/* Pour lancer le thread 
	 * 

	public static void main(String[] args) {
		ThreadTrajectoire T = new ThreadTrajectoire();
		T.start();
	}
	*/

}
