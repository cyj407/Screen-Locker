package screenLocker.loader;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;

import screenLocker.Application;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static com.sun.jna.platform.win32.WinReg.HKEY_CURRENT_USER;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public final class WindowsLoader extends Loader implements Runnable {
	private static WindowsLoader _instance;
	private static int _currentNumber;
	private static int _totalNumber;
	
	private ArrayList<String> _executableList;
	private String _defaultPath = "Software\\Microsoft\\Windows\\CurrentVersion\\Uninstall";
	private String _defaultPathOfCurrentUser = "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall";
	private String _anotherPath = "Software\\Wow6432Node\\Microsoft\\Windows\\CurrentVersion\\Uninstall";
	private String _defaultAppPath = "Software\\Microsoft\\Windows\\CurrentVersion\\App Paths";
	private String _defaultAppPathOfCurrentUser = "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\App Paths";
	private String _anotherAppPath = "Software\\Wow6432Node\\Microsoft\\Windows\\CurrentVersion\\App Paths";
	
	public WindowsLoader() {
		_executableList = new ArrayList<String>();
		_appList = new ArrayList<Application>();
		_currentNumber = 0;
		_totalNumber = 0;
		_getTotalDataNumber();
	}
	
	public static Loader GetInstance() {
		// use Singleton design pattern to implement the WindowsLoader
		if(_instance == null){
            synchronized(WindowsLoader.class){
                if(_instance == null){
                    _instance = new WindowsLoader();
                }    
            }
        } 
        return _instance;
	}
	public static int GetCurrentNumber() {
		return _currentNumber;
	}
	public static Application GetApplication(String name) {
		for (Application iter : _appList) {
			if (iter.GetDisplayName().equals(name))
				return iter;
		}
		return null;
	}
	
	@Override
	public double LoadProgressPercentage() {
		return (double)_currentNumber / _totalNumber;
	}
	@Override
	public String LoadStatus() {
		if (_currentNumber < _totalNumber / 2) {
			return "Now is fetching the data ...";
		}
		String _str = "Now is loading " + _appList.get(_appList.size() - 1).GetDisplayName() + " ...";
		return _str;
	}
	
	private void _getTotalDataNumber() {
		// get all key of the target registry.
		String[] _defaultAppRegister = Advapi32Util.registryGetKeys(HKEY_LOCAL_MACHINE, _defaultPath);
		String[] _anotherAppRegister = Advapi32Util.registryGetKeys(HKEY_LOCAL_MACHINE, _anotherPath);
		String[] _userDefaultAppRegister = null;
		try {
			_userDefaultAppRegister = Advapi32Util.registryGetKeys(HKEY_CURRENT_USER, _defaultPathOfCurrentUser);
		} catch (Exception e) {
			
		}
		_totalNumber += _defaultAppRegister.length + _anotherAppRegister.length;
		try {
			_totalNumber += _userDefaultAppRegister.length;
		} catch (Exception e) {
			
		}
		_defaultAppRegister = Advapi32Util.registryGetKeys(HKEY_LOCAL_MACHINE, _defaultAppPath);
		_anotherAppRegister = Advapi32Util.registryGetKeys(HKEY_LOCAL_MACHINE, _anotherAppPath);
		_userDefaultAppRegister = null;
		try {
			_userDefaultAppRegister = Advapi32Util.registryGetKeys(HKEY_CURRENT_USER, _defaultAppPathOfCurrentUser);
		} catch (Exception e) {
			
		}
		_totalNumber += _defaultAppRegister.length + _anotherAppRegister.length;
		try {
			_totalNumber += _userDefaultAppRegister.length;
		} catch (Exception e) {
			
		}
	}
	
	private static boolean _readNext() {
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (_currentNumber <= _totalNumber) {
			_currentNumber++;
			return true;
		}
		return false;
	}
	
	private void _getExecutableList() {
		// get all key of the target registry.
		String[] _defaultAppRegister = Advapi32Util.registryGetKeys(HKEY_LOCAL_MACHINE, _defaultAppPath);
		String[] _anotherAppRegister = Advapi32Util.registryGetKeys(HKEY_LOCAL_MACHINE, _anotherAppPath);
		String[] _userDefaultAppRegister = null;
		try {
			_userDefaultAppRegister = Advapi32Util.registryGetKeys(HKEY_CURRENT_USER, _defaultAppPathOfCurrentUser);
		} catch (Exception e) {
		}
		// visit the default register that record application executable path information.
		for (String _iter : _defaultAppRegister) {
			_readNext();
			String _temp = _defaultAppPath + "\\" + _iter;
		    TreeMap<String, Object> _tr = Advapi32Util.registryGetValues(HKEY_LOCAL_MACHINE, _temp); 
		    if (_tr.isEmpty())
		    	continue;
		    String _targetPath = String.valueOf(_tr.entrySet().iterator().next().getValue());
		    if (_targetPath.length() < 2)
		    	continue;
		    _executableList.add(_targetPath);
		}
		// visit the another register that record application executable path information.
		for (String _iter : _anotherAppRegister) {
			
			_readNext();
			String _temp = _anotherAppPath + "\\" + _iter;
			TreeMap<String, Object> _tr = Advapi32Util.registryGetValues(HKEY_LOCAL_MACHINE, _temp); 
		    if (_tr.isEmpty())
		    	continue;
		    String _targetPath = String.valueOf(_tr.entrySet().iterator().next().getValue());
		    if (_targetPath.length() < 2)
		    	continue;
		    _executableList.add(_targetPath);
		}
		try {
			// visit the local user register that record application executable path information.
			for (String _iter : _userDefaultAppRegister) {
				_readNext();
				String _temp = _defaultAppPathOfCurrentUser + "\\" + _iter;
				TreeMap<String, Object> _tr = Advapi32Util.registryGetValues(WinReg.HKEY_CURRENT_USER, _temp); 
			    if (_tr.isEmpty())
			    	continue;
				String _targetPath = String.valueOf(_tr.entrySet().iterator().next().getValue());
			    if (_targetPath.length() < 2)
			    	continue;
			    _executableList.add(_targetPath);
			}
		} catch (Exception e) {
			
		}
	}
	private void _loadAllApplication () {
		// get all key of the target registry.
		String[] _defaultAppRegister = Advapi32Util.registryGetKeys(HKEY_LOCAL_MACHINE, _defaultPath);
		String[] _anotherAppRegister = Advapi32Util.registryGetKeys(HKEY_LOCAL_MACHINE, _anotherPath);
		String[] _userDefaultAppRegister = null;
		try {
			_userDefaultAppRegister = Advapi32Util.registryGetKeys(HKEY_CURRENT_USER, _defaultPathOfCurrentUser);
		} catch (Exception e) {
			
		}
		// visit the default register that record application information.
		for (String _iter : _defaultAppRegister) {
			
			_readNext();
			String _temp = _defaultPath + "\\" + _iter;
		    TreeMap<String, Object> _tr = Advapi32Util.registryGetValues(HKEY_LOCAL_MACHINE, _temp); 
		    if (_tr.isEmpty()) {
		    	continue;
		    }
		    if (_iter.contains("Update")) {
		    	continue;
		    }
		    boolean _flag = true;
		    for(Map.Entry<String, Object> _entry : _tr.entrySet()) {
		    	String _s = _entry.getKey();
		    	Object _o = _entry.getValue();
		    	if(_o instanceof Number) {
			    	if (_s.equals("SystemComponent") && ((Number)_o).intValue() == 1) {
			    		_flag = false;
			    	}
		    	}
		    	if (_s.equals("UninstallString") && ((String.valueOf(_o)).isEmpty())) {
		    		_flag = false;
		    	}
		    }
		    if (_flag && _tr.containsKey("DisplayName")) {
		    	String _str = (String) _tr.get("DisplayName");
		    	if (!_str.contains("Update")) {
		    		_addApplication(_tr);
		    	}
		    }
		}
		// visit the another register that record application information.
		for (String _iter : _anotherAppRegister) {
			
			_readNext();
			String _temp = _anotherPath + "\\" + _iter;
		    TreeMap<String, Object> _tr = Advapi32Util.registryGetValues(HKEY_LOCAL_MACHINE, _temp); 
		    if (_tr.isEmpty()) {
		    	continue;
		    }
		    if (_iter.contains("Update")) {
		    	continue;
		    }
		    boolean _flag = true;
		    for(Map.Entry<String, Object> _entry : _tr.entrySet()) {
		    	String _s = _entry.getKey();
		    	Object _o = _entry.getValue();
		    	if (_s.equals("SystemComponent") && (Integer)_o == 1) {
		    		_flag = false;
		    	}
		    	if (_s.equals("UninstallString") && ((String.valueOf(_o)).isEmpty())) {
		    		_flag = false;
		    	}
		    }
		    if (_flag && _tr.containsKey("DisplayName")) {
		    	String _str = (String) _tr.get("DisplayName");
		    	if (!_str.contains("Update")) {
		    		_addApplication(_tr);
		    	}
		    }	
		}
		try {
			// visit the local user register that record application information.
			for (String _iter : _userDefaultAppRegister) {
				_readNext();
				String _temp = _defaultPathOfCurrentUser + "\\" + _iter;
			    TreeMap<String, Object> _tr = Advapi32Util.registryGetValues(WinReg.HKEY_CURRENT_USER, _temp); 
			    if (_tr.isEmpty()) {
			    	continue;
			    }
			    if (_iter.contains("Update")) {
			    	continue;
			    }
			    boolean _flag = true;
			    for(Map.Entry<String, Object> _entry : _tr.entrySet()) {
			    	String _s = _entry.getKey();
			    	Object _o = _entry.getValue();
			    	if (_s.equals("SystemComponent") && (Integer)_o == 1) {
			    		_flag = false;
			    	}
			    	if (_s.equals("UninstallString") && (String.valueOf(_o)).isEmpty()) {
			    		_flag = false;
			    	}
			    }
			    if (_flag && _tr.containsKey("DisplayName")) {
			    	String _str = (String) _tr.get("DisplayName");
			    	if (!_str.contains("Update")) {
			    		_addApplication(_tr);
			    	}
			    }
			}
		} catch (Exception e) {
			
		}
	}
	private void _addApplication(TreeMap<String, Object> _tr) {
		Application _app = new Application();
		try {
			_app.SetDisplayName((String)_tr.get("DisplayName"));
		} catch (Exception e) {
			
		}
		try {
			if (((String) (_tr.get("DisplayIcon"))).indexOf(".exe") >= 0) {
				_app.SetExecutePath((String)_tr.get("DisplayIcon"));
			} else {
				String _path = _getExecutablePath((String) (_tr.get("InstallLocation")));
				_app.SetExecutePath(_path);
			}
		} catch (Exception e) {
		}
		try {
    		_app.SetIconPath(((String)_tr.get("DisplayIcon")).replace(",0", ""));
		} catch (Exception e) {
		}
		try {
			if (_app.GetExecutePath() != null && _app.GetExecutePath().indexOf(".exe") >= 0) {
				File _file = new File(_app.GetExecutePath());
				String _str = _file.getPath().replace(_file.getParent(), "");
				_str = _str.replace("\\", "");
				_str = _str.replace(",0", "");
				_app.SetProcessName(_str);
			}
		} catch (Exception e) {
			
		}
		_appList.add(_app);
	}
	private String _getExecutablePath(String _installPath) {
		if (_installPath == null)
			return null;
		File _installDirectory = new File(_installPath);
		String _result = _find(_installDirectory);
		return _result;
	}
	private String _find(final File _folder) {
	    for (final File _iter : _folder.listFiles()) {
	        if (_iter.isDirectory()) {
	            String _result = _find(_iter);
	            if (_executableList.contains(_result)) {
		    		return _result;
		    	}
	        } else {
	        	File _currentDirectory = _iter.getParentFile();
	        	if (_executableList.contains(_iter.getPath()) || 
	        			(_iter.getName().indexOf(_currentDirectory.getName()) >= 0 && _iter.getName().indexOf(".exe") >= 0)) {
	        		return _iter.getPath();
	        	} else if (!_executableList.contains(_iter.getPath()) && 
	        			_iter.getName().toLowerCase().indexOf(_currentDirectory.getName().toLowerCase()) >= 0) {
	        		return _iter.getPath();
	        	}
	    	}
	    }
	    return null;
	}
	
	@Override
	public void run() {
		_getExecutableList();
		_loadAllApplication();
	}

}
