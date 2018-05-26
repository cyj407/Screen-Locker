package autoOpen;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.rmi.Naming;

public class ReOpen {

	public static void main(String[] args) throws Exception {

		String mainName = args[0];

		RmiServerIntf obj = null;
		obj = (RmiServerIntf) Naming.lookup("//localhost/ReOpenServer");

		while (true) {

			/** times up, leave loop **/
			try {
				if (obj.RMI_getRemainTime() <= 0) {
					break;
				}
			} catch (Exception e) {
				/** cannot find server -> application is down **/
				ProcessBuilder newProcessOpen = new ProcessBuilder("java", mainName);
				Process np = newProcessOpen.start();
				np.waitFor();

				/** if still can't find server, then loop back again **/
				try {
					obj = (RmiServerIntf) Naming.lookup("//localhost/ReOpenServer");
				} catch (Exception et) {
					continue;
				}

			}
		}
	}

	public static void openReOpen(String myExe, String workingDir) throws Exception {
		if (ReOpen_exist())
			return;

		/** start ReOpen Process **/
		try {
			ProcessBuilder pb = new ProcessBuilder("java", "autoOpen.ReOpen", myExe);
			pb.directory(new File(workingDir));
			pb.start();
		} catch (Exception e) {
			e.printStackTrace();
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
