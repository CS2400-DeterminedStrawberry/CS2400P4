package ADTPackage.Graph;
import java.util.Iterator;
import ADTPackage.*; // Classes that implement various ADTs
import ADTPackage.Dictionary.DictionaryInterface;
public final class Graph<T> implements GraphInterface<T>
{
	private DictionaryInterface<T, VertexInterface<T>> vertices;
	private int edgeCount;
	
	public Graph()
	{
		vertices = new LinkedDictionary<>();
		edgeCount = 0;
	} // end default constructor
	
	public boolean addVertex(T vertexLabel) {
		VertexInterface<T> addOutcome = vertices.add(vertexLabel, new Vertex<>(vertexLabel));
		return addOutcome == null;
	}

	public boolean addEdge(T begin, T end, double edgeWeight) {
	boolean result = false;
        VertexInterface<T> beginVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        if (beginVertex != null && endVertex != null) {
            result = beginVertex.connect(endVertex, edgeWeight);
            if (result) edgeCount++;
        }
        return result;
    }

	public boolean addEdge(T begin, T end) {
		return addEdge(begin, end, 0);
	}

	public boolean hasEdge(T begin, T end) {
		VertexInterface<T> beginVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        if (beginVertex != null && endVertex != null) {
            Iterator<VertexInterface<T>> neighbors = beginVertex.getNeighborIterator();
            while (neighbors.hasNext()) {
                if (neighbors.next().equals(endVertex)) {
                    return true;
                }
            }
        }
        return false;
	}

	public boolean isEmpty() {
		return vertices.isEmpty();
	}

	public int getNumberOfVertices() {
		return vertices.getSize();
	}

	public int getNumberOfEdges() {
		return edgeCount;
	}

	public void clear() {
		vertices.clear();
		edgeCount = 0;
	}

	public QueueInterface<T> getBreadthFirstTraversal(T origin) {
        QueueInterface<T> traversalOrder = new LinkedQueue<>();
        QueueInterface<VertexInterface<T>> vertexQueue = new LinkedQueue<>();
        VertexInterface<T> originVertex = vertices.getValue(origin);

        if (originVertex != null) {
            originVertex.visit();
            traversalOrder.enqueue(origin);
            vertexQueue.enqueue(originVertex);

            while (!vertexQueue.isEmpty()) {
                VertexInterface<T> frontVertex = vertexQueue.dequeue();
                Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();

                while (neighbors.hasNext()) {
                    VertexInterface<T> nextNeighbor = neighbors.next();
                    if (!nextNeighbor.isVisited()) {
                        nextNeighbor.visit();
                        traversalOrder.enqueue(nextNeighbor.getLabel());
                        vertexQueue.enqueue(nextNeighbor);
                    }
                }
            }
        }

        resetVertices();
        return traversalOrder;
    }

    public QueueInterface<T> getDepthFirstTraversal(T origin) {
        QueueInterface<T> traversalOrder = new LinkedQueue<>();
        StackInterface<VertexInterface<T>> vertexStack = new LinkedStack<>();
        VertexInterface<T> originVertex = vertices.getValue(origin);

        if (originVertex != null) {
            originVertex.visit();
            traversalOrder.enqueue(origin);
            vertexStack.push(originVertex);

            while (!vertexStack.isEmpty()) {
                VertexInterface<T> topVertex = vertexStack.peek();
                VertexInterface<T> nextNeighbor = topVertex.getUnvisitedNeighbor();

                if (nextNeighbor != null) {
                    nextNeighbor.visit();
                    traversalOrder.enqueue(nextNeighbor.getLabel());
                    vertexStack.push(nextNeighbor);
                } else {
                    vertexStack.pop();
                }
            }
        }

        resetVertices();
        return traversalOrder;
    }
} // end DirectedGraph
