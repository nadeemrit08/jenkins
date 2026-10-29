package in.kodewala.thread01.cf1.thenAccept;

import java.util.concurrent.CompletableFuture;

public class Driver {

	public static void main(String[] args) {
		
		CompletableFuture cf = CompletableFuture.supplyAsync(() -> {
			String name = "kodewala";
			
			return name;
		}).thenApply((n) -> n.toUpperCase());
		
		System.out.println("Response :: " + cf.join());
	}
}
