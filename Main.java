package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	
	static ArrayList<Challenger> teams = new ArrayList<Challenger>();
	static ArrayList<Challenge> games = new ArrayList<Challenge>();
	public static void main(String[] args) {
	
		//get name of teams
        File file = new File(args[0]);
        BufferedReader br;
        ArrayList<String> teamnames = new ArrayList<String>();
		try {
			br = new BufferedReader(new FileReader(file));
	        
	        String line = br.readLine();
	        while (line != null) {
	            teamnames.add(line);
	            line = br.readLine();}
	        br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error loading teams file");
			e.printStackTrace();
		}
		
		for (int i = 0; i < teamnames.size();i++){
			Challenger tempteam = new Challenger();
			tempteam.setName(teamnames.get(i));
			tempteam.setRank(i+1);
			teams.add(tempteam);
		
		}
		
		for (int i = 0; i < teamnames.size()-1;i++) {
			Challenge tempchallenge = new Challenge();
			games.add(tempchallenge);
		}
		
		//set up the challenges. first split the teams into two arrays. then each in each array
		//the highes ranked team plays the lowest rank team, and so on. the winner of the two
		//arrays plays for the 'ship. 
		ArrayList<Integer> half1 = new ArrayList<Integer>();
		ArrayList<Integer> half2 = new ArrayList<Integer>();
		boolean arrayX = true; //which array to add to. X or Y
		for (int i =0; i< teams.size();i++) {
			if (arrayX) {
				half1.add(i);
			}
			else {
				half2.add(i);
			}
			if (i%2 == 0) {
				arrayX = !arrayX;
			}
		}
		System.out.println(half1.size());
		System.out.println(half2.size());
		for (int i = 0; i < teams.size()/4;i++) {//set
			games.get(i).setChallenger(0, teams.get(half1.get(i)));//higher ranked team
			games.get(i).setChallenger(1, teams.get(half1.get(half1.size()-1-i)));
			
			games.get(i + teams.size()/4).setChallenger(0, teams.get(half2.get(i)));
			games.get(i + teams.size()/4).setChallenger(1, teams.get(half2.get(half2.size()-1-i)));
		}
		/* verify that matches are setup correctly
		for (int i = 0; i < teams.size()/2;i++) {
			System.out.println(games.get(i).getChallenger(0).getName());
			System.out.println("Vs " + games.get(i).getChallenger(1).getName() + "\n");
		}
		*/

    
	//System.out.println(challenger2.getName());
	GUI gui1 = new GUI();
	gui1.main(args);
	
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public boolean setScore(int challengeIndex, int [] scores) {
		games.get(challengeIndex).setScore(0, scores[0]);
		games.get(challengeIndex).setScore(1, scores[1]);
		if (scores[0] > scores[1]) {
			games.get(challengeIndex).setWinner(0);
		}
		else {
			games.get(challengeIndex).setWinner(1);
		}
		//to do: set winner as next round challenger
		//to do: check if this was last game. if so tournament is done.
		return true;
	}
	
	public Challenge getChallenge(int challengeIndex) {
		return games.get(challengeIndex);
	}
	
	public Challenger getTeam(int index) {
		return teams.get(index);
	}
	
	//returns number of games (which is teams -1)
	public int getSize() {
		return games.size();
	}
}
