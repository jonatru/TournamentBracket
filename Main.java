package application;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

///////////////////////////////////////////////////////////////////////////////
//
//Title:            JavaFX Tournament Bracket A-team 66
//Files:            Main.java, Tournament.java, Challenge.java, Challenger.java
//Semester:         CS 400 Spring 2018
//
//Author:           Brandon Jonen,  JOSHUA MATHEWS, MICHAEL O'CONNOR, JONATHON TRUTTMANN
//Email:            bjonen@wisc.edu
//CS Login:         jonen,
//Lecturer's Name:  Deb Deppeler
//

/*
 * The main class of the tournament program. This class contains the functions for setting up the gui.
 * It instantiates the tournament class, which instantiates challenges and challengers as per the input 
 * teams file. 
 * Main takes an argument which is the filename of a file containing the names of the challengers 
 * in order of their rank.
 * It is expected the teams list is a power of two in size. The games are setup in seeded order.
 * once the final game score has been set, a winner, second place, and third place are shown.
 */
public class Main extends Application {
    private static int teamCount = 0; //the number of teams in the competition
    private static int numMatches; //the number of matches
    private static Label[][] teamNames; //stores the team name labels [matchNumber][teamNumber (0 or 1)]
    private static TextField[][] scoreInputs; //stores score input text fields [matchNumber][teamNumber (0 or 1)]
    private static Label[] finalScores; // stores the final scores of each game [matchNumber]
    private static Button[] buttons; //stores the submit buttons [matchNumber]
    private static Label champion; //the label for the champion
    private static Label second; //the label for the second place
    private static Label third; //the label for the third place
    
    private static Tournament tourney;
    public static void main(String[] args) {
    	tourney = new Tournament(args);
    	teamCount = (tourney.getSize() + 1); //getSize is the number of games. in a single elim tournament, num of teams is games +1
    	System.out.println("teams: " + teamCount);
        numMatches = teamCount-1;

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
        HBox secondBox;
        HBox thirdBox;

        
        mainBox.setStyle("-fx-background-color: #FFEBCD;");
        
        teamNames = new Label[numMatches][2];
        scoreInputs = new TextField[numMatches][2];
        buttons = new Button[numMatches];
        finalScores = new Label[numMatches];
        
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
        
        for(int i = 0; i < finalScores.length; i++) {
            finalScores[i] = new Label("FINAL: ");
            finalScores[i].setAlignment(Pos.CENTER_LEFT);
        }
        
        for(int i = 0; i < buttons.length; i++) {
        	final int index = i;
            buttons[i] = new Button("Submit");
            buttons[i].setOnAction(new EventHandler<ActionEvent>() {
            	
                @Override
                public void handle(ActionEvent event) {
                	int[] scores = new int[2];
                	
                	scores[0] = Integer.parseInt(scoreInputs[index][0].getText());
                	scores[1] = Integer.parseInt(scoreInputs[index][1].getText());
                	
                	boolean validUpdate = tourney.setScore(index, scores);
                     updateNames();//now that scores have been sent, 
                    if (validUpdate) {
                    	updateFinal(index);
                    }
                    else {
                    	System.out.println("Cannot submit score of tie game");
                    }
                }
            });
        }
        
        //This sections sets up the tournament structure
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
        
        //This section sets up the Champion/podium area
        vbox = new VBox();
        championBox = new HBox();
        secondBox = new HBox();
        thirdBox = new HBox();
        champion = new Label("TBD");
        second = new Label("TBD");
        third = new Label("TBD");
        championBox.getChildren().addAll(new Label("Champion: "), champion);
        secondBox.getChildren().addAll(new Label("Second: "), second);
        thirdBox.getChildren().addAll(new Label("Third: "), third);
        championBox.setAlignment(Pos.CENTER);
        championBox.setPadding(new Insets(5, 5, 5, 5));
        championBox.setStyle("-fx-border-color: black;\n" +
                "-fx-border-width: 1;\n" +
                "-fx-background-color: #FFC44E;" +
                "-fx-font-size: 20px;");
        championBox.setMinWidth(120);
        secondBox.setStyle("-fx-border-color: black;\n" +
                "-fx-border-width: 1;\n" +
                "-fx-background-color: #C0C0C0;" +
                "-fx-font-size: 18px;");
        secondBox.setAlignment(Pos.CENTER);
        secondBox.setPadding(new Insets(5, 5, 5, 5));
        thirdBox.setStyle("-fx-border-color: black;\n" +
                "-fx-border-width: 1;\n" +
                "-fx-background-color: #CD7F32;" +
                "-fx-font-size: 18px;");
        thirdBox.setAlignment(Pos.CENTER);
        thirdBox.setPadding(new Insets(5, 5, 5, 5));
        vbox.getChildren().addAll(createVSpacer(), championBox, secondBox, thirdBox,
                createVSpacer());
        vbox.setAlignment(Pos.CENTER); vbox.setPadding(new Insets(0,40,0,10));
        mainBox.getChildren().add(vbox);
        

        updateNames();
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
        vbox.getChildren().addAll(team1, buttons[matchNumber], finalScores[matchNumber], team2);
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
    
    /**
     * Gets the names of the current set of challengers from the tournament class
     */
    private void updateNames() {
        if(teamCount > 1) {
            System.out.println("nummatchs: " + numMatches);
            for(int i = 0; i < numMatches*2; i++) {
                teamNames[i/2][i%2].setText(tourney.getChallenge(i/2).getChallenger(i%2).getName());
            }
        }
        else {
        	updateChampion();
        }
    }
    /**
     * Updates the VBox to include the final score of the game
     * @param matchNumber
     */
    private void updateFinal(int matchNumber) {
        if(teamCount > 1) {
                finalScores[matchNumber].setText("FINAL: "+Integer.parseInt(scoreInputs[matchNumber][0].getText())+" - "+Integer.parseInt(scoreInputs[matchNumber][1].getText()));
        }
    }
    /**
     * Sets the top 3 positions after the tournament ends
     * 
     */
    public static void updateChampion(){
        String firstString = tourney.getChallenge(numMatches-1).getWinner().getName();
        String secondString;
        if (tourney.getChallenge(numMatches-1).getChallenger(0).getName().equals(firstString)) {
            secondString = tourney.getChallenge(numMatches - 1).getChallenger(1).getName();
        } else {
            secondString = tourney.getChallenge(numMatches - 1).getChallenger(0).getName();
        }
        String thirdString;
        int third1; //1st contestant for 3rd place
        int third2; //2nd contestan for 3rd place
        if (tourney.getChallenge(numMatches-2).getChallenger(0).getName().equals(firstString) ||
                tourney.getChallenge(numMatches-2).getChallenger(0).getName().equals(secondString)) {
            third1 = 1;
        } else {
            third1 = 0;
        }
        if (tourney.getChallenge(numMatches-3).getChallenger(0).getName().equals(firstString) ||
                tourney.getChallenge(numMatches-3).getChallenger(0).getName().equals(secondString)) {
            third2 = 1;
        } else {
            third2 = 0;
        }
        if (tourney.getChallenge(numMatches-2).getScore(third1) >
                tourney.getChallenge(numMatches-3).getScore(third2)) {
            thirdString = tourney.getChallenge(numMatches-2).getChallenger(third1).getName();
        } else {
            thirdString = tourney.getChallenge(numMatches-3).getChallenger(third2).getName();
        }
        champion.setText(firstString);
        second.setText(secondString);
        third.setText(thirdString);
    }

     
}
