package screenLocker;

import javafx.scene.Parent;

public class GUISetting extends GUI{
	
	public GUISetting(Parent arg0) {
		super(arg0);
	}

	public void setTimer(String app,int time) {
		applications.put(app, time);
	}
	
	public int getTimer(String app) {
		return applications.get(app);
	}

}
