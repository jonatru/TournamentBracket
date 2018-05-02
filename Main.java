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
			Challenger tempchallenger = new Challenger();
			tempchallenge.setChallenger(0, tempchallenger);
			tempchallenge.setChallenger(1, tempchallenger);
			games.add(tempchallenge);
		}
		
		for (int i = 0; i < teamnames.size();i++) {
			games.get(i/2).setChallenger(i%2, teams.get(getfirstRoundOrder()[i]-1));
		}
		for (int i = 0; i < teams.size()/2;i++) {
			System.out.println(games.get(i).getChallenger(0).getName());
			System.out.println("Vs " + games.get(i).getChallenger(1).getName() + "\n");
		}
    
	GUI gui1 = new GUI();
	System.out.println("MAin running");
	gui1.main(args);
	
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public boolean setScore(int challengeIndex, int [] scores) {
		if (scores[0] == scores[1]) {
			return false; //cannot submit a tie
		}
		games.get(challengeIndex).setScore(0, scores[0]);
		games.get(challengeIndex).setScore(1, scores[1]);
		if (scores[0] > scores[1]) {
			games.get(challengeIndex).setWinner(0);
		}
		else {
			games.get(challengeIndex).setWinner(1);
		}
		System.out.println("Winner: "+games.get(challengeIndex).getWinner().getName());
		if (challengeIndex == games.size()-1) {//this is the championship game
			//tourney over. tell GUI?
		}
		else {
		//to do: set winner as next round challenger
		//next round challenge index = 
		int thisGameRound = getRound(challengeIndex);
		int roundsReverse = (int) (Math.log(teams.size())/Math.log(2)) - thisGameRound;//this is rounds from the back
		int indexOfFirstgameofRound = (int) (games.size() - Math.pow(2, roundsReverse) + 1);
		int nextRoundIndex = games.size()-((games.size()-challengeIndex)/2);
		games.get(nextRoundIndex).setChallenger(challengeIndex%2, games.get(challengeIndex).getWinner());
		for (int i = 0; i < games.size(); i ++ ) {
			System.out.println("Game" + i + ": " + games.get(i).getChallenger(0).getName()+
					"vs " + games.get(i).getChallenger(1).getName());
		}
		}
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
	
	//given index of the challenge, returns the round that this game is in.
	private static int getRound(int index) {
		int round = 1;
		int gamesThisRound = teams.size()/2;
		int totalrounds = (int) (Math.log(teams.size())/Math.log(2));
		System.out.println("totalrounds:" + totalrounds);;
		int tempGames = 0;
		for (int i = 1; i <= totalrounds; i++) {
			tempGames = tempGames + gamesThisRound;
			if (index +1 <= tempGames) {
				return round;
			}
			else if (round == totalrounds) {
				return round;
			}
			else {
				round++;
				gamesThisRound = gamesThisRound/2;
			}
		}
		return -1;
	}
	
	private static int[] getfirstRoundOrder(){
		int[] array = null;
		if (teams.size() == 0) {
			
		}
		if (teams.size() == 16) {
			array = new int[]{1, 16, 8, 9, 4, 13, 5,12,2,15,7,10,3,14,6,11};
		}
		if (teams.size() == 8) {
			array = new int[]{1,8,4,5,3,6,2,7};
		}
		if (teams.size() == 4) {
			array = new int[] {1,4,2,3};
		}
		if (teams.size() == 2) {
			array = new int[] {1,2};
		}
		return array;
	}
	
}
