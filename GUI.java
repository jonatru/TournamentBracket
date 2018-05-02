package application;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GUI extends Application {
    private static int teamCount = 16; //the number of teams in the competition
    private static int numMatches; //the number of matches
    private static Label[][] teamNames; //stores the team name labels [matchNumber][teamNumber (0 or 1)]
    private static TextField[][] scoreInputs; //stores score input text fields [matchNumber][teamNumber (0 or 1)]
    private static Button[] buttons; //stores the submit buttons [matchNumber]
    private static Label champion; //the label for the champion
    
    private static Main tourney;
    public static void main(String[] args) {
    	tourney = new Main();
    	teamCount = tourney.getSize() + 1; //getsize is the number of games. in a single elim tournament, num of teams is games +1
    	System.out.println("teams: " + teamCount);
        numMatches = 0;
        for(int i = teamCount/2; i >= 1; i = i/2) {
            numMatches += teamCount;
        }
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        HBox mainBox = new HBox(); //contains all of the VBox columns of matches
        Scene tournament = new Scene(mainBox, 1100, 1000);
        VBox vbox; //contains a column of matches
        VBox matchVBox; //VBox that contains each match
        HBox hbox1; //contains team name and score input for team 1
        HBox hbox2; //contains team name and score input for team 2
        HBox championBox;
        
        mainBox.setStyle("-fx-background-color: #FFEBCD;");
        
        teamNames = new Label[numMatches*2][2];
        scoreInputs = new TextField[numMatches*2][2];
        buttons = new Button[numMatches];
        
        for(int i = 0; i < teamNames.length; i++) {
            for(int j = 0; j < teamNames[0].length; j++) {
                teamNames[i][j] = new Label("TBD");
                teamNames[i][j].setAlignment(Pos.CENTER); teamNames[i][j].setMinHeight(25); teamNames[i][j].setMinWidth(80); teamNames[i][j].setTextFill(Color.BLACK);
            }
        }
        
        for(int i = 0; i < scoreInputs.length; i++) {
            for(int j = 0; j < scoreInputs[0].length; j++) {
                scoreInputs[i][j] = new TextField();
                scoreInputs[i][j].setAlignment(Pos.CENTER_RIGHT); scoreInputs[i][j].setMaxWidth(50);
                scoreInputs[i][j].setPromptText("Score");
            }
        }
        
        for(int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button("Submit");
        }
        
        int matchesInColumn = teamCount/2;
        int matchNumber = 0;
        for(int i = 0; i < Math.log(teamCount)/Math.log(2); i++) {
            vbox = new VBox();
            vbox.setAlignment(Pos.CENTER); vbox.setPadding(new Insets(5,0,5,10));
            for(int j = 0; j < matchesInColumn; j++) { //this loop creates VBoxes for each match and adds them to the column VBox
                vbox.getChildren().add(createVSpacer()); //creates an expanding spacer between each match
                hbox1 = createTeamHBox(matchNumber, 0);
                hbox2 = createTeamHBox(matchNumber, 1);
                matchVBox = createMatchVBox(hbox1, hbox2, matchNumber);
                vbox.getChildren().add(matchVBox); //adds the match VBox to the column of matches
                matchNumber++;
            }
            vbox.getChildren().add(createVSpacer());
            mainBox.getChildren().addAll(vbox, createHSpacer());
            matchesInColumn = matchesInColumn/2;
        }
        vbox = new VBox();
        championBox = new HBox();
        champion = new Label("TBD");
        championBox.getChildren().addAll(new Label("Champion: "), champion);
        championBox.setAlignment(Pos.CENTER);
        championBox.setPadding(new Insets(5, 5, 5, 5));
        championBox.setStyle("-fx-border-color: black;\n" +
                             "-fx-border-width: 1;\n" +
                             "-fx-background-color: #FFC44E;");
        championBox.setMinWidth(120);
        vbox.getChildren().addAll(createVSpacer(), championBox, createVSpacer());
        vbox.setAlignment(Pos.CENTER); vbox.setPadding(new Insets(0,40,0,10));
        mainBox.getChildren().add(vbox);
        
        if(teamCount > 1) {
            for(int i = 0; i < teamCount; i++) {
                teamNames[i/2][i%2].setText(tourney.getTeam(i).getName());
            }
        }
        
        primaryStage.setScene(tournament);
        primaryStage.show();
    }
    
    /**
     * Creates a new HBox for the team name and score input 
     * @param matchNumber
     * @param teamNumber the 
     * @return HBox
     */
    private HBox createTeamHBox(int matchNumber, int teamNumber) {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(5, 5, 5, 5)); hbox.setSpacing(10);
        hbox.getChildren().addAll(teamNames[matchNumber][teamNumber], scoreInputs[matchNumber][teamNumber]);
        return hbox;
    }
    
    /**
     * 
     * @param team1 the HBox for team 1 in the match
     * @param team2 the HBox for team 2 in the match
     * @param matchNumber
     * @return VBox
     */
    private VBox createMatchVBox(HBox team1, HBox team2, int matchNumber) {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(5, 5, 5, 5));
        vbox.setStyle("-fx-border-color: black;\n" +
                      "-fx-border-width: 1;\n" +
                      "-fx-background-color: #FF7B5F;");
        vbox.setAlignment(Pos.CENTER_RIGHT);
        vbox.getChildren().addAll(team1, buttons[matchNumber], team2);
        return vbox;
    }
    
    /**
     * Creates a new vertical spacer region
     * @return Region
     */
    private Region createVSpacer() {
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        return spacer;
    }
    
    /**
     * Creates a new vertical spacer region
     * @return Region
     */
    private Region createHSpacer() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }
}
