package it.polito.tdp.PremierLeague.model;

public class PuntiSquadra implements Comparable<PuntiSquadra> {
	private Team team;
	private int differenzaPunti;
	
	
	public PuntiSquadra(Team team,int differenzaPunti) {
		super();
		this.team = team;
		this.differenzaPunti=differenzaPunti;
	}


	public Team getTeam() {
		return team;
	}


	public void setTeam(Team team) {
		this.team = team;
	}


	


	public int getDifferenzaPunti() {
		return differenzaPunti;
	}


	public void setDifferenzaPunti(int differenzaPunti) {
		this.differenzaPunti = differenzaPunti;
	}


	@Override
	public int compareTo(PuntiSquadra o) {
		
		return this.getDifferenzaPunti()-o.getDifferenzaPunti() ;
	}


	@Override
	public String toString() {
		return  this.getTeam().getName()+"("+this.getDifferenzaPunti()+")";
	}
	
	
	
	

}
