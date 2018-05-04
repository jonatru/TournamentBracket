package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
/**
 * The tournament class is instantiated by the MAin class. It loads the input file
 * and keeps track of challenges, challengers, winners, losers, status, among others.
 * @author jonen, Mathews, O'Connor, Truttmann
 */
public class Tournament {
	
	static ArrayList<Challenger> teams = new ArrayList<Challenger>();//all the teams in the tournament
	static ArrayList<Challenge> games = new ArrayList<Challenge>();//the games in the tournament
	
	/**
	 * constructor. Sets up the initial tournament
	 * @param args that were passed into Main.java. Should contain a pointer to a file containg teams
	 */
	public Tournament(String[] args) {
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
		
		//gets the seeding based on the number of teams and sets the challenges.
		for (int i = 0; i < teamnames.size();i++) {
			games.get(i/2).setChallenger(i%2, teams.get(getfirstRoundOrder()[i]-1));
		}
		//status information
		for (int i = 0; i < teams.size()/2;i++) {
			System.out.println(games.get(i).getChallenger(0).getName());
			System.out.println("Vs " + games.get(i).getChallenger(1).getName() + "\n");
		}
	}
	
	/**
	 * CAlled by the GUI. based on the score, it determines winner and sets the challenger for the next game.
	 * Checks if it was the last game.
	 * @param challengeIndex
	 * @param scores
	 * @return boolean
	 */
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
			System.out.println("Tourney over! Winner is: " + games.get(challengeIndex).getWinner().getName());
			Main.updateChampion();
		}
		else {
			int nextRoundIndex = games.size()-((games.size()-challengeIndex)/2);
			games.get(nextRoundIndex).setChallenger(challengeIndex%2, games.get(challengeIndex).getWinner());
			for (int i = 0; i < games.size(); i ++ ) {
				System.out.println("Game" + i + ": " + games.get(i).getChallenger(0).getName()+
						" vs " + games.get(i).getChallenger(1).getName());
			}
		}
		return true;
	}
	
	/**
	 * Returns a challenge of the tournament. Challenges are indexed in round order (championship game is last)
	 * @param challengeIndex
	 * @return
	 */
	public Challenge getChallenge(int challengeIndex) {
		return games.get(challengeIndex);
	}
	
	/**
	 * Get a specific team in the tournament. indexed in same order as input file
	 * @param index
	 * @return
	 */
	public Challenger getTeam(int index) {
		return teams.get(index);
	}
	
	/**
	 * Return number of games in the tournamet
	 * @return
	 */
	public int getSize() {
		return games.size();
	}
	
	/**Helper function
	 * Looks at team size and returns an array of the order of seedings in the tournament.
	 * @return
	 */
	private static int[] getfirstRoundOrder(){
		int[] array = null;
		if (teams.size() == 0) {
			
		}
		
		if(teams.size() == 64){
			array = new int[] {1,64,32,33,17,48,16,49,9,56,24,41,25,40,8,57,4,61,29,36,20,45,13,52,12,
			53,21,44,28,37,5,60,2,63,31,34,18,47,15,50,10,55,23,42,26,39,7,58,3,62,30,35,19,
			46,14,51,11,54,22,43,27,38,6,59};

		}
		if (teams.size()==32) {
			array = new int[] {1,32,16,17,9,24,8,25,4,29,13,20,12,21,5,28,2,31,
					15,18,10,23,7,26,3,30,14,19,11,22,6,27};
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
