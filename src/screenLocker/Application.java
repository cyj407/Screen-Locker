package screenLocker;

import java.util.Date;

public class Application {
	
	private String _displayName;
	private String _processName;
	private String _iconPath;
	private String _executePath;
	private String _version;
	private String _publisher;
	private Date _lastUsed;
	
	/** constructors **/
	public Application() {
		_displayName = "";
		_processName = "";
		_iconPath = "";
		_executePath = "";
		_version = "";
		_publisher = "";
		_lastUsed = new Date();
	}
	
	/** getters **/
	public String GetDisplayName() {
		return _displayName;
	}
	public String GetIconPath() {
		return _iconPath;
	}
	public String GetExecutePath() {
		return _executePath;
	}
	public String GetProcessName() {
		return _processName;
	}
	
	/** setters **/
	public void SetDisplayName(String name) {
		_displayName = name;
	}
	public void SetProcessName(String name) {
		_processName = name;
	}
	public void SetIconPath(String path) {
		_iconPath = path;
	}
	public void SetExecutePath(String path) {
		_executePath = path;
	}
	public void SetPublisher(String s) {
		_publisher = s;
	}
	public void SetLastUsed(Date d) {
		_lastUsed = d;
	}
	public void SetVersion(String s) {
		_version = s;
	}
	/*
	public static void main(String argv[]) {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd EE kk:mm:ss");
		System.out.println(dateFormat.format(date));
	}
	*/
}

