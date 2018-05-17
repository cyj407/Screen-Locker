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
	/*
	public static void main(String argv[]) {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd EE kk:mm:ss");
		System.out.println(dateFormat.format(date));
	}
	*/
}

