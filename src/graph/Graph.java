package graph;

import java.util.ArrayList;

public class Graph {
    public final int numberOFVertices;
    public ArrayList<Integer>[] adjacencyLists;

    public Graph(int numberOfVertices) {
	this.numberOFVertices = numberOfVertices;
	adjacencyLists = (ArrayList<Integer>[]) new ArrayList[numberOfVertices];
	for (int i = 0; i < numberOfVertices; i++) {
	    adjacencyLists[i] = new ArrayList<Integer>();
	}
    }

    public void addEdge(int v, int w) {
	adjacencyLists[v].add(w);
	adjacencyLists[w].add(v);
    }

    public ArrayList<Integer> getAdjacentVertices(int v) {
	return adjacencyLists[v];
    }

}
