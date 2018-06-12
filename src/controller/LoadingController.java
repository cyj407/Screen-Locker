package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import screenLocker.loader.Loader;

public class LoadingController implements Initializable {
    private double _x, _y;
    @FXML
    private ProgressBar _progressBar;
    @FXML
    private Text _loadingText;
    
	@FXML
    public void Draged(MouseEvent _event) {
    	Stage _stage = (Stage)((Node)_event.getSource()).getScene().getWindow();
    	_stage.setX(_event.getScreenX() - _x);
    	_stage.setY(_event.getScreenY() - _y);
    }

    @FXML
    public void Pressed(MouseEvent _event) {
    	_x = _event.getSceneX();
    	_y = _event.getSceneY();
    }
    
    @FXML
    public void Close(MouseEvent _event) {
    	Stage _stage = (Stage)((Node)_event.getSource()).getScene().getWindow();
    	_stage.close();
    }
    @FXML
    public void Shrink(MouseEvent _event) {
    	Stage _stage = (Stage)((Node)_event.getSource()).getScene().getWindow();
    	_stage.setIconified(true);
    }

	@Override
	public void initialize(URL _arg0, ResourceBundle _arg1) {
		Loader.GetInstance();
		_progressBar.progressProperty().addListener(new ChangeListener<Number> (){
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				_loadingText.setText(Loader.GetInstance().LoadStatus());
				if (newValue.intValue() == 1) {
					try {
						Thread.sleep(1000);
						// switch to main scene.
						Stage _stage = (Stage) _progressBar.getScene().getWindow();
						Event _event = new WindowsTransferEvent(this, _stage, WindowsTransferEvent.TransferToMain);
						_progressBar.fireEvent(_event);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		});
		Task<Object> _task = _loadTask();
		_progressBar.progressProperty().bind(_task.progressProperty());
		new Thread(_task).start();
	}
	
	private Task<Object> _loadTask() {
		return new Task<Object>() {	
			@Override
			protected Object call() throws Exception {
				Loader.GetInstance();
				for(int i = 0 ; i < Loader.GetApplicationNumber() ; ++i) {
					int _randomTime = (int)(Math.random() * 0 + 10); //*150 + 50  // *0+10
					Loader.GetInstance().LoadApplication();
					try {
						Thread.sleep(_randomTime);
						updateProgress(Loader.GetInstance().LoadProgressPercentage(), 1);
					} catch (Exception _e) {
					}
				}
				return true;
			}
		};
	}

}
