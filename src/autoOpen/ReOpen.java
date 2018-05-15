package autoOpen;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.lang.Runtime;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/** TODO: no need to close ReOpen **/
public class ReOpen {

	private static long duration_milli;
	private static long ori_time;

	public static void main(String[] args) {
		String mainName = args[0];
		String mainPid = args[1];
		String time = args[2];

		setTime(Integer.parseInt(time));
		String myPid = getPid();
		while (true) {
			/** sleep for 5 secs **/
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			/** times up **/
			Date cur_time = new Date();
			if (cur_time.getTime() - ori_time >= duration_milli) {
				try {
					ProcessBuilder closeMe = new ProcessBuilder("kill", myPid);
					closeMe.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			/** detect if process closed **/
			try {
				Process p = Runtime.getRuntime().exec(String.format("ps -q %s -o pid=", mainPid));
				BufferedReader output = new BufferedReader(new InputStreamReader(p.getInputStream()));

				String res = output.readLine();
				if (res == null) {
					/** process has been shutdown , reopen **/
					ProcessBuilder newProcessOpen = new ProcessBuilder("java", mainName, System.getProperty("user.dir"));
					Process proc = newProcessOpen.start();

					/** close this process **/
					ProcessBuilder closeMe = new ProcessBuilder("kill", myPid);
					closeMe.start();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void setTime(int sec) {
		duration_milli = sec * 1000;
		Date cur_time = new Date();
		ori_time = cur_time.getTime();
	}

	private static String getPid() {
		String name = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();

		return name.substring(0, name.indexOf("@"));
	}
}
