package org.insa.algo.utils;
import org.insa.algo.AbstractInputData;
import org.insa.algo.AbstractInputData.Mode;
import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.graph.Node;
import org.insa.graph.Point;

public class LabelStar extends Label {
	protected float estimatedCost;  

	
	protected double calcEstimatedCost(int NodeId, ShortestPathData data) {
    	AbstractInputData.Mode mode = data.getMode(); 
    	double d = data.getGraph().get(NodeId).getPoint().distanceTo(data.getDestination().getPoint()); 
    	return (mode == Mode.LENGTH) ? d : (d/((Math.max(data.getGraph().getGraphInformation().getMaximumSpeed(), data.getMaximumSpeed()) / 3.6))); 
    }

	public LabelStar(Node noeud, ShortestPathData data) {
		super(noeud);
		this.estimatedCost = (float) calcEstimatedCost(noeud.getId(), data); 
	}
	
	@Override
	public float getTotalCost() {
		return this.cost + this.estimatedCost; 
	}
}



