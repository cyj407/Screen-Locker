package screenLocker;

import java.io.Serializable;

public class Application implements Serializable{
	
	private String _displayName;
	private String _processName;
	private String _iconPath;
	private String _executePath;
	
	/** constructors **/
	public Application() {
		_displayName = "";
		_processName = "";
		_iconPath = "";
		_executePath = "";
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
}