package org.insa.algo.shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Label;
import org.insa.graph.Node;
import org.insa.graph.Path;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {
	private Label labels[];
	private int nodesNb; 
	private Graph graph; 
	private int origin, destination;
	private BinaryHeap<Label> tas; 
	private ShortestPathData data; 
	
    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
        this.data = data;
        this.graph = data.getGraph();
        this.nodesNb = graph.size(); 
        this.origin = data.getOrigin().getId();
        this.destination = data.getDestination().getId();
        this.tas = new BinaryHeap<>(); 
        this.initialisationLabels();
    }
    
    private void initialisationLabels() {
    	this.labels = new Label[nodesNb]; 
    	for (int i=0; i<labels.length; i++) {
    		labels[i].setId(i);
    	}
    	labels[this.origin].setCost(0);
    	tas.insert(labels[this.origin]);
    }

    @Override
    protected ShortestPathSolution doRun() {
        //ShortestPathData data = getInputData();
        //ShortestPathSolution solution = null;
        djikstraProcess();
        return solution();
    }
    
    protected ShortestPathSolution solution()
    {
    	if(labels[data.getDestination().getId()].getPere() == null)
		{
			return new ShortestPathSolution(data, Status.INFEASIBLE);
		}
		//notifyDestinationReached(data.getDestination());
		
		ArrayList<Arc> path = new ArrayList<Arc>();
		Arc arc = labels[data.getDestination().getId()].getPere();
		while (arc != null) {
			path.add(arc);
			arc = labels[arc.getOrigin().getId()].getPere();
		}
		Collections.reverse(path);
		return new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, path));
    }
    
    private void djikstraProcess()
    {
    	int idx;
    	boolean condition = true; 
    	do
		{
			Label lx = tas.deleteMin();
			idx = lx.getId();
			Node nx = graph.get(idx);
			lx.setMarquage(condition);
			
			ArrayList<Arc> successors = nx.getSuccessorsBis();
			
			for(Arc successor : successors)
			{
				if(data.isAllowed(successor))
				{
					Label ly = labels[successor.getDestination().getId()];
					
					if(!ly.getMarquage())
					{
						double newCost = lx.getCost() + data.getCost(successor);
						if(ly.getCost()>newCost)
						{
							ly.setCost(newCost);
							if(!tas.exists(ly))
							{
								tas.insert(ly);
								//notify à placer ici
							}
							else
							{
								tas.update();
							}
							ly.setPere(successor);
						}
					}
					
				}
				
			}
				
		
		}while(!tas.isEmpty() && idx != this.destination);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
