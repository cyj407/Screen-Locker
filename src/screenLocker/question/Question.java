package screenLocker.question;

import java.util.Map;

public class Question {
	
	private String qn;
	private String choiceA, choiceB, choiceC, choiceD;
	private String ans;
	
	Question(){
		// set question
		Q_set database = new Q_set();
		Map<String,String> target = database.getQuestion();
		qn = target.get("qn");
		choiceA = target.get("choiceA");
		choiceB = target.get("choiceB");
		choiceC = target.get("choiceC");
		choiceD = target.get("choiceD");
		ans = target.get("ans");
	}
	// get question
	public String getqn() {
		return qn;
	}
	public String getA() {
		return choiceA;
	}
	public String getB() {
		return choiceB;
	}
	public String getC() {
		return choiceC;
	}
	public String getD() {
		return choiceD;
	}
	public String getans() {
		return ans;
	}
	
	// 4 choices

	// click button to answer
	// timer
	
	// wrong
	// correct
}
