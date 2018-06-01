package screenLocker;
public class GUImain extends GUI{
	
	public int showTimer(String app) {
		return getTimer(app);
	}
	
	public int getTimer(String app) {
		return applications.get(app);
	}
	public static void main(String args[]) {
		//Loader.GetInstance().LoadApplication();
		//System.out.println(((WindowsLoader)Loader.GetInstance()).listRunningProcesses());
	}
}