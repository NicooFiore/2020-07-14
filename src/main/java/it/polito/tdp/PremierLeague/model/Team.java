package it.polito.tdp.PremierLeague.model;

public class Team implements Comparable<Team>{
	Integer teamID;
	String name;
	Integer punti;
	int giornalistiPresenti;

	public Team(Integer teamID, String name) {
		super();
		this.teamID = teamID;
		this.name = name;
		this.punti=0;
		this.giornalistiPresenti=0;
	}
	
	public Integer getTeamID() {
		return teamID;
	}
	public void setTeamID(Integer teamID) {
		this.teamID = teamID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

	@Override
	public String toString() {
		return name;
	}
	

	public int getGiornalistiPresenti() {
		return giornalistiPresenti;
	}

	public void aggiungiGiornalisti(int giornalisti) {
		this.giornalistiPresenti += giornalisti;
	}
	public void rimuoviGiornalisti(int giornalisti) {
		this.giornalistiPresenti -= giornalisti;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((teamID == null) ? 0 : teamID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Team other = (Team) obj;
		if (teamID == null) {
			if (other.teamID != null)
				return false;
		} else if (!teamID.equals(other.teamID))
			return false;
		return true;
	}

	public Integer getPunti() {
		return punti;
	}

	public void setPunti(Integer punti) {
		this.punti = punti;
	}

	@Override
	public int compareTo(Team o) {
		
		return this.getPunti()-o.getPunti();
	}
	
	
}
