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

			for (String item: _blacklist) {
				ProcessBuilder pb = new ProcessBuilder("ps", "-C", item);
				Process p;

				try {
					p = pb.start();
					BufferedReader buf = new BufferedReader(new InputStreamReader(p.getInputStream()));
					buf.readLine();
					String res = buf.readLine();
					System.out.println("res = " + res);
					if (res != null) {
						Runtime.getRuntime().exec("pkill " + item);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
