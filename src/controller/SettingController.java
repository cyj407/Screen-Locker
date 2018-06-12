package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import net.sf.image4j.codec.ico.ICODecoder;
import screenLocker.Application;
import screenLocker.LockerTimer;
import screenLocker.ProgramManager;
import screenLocker.loader.Loader;

public class SettingController implements Initializable {

	static class AppCell extends ListCell<Application> {
		private HBox _pane;
        private ImageView _icon;
        private ImageView _lockImage;
        private Application _lastItem;
        private Text _appName;

        public AppCell() {
            super();
            _pane = new HBox();
            _icon = new ImageView();
            _lockImage = new ImageView();
            _appName = new Text();
            _appName.setStyle("-fx-fill: white;");
            _appName.setWrappingWidth(160);
            _pane.getChildren().addAll(_icon, _appName, _lockImage);
            _pane.setStyle("-fx-padding: 0px 0px 0px 10px;");
            _pane.setAlignment(Pos.CENTER_LEFT);
            _pane.setMinWidth(220);
            getStylesheets().add(this.getClass().getResource("/stylesheets/_appListView.css").toExternalForm());
        }
        
        public void SetHoverStyle() {
        	_appName.setStyle("-fx-fill: #383838;");
        }
        public void SetUnhoverStyle() {
        	_appName.setStyle("-fx-fill: #f2f4f4;");
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
                if (LockerTimer.BlackList().contains(_item.GetProcessName()))
                	_lockImage.setImage(new Image(this.getClass().getResourceAsStream("/images/_iconListening.png")));
                else
                	_lockImage.setImage(null);
                if (_item.GetDisplayName().length() > 18)
                	_appName.setText("  " + _item.GetDisplayName().substring(0, 18) + "...");
                else
                	_appName.setText("  " + _item.GetDisplayName());
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
                	System.out.println(e.getMessage());
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
    }
	
	static public class TimerEntry {
	    private int _number;
	    private int _time;

	    public TimerEntry(int _numberValue, int _timeValue) {
	        _number = _numberValue;
	        _time = _timeValue;
	    }

	    public int getNumber() {
	        return _number;
	    }

	    public int getTime() {
	        return _time;
	    }
	}
	
	
    private double _x, _y;
    private ArrayList<Application> _currentList;
    private Stage _setIntervalStage;
    private SetIntervalController _setIntevalController;
    @FXML
    private Button _shrinkButton;
    @FXML
    private Button _enlargeButton;
    @FXML
    private Button _closeButton;
    @FXML
    private Button _returnButton;
    @FXML
    private GridPane _rightItems;
    @FXML
    private ListView<Application> _appListView;
    @FXML
    private Text _appName;
    @FXML
    private Text _appLastUsed;
    @FXML
    private Text _appStatus;
    @FXML
    private ImageView _appIcon;
    @FXML
    private TableView<TimerEntry> _timerTable;
    @FXML
    private TextField _searchTextField;
    
