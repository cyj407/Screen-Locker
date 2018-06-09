package screenLocker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
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
import java.io.IOException;
import java.net.URL;

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
//	private Scene _activeGui;
	public static RMIServer rmiServer = RMIServer.StartServer();

	public static void main(String[] args) {

		

		//-------------------- f26401004's section -----------------------//
		
		//------------------------ yiiju's section -----------------------//

		Thread thread = new ProcessListener();
		thread.start();
		
		//----------------------- afcidk's section -----------------------//
		//IMPORTANT!! Must be placed after launch(args)

		String _myDir = System.getProperty("user.dir");
		if (!_myDir.substring(_myDir.length() - 4).equals("/bin")) {
			_myDir += "/bin";
		}

		try {
			ReOpen.openReOpen("screenLocker.ProgramManager", _myDir);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("here");
		launch(args);

		//----------------------- cyj407's section -----------------------//
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
	//	_activeGui = _guiLoading;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void _instantiateScene() throws IOException {
		FXMLLoader _loading = new FXMLLoader(
				this.getClass().getResource("/views/_loadingLayout.fxml"));
		_guiLoading = new Scene(_loading.load());
	}
	
	private void _addTransferListener() {
		_rootStage.addEventHandler(WindowsTransferEvent.TransferToMain, e -> {
			changeScene("/views/_mainLayout.fxml");
		});
		_rootStage.addEventHandler(WindowsTransferEvent.TransferToSetting, e -> {
			changeScene("/views/_SettingLayout.fxml");
		});
		_rootStage.addEventHandler(WindowsTransferEvent.TransferToQuestion, e -> {
			changeScene("/views/_questionLayout.fxml");
		});
	}

}
