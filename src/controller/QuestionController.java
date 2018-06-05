package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import qa_part.Question;


public class QuestionController implements Initializable{
	
	Question _questionContent = new Question();
	
	@FXML private Label _question;
	@FXML private Button _btn_a;
	@FXML private Button _btn_b;
	@FXML private Button _btn_c;
	@FXML private Button _btn_d;

	@FXML private void handleButtonClick(ActionEvent _onClickButton) {
		

        Button _buttonClicked= ((Button) _onClickButton.getSource());
		
        String _correctAnswer = _questionContent.getans();
        String _userAnswer = _buttonClicked.getText();
        
		if(_userAnswer.equals(_correctAnswer)) {
	        _buttonClicked.setText("CORRECT");
	        _buttonClicked.setStyle("-fx-text-fill: red; -fx-font-size: 21; -fx-font-weight: bold");
	        
	        // sucess, open the application
		}
		else {
			
			_buttonClicked.setText("WRONG");
	        _buttonClicked.setStyle("-fx-text-fill: green; -fx-font-size: 21; -fx-font-weight: bold");
	        
	        try {
		        Parent _noticeFXML = FXMLLoader.load(getClass().getClassLoader().getResource("_wrongAnswerNoticeLayout.fxml"));
		        Stage _showWrongStage = new Stage();
		        _showWrongStage.setScene(new Scene(_noticeFXML));
		        _showWrongStage.setTitle("NOTICE");
		        _showWrongStage.show();
	        } catch(Exception e) {
	        	e.printStackTrace();
	        }
	        
	        // fail, add extra time to lock the application
		}
		/*
		try {
        	Thread.sleep(5000);
        } catch (InterruptedException e) {
        	e.printStackTrace();
        }
        
        Stage _stage = (Stage) _buttonClicked.getScene().getWindow();
        _stage.close();
        */
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// set the label, button contents
		
		_question.setText(_questionContent.getqn());
		_btn_a.setText(_questionContent.getA());
		_btn_b.setText(_questionContent.getB());
		_btn_c.setText(_questionContent.getC());
		_btn_d.setText(_questionContent.getD());
		
	}

}
