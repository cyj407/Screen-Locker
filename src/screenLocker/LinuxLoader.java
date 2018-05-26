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
	private static double percentage;
	private static String currentLoad;
	private static String currentExec;

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
		appList = new ArrayList<Application>();
		loadApplication();

	}

	@Override
	public boolean loadApplication() {
		addInfo("/usr/share/applications");
		return true;
	}

	private void addInfo(String path) {
		File folder = new File(path);
		File[] files = folder.listFiles();
		int size = files.length, fileCnt = 0;
		percentage = 0;

		for (File file : files) {
			if (file.isFile()) {
				String fileName = file.getName();

				/** ensure the file is a desktop entry **/
				if (fileName.contains(".desktop")) {
					Application new_app = new Application();
					try {
						FileReader fr = new FileReader(file);
						BufferedReader br = new BufferedReader(fr);
						String line;
						while ((line = br.readLine()) != null) {
							/** assume the first entry is [Desktop Entry] **/
							/** if over desktop entry then leave this file **/
							if (line.length() != 0 && line.charAt(0) == '[' && !line.equals("[Desktop Entry]"))
								break;

							if (line.length() < 5)
								continue;
							String front = line.substring(0, 5);
							String back = line.substring(5);

							if (front.equals("Name=")) {
								new_app.setDisplayName(back);
								currentLoad = back;
							} else if (front.equals("Exec=")) {
								String exePath = back.split(" ")[0];
								String exeName = "";
								try {
									exeName = findExeName(exePath);
								} catch (Exception e) {
									/** does not exist backslash **/
									exeName = exePath;
								}
								// System.out.println(exeName);
								currentExec = exeName;
							} else if (front.equals("Icon=")) {
								new_app.setIconPath(back);
							}
						}

						appList.add(new_app);
						br.close();

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			++fileCnt;
			percentage = (double) fileCnt / size;
			loadProgressPercentage();
			loadStatus();
		}

	}

	@Override
	public double loadProgressPercentage() {
		//System.out.printf("%.2f ", percentage * 100);
		return percentage*100;
	}

	@Override
	public String loadStatus() {
		//System.out.println(currentLoad + " " + currentExec);
		return "loading..." + currentExec;
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
