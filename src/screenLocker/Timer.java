package screenLocker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

public class Timer {

	public Hashtable<String, Integer> applications = new Hashtable<String, Integer>();

	public int getTime(String application) throws IOException {
		return applications.get(application);
		/*
		FileReader fr = new FileReader("time.txt");
		BufferedReader br = new BufferedReader(fr);
		String app;
		int in = 0;
		while((app = br.readLine()) != null) {
			System.out.println(app);
			if(app.equals(application)) {
				in = 1;
				int time = Integer.valueOf(br.readLine());
				fr.close();
				return time;
			}
			else {
				String e = br.readLine();
				System.out.println("else:"+e);
			}
		}
		if(in == 0) fr.close();
		return 0;
		*/
	}
	
	public void setTime(String application, int time) throws IOException {
		applications.put(application, time);
		FileWriter fw = new FileWriter("time.txt",true);
		fw.write(application + "\r");
		fw.write(Integer.toString(time) + "\r\n");
		fw.close();
	}
	
	private void addSecond() throws IOException {
		
	}
}
