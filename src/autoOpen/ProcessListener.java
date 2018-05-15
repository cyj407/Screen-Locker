package autoOpen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessListener extends Thread {
	private String[] blacklist = {"gedit", "firefox"};
	
	public ProcessListener(String[] blacklist) {
		this.blacklist = blacklist;
	}
	public ProcessListener() {
		System.out.println("don't use this constructer!! remember to set blacklist!!");
	}

	public void run() {

		while (true) {
			/** sleep 5 secs **/
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			for (int i = 0; i < blacklist.length; ++i) {
				ProcessBuilder pb = new ProcessBuilder("ps", "-C", blacklist[i]);
				Process p;

				try {
					p = pb.start();
					BufferedReader buf = new BufferedReader(new InputStreamReader(p.getInputStream()));
					buf.readLine();
					String res = buf.readLine();
					if (res != null) {
						Runtime.getRuntime().exec("kill `ps -C " + blacklist[i] + " -o pid=`");
					}
				} catch (IOException e) { // TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
