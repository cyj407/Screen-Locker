package screenLocker;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;
import com.sun.jna.platform.win32.WinReg.HKEY;
import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static com.sun.jna.platform.win32.WinReg.HKEY_CURRENT_USER;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public final class WindowsLoader extends Loader {
	private static WindowsLoader _instance;
	private static int _applicationNumber;
	private static int _currentNumber;
	
	public static int getCurrentNumber() {
		return _currentNumber;
	}
	public static int getApplicationNumber() {
		return _applicationNumber;
	}
	public static Application getApplication(String name) {
		for (Application iter : appList) {
			if (iter.getDisplayName().equals(name))
				return iter;
		}
		return null;
	}
	public static Loader getInstance() {
		if(_instance == null){
            synchronized(WindowsLoader.class){
                if(_instance == null){
                    _instance = new WindowsLoader();
                }    
            }
        } 
        return _instance;
	}
	private static boolean _readNext() {
		return true;
	}
	
	private ArrayList<String> list;
	private String _defaultPath = "Software\\Microsoft\\Windows\\CurrentVersion\\Uninstall";
	private String _defaultPathOfCurrentUser = "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall";
	private String _anotherPath = "Software\\Wow6432Node\\Microsoft\\Windows\\CurrentVersion\\Uninstall";
	public WindowsLoader() {
		list = new ArrayList<String>();
		String[] defaultAppRegister = Advapi32Util.registryGetKeys(HKEY_LOCAL_MACHINE, _defaultPath);
		String[] anotherAppRegister = Advapi32Util.registryGetKeys(HKEY_LOCAL_MACHINE, _anotherPath);
		String[] userDefaultAppRegister = Advapi32Util.registryGetKeys(HKEY_CURRENT_USER, _defaultPathOfCurrentUser);
		for (String iter : defaultAppRegister) {
			String temp = _defaultPath + "\\" + iter;
		    TreeMap<String, Object> tr = Advapi32Util.registryGetValues(HKEY_LOCAL_MACHINE, temp); 
		    if (tr.isEmpty()) {
		    	continue;
		    }
		    if (iter.contains("Update")) {
		    	continue;
		    }
		    boolean flag = true;
		    for(Map.Entry<String, Object> entry : tr.entrySet()) {
		    	String s = entry.getKey();
		    	Object o = entry.getValue();
		    	if (s.equals("SystemComponent") && (int)o == 1)
		    		flag = false;
		    	//if (s.equals("WindowsInstaller") && (int)o == 1)
		    		//flag = false;
		    	if (s.equals("UninstallString") && ((String)o).isEmpty())
		    		flag = false;
		    }
		    if (flag && tr.containsKey("DisplayName")) {
		    	String str = (String) tr.get("DisplayName");
		    	if (!str.contains("Update"))
		    		list.add((String)tr.get("DisplayName"));
		    }
		}
		
		for (String iter : anotherAppRegister) {
			String temp = _anotherPath + "\\" + iter;
		    TreeMap<String, Object> tr = Advapi32Util.registryGetValues(HKEY_LOCAL_MACHINE, temp); 
		    if (tr.isEmpty()) {
		    	continue;
		    }
		    if (iter.contains("Update")) {
		    	continue;
		    }
		    boolean flag = true;
		    for(Map.Entry<String, Object> entry : tr.entrySet()) {
		    	String s = entry.getKey();
		    	Object o = entry.getValue();
		    	if (s.equals("SystemComponent") && (int)o == 1) {
		    		flag = false;
		    	}
		    	//if (s.equals("WindowsInstaller") && (int)o == 1) {
		    		//flag = false;
		    	//}
		    	if (s.equals("UninstallString") && ((String)o).isEmpty()) {
		    		flag = false;
		    	}
		    }
		    if (flag && tr.containsKey("DisplayName")) {
		    	String str = (String) tr.get("DisplayName");
		    	if (!str.contains("Update"))
		    		list.add((String)tr.get("DisplayName"));
		    }
		    	
		}
		for (String iter : userDefaultAppRegister) {
			String temp = _defaultPathOfCurrentUser + "\\" + iter;
		    TreeMap<String, Object> tr = Advapi32Util.registryGetValues(WinReg.HKEY_CURRENT_USER, temp); 
		    if (tr.isEmpty()) {
		    	continue;
		    }
		    if (iter.contains("Update")) {
		    	continue;
		    }
		    boolean flag = true;
		    for(Map.Entry<String, Object> entry : tr.entrySet()) {
		    	String s = entry.getKey();
		    	Object o = entry.getValue();
		    	if (s.equals("SystemComponent") && (int)o == 1)
		    		flag = false;
		    	//if (s.equals("WindowsInstaller") && (int)o == 1)
		    		//flag = false;
		    	if (s.equals("UninstallString") && ((String)o).isEmpty())
		    		flag = false;
		    }
		    if (flag && tr.containsKey("DisplayName")) {
		    	String str = (String) tr.get("DisplayName");
		    	if (!str.contains("Update"))
		    		list.add((String)tr.get("DisplayName"));
		    }
		}

		
		for(String iter : list) {
			System.out.println(iter);
		}
		
		
		System.out.println(list.size());
	
	}
	
	
	  public static List<String> listRunningProcesses() {
		    List<String> processes = new ArrayList<String>();
		    try {
		      String line;
		      Process p = Runtime.getRuntime().exec("tasklist.exe /fo csv /nh");
		      BufferedReader input = new BufferedReader
		          (new InputStreamReader(p.getInputStream()));
		      while ((line = input.readLine()) != null) {
		          if (!line.trim().equals("")) {
		              // keep only the process name
		              line = line.substring(1);
		              processes.add(line.substring(0, line.indexOf("\"")));
		          }

		      }
		      input.close();
		    }
		    catch (Exception err) {
		      err.printStackTrace();
		    }
		    return processes;
		  }


	@Override
	public boolean loadApplication() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int loadProgressPercentage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String loadStatus() {
		// TODO Auto-generated method stub
		return null;
	}
}
