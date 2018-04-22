import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUI extends Application{
	private static int teamCount = 16;
	private static int numMatches;
	private static Label[] teamNames;
	private static TextField[] scoreInputs;
	private static Button[] buttons;
	

	public static void main(String[] args) {
		for(int i = teamCount/2; i >= 1; i = i/2) {
			numMatches += teamCount;
		}
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		BorderPane bPane = new BorderPane();
		Scene tournament = new Scene(bPane, 1000, 1000);
		VBox vbox;
		VBox matchVBox;
		HBox hbox1;
		HBox hbox2;
		BorderPane newBP;
		
		teamNames = new Label[numMatches*2];
		scoreInputs = new TextField[numMatches*2];
		buttons = new Button[numMatches];
		
		for(int i = 0; i < teamNames.length; i++) {
			teamNames[i] = new Label();
			teamNames[i].setAlignment(Pos.CENTER); teamNames[i].setMinHeight(25); teamNames[i].setMinWidth(80);
			teamNames[i].setText("TBD");
		}
		
		for(int i = 0; i < scoreInputs.length; i++) {
			scoreInputs[i] = new TextField();
			scoreInputs[i].setAlignment(Pos.CENTER_RIGHT); scoreInputs[i].setMaxWidth(45);
			scoreInputs[i].setPromptText("Score");
		}
		
		for(int i = 0; i < buttons.length; i++) {
			buttons[i] = new Button();
			buttons[i].setText("Submit");
		}
		
		int matchesInColumn = teamCount/2;
		int matchNumber = 0;
		BorderPane curBP = bPane;
		for(int i = 0; i < Math.log(teamCount)/Math.log(2); i++) {
			vbox = new VBox();
			vbox.setAlignment(Pos.CENTER); vbox.setPadding(new Insets(0,40,0,10));
			
			
			for(int j = 0; j < matchesInColumn; j++) {
				vbox.getChildren().add(createSpacer());
				matchVBox = new VBox();
				matchVBox.setPadding(new Insets(5, 5, 5, 5));
				matchVBox.setStyle("-fx-border-color: black;\n" +
								   "-fx-border-width: 1;\n");
				matchVBox.setAlignment(Pos.CENTER_RIGHT);
				hbox1 = new HBox();
				hbox2 = new HBox();
				hbox1.setPadding(new Insets(5, 5, 5, 5));
				hbox1.setSpacing(10);
				hbox2.setPadding(new Insets(5, 5, 5, 5));
				hbox2.setSpacing(10);
				hbox1.getChildren().addAll(teamNames[matchNumber*2], scoreInputs[matchNumber*2]);
				hbox2.getChildren().addAll(teamNames[matchNumber*2 + 1], scoreInputs[matchNumber*2 + 1]);
				matchVBox.getChildren().addAll(hbox1, buttons[matchNumber], hbox2);
				vbox.getChildren().add(matchVBox);
				curBP.setLeft(vbox);
				matchNumber++;
			}
			vbox.getChildren().add(createSpacer());
			matchesInColumn = matchesInColumn/2;
			newBP = new BorderPane();
			curBP.setCenter(newBP);
			curBP = newBP;
		}
		primaryStage.setScene(tournament);
	 	primaryStage.show();
	}
	
	private Region createSpacer() {
		Region spacer = new Region();
		VBox.setVgrow(spacer, Priority.ALWAYS);
		return spacer;
	}
}
