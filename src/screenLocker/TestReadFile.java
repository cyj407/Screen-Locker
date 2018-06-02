package screenLocker;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class TestReadFile {
	public static void main(String[] args) throws IOException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("time.txt"));
		try {
			while(true) {
				String string = (String)ois.readObject();
				int num = ois.readInt();
				System.out.println("App = " + string);
				System.out.println("time = " + num);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		ois.close();
	}
}
