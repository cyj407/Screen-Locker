package screenLocker.autoOpen;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import screenLocker.loader.Loader;

public class ReOpen {
	private final static String _deli = Loader.IsLinux() ? "/" : "\\";
	private final static String _deli2 = Loader.IsLinux() ? ":" : ";";

	public static void main(String[] args) throws Exception {

		String _mainName = args[1];
		String _path = args[0];
		_path = String.format(
				"\"%1$s%2$sbin%3$s%1$s%2$slib%2$sjna-platform.jar%3$s%1$s%2$slib%2$sjna.jar%3$s%1$s%2$slib%2$sjRegistryKey.jar\"",
				_path, _deli, _deli2);
		RmiServerIntf _obj = null;
		_obj = (RmiServerIntf) Naming.lookup("//localhost/ReOpenServer");

		while (true) {

			/** times up, leave loop **/
			try {
				if (_obj.GetRemainTime() <= 0) {
					break;
				}
			} catch (Exception e) {
				/** cannot find server -> application is down **/
				if (Loader.IsLinux()) Runtime.getRuntime().exec(String.format("java %s", _mainName));
				else Runtime.getRuntime().exec(String.format("java -classpath %s %s", _path, _mainName));

				/** if still can't find server, then loop back again **/
				while (true) {
					try {
						_obj = (RmiServerIntf) Naming.lookup("//localhost/ReOpenServer");
						break;
					} catch (Exception et) {
						et.printStackTrace();
					}
				}

			}
		}
	}

	public static void openReOpen(String myExe, String workingDir) throws Exception {
		if (ReOpen_exist())
			return;

		/** start ReOpen Process **/
		try {
			ProcessBuilder _pb = new ProcessBuilder("java", "screenLocker.autoOpen.ReOpen",
					workingDir.substring(0, workingDir.lastIndexOf(_deli)), myExe);
			_pb.directory(new File(workingDir));
			Process p = _pb.start();
			/*
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String st;
			while ((st=br.readLine()) != null) {
				System.out.println(st);
			}
			*/

			/**
			 * checkout the pid, do wmic path win32_process where name="java.exe" get
			 * commandline,processid for debugging: if want to kill pid on windows, do
			 * taskkill /f /fi "PID eq mypid"
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static boolean ReOpen_exist() throws Exception {

		if (Loader.IsLinux()) {
			Process _p = null;
			BufferedReader _br = null;
			_p = Runtime.getRuntime().exec("ps -C java -o pid=");
			_br = new BufferedReader(new InputStreamReader(_p.getInputStream()));

			String _st = "", _t;
			while ((_t = _br.readLine()) != null) {
				_st += _t + " ";
			}
			_br.close();

			_p = Runtime.getRuntime().exec("ps -c " + _st);
			_br = new BufferedReader(new InputStreamReader(_p.getInputStream()));
			_st = "";
			while ((_t = _br.readLine()) != null) {
				_st += _t + " ";
			}
			return _st.contains("ReOpen");
		} else {
			Process _p = Runtime.getRuntime().exec("wmic path win32_process where name=\"java.exe\" get commandline");
			BufferedReader _br = new BufferedReader(new InputStreamReader(_p.getInputStream()));
			String _lines = "";
			String _line;
			while ((_line = _br.readLine()) != null) {
				_lines += _line;
			}
			_p = Runtime.getRuntime().exec("wmic path win32_process where name=\"javaw.exe\" get commandline");
			_br = new BufferedReader(new InputStreamReader(_p.getInputStream()));
			while ((_line = _br.readLine()) != null) {
				_lines += _line;
			}

			return _lines.contains("ReOpen");
		}

	}
}
