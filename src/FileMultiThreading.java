import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Logger;
import org.lttng.ust.agent.jul.LttngLogHandler;

public class FileMultiThreading implements Runnable 
{ 
	private int x;
	private int answer;
	public FileMultiThreading(int x,int asnwer) {
		this.x = x;
		this.answer=asnwer;
	}

	public void run() 
	{ 
		// Create a logger
		Logger logger = Logger.getLogger("filelogger");

		// Create an LTTng-UST log handler
		Handler lttngUstLogHandler=null;

		try {						
			lttngUstLogHandler= new LttngLogHandler();

			// Add the LTTng-UST log handler to our logger
			logger.addHandler(lttngUstLogHandler);

			logger.finer("Thread Id is "+Thread.currentThread().getId()+"");

			List<String> lines = Arrays.asList("Thread Id is: "+Thread.currentThread().getId()+"","Number is: "+x+"","Answer is: "+answer+"");
			Path path = Paths.get("sample-file.txt");
			if( !Files.exists(path))
				Files.createFile(path);
			Files.write(path, lines, StandardCharsets.UTF_8,StandardOpenOption.APPEND);

			logger.info("write to the text file");	
		}catch(Exception ex){
			logger.severe(ex.getMessage());
		}finally {
			logger.info("end of writing");

			// Not mandatory, but cleaner
			logger.removeHandler(lttngUstLogHandler);
			lttngUstLogHandler.close();  
		}   
	} 
} 