package it.polito.tdp.imdb.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	private int narchi;
	private int nvertici;
	private Graph<Actor,DefaultWeightedEdge> grafo;
	private ImdbDAO dao=new ImdbDAO ();
	public List<String> genres(){
		return dao.listAllgenre();
	}
	
	public int getNarchi() {
		return narchi;
	}

	

	public int getNvertici() {
		return nvertici;
	}
	public List<Actor> getsimili(Actor a){
		List<Actor> lista=new LinkedList<Actor>(Graphs.neighborListOf(grafo, a));
		Collections.sort(lista,new Comparatorenomi());
		return lista;
	}
	
	public List<Actor> getattori(String genre){
		
		return dao.listAllActorsbygenre(genre);
	}
	
	public void creagrafo(String genre) {
		grafo= new SimpleWeightedGraph<Actor,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, dao.listAllActorsbygenre(genre));
		List<Actor> lista=new LinkedList<Actor>(dao.listAllActorsbygenre(genre));
		for(Actor a1 :lista) {
			for(Actor a2 :lista) {
				int d=dao.stessofilm(a1.getId(), a2.getId());
				if(a1.getId()!=a2.getId() && d>0 ) {
					grafo.addEdge(a1, a2);
					grafo.setEdgeWeight(a1, a2, d);
				}
			}
		}
		this.narchi=grafo.edgeSet().size();
		this.nvertici=grafo.vertexSet().size();
	}
}
