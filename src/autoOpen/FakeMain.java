package autoOpen;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import screenLocker.*;

public class FakeMain {

	public static void main(String[] args) throws Exception {
		LinuxLoader ll = new LinuxLoader();

		/**
		String myDir = System.getProperty("user.dir");
		if (!myDir.substring(myDir.length() - 4).equals("/bin")) {
			myDir += "/bin";
		}

		RMIServer.startServer(1000);
		**/
		/**
		 * I only want to open one ReOpen process (even if FakeMain is killed), The
		 * dumbest way to check is to find if there exists substring ReOpen in the
		 * process list.
		 */
		//ReOpen.openReOpen("autoOpen.FakeMain", myDir);

		/** bad example **/
		/**
		Thread t = new ProcessListener();
		t.start();
		while (true) {

		}
		**/
	}
}
