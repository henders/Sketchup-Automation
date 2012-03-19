package osa.ora;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;


public class TestListen {
	private static Server server;
	
	private static void StartHttpServer() throws Exception {
		server = new Server(6666);
		 
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
 
        context.addServlet(new ServletHolder(new HttpServer()),"/*");
 
        server.start();
	}
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// Start up the http server first
		StartHttpServer();
		
		ConfigurationManager cm = new ConfigurationManager(TestListen.class.getResource("myconfig.xml"));
		Recognizer recognizer = (Recognizer)cm.lookup("recognizer");
		recognizer.allocate();
		Microphone microphone = (Microphone)cm.lookup("microphone");
		if(!microphone.startRecording()){
			System.out.println("Cannot start microphone.");
			recognizer.deallocate();
			System.exit(1);
		}
		System.out.println("Say: (Good morning | Hello) ( Bhiksha | Evandro | Paul | Philip | Rita | Will )");
		do {
			System.out.println("Start speaking. Press Ctrl-C to quit.\n");
			Result result = recognizer.recognize();
			if(result != null) {
				String resultText = result.getBestFinalResultNoFiller();
				System.out.println((new StringBuilder()).append("You said: ").append(resultText).append('\n').toString());
			} else {
				System.out.println("I can't hear what you said.\n");
			}
		} while(true);
	}

}