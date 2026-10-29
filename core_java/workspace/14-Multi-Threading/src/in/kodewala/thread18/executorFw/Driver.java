package in.kodewala.thread18.executorFw;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class EmailSender implements Callable<Boolean> {

	@Override
	public Boolean call() {
		System.out.println(Thread.currentThread().getName());
		return true;
	}

}

public class Driver {

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		ExecutorService executorService = Executors.newFixedThreadPool(1);

		EmailSender task = new EmailSender();

		Future<Boolean> response = executorService.submit(task);

		System.out.println("Response from call method is " + response.get());
	}
}
