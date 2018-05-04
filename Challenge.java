package application;

/*
 * Challenge represents a match between two challengers in the tournament.
 * Each challenge consists of 2 challengers, 2 scores, a winner, and a complete boolean.
 * Challenges are instantiated when the tournament is initially loaded
 */
public class Challenge {
	private Challenger[] challengers;//the two challengers
	private int[] scores; //the respective scores of the challengers
	private Challenger winner; //the winner of the challenge
	private Boolean complete;//status as to whether it is complete

	/*
	 * Constructor. creates the challenger array and scores.
	 */
	public Challenge() {
		challengers= new Challenger[2];
		scores = new int[2];
		complete = false;
	}

	/*
	 * returns the challenger at the index
	 */
	public Challenger getChallenger(int index) {
		return challengers[index];
	}

	/*
	 * sets a challenger in the challenge at the index. returns false if given null challenger
	 */
	public Boolean setChallenger(int index, Challenger challenger) {
		if (challenger == null) {
			return false;
		}
		challengers[index] = challenger;
		return true;
	}

	/*
	 * returns winner of the challenge
	 */
	public Challenger getWinner() {
		return winner;
	}

	/*
	 * sets the winner of the challenge
	 */
	public boolean setWinner(int index) {
		winner = challengers[index];
		return true;
	}

	/*
	 * gets the score of the challenger at the index
	 */
	public int getScore(int index) {
		return scores[index];
	}

	/*
	 * sets the score at the index
	 */
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
