package screenLocker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.TimerTask;
import screenLocker.loader.Loader;

import java.util.List;

public class LockerTimer extends TimerTask {

	private static Hashtable<String, Integer> _applications = new Hashtable<String, Integer>();
	private static String _path;
	private final static String _deli = Loader.IsLinux() ? "/" : "\\";
	private static boolean _firstTime = true;

	public LockerTimer() {
		_path = System.getProperty("user.dir") + _deli + "time.txt";
		File _checkFile = new File(_path);
		if (_checkFile.exists()) {
			ObjectInputStream _ois = null;
			try {
				_ois = new ObjectInputStream(new FileInputStream(_path));
				while (true) {
					String _string = (String) _ois.readObject();
					int _num = _ois.readInt();
					_applications.put(_string, _num);
				}
			} catch (Exception e) {
			}

		}
		System.out.println(BlackList());
	}

	public static int getTime(String _application) {
		if (_applications.containsKey(_application))
			return _applications.get(_application);
		return -1;
	}

	/* return the maximum time value in applications */
	public static int getLargeTime() {
		if (_firstTime) return -999;
		Enumeration<String> _e = _applications.keys();
		int _maxtime = 0;
		while (_e.hasMoreElements()) {
			String _s = _e.nextElement().toString();
			int _num = _applications.get(_s);
			if (_maxtime < _num) {
				_maxtime = _num;
			}
		}
		return _maxtime;
	}

	public void setTime(String _application, int _time) {
		_firstTime = false;
		_applications.put(_application, _time * 3600);
		try {
			ObjectOutputStream _oos = new ObjectOutputStream(new FileOutputStream(_path));
			Enumeration<String> _e = _applications.keys();
			while (_e.hasMoreElements()) {
				String _s = _e.nextElement().toString();
				_oos.writeObject(_s);
				_oos.writeInt(_applications.get(_s));
			}
			_oos.flush();
			_oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<String> BlackList() {
		List<String> _blacklist = new ArrayList<>();
		Enumeration<String> _e = _applications.keys();
		while (_e.hasMoreElements()) {
			String _s = _e.nextElement().toString();
			_blacklist.add(_s);
		}
		return _blacklist;
	}

	public static void setAddOneHour(String _punishedApp) {
		try {  
			ObjectOutputStream _oos = new ObjectOutputStream(new FileOutputStream(_path));
			_oos.writeObject(_punishedApp);
			int _newtime = _applications.get(_punishedApp) + 3600;
			_oos.writeInt(_newtime);
			_applications.put(_punishedApp, _newtime);
			_oos.flush();
			_oos.close();
		} catch (IOException ex) {

		}
	}

	public static void removeApp(String _removeApp) {
		_applications.remove(_removeApp);
		try {
			ObjectOutputStream _oos = new ObjectOutputStream(new FileOutputStream(_path));
			Enumeration<String> _e = _applications.keys();
			while (_e.hasMoreElements()) {
				String _s = _e.nextElement().toString();
				_oos.writeObject(_s);
				_oos.writeInt(_applications.get(_s));
			}
			_oos.flush();
			_oos.close();
		} catch (IOException ex) {

		}
	}
	
	public static void updateFile() {
		try {
			ObjectOutputStream _oos = new ObjectOutputStream(new FileOutputStream(_path));
			Enumeration<String>_e = _applications.keys();
			while(_e. hasMoreElements()){
				String _s= _e.nextElement().toString();
				_oos.writeObject(_s);
				_oos.writeInt(_applications.get(_s));
			}
			_oos.flush();
			_oos.close();
		} catch (IOException ex) {

		}
	}

	private int _twomin = 0;

	public void run() {
		Enumeration<String>_e = _applications.keys();
		while(_e. hasMoreElements()){
			String _s= _e.nextElement().toString();
			int _secondtime = _applications.get(_s);
			_secondtime--;
			if (_secondtime > 0) {
				_applications.remove(_s);
				_applications.put(_s, _secondtime);
			} else {
				_applications.remove(_s);
				try {
					ObjectOutputStream _oos = new ObjectOutputStream(new FileOutputStream(_path));
					Enumeration<String>_e2 = _applications.keys();
					while(_e2. hasMoreElements()){
						String _s2= _e2.nextElement().toString();
						_oos.writeObject(_s2);
						_oos.writeInt(_applications.get(_s2));
					}
					_oos.flush();
					_oos.close();
				} catch (IOException ex) {

				}
			}
		}
		_twomin++;
		if (_twomin == 120) {
			_twomin = 0;
			try {
				ObjectOutputStream _oos = new ObjectOutputStream(new FileOutputStream(_path));
				Enumeration<String>_e2 = _applications.keys();
				while(_e2. hasMoreElements()){
					String _s= _e2.nextElement().toString();
					_oos.writeObject(_s);
					_oos.writeInt(_applications.get(_s));
				}
				_oos.flush();
				_oos.close();
			} catch (IOException ex) {

			}
		}
	}
}
