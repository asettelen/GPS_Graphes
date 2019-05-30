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

	
	/* 
	protected double calcEstimatedCost(Node node, ShortestPathData data) {
    	Point p1 = node.getPoint(); 
    	Point p2 = data.getDestination().getPoint(); 
    	if (data.getMode()==AbstractInputData.Mode.TIME) {
    		int vitesse = Math.max(data.getGraph().getGraphInformation().getMaximumSpeed(), data.getMaximumSpeed()); 
    		return Point.distance(p1, p2) / ((double) vitesse/3.6); 
    	}
    	else {
    		return Point.distance(p1, p2);
    	}
	}
	*/ 
	
	public LabelStar(Node noeud, ShortestPathData data) {
		super(noeud);
		this.estimatedCost = (float) calcEstimatedCost(noeud.getId(), data); 
	}
	
	@Override
	public float getTotalCost() {
		return this.cost + this.estimatedCost; 
	}
}
