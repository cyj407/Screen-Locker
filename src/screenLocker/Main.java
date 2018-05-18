package screenLocker;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		Timer timer = new Timer();
		timer.setTime("java", 1);
		timer.setTime("eclipse", 2);
		timer.setTime("test", 4);
		int t = timer.getTime("test");
		System.out.println("main:" + t);
		FileWriter fw = new FileWriter("time.txt");
		fw.close();
	}

}