    public void RefreshView() {
    	_appListView.refresh();
    	Application _target = (Application)_appListView.getSelectionModel().getSelectedItem();
    	_timerTable.getItems().clear();
    	try {
    		LockerTimer _timer = new LockerTimer();
        	if(!LockerTimer.BlackList().contains(_target.GetProcessName())) {
        		return;
        	}
			if (_timer.getTime(_target.GetProcessName()) == -1) {
				return;
			}
			int _timeValue = _timer.getTime(_target.GetProcessName());
			_timerTable.getItems().add(new TimerEntry(1, _timeValue / 3600));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
	@FXML
    public void Draged(MouseEvent event) {
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	stage.setX(event.getScreenX() - _x);
    	stage.setY(event.getScreenY() - _y);
    	_setIntervalStage.close();
    }
    
	@FXML
	public void Pressed(MouseEvent event) {
		_x = event.getSceneX();
		_y = event.getSceneY();
		_setIntervalStage.close();
	}

	@FXML
	public void Close(MouseEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		_setIntervalStage.close();
	}

	@FXML
	public void Shrink(MouseEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setIconified(true);
		_setIntervalStage.close();
	}

	@FXML
	public void ToGUIMain(ActionEvent event) {
		// switch to main scene.
        Event _event = new WindowsTransferEvent(this, ProgramManager.RootStage(), WindowsTransferEvent.TransferToMain);
    	Event.fireEvent(ProgramManager.RootStage(), _event);
    	_setIntervalStage.close();
	}
    
    @FXML
    public void ShowSetIntervalStage(ActionEvent event) {
		_setIntevalController.SetApplication((Application)_appListView.getSelectionModel().getSelectedItem());
        _setIntevalController.SetParentController(this);
	    _setIntervalStage.show();
    }
    
    @FXML
    public void SearchApplication(KeyEvent _event) {
    	if (_searchTextField.getText() != "") {
    		_currentList.clear();
    		Loader.GetInstance();
			for(Application _iter : Loader.GetApplication()) {
    			if (_iter.GetDisplayName().toLowerCase().contains(_searchTextField.getText().toLowerCase())) {
    				_currentList.add(_iter);
    			}
    		}
            ObservableList<Application> _observableList = FXCollections.observableArrayList(_currentList);
            _appListView.setItems(_observableList);
    	}
    	_setIntervalStage.close();
    }
    
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			// load the set interval stage.
			FXMLLoader _fxmlLoader = new FXMLLoader(this.getClass().getClassLoader().getResource("views/_setIntervalLayout.fxml"));
			Parent _intervalFXML = _fxmlLoader.load();
			_setIntevalController = (SetIntervalController)_fxmlLoader.getController();
			_setIntervalStage = new Stage();
	        _setIntervalStage.setScene(new Scene(_intervalFXML));
	        _setIntervalStage.initStyle(StageStyle.UNDECORATED);
	        _setIntervalStage.setResizable(false); 
	        
		} catch (Exception e) {
			
		}
	
		System.out.println(_timerTable.getItems().getClass());
		
		// set timer table view column factory.
		TableColumn _numberColumn = (TableColumn)_timerTable.getColumns().get(0);
		_numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
		TableColumn _timeColumn = (TableColumn)_timerTable.getColumns().get(1);
		_timeColumn.setCellValueFactory(new PropertyValueFactory("time"));
		
		_rightItems.setVisible(false);
		_currentList = new ArrayList<Application>();
		Loader.GetInstance();
		for (Application _iter : Loader.GetApplication()) {
			_currentList.add(_iter);
		}
		ObservableList<Application> _observableList = FXCollections.observableArrayList(_currentList);
		_appListView.setItems(_observableList);
		_appListView.setPrefHeight(35 * 8);
		_appListView.setCellFactory(new Callback<ListView<Application>, ListCell<Application>>() {
            @Override
            public ListCell<Application> call(ListView<Application> param) {
            	_setIntervalStage.close();
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
            	return _cell;

            }
		});

		// add mouse click handler
		_appListView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				_setIntervalStage.close();
				RefreshView();
				if (!_rightItems.visibleProperty().get())
					_rightItems.setVisible(true);
				Application _selected = (Application) _appListView.getSelectionModel().getSelectedItem();
				System.out.println(_selected.GetDisplayName());
				// render right items
				if (_selected.GetDisplayName().length() > 15) {
					_appName.setText("應用程式   " + _selected.GetDisplayName().substring(0, 15) + "...");
				} else {
					_appName.setText("應用程式   " + _selected.GetDisplayName());
				}
				// _appLastUsed.setText(_selected.Get);
				if (LockerTimer.BlackList().contains(_selected.GetProcessName())) {
					_appStatus.setText("目前狀態   鎖定中");
				} else {
					_appStatus.setText("目前狀態   可使用");
				}
				for (Application _appIter : Loader.GetApplication()) {
					if (_selected.GetDisplayName().equals(_appIter.GetDisplayName())) {
						try {
							if (_appIter.GetIconPath() != null && _appIter.GetIconPath() != "") {
								if (_appIter.GetIconPath().indexOf(".ico") < 0) {
									// get the exe icon file.
									File _file = new File(_appIter.GetIconPath());
									sun.awt.shell.ShellFolder _sf = sun.awt.shell.ShellFolder.getShellFolder(_file);
									javax.swing.Icon _iconImage = new ImageIcon(_sf.getIcon(true));
									BufferedImage _bufferedImage = new BufferedImage(_iconImage.getIconWidth(),
											_iconImage.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
									_iconImage.paintIcon(null, _bufferedImage.getGraphics(), 0, 0);
									_appIcon.setImage(SwingFXUtils.toFXImage(_bufferedImage, null));
								} else {
									File _file = new File(_appIter.GetIconPath());
									List<BufferedImage> _images = ICODecoder.read(_file);
									for (BufferedImage _iter : _images) {
										if (_iter.getWidth() > 36 && _iter.getWidth() < 76) {
											_appIcon.setImage(SwingFXUtils.toFXImage(_iter, null));
											break;
										}
									}
								}
							} else {
								// get the default exe icon file.
								File _file = new File(this.getClass().getResource("/images/_iconExe.ico").getPath());
								List<BufferedImage> _images = ICODecoder.read(_file);
								for (BufferedImage _iter : _images) {
									if (_iter.getWidth() > 24 && _iter.getWidth() < 48) {
										_appIcon.setImage(SwingFXUtils.toFXImage(_iter, null));
										break;
									}
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
        });
	}
}
