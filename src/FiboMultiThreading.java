import java.util.logging.Handler;
import java.util.logging.Logger;

import org.lttng.ust.agent.jul.LttngLogHandler;

public class FiboMultiThreading extends Thread {
	private int x;
	public int answer;

	public FiboMultiThreading(int x) {
		this.x = x;
	}

	public void run() {
		// Create a logger
		Logger logger = Logger.getLogger("fibologger");

		// Create an LTTng-UST log handler
		Handler lttngUstLogHandler=null;

		try {						
			lttngUstLogHandler= new LttngLogHandler();

			// Add the LTTng-UST log handler to our logger
			logger.addHandler(lttngUstLogHandler);

			logger.finer("Thread Id is "+Thread.currentThread().getId()+"");

			if (x == 0){ 
				answer = 0;
			}else if( x == 1 || x == 2 ) {
				answer = 1;
			}else {           
				logger.info("compute fibo");

				/*
				 * Below we are invoking 2 threads to compute separate values
				 * This will for a tree structure
				 */
				FiboMultiThreading f1 = new FiboMultiThreading(x-1);
				FiboMultiThreading f2 = new FiboMultiThreading(x-2);
				f1.start();
				f2.start();
				f1.join();
				f2.join();
				answer = f1.answer + f2.answer; 				
			}			
		}catch(Exception ex){
			logger.severe(ex.getMessage());
		}finally {
			logger.info("end of computing");

			// Not mandatory, but cleaner
			logger.removeHandler(lttngUstLogHandler);
			lttngUstLogHandler.close();  
		}      	    	   
	}
}