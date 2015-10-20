package graph;

//import java.io.InputStream;
import java.util.ArrayList;

public class Digraph {
    public final int numberOFVertices;
    public ArrayList<Integer>[] adjacencyLists;

    public Digraph(int numberOfVertices) {
	this.numberOFVertices = numberOfVertices;
	adjacencyLists = (ArrayList<Integer>[]) new ArrayList[numberOfVertices];
	for (int i = 0; i < numberOfVertices; i++) {
	    adjacencyLists[i] = new ArrayList<Integer>();
	}
    }

    public void addEdge(int v, int w) {
	adjacencyLists[v].add(w);	
    }

    public ArrayList<Integer> getAdjacentVertices(int v) {
	return adjacencyLists[v];
    }

//    public Digraph(InputStream in) {
//	
//    }

    public int getNumberOfVertices() {
	return numberOFVertices;
    }

    public int getNumberOfEdges() {
	return 0;
    }

    /**
     * returns reverse graph.
     * 
     * @return
     */
    public Digraph reverse() {
	return null;
    }

    @Override
    public String toString() {
	return null;
    }

}
