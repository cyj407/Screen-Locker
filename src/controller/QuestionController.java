package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
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
	
	private Stage _stage;
	private boolean _hasAnswered = false;
	
    private double _x, _y;
    
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
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
   // 	screenLocker.ProgramManager.rmiServer.CloseServer();
    	stage.close();
    }
    @FXML public void Shrink(MouseEvent event) {
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	stage.setIconified(true);
    }
	
	
	private void updateRemainTime() {
		Timeline _time = new Timeline();
		KeyFrame _cycle= new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent event) {
				_remainTime.setVisible(true);
				if(_hasAnswered)
					_time.stop();
				_countdown--;
				if(_countdown <= 10) {
					_remainTime.setStyle("-fx-text-fill: red");
				}
				_remainTime.setText(String.valueOf(_countdown));
			    if(_countdown < 0) {
			    	try {
			        	Parent _noticeFXML = FXMLLoader.load(getClass().getClassLoader().getResource("views/_timeoutNoticeLayout.fxml"));	        
			        	_stage = (Stage) _remainTime.getScene().getWindow();
			        	Stage _showTimeoutStage = new Stage();
				        _showTimeoutStage.setScene(new Scene(_noticeFXML));
				        _showTimeoutStage.initStyle(StageStyle.UNDECORATED);
				        _showTimeoutStage.setResizable(false); 
				        _showTimeoutStage.show();
						_stage.close();
			        } catch(Exception e) {
			        	e.printStackTrace();
			        }
			    	_time.stop();
			    
			    	// fail, add 10 minutes time to lock the application
			    }
			}		
		});
		_time.setCycleCount(Timeline.INDEFINITE);
		_time.getKeyFrames().add(_cycle);
		_time.play();
	}
	

	@FXML private void handleButtonClick(ActionEvent _onClick) {
		_hasAnswered = true;
        Button _clickedButton= ((Button) _onClick.getSource());
        String _correctAnswer = _questionContent.getans();
        String _userAnswer = _clickedButton.getText();
        _stage = (Stage) _clickedButton.getScene().getWindow();
        if(_userAnswer.equals(_correctAnswer)) {
			_clickedButton.setStyle("-fx-background-color: red; -fx-background-radius: 20; -fx-text-fill: white");
	        try {
	        	Parent _noticeFXML = FXMLLoader.load(getClass().getClassLoader().getResource("views/_correctAnswerNoticeLayout.fxml"));
		        Stage _showCorrectStage = new Stage();
		        _showCorrectStage.setScene(new Scene(_noticeFXML));
		        _showCorrectStage.setResizable(false);
		        _showCorrectStage.initStyle(StageStyle.UNDECORATED);
		        PauseTransition _delay = new PauseTransition(Duration.seconds(1));
		        _delay.setOnFinished(event ->{
						_showCorrectStage.show();
						_stage.close();
					}
				);
		        _delay.play();		       
	        } catch(Exception e) {
	        	e.printStackTrace();
	        }
	        
	        // sucess, open the application
		}
		else {
			_clickedButton.setStyle("-fx-background-color: green; -fx-background-radius: 20; -fx-text-fill: white");
	        try {
	        	Parent _noticeFXML = FXMLLoader.load(getClass().getClassLoader().getResource("views/_wrongAnswerNoticeLayout.fxml"));	        
		        Stage _showWrongStage = new Stage();
		        _showWrongStage.setScene(new Scene(_noticeFXML));
		        _showWrongStage.setResizable(false);
		        _showWrongStage.initStyle(StageStyle.UNDECORATED);
		        PauseTransition _delay = new PauseTransition(Duration.seconds(1));
		        _delay.setOnFinished(event ->{
						_showWrongStage.show();
						_stage.close();
					}
				);
				_delay.play();
	        } catch(Exception e) {
	        	e.printStackTrace();
	        }
	        
	        // fail, add 10 minutes time to lock the application
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