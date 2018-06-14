package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import com.sun.javafx.stage.StageHelper;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import net.sf.image4j.codec.ico.ICODecoder;
import screenLocker.Application;
import screenLocker.LockerTimer;
import screenLocker.ProgramManager;
import screenLocker.loader.Loader;

public class MainController implements Initializable{
	private double _x, _y;
    @FXML
    private Button _shrinkButton;
    @FXML
    private Button _enlargeButton;
    @FXML
    private Button _closeButton;
    @FXML
    private ListView<Application> _appListView;
    private Stage _enterQStage;
	
	static class AppCell extends ListCell<Application> {
        private HBox _pane;
        private ImageView _icon;
        private Application _lastItem;
        private Text _time;
        private Text _appName;

        public AppCell() {
            super();
            _pane = new HBox();
            _icon = new ImageView();
            _time = new Text();
            _appName = new Text();
            _appName.setStyle("-fx-fill: white;");
            _appName.setWrappingWidth(130);
            _time.setStyle("-fx-fill: red;");
            _time.setWrappingWidth(56);
            _time.setTextAlignment(TextAlignment.RIGHT);
            _pane.getChildren().addAll(_icon, _appName,_time);
            _pane.setStyle("-fx-padding: 0px 0px 0px 10px;");
            _pane.setAlignment(Pos.CENTER_LEFT);
            _pane.setMinWidth(220);
            _pane.setMinHeight(35);
            setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
        }
        
        public void SetHoverStyle() {
        	_appName.setStyle("-fx-fill: #383838;");
        	_pane.setStyle("-fx-padding: 0px 0px 0px 10px;" + "-fx-background-color: #fefefe;" +  "-fx-background-radius: 16px;" + "-fx-cursor: hand;");
        }
        public void SetUnhoverStyle() {
        	_appName.setStyle("-fx-fill: #f2f4f4;");
        	_pane.setStyle("-fx-padding: 0px 0px 0px 10px;" + "-fx-background-color: rgba(0, 0, 0, 0);" + "-fx-background-radius: 16px;");
        }
        
        @Override
        protected void updateItem(Application _item, boolean _empty) {
            super.updateItem(_item, _empty);
            setText(null);
            if (_empty) {
                _lastItem = null;
                setGraphic(null);
            } else {
                _lastItem = _item;
                if (_item.GetDisplayName().length() > 18)
                	_appName.setText("  " + _item.GetDisplayName().substring(0, 18) + "...");
                else
                	_appName.setText("  " + _item.GetDisplayName());
                for(String _iter : LockerTimer.BlackList()) {
                	if (_iter.equals(_lastItem.GetProcessName())) {
                		//LockerTimer _timer = new LockerTimer();
                		int _timeValue = LockerTimer.getTime(_lastItem.GetProcessName());                  
                		_time.setText(Integer.toString(_timeValue / 3600) + ":" + Integer.toString((_timeValue % 3600) / 60) + ":" + Integer.toString(_timeValue % 60));
                	}
                }                     
                try {
                	if (_lastItem.GetIconPath() != null && _lastItem.GetIconPath() != "" ) {
		                if (_lastItem.GetIconPath().indexOf(".ico") < 0) {
		                	// get the exe icon file.
		                	File _file = new File(_lastItem.GetIconPath());
		                    sun.awt.shell.ShellFolder _sf =
		                            sun.awt.shell.ShellFolder.getShellFolder(_file);
		                    javax.swing.Icon _iconImage = new ImageIcon(_sf.getIcon(true));
		                    BufferedImage _bufferedImage = new BufferedImage(_iconImage.getIconWidth(), _iconImage.getIconHeight(),
		                    		BufferedImage.TYPE_INT_ARGB);
		                    _iconImage.paintIcon(null, _bufferedImage.getGraphics(), 0, 0);
		                    _icon.setImage(SwingFXUtils.toFXImage(_bufferedImage, null));
		                } else {
		                	File _file = new File(_lastItem.GetIconPath());
		                    List<BufferedImage> _images = ICODecoder.read(_file);
		                    for(BufferedImage _iter : _images) {
		                    	if (_iter.getWidth() > 24 && _iter.getWidth() < 48) {
		                    		_icon.setImage(SwingFXUtils.toFXImage(_iter, null));
		                    		break;
		                    	}
		                    }
		                }
                	} else {
	                	// get the default exe icon file.
	                	File _file = new File(this.getClass().getResource("/images/_iconExe.ico").getPath());
	                	List<BufferedImage> _images = ICODecoder.read(_file);
	                    for(BufferedImage _iter : _images) {
	                    	if (_iter.getWidth() > 24 && _iter.getWidth() < 48) {
	                    		_icon.setImage(SwingFXUtils.toFXImage(_iter, null));
	                    		break;
	                    	}
	                    }
                	}
                } catch (Exception e) {
                	//System.out.println(e.getMessage());
                	// get the default exe icon file.
                	File _file = new File(this.getClass().getResource("/images/_iconExe.ico").getPath());
                	List<BufferedImage> _images;
					try {
						_images = ICODecoder.read(_file);
						for(BufferedImage _iter : _images) {
	                    	if (_iter.getWidth() > 24 && _iter.getWidth() < 48) {
	                    		_icon.setImage(SwingFXUtils.toFXImage(_iter, null));
	                    		break;
	                    	}
	                    }
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                    
                }
                setGraphic(_pane);
            }
        }

		public Application GetApplication() {
			return _lastItem;
		}
    }
    
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
    	if(_enterQStage != null)
    		_enterQStage.close();
    	ObservableList<Stage> _stage = StageHelper.getStages();
    	// get all existing stages  	
    	for(int i = 0;i<_stage.size();++i)
			if(_stage.get(i).isShowing())
				_stage.get(i).close();
    			// close all showing windows

    }

