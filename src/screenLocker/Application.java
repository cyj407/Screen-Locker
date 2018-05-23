package screenLocker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Application {
	
	private String _displayName;
	private String _processName;
	private String _iconPath;
	private String _executePath;
	private String _uninstallString;
	private String _version;
	private String _publisher;
	private Date _lastUsed;
	
	/** constructors **/
	public Application() {
		_displayName = "";
		_processName = "";
		_iconPath = "";
		_executePath = "";
		_uninstallString = "";
		_version = "";
		_publisher = "";
		_lastUsed = new Date();
	}
	
	/** getters **/
	public String getDisplayName() {
		return _displayName;
	}
	public String getIconPath() {
		return _iconPath;
	}
	public String getExecutePath() {
		return _iconPath;
	}
	public String getProcessName() {
		return _processName;
	}
	
	/** setters **/
	public void setDisplayName(String name) {
		_displayName = name;
	}
	public void setProcessName(String name) {
		_processName = name;
	}
	public void setIconPath(String path) {
		_iconPath = path;
	}
	public void setExecutePath(String path) {
		_executePath = path;
	}
	public void setUninstallString(String s) {
		_uninstallString = s;
	}
	public void setPublisher(String s) {
		_publisher = s;
	}
	public void setLastUsed(Date d) {
		_lastUsed = d;
	}
	/*
	public static void main(String argv[]) {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd EE kk:mm:ss");
		System.out.println(dateFormat.format(date));
	}
	*/
}

