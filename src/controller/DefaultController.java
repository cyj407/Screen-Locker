package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class DefaultController implements Initializable {
    private double _x, _y;
    @FXML
    private Button _shrinkButton;
    private Button _enlargeButton;
    private Button _closeButton;
    
	@FXML
    public void Draged(MouseEvent event) {
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	stage.setX(event.getScreenX() - _x);
    	stage.setY(event.getScreenY() - _y);
    }

    @FXML
    public void Pressed(MouseEvent event) {
    	_x = event.getSceneX();
    	_y = event.getSceneY();
    }
    
    @FXML
    public void Close(MouseEvent event) {
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	stage.close();
    }
    @FXML
    public void Shrink(MouseEvent event) {
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	stage.setIconified(true);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
