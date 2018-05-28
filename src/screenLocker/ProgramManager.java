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
		String _myDir = System.getProperty("user.dir");
		if (!_myDir.substring(_myDir.length() - 4).equals("/bin")) {
			_myDir += "/bin";
		}

		RMIServer.StartServer();
		try {
			ReOpen.openReOpen("screenLocker.ProgramManager", _myDir);
		} catch (Exception e) {
			e.printStackTrace();
		}

		/************************ cyj407's section ***********************/
	}

}
