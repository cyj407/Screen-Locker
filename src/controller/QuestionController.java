package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import screenLocker.LockerTimer;
import screenLocker.ProgramManager;
import screenLocker.question.Question;

public class QuestionController implements Initializable{
	
	private Question _questionContent = new Question();
	private int _countdown = _questionContent.getTime();
	@FXML private Label _question;
	@FXML private Label _remainTime;
	@FXML private Button _btn_a;
	@FXML private Button _btn_b;
	@FXML private Button _btn_c;
	@FXML private Button _btn_d;
    @FXML private Button _shrinkButton;
    @FXML private Button _enlargeButton;
    @FXML private Button _closeButton;
	
    private Timeline _time = new Timeline();
    private double _x, _y;
    private Stage _showResultStage;
    
	@FXML public void Draged(MouseEvent event) {
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	stage.setX(event.getScreenX() - _x);
    	stage.setY(event.getScreenY() - _y);
    }

    @FXML public void Pressed(MouseEvent event) {
    	_x = event.getSceneX();
    	_y = event.getSceneY();
    }
    
    @FXML public void Close(MouseEvent event) {
    //	do nothing >> prevent user close the question window
    }

    @FXML public void Shrink(MouseEvent event) {
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	stage.setIconified(true);
    }
	
	private void updateRemainTime() {
		KeyFrame _cycle= new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent event) {
				_remainTime.setVisible(true);
				_countdown--;
				if(_countdown <= 10) {
					_remainTime.setStyle("-fx-text-fill: red");
				}
				_remainTime.setText(String.valueOf(_countdown));
			    if(_countdown < 0) {
			    	try {
			        	Parent _noticeFXML = FXMLLoader.load(getClass().getClassLoader().getResource("views/_timeoutNoticeLayout.fxml"));	        
			        	_showResultStage = new Stage();
			        	_showResultStage.setScene(new Scene(_noticeFXML));
			        	_showResultStage.initStyle(StageStyle.UNDECORATED);
			        	_showResultStage.setResizable(false); 
			        	_showResultStage.show();
				        Event _event = new WindowsTransferEvent(this, ProgramManager.RootStage(), WindowsTransferEvent.TransferToMain);
				    	Event.fireEvent(ProgramManager.RootStage(), _event);
			        } catch(Exception e) {
			        	e.printStackTrace();
			        }
			    	_time.stop();
			    
			    	// fail, add an hour time to lock the application
			    }
			}		
		});
		_time.setCycleCount(Timeline.INDEFINITE);
		_time.getKeyFrames().add(_cycle);
		_time.play();
	}
	

	@FXML private void handleButtonClick(ActionEvent _onClick) {
		_time.stop();		// when clicking button, stop counting down
        Button _clickedButton= ((Button) _onClick.getSource());
        String _correctAnswer = _questionContent.getans();
        String _userAnswer = _clickedButton.getText();
        if(_userAnswer.equals(_correctAnswer)) {
			_clickedButton.setStyle("-fx-background-color: green; -fx-background-radius: 20; -fx-text-fill: white");
	        try {
	        	Parent _noticeFXML = FXMLLoader.load(getClass().getClassLoader().getResource("views/_correctAnswerNoticeLayout.fxml"));
	        	_showResultStage = new Stage();
	        	_showResultStage.setScene(new Scene(_noticeFXML));
	        	_showResultStage.setResizable(false);
	        	_showResultStage.initStyle(StageStyle.UNDECORATED);
		        PauseTransition _delay = new PauseTransition(Duration.seconds(1));
		        _delay.setOnFinished(event ->{
		        		_showResultStage.show();
				        Event _event = new WindowsTransferEvent(this, ProgramManager.RootStage(), WindowsTransferEvent.TransferToMain);
				    	Event.fireEvent(ProgramManager.RootStage(), _event);
					}
				);
		        _delay.play();		       
	        } catch(Exception e) {
	        	e.printStackTrace();
	        }
	        
	        // sucess, open the application
	        LockerTimer.removeApp(ProgramManager.NowAccess.GetProcessName());
	        
	        try {
	        	Runtime.getRuntime().exec(ProgramManager.NowAccess.GetExecutePath());
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	        ProgramManager.NowAccess = null;
		}
		else {
			_clickedButton.setStyle("-fx-background-color: red; -fx-background-radius: 20; -fx-text-fill: white");
	        try {
	        	Parent _noticeFXML = FXMLLoader.load(getClass().getClassLoader().getResource("views/_wrongAnswerNoticeLayout.fxml"));	        
	        	_showResultStage = new Stage();
		        _showResultStage.setScene(new Scene(_noticeFXML));
		        _showResultStage.setResizable(false);
		        _showResultStage.initStyle(StageStyle.UNDECORATED);
		        PauseTransition _delay = new PauseTransition(Duration.seconds(1));
		        _delay.setOnFinished(event ->{
		        		_showResultStage.show();
				        Event _event = new WindowsTransferEvent(this, ProgramManager.RootStage(), WindowsTransferEvent.TransferToMain);
				    	Event.fireEvent(ProgramManager.RootStage(), _event);
					}
				);
				_delay.play();
	        } catch(Exception e) {
	        	e.printStackTrace();
	        }
	        
	        // fail, add 10 minutes time to lock the application
	        LockerTimer.setAddOneHour(ProgramManager.NowAccess.GetProcessName());
	        ProgramManager.NowAccess = null;
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		// set the label, button contents
		_question.setText(_questionContent.getqn());
		_remainTime.setText(String.valueOf(_countdown));
		_btn_a.setText(_questionContent.getA());
		_btn_b.setText(_questionContent.getB());
		_btn_c.setText(_questionContent.getC());
		_btn_d.setText(_questionContent.getD());
		
		updateRemainTime();
	}

}