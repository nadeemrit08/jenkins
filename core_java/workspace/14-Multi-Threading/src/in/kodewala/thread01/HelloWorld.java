package in.kodewala.thread01;

public class HelloWorld {

	public static void main(String[] args) {

		System.out.println("Who is executing this code ? " + Thread.currentThread().getName());

		
		/*
		 * Thread.currentThread().setName("My Thread....!!!!");
		 * 
		 * System.out.println("Now Who is executing this code ? " +
		 * Thread.currentThread().getName());
		 */
		 
		
		System.out.println("HelloWorld.main()");
		
		/*
		 * call method
		 */
		HelloWorld helloWorld = new HelloWorld();
		helloWorld.someMethod();

	}

	public void someMethod() {
		System.out.println("HelloWorld.someMethod() is called...!!!");
	}
}
