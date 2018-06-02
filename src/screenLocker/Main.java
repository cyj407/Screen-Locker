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
		Thread.sleep(3000);
		int t2 = timer.getTime("java");
		System.out.println("main:" + t2);
		MyTimer.getLargeTime();
		Thread.sleep(3000);
		MyTimer.getLargeTime();
		MyTimer.BlackList();
		MyTimer.setAddTenMins();
		int t = timer.getTime("test");
		System.out.println("maintest:" + t);
		
		try {
			Runtime.getRuntime().addShutdownHook(new Message());
		}catch (Exception e) {
		}
		
		//timer2.cancel();
	}
	
	static class Message extends Thread{
		public void run() {
			MyTimer.close();
		}
	}
}