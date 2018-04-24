package application;

public class Challenge {
Challenger[] challengers;
int[] scores;
Challenger winner;
Boolean complete;

	public Challenge() {
		challengers= new Challenger[2];
		scores = new int[2];
		complete = false;
	}
	
	public Challenger getChallenger(int index) {
		return challengers[index];
	}
	
	public Boolean setChallenger(int index, Challenger challenger) {
		if (challenger == null) {
			return false;
		}
		challengers[index] = challenger;
		return true;
	}
	
	public Challenger getWinner() {
		return winner;
	}
	
	public boolean setWinner(int index) {
		winner = challengers[index];
		return true;
	}
	
	public int getScore(int index) {
		return scores[index];
	}
	
	public boolean setScore(int index, int score) {
		scores[index] = score;
		return true;
	}
	
	public boolean getComplete() {
		return complete;
	}
	
	public boolean setComplete(boolean complete) {
		this.complete = complete;
		return true;
	}
}
