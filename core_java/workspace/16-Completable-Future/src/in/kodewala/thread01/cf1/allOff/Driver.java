package in.kodewala.thread01.cf1.allOff;

import java.util.concurrent.CompletableFuture;

public class Driver {

	public static void main(String[] args) {

		CompletableFuture<Double> future1 = CompletableFuture.supplyAsync(() -> 10.5);

		CompletableFuture<Double> future2 = CompletableFuture.supplyAsync(() -> 30.7);

		CompletableFuture<Double> finalFuture = future1.thenCombineAsync(future2, (n1, n2) -> n1 + n2);
		
		CompletableFuture.allOf(future1, future2);

		System.out.println(" Tasks has been completed ... ");
		
		System.out.println(finalFuture.join());

		


	}
}
