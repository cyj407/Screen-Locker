package screenLocker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import screenLocker.autoOpen.ProcessListener;
import screenLocker.autoOpen.RMIServer;
import screenLocker.autoOpen.ReOpen;
import screenLocker.loader.Loader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Timer;

import controller.DefaultController;
import controller.LoadingController;
import controller.MainController;
import controller.SettingController;
import controller.WindowsTransferEvent;
import java.util.Timer;

public class ProgramManager extends Application {
	private static Stage _rootStage;
	private static ProcessListener _pListen = null;
	private final static String _deli = Loader.IsLinux() ? "/" : "\\";
	public static RMIServer rmiServer = RMIServer.StartServer();
	//public static LockerTimer lockerTimer = new LockerTimer();

	public static void leave() {
		_pListen.close();
		rmiServer.CloseServer();
	}
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
		
		Timer _timer = new Timer();
		_timer.schedule(new LockerTimer(), 0, 1000);

		// -------------------- f26401004's section -----------------------//

		// ------------------------ yiiju's section -----------------------//
		_pListen = new ProcessListener();
		_pListen.start();

		// ----------------------- afcidk's section -----------------------//
		// IMPORTANT!! Must be placed before launch(args)
		/*
		try {
			ReOpen.openReOpen("screenLocker.ProgramManager", _myDir);
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/

		// ----------------------- cyj407's section -----------------------//
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
		// TODO: set all event for different scene transfer.
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
	
	public void changeScene(String fxml){
	    Parent pane;
		try {
			pane = FXMLLoader.load(
			       getClass().getResource(fxml));
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
			changeScene("/views/_settingLayout.fxml");
		});
		_rootStage.addEventHandler(WindowsTransferEvent.TransferToQuestion, e -> {
			changeScene("/views/_questionLayout.fxml");
		});
	}

}