    @FXML
    public void Shrink(MouseEvent event) {
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	stage.setIconified(true);
    }
    @FXML
    public void ToGUISetting(ActionEvent event) {
		// switch to main scene.
		Stage _stage = (Stage) _shrinkButton.getScene().getWindow();
		Event _event = new WindowsTransferEvent(this, _stage, WindowsTransferEvent.TransferToSetting);
		_shrinkButton.fireEvent(_event);
    }
    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		ArrayList<Application> _appList = new ArrayList<Application>();
		_appList.clear();
		for(String _iter : LockerTimer.BlackList()) {
			Loader.GetInstance();
			for(Application _appIter : Loader.GetApplication()) {
				if (_iter.equals(_appIter.GetProcessName())) {
					_appList.add(_appIter);
				}
			}
		}
        ObservableList<Application> _observableList = FXCollections.observableArrayList(_appList);
        _appListView.setItems(_observableList);
        _appListView.setCellFactory(new Callback<ListView<Application>, ListCell<Application>>() {
            @Override
            public ListCell<Application> call(ListView<Application> param) {
            	AppCell _cell = new AppCell();
            	_cell.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                    	_cell.SetHoverStyle();
                    }
                });
            	_cell.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                    	_cell.SetUnhoverStyle();
                    }
                });
            	_cell.setOnMouseClicked(new EventHandler<MouseEvent>() {

        			@Override
        			public void handle(MouseEvent event) {
        				if (_cell.GetApplication() != null) {
        					System.out.println(_cell.GetApplication().GetDisplayName());
        					ProgramManager.NowAccess = _cell.GetApplication();
        					_enterQStage = new Stage();
        					Parent parent;
        					try {
        						FXMLLoader _loader = new FXMLLoader(getClass().getResource("/views/_questionEntranceLayout.fxml"));
        						parent = (Parent) _loader.load();
        						Scene scene = new Scene(parent);
        						_enterQStage.initStyle(StageStyle.UNDECORATED);
        						_enterQStage.setScene(scene);
        						_enterQStage.setResizable(false);
        						_enterQStage.show();
        					} catch (IOException e1) {
        						// TODO Auto-generated catch block
        						e1.printStackTrace();
        					}
        				}

        			}
                	
                });
            	return _cell;
            }
        });
        
    Timeline _time = new Timeline();
		KeyFrame _cycle= new KeyFrame(Duration.seconds(0.5), new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent event) {
				_appListView.refresh();
				for(Application _iter : _appListView.getItems()) {
					int _timeValue = LockerTimer.getTime(_iter.GetProcessName());      
             		if(_timeValue == -1) {
	                	ArrayList<Application> _appList = new ArrayList<Application>();
	                	_appList.clear();
	                	for(String _iter2 : LockerTimer.BlackList()) {
	                		Loader.GetInstance();
	                		for(Application _appIter : Loader.GetApplication()) {
	                			if (_iter2.equals(_appIter.GetProcessName())) {
	                				_appList.add(_appIter);
	                			}
	                		}
	                	}
	                    ObservableList<Application> _observableList = FXCollections.observableArrayList(_appList);
	                    _appListView.setItems(_observableList);
             		}
				} 
			}		
		});
		_time.setCycleCount(Timeline.INDEFINITE);
		_time.getKeyFrames().add(_cycle);
		_time.play();
        
        
	}

}
