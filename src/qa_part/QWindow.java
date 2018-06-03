package qa_part;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class QWindow extends Application {
	private Question _chosenQuestion = new Question();
	private GridPane _layout = new GridPane();
//	private Pane _layout = new Pane();
	private Button choice_a, choice_b, choice_c, choice_d;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage questionStage) throws Exception {
		questionStage.setTitle("Screen Locker");
		
		_layout.setAlignment(Pos.CENTER);
		_layout.setHgap(10);	// horizontal distance
		_layout.setVgap(10);	// vertical distance
		_layout.setPadding(new Insets(50, 50, 50, 50));		// fill the border
		
		Scene scene = new Scene(_layout, 800, 500);
		questionStage.setScene(scene);
		
		Text question = new Text(_chosenQuestion.getqn());
		question.setWrappingWidth(700); 
		  
		question.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        _layout.add(question, 0, 0, 2, 2);

        choice_a = new Button();
		choice_b = new Button();
		choice_c = new Button();
		choice_d = new Button();
		
		SetButtonFormat(choice_a);
		SetButtonFormat(choice_b);
		SetButtonFormat(choice_c);
		SetButtonFormat(choice_d);
		
		SetButtonContent();
		
	//	button.setWrapText(true);
		
		_layout.add(choice_a,1,2);
		_layout.add(choice_b,2,2);
		_layout.add(choice_c,1,3);
		_layout.add(choice_d,2,3);

		questionStage.show();
	}
	
	public void SetButtonFormat(Button btn) {
		btn.setMaxSize(150, 80);
		btn.setStyle("-fx-background-color: #FFFFFF;"
				+ "-fx-font-size: 1.5em;"
				+ "-fx-background-radius: 80;");
	//	btn.setPrefSize(120, 40);
	}
	
	public void SetButtonContent() {
		choice_a.setText("(A) " + _chosenQuestion.getA());
		choice_b.setText("(B) " + _chosenQuestion.getB());
		choice_c.setText("(C) " + _chosenQuestion.getC());
		choice_d.setText("(D) " + _chosenQuestion.getD());
	}
}
