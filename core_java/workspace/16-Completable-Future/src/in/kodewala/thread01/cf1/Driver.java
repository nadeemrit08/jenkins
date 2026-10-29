package in.kodewala.thread01.cf1;

import java.util.concurrent.CompletableFuture;

public class Driver {

	public static void main(String[] args) {

		CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {

			System.out.println("I am from supplyAsync");

			return "Success";
		});

		// Get the result from CompletableFuture
		String result = completableFuture.join();

		System.out.println("CompletableFuture's response from supply sync : " + result);
	}
}