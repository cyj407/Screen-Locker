package screenLocker;

import autoOpen.RMIServer;
import autoOpen.ReOpen;

public class ProgramManager {

	private static GUImain _guiMain = new GUImain();
	private static GUIquestion _guiQuestion = new GUIquestion();
	private static GUIsetting _guiSetting = new GUIsetting();
	private static Loader _loader = Loader.getInstance();

	public static void main(String[] args) {

		/********************* f26401004's section ***********************/
		
		/************************* yiiju's section ***********************/

		/*********************** afcidk's section ************************/
		String myDir = System.getProperty("user.dir");
		if (!myDir.substring(myDir.length() - 4).equals("/bin")) {
			myDir += "/bin";
		}

		RMIServer.startServer();
		try {
			ReOpen.openReOpen("screenLocker.ProgramManager", myDir);
		} catch (Exception e) {
			e.printStackTrace();
		}

		/************************ cyj407's section ***********************/
	}

}
