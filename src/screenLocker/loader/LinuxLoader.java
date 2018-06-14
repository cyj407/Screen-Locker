package screenLocker.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import screenLocker.Application;

public final class LinuxLoader extends Loader implements Runnable {
	private static LinuxLoader _instance;
	private static int _fileSize;
	private static int _curLoadCnt;
	private static String _curLoadName;

	public static Loader GetInstance() {
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
		// _loadALLApplication();
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

	@Override
	public int GetApplicationNumber() {
		while (_fileSize == 0)
			;
		return _fileSize;
	}

	private void _addInfo(String _path) {
		List<String> nameList = new ArrayList<String>();
		File _folder = new File(_path);
		File[] _files = _folder.listFiles();
		File _record = new File(System.getProperty("user.dir") + "/appList.dat");
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(_record));
			oos.writeObject(new Date());
			oos.writeInt(_files.length);
		} catch (Exception e) {
			e.printStackTrace();

		}
		_fileSize = _files.length;

		for (File _file : _files) {
			++_curLoadCnt;
			if (_file.isFile()) {
				String _fileName = _file.getName();

				/** ensure the file is a desktop entry **/
				if (_fileName.contains(".desktop")) {
					boolean duplicate = false;
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
							} else if (_front.equals("Exec=")) {
								String _exePath = _back.split(" ")[0];
								String _exeName = "";
								try {
									_exeName = findExeName(_exePath);
								} catch (Exception e) {
									/** does not exist backslash **/
									_exeName = _exePath;
								}
								if (_exeName.equals("") || nameList.contains(_exeName)) {
									duplicate = true;
									break;
								}
								nameList.add(_exeName);
								_newApp.SetProcessName(_exeName);
								_newApp.SetExecutePath(_exePath);
								_curLoadName = _exeName;
							} else if (_front.equals("Icon=")) {
								if (_back.contains("/"))
									_newApp.SetIconPath(_back);
								else {
									Process p = Runtime.getRuntime()
											.exec(String.format("find /usr/share -name *%s*", _back));
									BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
									String st;
									while ((st = br.readLine()) != null) {
										if (st.contains("32x32")) {
											break;

										}
									}
									_newApp.SetIconPath(st);

								}
							}
						}

						if (!duplicate) {
							_appList.add(_newApp);
							oos.writeObject(_newApp);
						}
						_br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		try {
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public double LoadProgressPercentage() {
		return (double) _curLoadCnt / _fileSize;
	}

	@Override
	public String LoadStatus() {
		return "loading..." + _curLoadName;
	}

	private String findExeName(String path) throws Exception {
		if (path.contains("java") || path.contains("jvm"))
			return "";
		Process p = Runtime.getRuntime().exec(String.format("file %s", path));
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String rd = br.readLine();
		String ret = "";

		/** executable path **/
		if (rd.indexOf("text") > 0 || rd.indexOf("symbolic") > 0) {
			List<String> oriList = getCurrentState();
			_openProc(path);
			Thread.sleep(3000);
			List<String> aftList = getCurrentState();
			ret = getDiff(path, oriList, aftList);
			return ret;

		}

		return path.substring(path.lastIndexOf("/") + 1);

	}

	private void _openProc(String path) throws IOException {
		if (path.contains("jvm") || path.contains("java"))
			return;
		Runtime.getRuntime().exec(path);
	}

	private String getDiff(String path, List<String> s1, List<String> s2) throws Exception {
		int len2 = s2.size();

		for (int i = 0; i < len2; ++i) {
			String s2s = s2.get(i);
			if (!s1.contains(s2s) && !s2s.contains(" ps")) {
				String s2s_name = (String) (" " + s2s).split("\\s+")[4];
				if (path.contains(s2s_name)) {
					Runtime.getRuntime().exec("pkill " + s2s_name);
					return s2s_name;
				}
			}
		}
		return "";
	}

	@Override
	public void run() {
		if (!_addFromFile()) {
			_addInfo("/usr/share/applications");
		}
	}

	private static boolean _addFromFile() {
		File _file = new File(System.getProperty("user.dir") + "/appList.dat");
		if (!_file.exists())
			return false;
		else {
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(_file));
				/* Date, sum */
				Date time = (Date) ois.readObject();
				Date now = new Date();
				_fileSize = ois.readInt();
				_curLoadCnt = 0;

				if (now.getTime() - time.getTime() > 5 * 60 * 60 * 1000) {
					ois.close();
					return false;
				}

				Application _newApp;
				while (true) {
					Thread.sleep(100);
					_newApp = (Application) ois.readObject();
					++_curLoadCnt;
					_curLoadName = _newApp.GetDisplayName();
					_appList.add(_newApp);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			int _last = _curLoadCnt;

			while (_curLoadCnt <= _fileSize) {
				_curLoadName = _appList.get(_curLoadCnt-_last).GetDisplayName();
				++_curLoadCnt;
				try {
					Thread.sleep(100);
				} catch (Exception e) {

				}
			}

		}

		return true;
	}
}
