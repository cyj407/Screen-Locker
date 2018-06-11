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
//import screenLocker.gui.GUI;
//import screenLocker.gui.GUILoading;
import screenLocker.loader.Loader;
import java.io.IOException;

import controller.DefaultController;
import controller.LoadingController;
import controller.MainController;
import controller.SettingController;
import controller.WindowsTransferEvent;

public class ProgramManager extends Application {
	private Stage _rootStage;
	private static ProcessListener _pListen = null;
	public static RMIServer rmiServer = RMIServer.StartServer();

	public static void leave() {
		_pListen.close();
		rmiServer.CloseServer();
	}

	public static void main(String[] args) {

		// -------------------- f26401004's section -----------------------//

		// ------------------------ yiiju's section -----------------------//
		_pListen = new ProcessListener();
		_pListen.start();

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
		// initantiate loading scene.
		FXMLLoader _loading = new FXMLLoader(
				this.getClass().getResource("/views/_loadingLayout.fxml"));
		_rootStage.setScene(new Scene(_loading.load()));
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
			
			boolean _enterQuestion;
			Stage _enterQStage = new Stage();
			Parent parent;
			try {
				parent = FXMLLoader.load(getClass().getResource("views/_questionEntranceLayout.fxml"));
				Scene scene = new Scene(parent);
				_enterQStage.initStyle(StageStyle.UNDECORATED);
				_enterQStage.setScene(scene);
				_enterQStage.setResizable(false);
				_enterQStage.show();
				
		//		if(_enterQStage._enter)
					changeScene("/views/_questionLayout.fxml");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
	}

}
