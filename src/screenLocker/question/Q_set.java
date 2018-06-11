package screenLocker.question;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Q_set {
	private Map<String,String> chooseQ;
	private JSONObject quest;	
	Q_set(){
		InternetCheck internet = new InternetCheck();
		String database = "";
 		if(internet.isConnected())
 			database = internet.getList();		
 		else {		// create the question from local site
			try {
	            BufferedReader _br = new BufferedReader(new InputStreamReader(new FileInputStream("./src/screenLocker/question/LocalData.txt"),"utf8"));
	            while(_br.ready()) {
	                String _brStr = _br.readLine();
	                if(_brStr.length() > 0) {
	                	_brStr = _brStr.substring(1, _brStr.length());
	                    // utf8 format's first word is '?'
	                	database += _brStr;
	                }
	            }
	            _br.close();
	        }catch(Exception e){
	            e.printStackTrace();
	        }
			
 		}
 		
 		JSONArray q_set = new JSONArray(database);
		int chooseID = (int)(Math.random()*q_set.length());		// index range = [0:q_set.length()-1]
		quest = q_set.getJSONObject(chooseID);
 		
 		setQuestion(quest);
     }
	
	public void setQuestion(JSONObject obj) {
		chooseQ = new HashMap<String,String>();
		chooseQ.put("qn", obj.getString("qn"));
		chooseQ.put("choiceA", obj.getString("choiceA"));
		chooseQ.put("choiceB", obj.getString("choiceB"));
		chooseQ.put("choiceC", obj.getString("choiceC"));
		chooseQ.put("choiceD", obj.getString("choiceD"));
		chooseQ.put("ans", obj.getString("ans"));
		chooseQ.put("time", obj.getString("time"));
	}
	
	public Map<String,String> getQuestion(){
		return chooseQ;
	}
	
}