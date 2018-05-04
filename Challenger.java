package application;
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
/*
 * The challenger class represents a single entrant into the tournament. Each entrant has a 
 * name and rank. 
 */
public class Challenger {
	private String name;//Name of the challenger
	private Integer rank;//rank of the challenger.

	/*Constructor. Initializes team name to TBD and rank to -1.
	 * 
	 */
	public Challenger() {
		name = "TBD";
		rank = -1;
	}

	/*Additional Contructor initializing name and rank to those given.
	 * 
	 */
	public Challenger(String name, Integer rank) {
		this.name = name;
		this.rank = rank;
	}
	/*
	 * Returns the name of the Challenger
	 */
	public String getName() {
		return name;
	}
	/*
	 * Sets the name of the challenger. returns false if attempting to use a null name
	 */
	public boolean setName(String name) {
		if (name == null) {
			return false;
		}
		this.name = name;
		return true;
	}
	
	/*
	 * Returns rank of the challenger
	 */
	public Integer getRank() {
		return this.rank;
	}
	
	/*
	 * Sets the rank of the challenger.
	 */
	public Boolean setRank(Integer rank) {
		if (rank == null) {
			return false;
		}
		else {
			this.rank = rank;
			return true;
		}
	}
}
