package screenLocker;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import screenLocker.autoOpen.ProcessListener;
import screenLocker.autoOpen.RMIServer;
import screenLocker.autoOpen.ReOpen;
import screenLocker.loader.Loader;

import java.io.IOException;
import java.util.Timer;

import com.sun.javafx.stage.StageHelper;

import controller.WindowsTransferEvent;

public class ProgramManager extends Application {
	private static Stage _rootStage;
	private static ProcessListener _pListen = null;
	private static Timer _timer;
	private final static String _deli = Loader.IsLinux() ? "/" : "\\";
	public static RMIServer rmiServer = RMIServer.StartServer();
	public static screenLocker.Application NowAccess;

	public static Stage RootStage() {
		return _rootStage;
	}

	public static void main(String[] args) {
		
		// ------------------must be placed at the first---------------------//
		String _myDir = System.getProperty("user.dir");
		if (!_myDir.substring(_myDir.length() - 4).equals(_deli+"bin")) {
			_myDir += _deli+"bin";
		}
		System.setProperty("user.dir", _myDir);
		// ------------------------------------------------------------------//
		
		_timer = new Timer();
		_timer.schedule(new LockerTimer(), 0, 1000);


		_pListen = new ProcessListener();
		_pListen.start();

		/*
		try {
			ReOpen.openReOpen("screenLocker.ProgramManager", _myDir);
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/

		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// initialize the stage.
		_rootStage = primaryStage;
		_rootStage.setResizable(false);
		_rootStage.setFullScreen(false);
		_rootStage.initStyle(StageStyle.UNDECORATED);
		_rootStage.setWidth(800);
		_rootStage.setHeight(548);
		_addTransferListener();
		// initantiate loading scene.
		FXMLLoader _loading = new FXMLLoader(
				this.getClass().getResource("/views/_loadingLayout.fxml"));
		_rootStage.setScene(new Scene(_loading.load()));
		// set the current scene.
		_rootStage.show();
		// Loader start load application
		Loader.GetInstance().LoadApplication();

	}
	
	public void stop() {
		_timer.cancel();
		_pListen.close();
		rmiServer.CloseServer();
	}
	
	public void changeScene(String fxml){
	    Parent pane;
		try {
			pane = FXMLLoader.load(getClass().getResource(fxml));
			_rootStage.getScene().setRoot(pane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void _addTransferListener() {
		_rootStage.addEventHandler(WindowsTransferEvent.TransferToMain, e -> {
			changeScene("/views/_mainLayout.fxml");
		});
		_rootStage.addEventHandler(WindowsTransferEvent.TransferToSetting, e -> {
			LockerTimer.updateFile();
			changeScene("/views/_settingLayout.fxml");
		});
		_rootStage.addEventHandler(WindowsTransferEvent.TransferToQuestion, e -> {
			changeScene("/views/_questionLayout.fxml");
		});
	}

}
