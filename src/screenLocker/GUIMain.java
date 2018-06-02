package screenLocker;

import javafx.scene.Parent;

public class GUIMain extends GUI {
	
	public GUIMain(Parent arg0) {
		super(arg0);
	}

	public int showTimer(String app) {
		return getTimer(app);
	}
	
	public int getTimer(String app) {
		return applications.get(app);
	}
}
