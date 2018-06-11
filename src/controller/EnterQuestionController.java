package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class EnterQuestionController implements Initializable{
    @FXML private Button _closeButton;
    @FXML private Button _enterQButton;
    private boolean _enter;

    @FXML public boolean Close(MouseEvent event) {
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
   // 	screenLocker.ProgramManager.rmiServer.CloseServer();
    	stage.close();
    	_enter = false;
    	return _enter;
    }
    
    @FXML public boolean Entrance(MouseEvent _clickEntrance) {
    	Stage stage = (Stage)((Node)_clickEntrance.getSource()).getScene().getWindow();
   // 	screenLocker.ProgramManager.rmiServer.CloseServer();
        PauseTransition _delay = new PauseTransition(Duration.seconds(1));
        _delay.setOnFinished(event ->{
        	stage.close();
		});
		_delay.play();
		_enter = true;
		return _enter;
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
