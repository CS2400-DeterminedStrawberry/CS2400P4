package ADTPackage.Graph;
import java.util.Iterator;
import ADTPackage.Dictionary.*; 
import ADTPackage.Queue.*;
import ADTPackage.Stack.*;
public final class Graph<T> implements GraphInterface<T>
{
	private DictionaryInterface<T, VertexInterface<T>> vertices;
	private int edgeCount;
	
	public Graph()
	{
		vertices = new UnsortedLinkedDictionary<>();
		edgeCount = 0;
	} // end default constructor
	
	//basic graph interface implemented
	public boolean addVertex(T vertexLabel) {
		VertexInterface<T> addOutcome = vertices.add(vertexLabel, new Vertex<>(vertexLabel));
		return addOutcome == null;
	} //end addVertex

	public boolean addEdge(T begin, T end, double edgeWeight) {
		boolean result = false;
        VertexInterface<T> beginVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        if (beginVertex != null && endVertex != null) {
            result = beginVertex.connect(endVertex, edgeWeight);
            if (result) edgeCount++;
        }
        return result;
    } // end addEdge

	public boolean addEdge(T begin, T end) {
		return addEdge(begin, end, 0);
	} //end addEdge

	public boolean hasEdge(T begin, T end) {
		boolean hasEdge = false;
		VertexInterface<T> beginVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        if (beginVertex != null && endVertex != null) {
            Iterator<VertexInterface<T>> neighbors = beginVertex.getNeighborIterator();
            while (!hasEdge && neighbors.hasNext()) {
                if (neighbors.next().equals(endVertex)) {
                    hasEdge = true;
                } //end if
            } //end while
        } //end if
        return hasEdge;
	} //end hasEdge

	public boolean isEmpty() {
		return vertices.isEmpty();
	} //end isEmpty

	public int getNumberOfVertices() {
		return vertices.getSize();
	} //end getNumberOfVertices

	public int getNumberOfEdges() {
		return edgeCount;
	} // end getNumberOfEdges

	public void clear() {
		vertices.clear();
		edgeCount = 0;
	} //end of clear

	protected void resetVertices() {
		Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
		while (vertexIterator.hasNext())
		{
			 VertexInterface<T> nextVertex = vertexIterator.next();
			 nextVertex.unvisit();
			 nextVertex.setCost(0);
			 nextVertex.setPredecessor(null);
		} // end while
	 } // end resetVertices
 

	public QueueInterface<T> getBreadthFirstTraversal(T origin) {
		//reset visited status of all vertices
		resetVertices();
        QueueInterface<T> traversalOrder = new LinkedQueue<>();
        QueueInterface<VertexInterface<T>> vertexQueue = new LinkedQueue<>();

        VertexInterface<T> originVertex = vertices.getValue(origin);

        if (originVertex != null) {
            originVertex.visit();
            traversalOrder.enqueue(origin); //enqueue vertex label
            vertexQueue.enqueue(originVertex); //enqueue vertex

            while (!vertexQueue.isEmpty()) {
                VertexInterface<T> frontVertex = vertexQueue.dequeue();
                Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();

                while (neighbors.hasNext()) {
                    VertexInterface<T> nextNeighbor = neighbors.next();
                    if (!nextNeighbor.isVisited()) {
                        nextNeighbor.visit();
                        traversalOrder.enqueue(nextNeighbor.getLabel());
                        vertexQueue.enqueue(nextNeighbor);
                    } //end if
                } //end while
            } //end while
        } //end if

        return traversalOrder;
    } //end getBreadthFirstTraversal

    public QueueInterface<T> getDepthFirstTraversal(T origin) {
		//reset visited status of all vertices
		resetVertices();

		//queue to store the traversal order
        QueueInterface<T> traversalOrder = new LinkedQueue<>();

		// Stack to perform depth-first traversal
        StackInterface<VertexInterface<T>> vertexStack = new ResizableArrayStack<>();

		//get vertex corresponding to origin value and mark it as visited
        VertexInterface<T> originVertex = vertices.getValue(origin);

        if (originVertex != null) {
            originVertex.visit();
            traversalOrder.enqueue(origin);
            vertexStack.push(originVertex);

			//perform depth-first traversal
            while (!vertexStack.isEmpty()) {
				//peek top vertex from stack
                VertexInterface<T> topVertex = vertexStack.peek();
				//get unvisited neighbor of the top vertex
                VertexInterface<T> nextNeighbor = topVertex.getUnvisitedNeighbor();

                if (nextNeighbor != null) {
					//if unvisited neighbor is found, mark as visited,
					//enqueue its label, and push onto stack
                    nextNeighbor.visit();
                    traversalOrder.enqueue(nextNeighbor.getLabel());
                    vertexStack.push(nextNeighbor);
                } else {
					//if no unvisited neighbor found, pop top vertex from stack
                    vertexStack.pop();
                } //end if
            }// end while
        } //end if

        return traversalOrder;
    } //end getDepthFirstTraversal

	public int getShortestPath(T begin, T end, StackInterface<T> path)
   {
      resetVertices();
      boolean done = false;
      QueueInterface<VertexInterface<T>> vertexQueue = new LinkedQueue<>();
      VertexInterface<T> originVertex = vertices.getValue(begin);
      VertexInterface<T> endVertex = vertices.getValue(end);
      originVertex.visit();
   
      // Assertion: resetVertices() has executed setCost(0)
      // and setPredecessor(null) for originVertex
   
      vertexQueue.enqueue(originVertex);
      while (!done && !vertexQueue.isEmpty())
      {
         VertexInterface<T> frontVertex = vertexQueue.dequeue();
         Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
         while (!done && neighbors.hasNext())
         {
            VertexInterface<T> nextNeighbor = neighbors.next();
            if (!nextNeighbor.isVisited())
            {
               nextNeighbor.visit();
               nextNeighbor.setCost(1 + frontVertex.getCost());
               nextNeighbor.setPredecessor(frontVertex);
               vertexQueue.enqueue(nextNeighbor);
            } // end if
   
            if (nextNeighbor.equals(endVertex))
               done = true;
         } // end while
      } // end while
   
      // Traversal ends; construct shortest path
      int pathLength = (int)endVertex.getCost();
      path.push(endVertex.getLabel());
   
      VertexInterface<T> vertex = endVertex;
      while (vertex.hasPredecessor())
      {
         vertex = vertex.getPredecessor();
         path.push(vertex.getLabel());
      } // end while
   
      return pathLength;
   } // end getShortestPath
} // end DirectedGraph
