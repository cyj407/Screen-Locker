package screenLocker.autoOpen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import screenLocker.LockerTimer;

public class ProcessListener extends Thread{
	private List<String> _blacklist;
	private static boolean _listening;
	
	public ProcessListener() {
		_blacklist = new ArrayList<>();
		_listening = true;
	}
	public void close() {
		_listening = false;
	}
	
	public void run() {

		while (_listening) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
			}
			
			_blacklist = LockerTimer.BlackList();

			if(screenLocker.loader.Loader.IsLinux()) {
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
			if(screenLocker.loader.Loader.IsWindows()) {
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
