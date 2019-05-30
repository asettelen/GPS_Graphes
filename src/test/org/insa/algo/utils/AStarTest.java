package org.insa.algo.utils;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.insa.algo.AbstractInputData;
import org.insa.algo.ArcInspector;
import org.insa.algo.ArcInspectorFactory;
import org.insa.algo.shortestpath.AStarAlgorithm;
import org.insa.algo.shortestpath.BellmanFordAlgorithm;
import org.insa.algo.shortestpath.DijkstraAlgorithm;
import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.algo.shortestpath.ShortestPathSolution;
import org.insa.graph.Graph;
import org.insa.graph.Path;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue; 

public class AStarTest {
	
	@Test
	public void testScenarioDistance() throws IOException{
		String mapName = "C:\\Users\\antoi\\Desktop\\be-graphes\\Cartes\\europe\\france\\haute-garonne.mapgr";
		int origine;
		int destination;
		
		System.out.println("#####----- Test de validit√© avec oracle sur une carte-----######");
		System.out.println("#####----- Carte : Haute-Garonne -------------------------######");
		System.out.println("#####----- Mode : DISTANCE -------------------------------######");
		System.out.println();
		
		System.out.println("----- Cas d'un chemin simple ------");
		origine = 38926;
		destination = 59015;
		
		GraphReader reader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
		
		Graph graph = reader.read(); 
		
		ArcInspector arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(0);
		ShortestPathData data = new ShortestPathData(graph, graph.get(origine), graph.get(destination), arcInspectorDijkstra);			
		AStarAlgorithm A = new AStarAlgorithm(data);
		ShortestPathSolution solution = A.run();
		float coutAStar = A.getCoutFinal(); 
		System.out.println("solution en distance : " + solution);
		
		if (solution.getInputData().getMode() == AbstractInputData.Mode.LENGTH) {
			assertEquals(coutAStar, solution.getPath().getLength(), 1e-6); 
		}
		else throw new IOException();
		
		System.out.println();
		
		arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(2);
		data = new ShortestPathData(graph, graph.get(origine), graph.get(destination), arcInspectorDijkstra);			
		A = new AStarAlgorithm(data);
		solution = A.run();
		coutAStar = A.getCoutFinal(); 
		System.out.println("solution en temps : " + solution);
		
		if (solution.getInputData().getMode() == AbstractInputData.Mode.TIME) {
			assertEquals((double) coutAStar, solution.getPath().getMinimumTravelTime(), 1e-3); 
		}
		else throw new IOException();
		
		System.out.println();
		
		////////////////////////////////////////////////////////////////////////////
		
		System.out.println("----- Cas d'un chemin nul ------");
		origine = 38926;
		destination = 38926;
		
		arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(0);
		data = new ShortestPathData(graph, graph.get(origine), graph.get(destination), arcInspectorDijkstra);			
		A = new AStarAlgorithm(data);
		solution = A.run();
		coutAStar = A.getCoutFinal(); 
		System.out.println("solution en distance : " + solution);
		
		if ((solution.getInputData().getMode() == AbstractInputData.Mode.LENGTH) && solution.getPath() != null) {
			assertEquals(coutAStar, solution.getPath().getLength(), 1e-6); 
		}
		// test si chemin nul 
		else assertEquals(coutAStar, 0, 1e-6);
		
		System.out.println();
		
		arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(2);
		data = new ShortestPathData(graph, graph.get(origine), graph.get(destination), arcInspectorDijkstra);			
		A = new AStarAlgorithm(data);
		solution = A.run();
		coutAStar = A.getCoutFinal(); 
		System.out.println("solution en temps : " + solution);
		
		if ((solution.getInputData().getMode() == AbstractInputData.Mode.TIME) && solution.getPath() != null) {
			assertEquals((double) coutAStar, solution.getPath().getMinimumTravelTime(), 1e-3); 
		}
		else assertEquals(coutAStar, 0, 1e-3); 
		
		System.out.println();
		
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("----- Cas d'un chemin inexistant ------");
		origine = 90912;
		destination = 132839;
		
		arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(0);
		data = new ShortestPathData(graph, graph.get(origine), graph.get(destination), arcInspectorDijkstra);			
		A = new AStarAlgorithm(data);
		solution = A.run();
		coutAStar = A.getCoutFinal(); 
		System.out.println("solution en distance : " + solution);
		
		if ((solution.getInputData().getMode() == AbstractInputData.Mode.LENGTH) && solution.getPath() != null) {
			assertEquals(coutAStar, solution.getPath().getLength(), 1e-6); 
		}
		// test si chemin nul 
		else assertEquals(coutAStar, 0, 1e-6);
		
		System.out.println();
		
		arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(2);
		data = new ShortestPathData(graph, graph.get(origine), graph.get(destination), arcInspectorDijkstra);			
		A = new AStarAlgorithm(data);
		solution = A.run();
		coutAStar = A.getCoutFinal(); 
		System.out.println("solution en temps : " + solution);
		
		if ((solution.getInputData().getMode() == AbstractInputData.Mode.TIME) && solution.getPath() != null) {
			assertEquals((double) coutAStar, solution.getPath().getMinimumTravelTime(), 1e-3); 
		}
		else assertEquals(coutAStar, 0, 1e-6); 
		System.out.println();
	}
	
