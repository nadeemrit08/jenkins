package in.kodewala.thread18.executorFw1;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class EmailSender implements Callable<String> {

	@Override
	public String call() {
		System.out.println(Thread.currentThread().getName());
		return "Failed...!!!";
	}

}

public class Driver {

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		ExecutorService executorService = Executors.newFixedThreadPool(1);

		EmailSender task = new EmailSender();

		Future<String> response = executorService.submit(task);

		System.out.println("Response from call method is " + response.get());
	}
}
