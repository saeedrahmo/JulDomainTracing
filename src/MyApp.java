import java.io.PrintWriter;
import java.util.logging.Handler;
import java.util.logging.Logger;
import org.lttng.ust.agent.jul.LttngLogHandler;

public class MyApp
{
    public static void main(String args[]) throws Exception
    {
        // Create a logger
        Logger logger = Logger.getLogger("mylogger");

        // Create an LTTng-UST log handler
        Handler lttngUstLogHandler = new LttngLogHandler();

        // Add the LTTng-UST log handler to our logger
        logger.addHandler(lttngUstLogHandler);

        logger.info("create a text file");
        PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
        
        for (int i = 0; i < 50; i++) {
        	logger.warning("wating for: "+i*100+"");
        	logger.finer("write "+i+" line");
        	writer.println("This is line "+i+"");
		}
                
        logger.severe("close writer");
        writer.close();
        
        // Not mandatory, but cleaner
        logger.removeHandler(lttngUstLogHandler);
        lttngUstLogHandler.close();        
    }
}