	@Test
	public void testScenarioDistanceAStarBellmanFord() throws IOException{
		String mapName = "C:\\Users\\antoi\\Desktop\\be-graphes\\Cartes\\europe\\france\\haute-garonne.mapgr";
		int origine;
		int destination;
		
		System.out.println("#####----- Test de validit√© avec oracle sur une carte-----######");
		System.out.println("#####----- Carte : Haute-Garonne -------------------------######");
		System.out.println("#####----- Mode : DISTANCE -------------------------------######");
		System.out.println();
		
		GraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
		
		Graph graph = reader.read(); 
		
		
		System.out.println("----- Cas d'un chemin simple ------");
		origine = 38926;
		destination = 59015;
		
		ArcInspector arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(0);
		ShortestPathData data = new ShortestPathData(graph, graph.get(origine), graph.get(destination), arcInspectorDijkstra);			
		AStarAlgorithm A = new AStarAlgorithm(data);
		BellmanFordAlgorithm B = new BellmanFordAlgorithm(data); 
		
		ShortestPathSolution solutionA = A.run();
		ShortestPathSolution solutionB = B.run(); 
		
		System.out.println("solution AStar en distance : " + solutionA);
		System.out.println("solution Bellman en distance : " + solutionB);
		
		if ((solutionA.getInputData().getMode() == AbstractInputData.Mode.LENGTH) && (solutionA.getInputData().getMode() == AbstractInputData.Mode.LENGTH)) {
			assertEquals(solutionA.getPath().getLength(),solutionB.getPath().getLength(), 1e-6); 
		}
		else throw new IOException();
		
		System.out.println();
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(2);
		data = new ShortestPathData(graph, graph.get(origine), graph.get(destination), arcInspectorDijkstra);			
		A = new AStarAlgorithm(data); 
		B = new BellmanFordAlgorithm(data); 
		
		solutionA = A.run();
		solutionB = B.run(); 
		
		System.out.println("solution AStar en temps : " + solutionA);
		System.out.println("solution Bellman en temps : " + solutionB);
		
		if ((solutionA.getInputData().getMode() == AbstractInputData.Mode.TIME) && (solutionA.getInputData().getMode() == AbstractInputData.Mode.TIME)) {
			assertEquals(solutionA.getPath().getMinimumTravelTime(),solutionB.getPath().getMinimumTravelTime(), 1e-6); 
		}
		else throw new IOException();
		
		System.out.println();
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		System.out.println("----- Cas d'un chemin nul ------");
		origine = 38926;
		destination = 38926;
		
		arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(0);
		data = new ShortestPathData(graph, graph.get(origine), graph.get(destination), arcInspectorDijkstra);			
		A = new AStarAlgorithm(data);
		B = new BellmanFordAlgorithm(data); 
		
		solutionA = A.run();
		solutionB = B.run(); 
		
		System.out.println("solution AStar en distance : " + solutionA);
		System.out.println("solution Bellman en distance : " + solutionB);
		
		if ((solutionA.getInputData().getMode() == AbstractInputData.Mode.LENGTH) && (solutionA.getInputData().getMode() == AbstractInputData.Mode.LENGTH) && solutionA.getPath() != null && solutionB.getPath() != null) {
			assertEquals(solutionA.getPath().getLength(),solutionB.getPath().getLength(), 1e-6); 
		}
		else System.out.println("les deux solutions sont Ègales");
		
		System.out.println();
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(2);
		data = new ShortestPathData(graph, graph.get(origine), graph.get(destination), arcInspectorDijkstra);			
		A = new AStarAlgorithm(data);
		B = new BellmanFordAlgorithm(data); 
		
		solutionA = A.run();
		solutionB = B.run(); 
		
		System.out.println("solution AStar en temps : " + solutionA);
		System.out.println("solution Bellman en temps : " + solutionB);
		
		if ((solutionA.getInputData().getMode() == AbstractInputData.Mode.TIME) && (solutionA.getInputData().getMode() == AbstractInputData.Mode.TIME) && solutionA.getPath() != null && solutionB.getPath() != null) {
			assertEquals(solutionA.getPath().getLength(),solutionB.getPath().getLength(), 1e-6); 
		}
		else System.out.println("les deux solutions sont Ègales");
		
		System.out.println();
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		System.out.println("----- Cas d'un chemin inexistant ------");
		origine = 90912;
		destination = 132839;
		
		arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(0);
		data = new ShortestPathData(graph, graph.get(origine), graph.get(destination), arcInspectorDijkstra);			
		A = new AStarAlgorithm(data);
		B = new BellmanFordAlgorithm(data); 
		
		solutionA = A.run();
		solutionB = B.run(); 
		
		System.out.println("solution AStar en distance : " + solutionA);
		System.out.println("solution Bellman en distance : " + solutionB);
		
		if ((solutionA.getInputData().getMode() == AbstractInputData.Mode.LENGTH) && (solutionA.getInputData().getMode() == AbstractInputData.Mode.LENGTH) && solutionA.getPath() != null && solutionB.getPath() != null) {
			assertEquals(solutionA.getPath().getLength(),solutionB.getPath().getLength(), 1e-6); 
		}
		else System.out.println("les deux solutions sont Ègales");
		
		System.out.println();
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


		arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(2);
		data = new ShortestPathData(graph, graph.get(origine), graph.get(destination), arcInspectorDijkstra);			
		A = new AStarAlgorithm(data);
		B = new BellmanFordAlgorithm(data); 
		
		solutionA = A.run();
		solutionB = B.run(); 
		
		System.out.println("solution AStar en temps : " + solutionA);
		System.out.println("solution Bellman en temps : " + solutionB);
		
		if ((solutionA.getInputData().getMode() == AbstractInputData.Mode.TIME) && (solutionA.getInputData().getMode() == AbstractInputData.Mode.TIME) && solutionA.getPath() != null && solutionB.getPath() != null) {
			assertEquals(solutionA.getPath().getLength(),solutionB.getPath().getLength(), 1e-6); 
		}
		else System.out.println("les deux solutions sont Ègales");
		
		System.out.println();
	
	}
}







