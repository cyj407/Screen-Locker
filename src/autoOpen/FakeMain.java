package autoOpen;

public class FakeMain {
	
	public static void main(String[] args) {

		String pidFull = java.lang.management.ManagementFactory.getRuntimeMXBean().getName(); 
		String myPid = pidFull.substring(0, pidFull.indexOf('@'));
		String myDir = System.getProperty("user.dir"); 
		if (!myDir.substring(myDir.length()-4).equals("/bin")) {
			myDir += "/bin";
		}
		
		Thread t = new ProcessListener();
		t.start();
		ProcessOpen myProc = new ProcessOpen("autoOpen.FakeMain", myPid, myDir, 5);
		while (true) {
			
		}
	}
}
