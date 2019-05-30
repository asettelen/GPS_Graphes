package org.insa.algo;

import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.insa.algo.shortestpath.AStarAlgorithm;
import org.insa.algo.shortestpath.BellmanFordAlgorithm;
import org.insa.algo.shortestpath.DijkstraAlgorithm;
import org.insa.algo.shortestpath.ShortestPathAlgorithm;
import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.algo.shortestpath.ShortestPathSolution;
import org.insa.graph.Graph;
import org.insa.graph.io.BinaryGraphReader;
import org.junit.BeforeClass;
import org.junit.Test;

public class PerfoTest
{
	public static ShortestPathSolution result(ShortestPathAlgorithm algo)
	{
		return algo.run();
	}
	
	public static double averageduration(ShortestPathAlgorithm algo)
	{
		double begin = System.nanoTime();
		algo.run();
		double end = System.nanoTime();
		return (end-begin);
	}
	@Test
	public void hauteGaronneTest()
	{
		Graph hautegaronne = null;
		try {
			hautegaronne = (new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream("/Users/mael/Desktop/Cartes/europe/france/haute-garonne.mapgr"))))).read();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArcInspector aI =ArcInspectorFactory.getAllFilters().get(0);
		ShortestPathData data = new ShortestPathData(hautegaronne, hautegaronne.get(6), hautegaronne.get(21), aI);
		double b = averageduration(new BellmanFordAlgorithm(data));
		double d = averageduration(new DijkstraAlgorithm(data));
		double a = averageduration(new AStarAlgorithm(data));
		assertTrue(true);
		System.out.println("Test haute garonne : " + b + " bellmanford, " + d + " dijkstra " + a + " Astar");
	}
	
	
	@Test
	public void carreDenseTest()// d'un coin � l'autre, A* bat tous les records
	{
		Graph carredense = null;
		try
		{
			carredense = (new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream("/Users/mael/Desktop/Cartes/extras/carre-dense.mapgr"))))).read();
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		ArcInspector aI =ArcInspectorFactory.getAllFilters().get(0);
		ShortestPathData data = new ShortestPathData(carredense, carredense.get(321931), carredense.get(190456), aI);
		double b = averageduration(new BellmanFordAlgorithm(data));
		double d = averageduration(new DijkstraAlgorithm(data));
		double a = averageduration(new AStarAlgorithm(data));
		assertTrue(true);
		System.out.println("Test carre dense : " + b + " bellmanford, " + d + " dijkstra " + a + " Astar");
	}
	@Test
	public void fractalSpiralTest()// du centre � l'exterieur, BellmanFord gagne
	{
		Graph fractalspiral = null;
		try {
			fractalspiral = (new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream("/Users/mael/Desktop/Cartes/extras/fractal-spiral.mapgr"))))).read();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArcInspector aI =ArcInspectorFactory.getAllFilters().get(0);
		ShortestPathData data = new ShortestPathData(fractalspiral, fractalspiral.get(611565), fractalspiral.get(139116), aI);
		double b = averageduration(new BellmanFordAlgorithm(data));
		double d = averageduration(new DijkstraAlgorithm(data));
		double a = averageduration(new AStarAlgorithm(data));
		assertTrue(true);
		System.out.println("Test spirale fractale : " + b + " bellmanford, " + d + " dijkstra " + a + " Astar");
	}
	@Test
	public void fractalSpiralTest2()// de l'exterieur au centre, A* gagne
	{
		Graph fractalspiral = null;
		try {
			fractalspiral = (new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream("/Users/mael/Desktop/Cartes/extras/fractal-spiral.mapgr"))))).read();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArcInspector aI =ArcInspectorFactory.getAllFilters().get(0);
		ShortestPathData data = new ShortestPathData(fractalspiral, fractalspiral.get(139116), fractalspiral.get(611565), aI);
		double b = averageduration(new BellmanFordAlgorithm(data));
		double d = averageduration(new DijkstraAlgorithm(data));
		double a = averageduration(new AStarAlgorithm(data));
		assertTrue(true);
		System.out.println("Test spirale fractale : " + b + " bellmanford, " + d + " dijkstra " + a + " Astar");
	}


	// TODO

}