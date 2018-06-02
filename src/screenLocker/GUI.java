package screenLocker;

import java.util.Hashtable;

import javafx.scene.Parent;
import javafx.scene.Scene;

public class GUI extends Scene {
	
	public GUI(Parent arg0) {
		super(arg0);
	}
	public Hashtable<String, Integer> applications = new Hashtable<String, Integer>();

}
