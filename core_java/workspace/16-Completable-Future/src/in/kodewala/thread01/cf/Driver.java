package in.kodewala.thread01.cf;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Driver {

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		System.out.println("Driver.main() : start");

		CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
			                                             System.out.println("This is from run async");
		                                             });
		
		//System.out.println(completableFuture.get()); // null
		
		//System.out.println(completableFuture.join()); //null

		System.out.println("Driver.main() : end");
	}
}
