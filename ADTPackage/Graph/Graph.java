package ADTPackage.Graph;
import java.util.Iterator;
import ADTPackage.*; // Classes that implement various ADTs
public final class Graph<T> implements GraphInterface<T>
{
	private DictionaryInterface<T, VertexInterface<T>> vertices;
	private int edgeCount;
	
	public Graph()
	{
		vertices = new LinkedDictionary<>();
		edgeCount = 0;
	} // end default constructor

/* Implementations of the graph operations go here. 
   . . . */
  
} // end DirectedGraph
