package autoOpen;

import java.io.File;
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

	public static void openReOpen(String myExe, String workingDir) {

		/** start ReOpen Process **/
		try {
			ProcessBuilder pb = new ProcessBuilder("java", "autoOpen.ReOpen", myExe);
			pb.directory(new File(workingDir));
			pb.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
