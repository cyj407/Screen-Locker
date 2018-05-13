package autoOpen;

import java.io.File;

/** TODO: solve ReOpen.class not exist **/
public class ProcessOpen {
	/**
	 * get workingDir by System.getProperty("user.dir");
	 * get myPid by java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
	 */

	// public static void main(String[] args) throws IOException {
	public ProcessOpen(String myExe, String myPid, String workingDir, long time) {

		try {
			ProcessBuilder pb = new ProcessBuilder("java", "autoOpen.ReOpen", myExe, myPid, Long.toString(time));
			pb.directory(new File(workingDir));
			pb.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
