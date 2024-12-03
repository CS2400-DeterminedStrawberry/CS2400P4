package ADTPackage.Graph;
import java.util.Iterator;
import java.util.NoSuchElementException;
import ADTPackage.List.*; // Classes that implement various ADTs
/**
 A class of vertices for a graph.
 @author Frank M. Carrano
 @author Timothy M. Henry
 @version 5.0
 */
class Vertex<T> implements VertexInterface<T>
{
   private T label;
   private ListWithIteratorInterface<Edge> edgeList; // Edges to neighbors
   private boolean visited;                          // True if visited
   private VertexInterface<T> previousVertex;        // On path to this vertex
   private double cost;                              // Of path to this vertex
   
   public Vertex(T vertexLabel)
   {
      label = vertexLabel;
      edgeList = new LinkedListWithIterator<>();
      visited = false;
      previousVertex = null;
      cost = 0;
   } // end constructor

   public T getLabel() {
      return label;
   } //end getLabel

   public void visit() {
      visited = true;
   } //end visit

   public void unvisit() {
      visited = false;
   } //end unvisit

   public boolean isVisited() {
      return visited;
   }//end isVisited

   public boolean connect(VertexInterface<T> endVertex, double edgeWeight) {
      boolean result = false;

      if (!this.equals(endVertex))
       {  // Vertices are distinct
          Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
          boolean duplicateEdge = false;
    
          while (!duplicateEdge && neighbors.hasNext())
          {
             VertexInterface<T> nextNeighbor = neighbors.next();
             if (endVertex.equals(nextNeighbor))
                duplicateEdge = true;
          } // end while
    
          if (!duplicateEdge)
          {
             edgeList.add(new Edge(endVertex, edgeWeight));
             result = true;
          } // end if
       } // end if
    
       return result;
    } // end connect

    public boolean connect(VertexInterface<T> endVertex)
    {
       return connect(endVertex, 0);
    } // end connect

    public Iterator<VertexInterface<T>> getNeighborIterator()
    {
        return new NeighborIterator();
    } // end getNeighborIterator

    public boolean hasNeighbor()
    {
       return !edgeList.isEmpty();
    } // end hasNeighbor
 
    public VertexInterface<T> getUnvisitedNeighbor()
    {
       VertexInterface<T> result = null;
    
       Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
       while ( neighbors.hasNext() && (result == null) )
       {
          VertexInterface<T> nextNeighbor = neighbors.next();
          if (!nextNeighbor.isVisited())
             result = nextNeighbor;
       } // end while
    
       return result;
    } // end getUnvisitedNeighbor
 
    public void setPredecessor(VertexInterface<T> predecessor)
    {

    } // end setPredecessor
 
    public VertexInterface<T> getPredecessor()
    {
        return previousVertex;
    } // end getPredecessor
 
    public boolean hasPredecessor()
    {
        return previousVertex != null;
    } // end hasPredecessor

    public boolean equals(Object other)
    {
        boolean result;
        if((other == null) || (getClass() != other.getClass()))
            result = false;
        else{
            @SuppressWarnings("unchecked")
            Vertex<T> otherVertex = (Vertex<T>)other;
            result = label.equals(otherVertex.label);
        }
        return result;
    } // end equals
 
    public void setCost(double newCost)
    {
        cost = newCost;
    } // end setCost
 
    public double getCost()
    {
        return cost;
    } // end getCost


    private class NeighborIterator implements Iterator<VertexInterface<T>>
    {
        private Iterator<Edge> edges;
        private NeighborIterator()
        {
            edges = edgeList.getIterator();
        } // end default constructor
        public boolean hasNext()
        {
            return edges.hasNext();
        } // end hasNext
        public VertexInterface<T> next()
        {
            VertexInterface<T> nextNeighbor = null;
            if (edges.hasNext()){
                Edge edgeToNextNeighbor = edges.next();
                nextNeighbor = edgeToNextNeighbor.getEndVertex();
            }
            else
                throw new NoSuchElementException();
            return nextNeighbor;
        } // end next
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }

   protected class Edge
   {
      private VertexInterface<T> vertex; // Vertex at end of edge
      private double weight;
      
      protected Edge(VertexInterface<T> endVertex, double edgeWeight)
      {
         vertex = endVertex;
         weight = edgeWeight;
      } // end constructor
      
      protected Edge(VertexInterface<T> endVertex)
      {
         vertex = endVertex;
         weight = 0;
      } // end constructor

      protected VertexInterface<T> getEndVertex()
      {
         return vertex;
      } // end getEndVertex
      
      protected double getWeight()
      {
         return weight; 
      } // end getWeight
   } // end Edge
} // end Vertex
