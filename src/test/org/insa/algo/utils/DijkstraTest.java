package org.insa.algo.utils;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.insa.algo.AbstractInputData;
import org.insa.algo.ArcInspector;
import org.insa.algo.ArcInspectorFactory;
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

public class DijkstraTest {
	
	@Test
	public void testScenarioDistance() throws IOException{
		String mapName = "/Users/mael/Desktop/Cartes/europe/france/haute-garonne.mapgr";
		int origine;
		int destination;
		
		System.out.println("#####----- Test de validité avec oracle sur une carte-----######");
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
		DijkstraAlgorithm D = new DijkstraAlgorithm(data);
		ShortestPathSolution solution = D.run();
		float coutDijkstra = D.getCoutFinal(); 
		System.out.println("solution en distance : " + solution);
		
		if (solution.getInputData().getMode() == AbstractInputData.Mode.LENGTH) {
			assertEquals(coutDijkstra, solution.getPath().getLength(), 1e-6); 
		}
		else throw new IOException();
		
		
		arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(2);
		data = new ShortestPathData(graph, graph.get(origine), graph.get(destination), arcInspectorDijkstra);			
		D = new DijkstraAlgorithm(data);
		solution = D.run();
		coutDijkstra = D.getCoutFinal(); 
		System.out.println("solution en temps : " + solution);
		
		if (solution.getInputData().getMode() == AbstractInputData.Mode.TIME) {
			assertEquals((double) coutDijkstra, solution.getPath().getMinimumTravelTime(), 1e-3); 
		}
		else throw new IOException();
		
		////////////////////////////////////////////////////////////////////////////
		
		System.out.println("----- Cas d'un chemin nul ------");
		origine = 38926;
		destination = 38926;
		
		arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(0);
		data = new ShortestPathData(graph, graph.get(origine), graph.get(destination), arcInspectorDijkstra);			
		D = new DijkstraAlgorithm(data);
		solution = D.run();
		coutDijkstra = D.getCoutFinal(); 
		System.out.println("solution en distance : " + solution);
		
		if ((solution.getInputData().getMode() == AbstractInputData.Mode.LENGTH) && solution.getPath() != null) {
			assertEquals(coutDijkstra, solution.getPath().getLength(), 1e-6); 
		}
		// test si chemin nul 
		else assertEquals(coutDijkstra, 0, 1e-6);
		
		arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(2);
		data = new ShortestPathData(graph, graph.get(origine), graph.get(destination), arcInspectorDijkstra);			
		D = new DijkstraAlgorithm(data);
		solution = D.run();
		coutDijkstra = D.getCoutFinal(); 
		System.out.println("solution en temps : " + solution);
		
		if ((solution.getInputData().getMode() == AbstractInputData.Mode.TIME) && solution.getPath() != null) {
			assertEquals((double) coutDijkstra, solution.getPath().getMinimumTravelTime(), 1e-3); 
		}
		else assertEquals(coutDijkstra, 0, 1e-3); 
		
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("----- Cas d'un chemin inexistant ------");
		origine = 90912;
		destination = 132839;
		
		arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(0);
		data = new ShortestPathData(graph, graph.get(origine), graph.get(destination), arcInspectorDijkstra);			
		D = new DijkstraAlgorithm(data);
		solution = D.run();
		coutDijkstra = D.getCoutFinal(); 
		System.out.println("solution en distance : " + solution);
		
		if ((solution.getInputData().getMode() == AbstractInputData.Mode.LENGTH) && solution.getPath() != null) {
			assertEquals(coutDijkstra, solution.getPath().getLength(), 1e-6); 
		}
		// test si chemin nul 
		else assertEquals(coutDijkstra, 0, 1e-6);
		
		arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(2);
		data = new ShortestPathData(graph, graph.get(origine), graph.get(destination), arcInspectorDijkstra);			
		D = new DijkstraAlgorithm(data);
		solution = D.run();
		coutDijkstra = D.getCoutFinal(); 
		System.out.println("solution en temps : " + solution);
		
		if ((solution.getInputData().getMode() == AbstractInputData.Mode.TIME) && solution.getPath() != null) {
			assertEquals((double) coutDijkstra, solution.getPath().getMinimumTravelTime(), 1e-3); 
		}
		else assertEquals(coutDijkstra, 0, 1e-6); 
	}
	
