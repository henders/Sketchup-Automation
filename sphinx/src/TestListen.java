import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;

public class TestListen {
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		TestListen listener = new TestListen();
		listener.StartListening();
	}

	public void StartListening() {
		try {
			ConfigurationManager cm = new ConfigurationManager(TestListen.class.getResource("myconfig.xml"));
			System.out.println(cm.getGlobalProperties().toString());
			System.out.println(cm.getConfigURL().getPath());
			
			Recognizer recognizer = (Recognizer)cm.lookup("recognizer");
			System.out.println("Recognizer = " + recognizer.toString());
			recognizer.allocate();
			System.out.println("Recognizer = " + recognizer.toString());
			Microphone microphone = (Microphone)cm.lookup("microphone");
			if(!microphone.startRecording()){
				System.out.println("Cannot start microphone.");
				recognizer.deallocate();
				System.exit(1);
			}
			System.out.println("Say: (Draw Cube | Undo)");
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
		catch (Exception ex) {
			System.out.println("Error Will Robinson! - " + ex.getMessage());
		}
	}

}
