package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import screenLocker.ProgramManager;

public class EnterQuestionController implements Initializable{
    @FXML private Button _closeButton;
    @FXML private Button _enterQButton;
    
    @FXML public void Close(MouseEvent event) {
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	stage.close();
    }
    
    @FXML public void Entrance(ActionEvent _clickEntrance) {
    	Stage _stage = (Stage)((Node)_clickEntrance.getSource()).getScene().getWindow();
    	Event _event = new WindowsTransferEvent(this, ProgramManager.RootStage(), WindowsTransferEvent.TransferToQuestion);    	
    	Event.fireEvent(ProgramManager.RootStage(), _event);
        PauseTransition _delay = new PauseTransition(Duration.seconds(1));
        _delay.setOnFinished(event ->{
        	_stage.close();
		});
		_delay.play();
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	/*
	public boolean goToQuesiton(){
		return _enter;
	}
	*/
}
