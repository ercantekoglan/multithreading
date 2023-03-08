import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;


	//*************LESSON  7 - 8 supplier kullanmadigimiz zaman, thread edecegimiz sinifin callable ya da runnable implement etmesi gerekiyor

//public class SomeTask implements Runnable, Callable<TaskDto> {
//public class SomeTask implements Callable<TaskDto> {
//	private static int taskId = 0;
//
//	//runnable interface impl
////	@Override
////	public void run() {
////
////		for(int i =0; i<100000; i++) {
////			Integer int1 = new Random().nextInt();
////			Integer int2 = new Random().nextInt();
////			Double someThing = (int1 + int2) * 1.5;
////		}
////		taskId++;
////		System.out.println("Task-ID: " + taskId + " " + 
////		"Running on thread: " + Thread.currentThread().getName());
////	}
//
//	@Override
//	public TaskDto call()  {
//		TaskDto taskDto = new TaskDto();
//		for (int i = 0; i < 10000000; i++) {
//			Integer int1 = new Random().nextInt();
//			Integer int2 = new Random().nextInt();
//			Double something = (int1 + int2) * 1.5;
//			taskDto.setValue(something);
//		}
//		taskId++;
//		System.out.println("Hey look at me, I'm task-id: " + taskId + "! " + "I'm running on thread: "
//				+ Thread.currentThread().getName());
//		taskDto.setFinished(true);
//		return taskDto;
//	}

//}


		// ******* LESSON 9 CompletableFuture.supplyAsync da supplier kullandigimiz zaman class in Runnable ya da Callable interfacelerinden birini implement etmesine gerek yok

public class SomeTask {
	private static int taskId = 0;
	private TaskDto taskDto;
	
	// Dolayisi ile mothod override etmemize gerek yok. Istedigimiz rahatlikda method kullanabiliriz.
	
	public SomeTask doSomeWork() {
		taskDto = new TaskDto();
		for (int i=0; i<10000000; i++) {
			Integer int1 = new Random().nextInt();
			Integer int2 = new Random().nextInt();
			Double something = (int1 + int2) * 1.5;
			taskDto.setValue(something);
		}
		taskId++;
		System.out.println("Hey look at me, I'm task-id: " + taskId + "! "
				+ "I'm running on thread: " + Thread.currentThread().getName());
		
		return this;
	}
	
	public TaskDto markComplete () {
		taskDto.setFinished(true);
		return taskDto;
	}
	
}