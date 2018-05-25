package screenLocker;

import java.io.IOException;
import java.util.Timer;

public class Main {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Timer timer2 = new Timer();
		timer2.schedule(new MyTimer(), 1000, 1000);
		MyTimer timer = new MyTimer();
		timer.setTime("java", 1);
		timer.setTime("eclipse", 0);
		timer.setTime("test", 4);
		int t = timer.getTime("test");
		Thread.sleep(3000);
		int t2 = timer.getTime("java");
		System.out.println("main:" + t);
		System.out.println("main:" + t2);
		timer.getLargeTime();
		Thread.sleep(3000);
		timer.getLargeTime();
		timer.BlackList();
	}

}