package screenLocker.autoOpen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import screenLocker.MyTimer;
import screenLocker.loader.Loader;

public class ProcessListener extends Thread {
	private List<String> _blacklist;
	
	public ProcessListener() {
		_blacklist = new ArrayList<>();
	}

	public void run() {

		while (true) {
			/** sleep 5 secs **/
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			_blacklist.add("pietty0400b14.exe");
			_blacklist.add("GitHubDesktop.exe");
			
			//_blacklist = MyTimer.BlackList();

			if(Loader.IsLinux()) {
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
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			if(Loader.IsWindows()) {
				for (String item: _blacklist) {
					try {
						Process p = Runtime.getRuntime().exec("tasklist"+ " /FI \"imagename eq "+ item+ "\"");
						BufferedReader buf = new BufferedReader(new InputStreamReader(p.getInputStream()));
						buf.readLine();
						String res = buf.readLine();
						
						if (res != null) {
							Runtime.getRuntime().exec("taskkill /f /im " + item);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
