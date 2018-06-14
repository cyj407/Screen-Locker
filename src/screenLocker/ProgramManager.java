package screenLocker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import screenLocker.autoOpen.ProcessListener;
import screenLocker.autoOpen.RMIServer;
import screenLocker.autoOpen.ReOpen;
import screenLocker.loader.LinuxLoader;
import screenLocker.loader.Loader;

import java.io.IOException;
import java.util.Timer;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import static com.sun.jna.platform.win32.WinUser.GWL_STYLE;
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
		
		Thread t = new Thread(Loader.GetInstance());
		t.start();
		
		try {
			ReOpen.openReOpen("screenLocker.ProgramManager", _myDir);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
		_rootStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/images/_icon.png")));
		_rootStage.setTitle("ScreenLocker");
		_addTransferListener();
		// initantiate loading scene.
		FXMLLoader _loading = new FXMLLoader(
				this.getClass().getResource("/views/_loadingLayout.fxml"));
		_rootStage.setScene(new Scene(_loading.load()));
		// set the current scene.
		_rootStage.show();
		if (Loader.IsWindows()) {
			// reference the stage address.
	        long lhwnd = com.sun.glass.ui.Window.getWindows().get(0).getNativeWindow();
	        Pointer lpVoid = new Pointer(lhwnd);
	        HWND hwnd = new HWND(lpVoid);
	        // use Windows User32.dll API to set the style forcelly.
	        final User32 user32 = User32.INSTANCE;
	        int oldStyle = user32.GetWindowLong(hwnd, GWL_STYLE);
	        int newStyle = oldStyle | 0x00020000;//WS_MINIMIZEBOX
	        user32.SetWindowLong(hwnd, GWL_STYLE, newStyle);
		}
	}
	
	public void stop() {
		_timer.cancel();
		_pListen.close();
		rmiServer.CloseServer();
		LockerTimer.updateFile();
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
