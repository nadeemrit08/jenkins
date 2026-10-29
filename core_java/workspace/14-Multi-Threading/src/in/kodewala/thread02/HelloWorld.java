package in.kodewala.thread02;

public class HelloWorld {

	public static void main(String[] args) {

		System.out.println("Who is executing this code ? " + Thread.currentThread().getName());

		System.out.println("HelloWorld.main()");

		HelloWorld helloWorld = new HelloWorld();

	}
}
