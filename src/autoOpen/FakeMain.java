package autoOpen;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FakeMain {

	public static void main(String[] args) throws Exception {

		String myDir = System.getProperty("user.dir");
		if (!myDir.substring(myDir.length() - 4).equals("/bin")) {
			myDir += "/bin";
		}

		RMIServer.startServer(1000);
		/**
		 * I only want to open one ReOpen process (even if FakeMain is killed), The
		 * dumbest way to check is to find if there exists substring ReOpen in the
		 * process list.
		 */
		if (!ReOpen_exist()) {
			ReOpen.openReOpen("autoOpen.FakeMain", myDir);
		}
		
		/** bad example **/
		Thread t = new ProcessListener();
		t.start();
		while (true) {

		}
	}
	
	private static boolean ReOpen_exist() throws Exception{
		Process p = null;
		BufferedReader br = null;

		p = Runtime.getRuntime().exec("ps -C java -o pid=");
		br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		
		String st = "", t;
		while ((t = br.readLine()) != null) {
			st += t + " ";
		}
		br.close();
		
		p = Runtime.getRuntime().exec("ps -c " + st);
		br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		st = "";
		while ((t = br.readLine()) != null) {
			st += t + " ";
		}
		
		return st.contains("ReOpen");
	}
}
