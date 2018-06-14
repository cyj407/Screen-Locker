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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
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
	private MediaView _logo;

	@FXML
	public void Draged(MouseEvent _event) {
		Stage _stage = (Stage) ((Node) _event.getSource()).getScene().getWindow();
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
		Stage _stage = (Stage) ((Node) _event.getSource()).getScene().getWindow();
		_stage.close();
	}

	@FXML
	public void Shrink(MouseEvent _event) {
		Stage _stage = (Stage) ((Node) _event.getSource()).getScene().getWindow();
		_stage.setIconified(true);
	}

	@Override
	public void initialize(URL _arg0, ResourceBundle _arg1) {
		MediaPlayer _player = new MediaPlayer( new Media(this.getClass().getResource("/_logoAnimeSlow.mp4").toExternalForm()));
        _logo.setMediaPlayer(_player);
        _player.setCycleCount(MediaPlayer.INDEFINITE);
        _player.play();
        
		Loader.GetInstance();
		_progressBar.progressProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				_loadingText.setText(Loader.GetInstance().LoadStatus());
				if (newValue.intValue() == 1) {
					Stage _stage = (Stage) _progressBar.getScene().getWindow();
					Event _event = new WindowsTransferEvent(this, _stage, WindowsTransferEvent.TransferToMain);
					_progressBar.fireEvent(_event);

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
				double perc = Loader.GetInstance().LoadProgressPercentage();
				while (perc < 1) {
					updateProgress(perc, 1);
					perc = Loader.GetInstance().LoadProgressPercentage();
				}
				updateProgress(perc, 1);
				return true;
			}
		};
	}

}
