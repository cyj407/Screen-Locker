package autoOpen;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.rmi.Naming;

public class ReOpen {

	public static void main(String[] args) throws Exception {

		String _mainName = args[0];
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
				ProcessBuilder _newProcessOpen = new ProcessBuilder("java", _mainName);
				Process np = _newProcessOpen.start();
				np.waitFor();

				/** if still can't find server, then loop back again **/
				try {
					_obj = (RmiServerIntf) Naming.lookup("//localhost/ReOpenServer");
				} catch (Exception et) {
					continue;
				}

			}
		}
	}

	public static void openReOpen(String myExe, String workingDir) throws Exception {
		if (ReOpen_exist())
			return;

		/** start ReOpen Process **/
		try {
			ProcessBuilder _pb = new ProcessBuilder("java", "autoOpen.ReOpen", myExe);
			_pb.directory(new File(workingDir));
			_pb.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static boolean ReOpen_exist() throws Exception{
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
	}
}
