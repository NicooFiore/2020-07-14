package it.polito.tdp.PremierLeague.model;

public class Evento {
	
	public enum EventType{
		VITTORIA,
		PAREGGIO,
		SCONFITTA,
	}
	
	Match m;
	int ntot;
	EventType type;
	Team t1;
	

}
