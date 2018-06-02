package screenLocker;

import java.util.Hashtable;
import java.util.Random;

import javafx.scene.Parent;

public class GUIQuestion extends GUI{
	
	public GUIQuestion(Parent arg0) {
		super(arg0);
	}

	private int questionNum;
	public Hashtable<Integer, Integer> questions = new Hashtable<Integer, Integer>();
	
	public void setQusetion() {
		Random random = new Random();
		questionNum = (random.nextInt(2)+1);//two questions
	}
	
	public void getQuestion() {
		
	}
	
	public void setAns(int ans[]) {
		questions.put(1, ans[0]);
		questions.put(2, ans[1]);
	}
	
	public int getAns(int questionnum) {
		return questions.get(questionnum);
	}
	
	public void checkAns(int ans) {
		if(ans == getAns(questionNum)) {
			//close program
		}
		else {
			showAns();
			//delay 10 min
		}
	}
	
	public void showAns() {
		//show correct ans
	}
}
