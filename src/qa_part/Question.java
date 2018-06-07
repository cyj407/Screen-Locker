package qa_part;

import java.util.Map;

public class Question {
	
	private String qn,ans,time;
	private String choiceA, choiceB, choiceC, choiceD;
	
	public Question(){
		Q_set database = new Q_set();
		Map<String,String> target = database.getQuestion();
		qn = target.get("qn");
		choiceA = target.get("choiceA");
		choiceB = target.get("choiceB");
		choiceC = target.get("choiceC");
		choiceD = target.get("choiceD");
		ans = target.get("ans");
		time = target.get("time");
	}
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
	public int getTime() {
		return Integer.valueOf(time);
	}
}
