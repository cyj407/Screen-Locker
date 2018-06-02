package screenLocker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sceneController.DefaultController;

import java.awt.Toolkit;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import autoOpen.RMIServer;
import autoOpen.ReOpen;

public class ProgramManager extends Application {
	private Stage _rootStage;
	private GUI _guiMain;
	private GUI _guiQuestion;
	private GUI _guiSetting;
	private GUI _guiLoading;
	private GUI _activeGui;
	public static void main(String[] args) {

		/********************* f26401004's section ***********************/
		
		/************************* yiiju's section ***********************/

		/*********************** afcidk's section ************************/
		/*
		String myDir = System.getProperty("user.dir");
		if (!myDir.substring(myDir.length() - 4).equals("/bin")) {
			myDir += "/bin";
		}

		RMIServer.StartServer();
		try {
			ReOpen.openReOpen("screenLocker.ProgramManager", _myDir);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 */
		/************************ cyj407's section ***********************/
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
		// initantiate all scene.
		_instantiateScene();
		// setup the first scene to loading scene.
		_rootStage.setScene(_guiLoading);
		// set the current scene.
		_activeGui = _guiLoading;
		_rootStage.show();
	}
	
	private void _instantiateScene() {
		// initialize loading scene.
		try {
			FXMLLoader _fxmlLoader = new FXMLLoader(
					this.getClass().getResource("/assets/fxmls/default.fxml"));
			_fxmlLoader.setController(new DefaultController());
			_guiLoading = new GUILoading(_fxmlLoader.load());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		// initialize main scene.
		try {
			FXMLLoader _fxmlLoader = new FXMLLoader(
					this.getClass().getResource("/assets/fxmls/default.fxml"));
			_fxmlLoader.setController(new DefaultController());
			_guiMain = new GUILoading(_fxmlLoader.load());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		// initialize setting scene.
		try {
			FXMLLoader _fxmlLoader = new FXMLLoader(
					this.getClass().getResource("/assets/fxmls/default.fxml"));
			_fxmlLoader.setController(new DefaultController());
			_guiSetting = new GUILoading(_fxmlLoader.load());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		// initialize question scene.
		try {
			FXMLLoader _fxmlLoader = new FXMLLoader(
					this.getClass().getResource("/assets/fxmls/default.fxml"));
			_fxmlLoader.setController(new DefaultController());
			_guiQuestion = new GUILoading(_fxmlLoader.load());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
