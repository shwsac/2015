package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DepthFirstOrder {
    Stack<Integer> resultStack = new Stack<Integer>();
    boolean[] marked;
    Digraph G;
    protected List<Integer> getTopologicalOrder(Digraph inputG) {
	if (G != null) {
	    this.G = inputG;
	    marked = new boolean[G.getNumberOfVertices()];
	    for (int i = 0; i < G.getNumberOfVertices(); i++) {
		if(!marked[i]){
		    dfsWithStack(i);
		}
	    }
	    return (List<Integer>) resultStack.iterator();
	}
	return new ArrayList<Integer>();
    }
    
    public void dfsWithStack(int v){
	for(Integer i : G.getAdjacentVertices(v)){
	    if(!marked[i]){
		dfsWithStack(i);
	    }
	}
	resultStack.push(v);
    }

}
