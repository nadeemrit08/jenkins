package in.kw.thread02.ex.call;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class NotifyDelivery implements Runnable {

	@Override
	public void run() {

		System.out.println("NotifyDelivery.run() : Start " + Thread.currentThread().getName());

		DeliveyService deliveyService = new DeliveyService();

		deliveyService.confirmDelivery();

		//System.out.println("NotifyDelivery.run() : End " + Thread.currentThread().getName());

	}

}

public class Driver {

	/*
	 * Java 1.5 No of threads ? X reuse
	 */

	public static void main(String[] args) {

		/*
		 * Create the executor service
		 */
		
		/*
		 * ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
		 * // only single thread will be created
		 * 
		 * NotifyDelivery task = new NotifyDelivery();
		 * 
		 * singleThreadExecutor.execute(task);
		 * 
		 * singleThreadExecutor.shutdown();
		 */
		 

		/*ExecutorService newCachedThreadPool = Executors.newCachedThreadPool(); //Thread will be decide by exe framework
		NotifyDelivery task = new NotifyDelivery();

		newCachedThreadPool.execute(task);

		newCachedThreadPool.shutdown();*/
		
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(10);
		
		for (int i = 0; i < 20; i++) {
			
			NotifyDelivery task = new NotifyDelivery();
			
			newFixedThreadPool.execute(task);
		}
	}
}
