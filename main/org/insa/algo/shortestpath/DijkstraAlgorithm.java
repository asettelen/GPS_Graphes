package org.insa.algo.shortestpath;

import java.util.ArrayList;
import java.util.Collections;

import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.algo.utils.Label;
import org.insa.graph.*;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {
	
	private float coutFinal; 
	
	protected Label newLabel(Node node, ShortestPathData data) {
		return new Label(node); 
	}

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
        this.coutFinal = 0; 
    }
    
    public float getCoutFinal() {
    	return this.coutFinal;
    }

    protected ShortestPathSolution doRun() {
        ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        BinaryHeap<Label> tasDeLabels = new BinaryHeap<Label>(); 
        Label tableauDeLabels[] = new Label[data.getGraph().getNodes().size()]; 
        boolean resultatFinal = false; 
        
        tableauDeLabels[data.getOrigin().getId()] = newLabel(data.getOrigin(), data);
        tasDeLabels.insert(tableauDeLabels[data.getOrigin().getId()]);
        tableauDeLabels[data.getOrigin().getId()].setInTas();
        tableauDeLabels[data.getOrigin().getId()].setCost(0);
        
        notifyOriginProcessed(data.getOrigin());
        
        Arc[] predecessorArcs = new Arc[data.getGraph().getNodes().size()];
        
        while((tasDeLabels.isEmpty() == false) && (resultatFinal == false)) {
            
        	Label current = tasDeLabels.deleteMin(); 
        	notifyNodeMarked(current.getNode());
        	current.setMark();
        	
        	if (current.getNode() == data.getDestination()) {
        		coutFinal = current.getCost();
        		resultatFinal = true; 
        	}
        	
        	for(Arc a : current.getNode().getSuccessors()) {
        		
        		
        		if (!data.isAllowed(a)) continue;
        		
        		if (tableauDeLabels[a.getDestination().getId()] == null) {
        			notifyNodeReached(a.getDestination());
        			tableauDeLabels[a.getDestination().getId()] = newLabel(a.getDestination(), data); 
        		}
        		
        		if (tableauDeLabels[a.getDestination().getId()].getMark() == false) {
        			
        			if (tableauDeLabels[a.getDestination().getId()].getCost() > current.getCost() + data.getCost(a)) {
        				tableauDeLabels[a.getDestination().getId()].setCost(current.getCost() + (float) data.getCost(a)); 
        				
        				//Vérifier que le coût des labels marqués est croissant au cours des itérations ;
        				//System.out.println("" + a.getDestination().getId() + " " + tableauDeLabels[a.getDestination().getId()].getCost());
        				
        				tableauDeLabels[a.getDestination().getId()].setFather(current.getNode());
        				
        				if (tableauDeLabels[a.getDestination().getId()].getInTas()) {
        					tasDeLabels.remove(tableauDeLabels[a.getDestination().getId()]);	
        				}
        				else tableauDeLabels[a.getDestination().getId()].setInTas();
        					
        				tasDeLabels.insert(tableauDeLabels[a.getDestination().getId()]);
        				
        				//Afficher l’évolution de la taille du tas ;
        				//System.out.println("" + tasDeLabels.size()); 
        				
        				predecessorArcs[a.getDestination().getId()] = a; 
        				//int compteur = 0; 
        				// Afficher le nombre d’arcs du plus court chemin, le nombre d’itérations de l’algorithme.
        				/*
        				for(int k=0; k<predecessorArcs.length; k++) {
        					if (predecessorArcs[k] != null) {
        						compteur++; 
        					}
        				}
        				
        				System.out.println(compteur);
        				
        				*/
        				
        				
        			}
        		}
        	}
        }
        
        if (predecessorArcs[data.getDestination().getId()] == null) {
			solution = new ShortestPathSolution(data, Status.INFEASIBLE);
		} else {
			
			notifyDestinationReached(data.getDestination());
			ArrayList<Arc> arcs = new ArrayList<Arc>();	
			Arc arc = predecessorArcs[data.getDestination().getId()];

			while (arc != null) {
				arcs.add(arc);
				arc = predecessorArcs[arc.getOrigin().getId()];
			}
			
			Collections.reverse(arcs);
			solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(data.getGraph(), arcs));
		}
        
        return solution;
    }
}

