package screenLocker;

import java.util.List;

public abstract class Loader {
	public abstract boolean loadApplication();
	public abstract int loadProgressPercentage();
	public abstract String loadStatus();
	private static String OS = System.getProperty("os.name").toLowerCase();
	protected static List<Application> appList;
	public static Loader getInstance() {
		if (isWindows())
			return WindowsLoader.getInstance();
		else if (isLinux())
			return LinuxLoader.getInstance();
		return null;
	}
	public static String getOsType() {
		return OS;
	}
	public static boolean isWindows() {
		return (OS.indexOf("win") >= 0);
	}
	public static boolean isLinux() {
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
	}
	public static boolean isExisitedApplication(String name)
	{
		for(Application iter : appList)
			if (iter.getDisplayName().equals(name))
				return true;
		return false;
	}
	public static boolean isExisitedApplication(Application app)
	{
		for(Application iter : appList)
			if (iter.getDisplayName().equals(app.getDisplayName()))
				return true;
		return false;
	}
}
