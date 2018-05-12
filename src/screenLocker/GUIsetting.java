package screenLocker;

public class GUIsetting extends GUI{
	
	public void setTimer(String app,int time) {
		applications.put(app, time);
	}
	
	public int getTimer(String app) {
		return applications.get(app);
	}

}
