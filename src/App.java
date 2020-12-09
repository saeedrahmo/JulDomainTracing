import java.util.ArrayList;
import java.util.List;

public class App {

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		
		List<Thread> threadList = new ArrayList<Thread>();
		int n = 15; // Number of threads multiply 2
		for (int i=0; i<n; i++) 
		{
				FiboMultiThreading fibo = new FiboMultiThreading(i);
				threadList.add(fibo);
				fibo.start(); 				
				
				Thread file = new Thread(new FileMultiThreading(i,fibo.answer)); 									 			
				threadList.add(file);	
				file.start();								
		}

		for(Thread t : threadList) {
			// waits for this thread to die
			try {								
				t.join();
			} catch (Exception e) {
				System.out.println(e.getMessage());						
			}
		}
		
		System.out.println("Total threads [ms]: " + threadList.size());
		System.out.println("Total time [ms]: " + (System.currentTimeMillis() - startTime)); 
		System.out.println("**********END**********");	
	}
}



