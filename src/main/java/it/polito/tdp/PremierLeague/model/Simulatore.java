package it.polito.tdp.PremierLeague.model;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Simulatore {
	
	//Dati in ingresso
	private int N;
	private int X;
	
	
	
	// Dati in uscita
	  int count;
	  int tot;
	
	
	//Modello del mondo 
	private List<Match> partite;
	PriorityQueue<Match> coda;
	private Model model;
	private Map<Integer,Team> idMap;


	public Simulatore() {
		super();
		
		model = new Model();
		coda=new PriorityQueue<>();
		
		
	}
	


	
	
	
	
	//Coda degli eventi
	public void init(int n,int x) {
		idMap=model.getAllTeam();
		this.N=n;
		this.X=x;
		count=0;
		tot=0;
		coda.addAll(model.getAllMatches());
		for(Team t: idMap.values()) {
			t.aggiungiGiornalisti(n);
		}
	}
	public void run() {
		while(!coda.isEmpty()) {
			Match m=coda.poll();
			
			Team vincente=null;
			Team perdente=null;
			int giornalistiPartita=0;
			tot+=giornalistiPartita;
			    
			
			if(m.getResultOfTeamHome()==1){ // vittoria squadra di casa
				vincente=idMap.get(m.getTeamHomeID());
				perdente=idMap.get(m.getTeamAwayID());
				}
			else if(m.getResultOfTeamHome()==-1){ // vittoria squadra trasferta
				perdente=idMap.get(m.getTeamHomeID());
				vincente=idMap.get(m.getTeamAwayID());
			}
			else {  // pareggio
				Team tc=idMap.get(m.getTeamHomeID());
				Team tt=idMap.get(m.getTeamAwayID());
				giornalistiPartita=tt.getGiornalistiPresenti()+tc.getGiornalistiPresenti();
				
			}
			if(vincente !=null && perdente!=null) {// caso in cui qualcuno ha vinto
               double prob1=Math.random();
			   double prob2=Math.random();
			   giornalistiPartita=vincente.getGiornalistiPresenti()+perdente.getGiornalistiPresenti();
				if(prob1<=0.5) {
					if(vincente.giornalistiPresenti>0) {
						Team squadraMigliore=squadraMigliore(vincente); // questo metodo deve scegliere casualmente una delle squadre classificate meglio
						if(squadraMigliore!=null) {
							vincente.rimuoviGiornalisti(1);
							squadraMigliore.aggiungiGiornalisti(1);
						}
					}
				}
				
				if(prob2<=0.2) {
					if(perdente.giornalistiPresenti>0) {
						int bocciati=(int)(Math.random()*perdente.giornalistiPresenti)+1;
						Team squadraPeggiore=squadraPeggiore(perdente);// sceglie a caso una di quelle posizionate peggio
						if(squadraPeggiore!=null) {
							perdente.rimuoviGiornalisti(bocciati);
							squadraPeggiore.aggiungiGiornalisti(bocciati);
						}
					}
					
				}
				
			}
			if(giornalistiPartita<=X) { // controllo il livello di criticità della partita
				count++;
			}
			tot+=giornalistiPartita;
		}
	}

	private Team squadraPeggiore(Team perdente) {
		if(model.getPeggiori(perdente).size()>0) {
		int n=(int)(Math.random()*model.getPeggiori(perdente).size());
		Team t=model.getPeggiori(perdente).get(n).getTeam();
		return t;
		}
		return null;
	}
	
	private Team squadraMigliore(Team vincente) {
		if(model.getMigliori(vincente).size()>0) {
		int n=(int)(Math.random()*model.getMigliori(vincente).size());
		Team t=model.getMigliori(vincente).get(n).getTeam();
		return t;
		}
		return null;
	}
	public int getGiorniSottoX() {
		return count;
	}
	public double mediaGiornalisti() {
		return tot/model.getAllMatches().size();
	}

}
