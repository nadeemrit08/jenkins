package in.kodewala.thread01.cf1.thenCombine;

import java.util.concurrent.CompletableFuture;

public class Driver {

	public static void main(String[] args) {

		CompletableFuture<Double> future1 = CompletableFuture.supplyAsync(() -> 10.5);

		CompletableFuture<Double> future2 = CompletableFuture.supplyAsync(() -> 30.7);

		CompletableFuture<Double> finalFuture = future1.thenCombineAsync(future2, (n1, n2) -> n1 + n2);
		
		System.out.println(finalFuture.join());

		/*
		 * System.out.println("Sum of " + future1.join() + " + " + future2.join() +
		 * " = " + finalFuture.join());
		 * 
		 * System.out.println("********************************************");
		 * 
		 * CompletableFuture<Double> finalFuture1 = future1.thenCombineAsync(future2,
		 * (n1, n2) -> n1 * n2);
		 * 
		 * System.out.println("Multiply of " + future1.join() + " + " + future2.join() +
		 * " = " + finalFuture1.join());
		 */


	}
}
