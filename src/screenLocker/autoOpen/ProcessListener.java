package screenLocker.autoOpen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ProcessListener extends Thread {
	private List<String> _blacklist;
	/**TODO: blacklist must be obtained from Timer **/
	
	public ProcessListener(List<String> blacklist) {
		this._blacklist = blacklist;
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

			for (String item: _blacklist) {
				ProcessBuilder pb = new ProcessBuilder("ps", "-C", item);
				Process p;

				try {
					p = pb.start();
					BufferedReader buf = new BufferedReader(new InputStreamReader(p.getInputStream()));
					buf.readLine();
					String res = buf.readLine();
					if (res != null) {
						Runtime.getRuntime().exec("kill `ps -C " + item + " -o pid=`");
					}
				} catch (IOException e) { // TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
