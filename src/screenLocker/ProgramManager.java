package screenLocker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import screenLocker.autoOpen.ProcessListener;
import screenLocker.autoOpen.RMIServer;
import screenLocker.autoOpen.ReOpen;
//import screenLocker.gui.GUI;
//import screenLocker.gui.GUILoading;
import screenLocker.loader.Loader;

import java.awt.Toolkit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.util.Timer;

import controller.DefaultController;
import controller.LoadingController;
import controller.MainController;
import controller.SettingController;
import controller.WindowsTransferEvent;

public class ProgramManager extends Application {
	private Stage _rootStage;
	private Scene _guiMain;
	private Scene _guiQuestion;
	private Scene _guiSetting;
	private Scene _guiLoading;
	private Scene _activeGui;
	private static ProcessListener _pListen = null;
	public static RMIServer rmiServer = RMIServer.StartServer();

	public static void leave() {
		_pListen.close();
		// TODO: will freeze when close the second time
		// will stuck in the inf loop in CloseServer, but the inf loop is to ensure the server really unbounded
		rmiServer.CloseServer();
	}

	public static void main(String[] args) {
		// --------example of MyTimer and ProcessListener------------------//
		LockerTimer timer;
		try {
			timer = new LockerTimer();
			timer.setTime("abc", 1);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		_pListen = new ProcessListener();
		_pListen.start();

		// -------------------- f26401004's section -----------------------//

		// ------------------------ yiiju's section -----------------------//

		// ----------------------- afcidk's section -----------------------//
		// IMPORTANT!! Must be placed before launch(args)
		String _myDir = System.getProperty("user.dir");
		if (!_myDir.substring(_myDir.length() - 4).equals("/bin")) {
			_myDir += "/bin";
		}

		try {
			ReOpen.openReOpen("screenLocker.ProgramManager", _myDir);
		} catch (Exception e) {
			e.printStackTrace();
		}

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
		// initantiate all scene.
		_instantiateScene();
		// setup the first scene to loading scene.
		_rootStage.setScene(_guiLoading);
		// set the current scene.
		_activeGui = _guiLoading;
		_rootStage.show();
		// Loader start load application
		Loader.GetInstance().LoadApplication();
	}

	private void _instantiateScene() {
		// initialize loading scene.
		try {
			FXMLLoader _fxmlLoader = new FXMLLoader(this.getClass().getResource("/views/_loadingLayout.fxml"));
			_fxmlLoader.setController(new LoadingController());
			_guiLoading = new Scene(_fxmlLoader.load());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		// initialize main scene.
		try {
			FXMLLoader _fxmlLoader = new FXMLLoader(this.getClass().getResource("/views/_mainLayout.fxml"));
			_fxmlLoader.setController(new MainController());
			_guiMain = new Scene(_fxmlLoader.load());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		// initialize setting scene.
		try {
			FXMLLoader _fxmlLoader = new FXMLLoader(this.getClass().getResource("/views/_settingLayout.fxml"));
			_fxmlLoader.setController(new SettingController());
			_guiSetting = new Scene(_fxmlLoader.load());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		// initialize question scene.
		try {
			FXMLLoader _fxmlLoader = new FXMLLoader(this.getClass().getResource("/views/_questionLayout.fxml"));
			_guiQuestion = new Scene(_fxmlLoader.load());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void _addTransferListener() {
		_rootStage.addEventHandler(WindowsTransferEvent.TransferToMain, e -> {
			_rootStage.setScene(_guiMain);
			_activeGui = _guiMain;
		});
		_rootStage.addEventHandler(WindowsTransferEvent.TransferToSetting, e -> {
			_rootStage.setScene(_guiSetting);
			_activeGui = _guiSetting;
		});
		_rootStage.addEventHandler(WindowsTransferEvent.TransferToQuestion, e -> {
			_rootStage.setScene(_guiQuestion);
			_activeGui = _guiQuestion;
		});
	}
}
