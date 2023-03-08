import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadingApp {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		String message = "Starting";
		System.out.println(message);
		System.out.println("Thead-"+ Thread.currentThread().getName());
		
		// This starts up as many threads as there are iterations in the for loop.This is not ideal if you work with with cpu bounds.
		//You only want to fire up a ton of threads, if those threads are going to be waiting a lot... i.e. sending requests across the internet.
//		for (int i=0; i<50; i++) {
//			SomeTask task = new SomeTask();
//			new Thread(task).start();
//		}
		
		// For a CPU bound operation like we have in our "SomeTask" class, we should make use of an ExecutorService.it is more flexible.
		
		//newFixedThreadPool = you need to specify how many threads you want
		//ExecutorService service = Executors.newFixedThreadPool(6);
		
		// newCachedThreadPool ile newFixedThreadPool farki:newCachedThreadPool, you don't say how many threads you want in your thread pool.
		// ihtiyac oldugu kadar thread i devamli olarak kurar.Eger isi biten bir thread 6 sn boyunca kullanilmazsa o thread kapatilir.
		// CPU bound da calisiyosa bunu kullanma. Teorik olarak max kullaniyomus gibi dusunebilirsin. Good fir bor i/o type operaitons, http requests etc...
		
		
		//ExecutorService service = Executors.newCachedThreadPool();

		//ExecutorService service = Executors.newSingleThreadExecutor();

//		for (int i=0; i<50; i++) {
//			//service.execute(new SomeTask());
		
			//Callable interface den donen degeri almak icin Future kullaniyoruz. Yalniz burada, yazdigimiz kod senkronize calisiyor.
			// asagindaki "done" en son yazildi cunku main thread blocklandi. future.get() is a blocking task-call...we dont want to block main thread.
			// 
		
//			Future<TaskDto> futureTask = service.submit(new SomeTask());
//			System.out.println(futureTask.get());

//		}
		// ********LESSON 7 ****************
//		ExecutorService executor = Executors.newCachedThreadPool();
//		// if you do not specify an executor service, it uses default thread pool
//		--> 	supplyAsync icin kendi executor(thread pool) kuruyoruz
//		
//		for (int i = 0; i < 20; i++) {
//			CompletableFuture.supplyAsync(() -> new SomeTask(), executor)
//							 .thenApply(someTask -> someTask.call())
//							 .thenAccept(dto -> System.out.println(dto));
//
//			Futures were great prior to Java 8, but now we have something better
//			Future<TaskDto> futureTask = service.submit(new SomeTask());
//			System.out.println(futureTask.get());
//		}
//		
//		message = "Done";
//		System.out.println(message);
//	}
		// ******LESSON 8 ***************
		
//		List<CompletableFuture<Void>> tasks = new ArrayList<>();
//		
//		// This is how you get access to the ForkJoinPool's common pool, which is the default thread pool that's used with CompletableFutures
//		//   if you do not specify an executor
//		//		ForkJoinPool commonPool = ForkJoinPool.commonPool();
//		for (int i=0; i<20; i++) {
//			CompletableFuture<Void> task = CompletableFuture.supplyAsync(() -> new SomeTask())
//							 .thenApply(someTask -> someTask.call())
//							 .thenAccept(dto -> System.out.println(dto));
//			tasks.add(task);
//		}		
//		message = "Done";
//		System.out.println(message);	
//		while (tasks.stream()
//					.filter(CompletableFuture::isDone)
//				    .count() < 20) {
//		     // this just loops and keeps the main thread alive until all threads are done working. 
//		}
//	}
//	
//	
//}
		// ******LESSON 9 ***************
		
		// For a CPU bound operation like we have in our "SomeTask" class,
		//  we should make use of an ExecutorService
		
		ExecutorService cpuBoundTask = Executors.newFixedThreadPool(3);
		ExecutorService ioBoundTask = Executors.newCachedThreadPool();
		
		for (int i=0; i<20; i++) {
			CompletableFuture.supplyAsync(() -> new SomeTask(), ioBoundTask)
							 .thenApplyAsync(someTask -> someTask.doSomeWork(), cpuBoundTask)
							 .thenApplyAsync(someTask -> someTask.markComplete(), ioBoundTask)
							 .thenAcceptAsync(dto -> System.out.println(dto), ioBoundTask);
		}
		
		message = "Done";
		System.out.println(message);
		
	}
	
	
}		 