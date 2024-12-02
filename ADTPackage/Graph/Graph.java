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
} // end DirectedGraph
