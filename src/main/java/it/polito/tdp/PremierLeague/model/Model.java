package it.polito.tdp.PremierLeague.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	private PremierLeagueDAO dao;
	private Graph<Team,DefaultWeightedEdge> grafo;
	public Map<Integer,Team> idMap;
	public Map<String,PuntiSquadra> mappaSquadre;
	public List<Team> classifica;
	List<PuntiSquadra> migliori;
	List<PuntiSquadra> peggiori;
	private Simulatore sim;

	public Model() {
	dao = new PremierLeagueDAO();
	idMap=new HashMap<>();
	classifica = new LinkedList<Team>();
	
	}
    public Map<Integer,Team> getAllTeam(){
    	if(idMap.size()==0)
    	return dao.listAllTeams(idMap);
    	else 
    		return idMap;
    }
    public void doClassifica() {
    	getAllTeam();
    	for(Match m: this.getAllMatches()) {
    		Team casa=idMap.get(m.getTeamHomeID());
    		Team trasferta=idMap.get(m.getTeamAwayID());
    		if(m.getResultOfTeamHome()==1) {
    			casa.setPunti(casa.getPunti()+3);
    		}
    		else if(m.getResultOfTeamHome()==0) {
    			casa.setPunti(casa.getPunti()+1);
    			trasferta.setPunti(trasferta.getPunti()+1);
    		}
    		else if(m.getResultOfTeamHome()==-1)
    			trasferta.setPunti(trasferta.getPunti()+3);
    	}
    	classifica.addAll(idMap.values());
    	Collections.sort(classifica);
    	Collections.reverse(classifica);
    	//System.out.println(classifica.toString());
    	
    }
	
	
	public void creaGrafo() {
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo,this.getAllTeam().values() );
		this.doClassifica();
		for(Team t1: getAllTeam().values()) {
			for(Team t2: getAllTeam().values()) {
				if(t1.getPunti()>t2.getPunti()) {
					Graphs.addEdge(grafo, t1, t2, t1.getPunti()-t2.getPunti());
				}
			}
		}
		System.out.println("#N vertici : "+grafo.vertexSet().size()+" #N archi : "+grafo.edgeSet().size());

	}
	public String getClassifica(Team t){
		this.doClassifica();
		String res="";
		migliori=new LinkedList<>();
		peggiori=new LinkedList<>();
		for(Team t2: this.getAllTeam().values()) {
			int diffPunti=t.getPunti()-t2.getPunti();
			if(diffPunti>0) {
				PuntiSquadra p=new PuntiSquadra(t2,diffPunti);
				peggiori.add(p);
			}
			if(diffPunti<0) {
				PuntiSquadra p=new PuntiSquadra(t2,-diffPunti);
				migliori.add(p);
			}
		}
		Collections.sort(migliori);
		Collections.sort(peggiori);
		res="LE SQUADRE MIGLIORI SONO : \n";
		for(PuntiSquadra p: migliori) {
			res+=p.toString()+"\n";
		}
		res+="\nLE SQUADRE PEGGIORI SONO : \n";
		for(PuntiSquadra p: peggiori) {
			res+=p.toString()+"\n";
		}
		return res;
		
		//return res;
	
		
	}
	public List<Match> getAllMatches(){
		return dao.listAllMatches();
	}
	public List<PuntiSquadra> getMigliori(Team t){
		this.getClassifica(t);
		return migliori;
	}
	List<PuntiSquadra> getPeggiori(Team t){
		this.getClassifica(t);
		return peggiori;
	}
	public void iniziaSimulazione(int n,int x ) {
		sim=new Simulatore();
		sim.init(n, x);
		sim.run();
	}
	public int getGiorniSottoX() {
		return sim.getGiorniSottoX();
	}
	public double getMediaGiornalisti() {
		return sim.mediaGiornalisti();
	}
	
}
