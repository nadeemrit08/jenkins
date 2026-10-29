package in.kodewala.thread01.cf1.thenAppLy;

import java.util.concurrent.CompletableFuture;

public class Driver {

	public static void main(String[] args) {
		
		CompletableFuture cf = CompletableFuture.supplyAsync(() -> {
			String name = "kodewala";
			
			return name;
		}).thenAccept((n) -> System.out.println("Hello " + n.toUpperCase())); // it will only print response but no return
		
		System.out.println("Response :: " + cf.join()); 
	}
}
