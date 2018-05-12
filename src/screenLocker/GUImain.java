package screenLocker;

public class GUImain extends GUI{
	
	public int showTimer(String app) {
		return getTimer(app);
	}
	
	public int getTimer(String app) {
		return applications.get(app);
	}
}
