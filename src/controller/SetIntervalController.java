package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SetIntervalController implements Initializable {
    private double _x, _y;
    @FXML
    private Button _closeButton;
    @FXML
    private DatePicker _fromDatePicker;
    @FXML
    private DatePicker _toDatePicker;
    @FXML
    private ChoiceBox _fromTimePicker;
    @FXML
    private ChoiceBox _toTimePicker;
    
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
    public void Submit(MouseEvent event) {
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	if (_fromDatePicker.getValue() == null)
    		return;
    	if (_toDatePicker.getValue() == null)
    		return;
    	if (_fromTimePicker.getSelectionModel().isEmpty())
    		return;
    	if (_toTimePicker.getSelectionModel().isEmpty())
    		return;
    	
    	stage.close();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		_fromTimePicker.setItems(FXCollections.observableArrayList(
			    "00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00",
			    "00:70", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00",
			    "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00",
			    "21:00", "22:00", "23:00")
			);
		_toTimePicker.setItems(FXCollections.observableArrayList(
			    "00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00",
			    "00:70", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00",
			    "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00",
			    "21:00", "22:00", "23:00")
			);
	}

}
