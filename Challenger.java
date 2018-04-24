package application;

public class Challenger {
	String name;
	Integer rank;

	public Challenger() {
		name = "";
		rank = -1;
		System.out.println("hi");
	}

	public Challenger(String name, Integer rank) {
		this.name = name;
		this.rank = rank;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean setName(String name) {
		if (name == null) {
			return false;
		}
		this.name = name;
		return true;
	}
	
	public Integer getRank() {
		return this.rank;
	}
	
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
