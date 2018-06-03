package screenLocker.autoOpen;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.rmi.Naming;

import screenLocker.loader.Loader;

public class ReOpen {

	public static void main(String[] args) throws Exception {

		String _mainName = args[1];
		String _path = args[0];
		_path = String.format("\"%s\\bin;%s\\lib\\jna-platform.jar;%s\\lib\\jna.jar;%s\\lib\\jRegistryKey.jar\"", _path,
				_path, _path, _path);
		RmiServerIntf _obj = null;
		System.out.println(_path);

		_obj = (RmiServerIntf) Naming.lookup("//localhost/ReOpenServer");

		while (true) {

			/** times up, leave loop **/
			try {
				if (_obj.GetRemainTime() <= 0) {
					break;
				}
			} catch (Exception e) {
				/** cannot find server -> application is down **/
				Runtime.getRuntime().exec(String.format("java -classpath %s %s", _path, _mainName));

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
			ProcessBuilder _pb = new ProcessBuilder("java", "autoOpen.ReOpen",
					workingDir.substring(0, workingDir.lastIndexOf("\\")), myExe);
			_pb.directory(new File(workingDir));
			Process p = _pb.start();
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String st;
			while ((st = br.readLine()) != null) {
				System.out.println(st);
			}

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
			
			System.out.println("contain?" + _lines.contains("ReOpen"));
			return _lines.contains("ReOpen");
		}

	}
}