	@Test
	public void testScenarioDistanceDijsktraBellmanFord() throws IOException{
		String mapName = "/Users/mael/Desktop/Cartes/europe/france/haute-garonne.mapgr";
		int origine;
		int destination;
		
		System.out.println("#####----- Test de validité avec oracle sur une carte-----######");
		System.out.println("#####----- Carte : Haute-Garonne -------------------------######");
		System.out.println("#####----- Mode : DISTANCE -------------------------------######");
		System.out.println();
		
		System.out.println("----- Cas d'un chemin simple ------");
		origine = 38926;
		destination = 59015;
		
		GraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
		
		Graph graph = reader.read(); 
		
	
		ArcInspector arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(0);
		ShortestPathData data = new ShortestPathData(graph, graph.get(origine), graph.get(destination), arcInspectorDijkstra);			
		DijkstraAlgorithm D = new DijkstraAlgorithm(data);
		BellmanFordAlgorithm B = new BellmanFordAlgorithm(data); 
		
		ShortestPathSolution solutionD = D.run();
		ShortestPathSolution solutionB = B.run(); 
		
		System.out.println("solution Dijkstra en distance : " + solutionD);
		System.out.println("solution Bellman en distance : " + solutionB);
		
		if ((solutionD.getInputData().getMode() == AbstractInputData.Mode.LENGTH) && (solutionD.getInputData().getMode() == AbstractInputData.Mode.LENGTH)) {
			assertEquals(solutionD.getPath().getLength(),solutionB.getPath().getLength(), 1e-6); 
		}
		else throw new IOException();
		
		/* 
		ArcInspector arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(2);
		ShortestPathData data = new ShortestPathData(graph, graph.get(origine), graph.get(destination), arcInspectorDijkstra);			
		DijkstraAlgorithm D = new DijkstraAlgorithm(data);
		BellmanFordAlgorithm B = new BellmanFordAlgorithm(data); 
		
		ShortestPathSolution solutionD = D.run();
		ShortestPathSolution solutionB = B.run(); 
		
		System.out.println("solution Dijkstra en temps : " + solutionD);
		System.out.println("solution Bellman en temps : " + solutionB);
		
		if ((solutionD.getInputData().getMode() == AbstractInputData.Mode.TIME) && (solutionD.getInputData().getMode() == AbstractInputData.Mode.TIME)) {
			assertEquals(solutionD.getPath().getMinimumTravelTime(),solutionB.getPath().getMinimumTravelTime(), 1e-6); 
		}
		else throw new IOException();
		*/ 
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		/* 
		System.out.println("----- Cas d'un chemin nul ------");
		origine = 38926;
		destination = 38926;
		
		GraphReader reader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(/Users/mael/Desktop/Cartes/europe/france/haute-garonne.mapgr))));
		
		Graph graph = reader.read(); 
		*/ 
		
		/*
		ArcInspector arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(0);
		ShortestPathData data = new ShortestPathData(graph, graph.get(origine), graph.get(destination), arcInspectorDijkstra);			
		DijkstraAlgorithm D = new DijkstraAlgorithm(data);
		BellmanFordAlgorithm B = new BellmanFordAlgorithm(data); 
		
		ShortestPathSolution solutionD = D.run();
		ShortestPathSolution solutionB = B.run(); 
		
		System.out.println("solution Dijkstra en distance : " + solutionD);
		System.out.println("solution Bellman en distance : " + solutionB);
		
		if ((solutionD.getInputData().getMode() == AbstractInputData.Mode.LENGTH) && (solutionD.getInputData().getMode() == AbstractInputData.Mode.LENGTH) && solutionD.getPath() != null && solutionB.getPath() != null) {
			assertEquals(solutionD.getPath().getLength(),solutionB.getPath().getLength(), 1e-6); 
		}
		else System.out.println("les deux solutions sont �gales");
		
		*/ 
		
		/*
		ArcInspector arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(2);
		ShortestPathData data = new ShortestPathData(graph, graph.get(origine), graph.get(destination), arcInspectorDijkstra);			
		DijkstraAlgorithm D = new DijkstraAlgorithm(data);
		BellmanFordAlgorithm B = new BellmanFordAlgorithm(data); 
		
		ShortestPathSolution solutionD = D.run();
		ShortestPathSolution solutionB = B.run(); 
		
		System.out.println("solution Dijkstra en temps : " + solutionD);
		System.out.println("solution Bellman en temps : " + solutionB);
		
		if ((solutionD.getInputData().getMode() == AbstractInputData.Mode.TIME) && (solutionD.getInputData().getMode() == AbstractInputData.Mode.TIME) && solutionD.getPath() != null && solutionB.getPath() != null) {
			assertEquals(solutionD.getPath().getLength(),solutionB.getPath().getLength(), 1e-6); 
		}
		else System.out.println("les deux solutions sont �gales");
		
		*/
		/*
		System.out.println("----- Cas d'un chemin inexistant ------");
		origine = 90912;
		destination = 132839;
		
		GraphReader reader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(/Users/mael/Desktop/Cartes/europe/france/haute-garonne.mapgr))));
		
		Graph graph = reader.read(); 
		*/
		
		/*
		ArcInspector arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(0);
		ShortestPathData data = new ShortestPathData(graph, graph.get(origine), graph.get(destination), arcInspectorDijkstra);			
		DijkstraAlgorithm D = new DijkstraAlgorithm(data);
		BellmanFordAlgorithm B = new BellmanFordAlgorithm(data); 
		
		ShortestPathSolution solutionD = D.run();
		ShortestPathSolution solutionB = B.run(); 
		
		System.out.println("solution Dijkstra en distance : " + solutionD);
		System.out.println("solution Bellman en distance : " + solutionB);
		
		if ((solutionD.getInputData().getMode() == AbstractInputData.Mode.LENGTH) && (solutionD.getInputData().getMode() == AbstractInputData.Mode.LENGTH) && solutionD.getPath() != null && solutionB.getPath() != null) {
			assertEquals(solutionD.getPath().getLength(),solutionB.getPath().getLength(), 1e-6); 
		}
		else System.out.println("les deux solutions sont �gales");
		*/ 
		
		/*
		ArcInspector arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(2);
		ShortestPathData data = new ShortestPathData(graph, graph.get(origine), graph.get(destination), arcInspectorDijkstra);			
		DijkstraAlgorithm D = new DijkstraAlgorithm(data);
		BellmanFordAlgorithm B = new BellmanFordAlgorithm(data); 
		
		ShortestPathSolution solutionD = D.run();
		ShortestPathSolution solutionB = B.run(); 
		
		System.out.println("solution Dijkstra en temps : " + solutionD);
		System.out.println("solution Bellman en temps : " + solutionB);
		
		if ((solutionD.getInputData().getMode() == AbstractInputData.Mode.TIME) && (solutionD.getInputData().getMode() == AbstractInputData.Mode.TIME) && solutionD.getPath() != null && solutionB.getPath() != null) {
			assertEquals(solutionD.getPath().getLength(),solutionB.getPath().getLength(), 1e-6); 
		}
		else System.out.println("les deux solutions sont �gales");
		*/ 
	
	}
}







