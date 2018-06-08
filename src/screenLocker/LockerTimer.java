package screenLocker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.TimerTask;
import java.util.List;


public class LockerTimer extends TimerTask{
	
	public static Hashtable<String, Integer> applications = new Hashtable<String, Integer>();
	
	public LockerTimer() throws FileNotFoundException, IOException {
		Path p = Paths.get("time.txt");
        if (Files.exists(p)) {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("time.txt"));
			try {
				while(true) {
					String string = (String)ois.readObject();
					int num = ois.readInt();
					applications.put(string, num);
					//System.out.println("App = " + string);
					//System.out.println("time = " + applications.get(string));	
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			ois.close();
        }
	}

	public int getTime(String application) throws IOException, ClassNotFoundException {	
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("time.txt"));
		try {
			while(true) {
				String string = (String)ois.readObject();
				int num = ois.readInt();
				if(string.equals(application)) {
					//System.out.println("App = " + string);
					//System.out.println("time = " + applications.get(application));
					return applications.get(application);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		ois.close();
		return -1;
	}
	
	/*return the maximum time value in applications*/
	public static int getLargeTime() throws IOException, ClassNotFoundException {
		Enumeration e = applications.keys();
		int maxtime = 0;
		while(e. hasMoreElements()) {
			String s= e.nextElement().toString();
			int num = applications.get(s);
			if(maxtime < num) {
				maxtime = num;
			}
		}
		return maxtime;
	}
	
	public void setTime(String application, int time) throws IOException {
		applications.put(application, time*3600);
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("time.txt"));
		Enumeration e = applications.keys();
		while(e. hasMoreElements()){
			String s= e.nextElement().toString();
			oos.writeObject(s);
			oos.writeInt(applications.get(s));
		}
		oos.flush(); 
		oos.close();
	}
	
	public static List<String> BlackList() {
		List<String> blacklist = new ArrayList<>();
		Enumeration<String> e = applications.keys();
		while(e.hasMoreElements()) {
			String s = e.nextElement().toString();
			blacklist.add(s);
		}
		System.out.println("list = " + blacklist);	
		return blacklist;	
	}
	
	public static void setAddTenMins() {
		try {  
			Enumeration e = applications.keys();
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("time.txt"));
			while(e. hasMoreElements()){
				String s= e.nextElement().toString();
				oos.writeObject(s);
				int newtime = applications.get(s)+360;
				oos.writeInt(newtime);
				applications.put(s, newtime);
			}
			oos.flush(); 
			oos.close();
		} catch (IOException ex) {                       
			
		}
	}
	
	private int twomin = 0;
	public void run() {
		Enumeration e = applications.keys();
		while(e. hasMoreElements()){
			String s= e.nextElement().toString();
			int secondtime = applications.get(s);
			secondtime--;
			if(secondtime > 0) {
				applications.remove(s);
				applications.put(s, secondtime);
				//System.out.println(s + secondtime);
			}
			else {
				applications.remove(s);
			}
		}
		twomin++;
		if(twomin == 120) {
			twomin = 0;
			try {        
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("time.txt"));
				Enumeration e2 = applications.keys();
				while(e2. hasMoreElements()){
					String s= e2.nextElement().toString();
					oos.writeObject(s);
					oos.writeInt(applications.get(s));
					//System.out.println(s);
					//System.out.println(applications.get(s));
				}
				oos.flush(); 
				oos.close();
			} catch (IOException ex) {                       
			
			}
		}
	}
	
	public static void close() {
		try {        
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("time.txt"));
			Enumeration e = applications.keys();
			while(e. hasMoreElements()){
				String s= e.nextElement().toString();
				oos.writeObject(s);
				oos.writeInt(applications.get(s));
			}
			oos.flush(); 
			oos.close();
		} catch (IOException ex) {                       
		}
	}
}
