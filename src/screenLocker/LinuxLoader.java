package screenLocker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public final class LinuxLoader extends Loader {
	private static LinuxLoader _instance;
	private static double _percentage;
	private static String _currentLoad;
	private static String _currentExec;

	public static Loader getInstance() {
		if (_instance == null) {
			synchronized (LinuxLoader.class) {
				if (_instance == null) {
					_instance = new LinuxLoader();
				}
			}
		}
		return _instance;
	}

	public LinuxLoader() {
		_appList = new ArrayList<Application>();
		LoadApplication();

	}

	@Override
	public boolean LoadApplication() {
		_addInfo("/usr/share/applications");
		return true;
	}

	private void _addInfo(String _path) {
		File _folder = new File(_path);
		File[] _files = _folder.listFiles();
		int _size = _files.length, _fileCnt = 0;
		_percentage = 0;

		for (File _file : _files) {
			if (_file.isFile()) {
				String _fileName = _file.getName();

				/** ensure the file is a desktop entry **/
				if (_fileName.contains(".desktop")) {
					Application _newApp = new Application();
					try {
						FileReader _fr = new FileReader(_file);
						BufferedReader _br = new BufferedReader(_fr);
						String _line;
						while ((_line = _br.readLine()) != null) {
							/** assume the first entry is [Desktop Entry] **/
							/** if over desktop entry then leave this file **/
							if (_line.length() != 0 && _line.charAt(0) == '[' && !_line.equals("[Desktop Entry]"))
								break;

							if (_line.length() < 5)
								continue;
							String _front = _line.substring(0, 5);
							String _back = _line.substring(5);

							if (_front.equals("Name=")) {
								_newApp.SetDisplayName(_back);
								_currentLoad = _back;
							} else if (_front.equals("Exec=")) {
								String _exePath = _back.split(" ")[0];
								String _exeName = "";
								try {
									_exeName = findExeName(_exePath);
								} catch (Exception e) {
									/** does not exist backslash **/
									_exeName = _exePath;
								}
								// System.out.println(exeName);
								_currentExec = _exeName;
							} else if (_front.equals("Icon=")) {
								_newApp.SetIconPath(_back);
							}
						}

						_appList.add(_newApp);
						_br.close();

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			++_fileCnt;
			_percentage = (double) _fileCnt / _size;
			LoadProgressPercentage();
			LoadStatus();
		}

	}

	@Override
	public double LoadProgressPercentage() {
		//System.out.printf("%.2f ", percentage * 100);
		return _percentage*100;
	}

	@Override
	public String LoadStatus() {
		//System.out.println(currentLoad + " " + currentExec);
		return "loading..." + _currentExec;
	}

	private String findExeName(String path) throws Exception {
		Process p = Runtime.getRuntime().exec(String.format("file %s", path));
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String ret = "";

		/** executable path **/
		if (br.readLine().contains("text")) {
			System.out.println("path = " + path);
			List<String> oriList = getCurrentState();
			openProc(path);
			Thread.sleep(5000);
			List<String> aftList = getCurrentState();
			ret = getDiff(path, oriList, aftList);
			return ret;

		}

		return path.substring(path.lastIndexOf("/") + 1);

	}

	private static List<String> getCurrentState() throws IOException {

		List<String> ret = new ArrayList<String>();
		Process pe = Runtime.getRuntime().exec("ps -A");
		BufferedReader bre = new BufferedReader(new InputStreamReader(pe.getInputStream()));

		String t;
		while ((t = bre.readLine()) != null)
			ret.add(t);

		return ret;
	}

	private String getDiff(String path, List<String> s1, List<String> s2) throws Exception {
		int len2 = s2.size();

		for (int i = 0; i < len2; ++i) {
			String s2s = s2.get(i);
			if (!s1.contains(s2s) && !s2s.contains(" ps")) {
				String s2s_name = (String) (" " + s2s).split("\\s+")[4];
				// System.out.println(s2s_name);
				if (path.contains(s2s_name)) {
					// System.out.println("kill!");
					Runtime.getRuntime().exec("pkill " + s2s_name);
					return s2s_name;
				}
			}
		}
		return "cannot find process name";
	}

	private void openProc(String path) throws IOException {
		Runtime.getRuntime().exec(path);
	}

}
