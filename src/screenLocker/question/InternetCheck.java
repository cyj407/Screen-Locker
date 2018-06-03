package screenLocker.question;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class InternetCheck {	
	private String urlContent = "";		// content from the url
	
	public boolean isConnected() {
		try {		// --> yes, get the content from the internet
			URL url = new URL("https://api.mlab.com/api/1/databases/q_set/collections/data?apiKey=6FtGlTbkwz4CseHCuLjVduelugYSulXp");		// online database url
		//	URL url = new URL("https://api.mlab.com/api/1/databases/q_set/collections/test?apiKey=6FtGlTbkwz4CseHCuLjVduelugYSulXp");		// online database url
			URLConnection connect = url.openConnection();
			connect.setConnectTimeout(5000);
			connect.setReadTimeout(3000);
			
			InputStream in = connect.getInputStream();
			BufferedReader q = new BufferedReader(new InputStreamReader(in,"utf8"));	// assign content encoding utf8
			
			String tmp;
			while((tmp = q.readLine())!=null) {
				urlContent += tmp;
			}
		
			return true;
		}catch(Exception e) {	// --> no, produce question from the local
			e.getStackTrace();
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public String getList(){
		return urlContent;
	}
	
}

