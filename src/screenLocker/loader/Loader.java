package screenLocker.loader;

import java.util.ArrayList;
import java.util.List;

import screenLocker.Application;

public abstract class Loader {
	public abstract boolean LoadApplication();
	public abstract double LoadProgressPercentage();
	public abstract String LoadStatus();
	private static String _OS = System.getProperty("os.name").toLowerCase();
	protected static List<Application> _appList;
	public static Loader GetInstance() {
		if (IsWindows())
			return WindowsLoader.GetInstance();
		else if (IsLinux())
			return LinuxLoader.GetInstance();
		return null;
	}
	public static String GetOsType() {
		return _OS;
	}
	public static boolean IsWindows() {
		return (_OS.indexOf("win") >= 0);
	}
	public static boolean IsLinux() {
		return (_OS.indexOf("nix") >= 0 || _OS.indexOf("nux") >= 0 || _OS.indexOf("aix") > 0);
	}
	public static int GetApplicationNumber() {
		return _appList.size();
	}
	public static boolean IsExisitedApplication(String _name) {
		for(Application _iter : _appList)
			if (_iter.GetDisplayName().equals(_name))
				return true;
		return false;
	}
	public static boolean IsExisitedApplication(Application _app) {
		for(Application _iter : _appList)
			if (_iter.GetDisplayName().equals(_app.GetDisplayName()))
				return true;
		return false;
	}
	public static ArrayList<Application> GetApplication() {
		ArrayList<Application> _result = new ArrayList<Application>();
		for(Application _iter : _appList) {
			_result.add(_iter);
		}
		return _result;
	}
}