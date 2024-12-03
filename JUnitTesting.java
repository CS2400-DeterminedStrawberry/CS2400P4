import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import ADTPackage.Graph.*;
import ADTPackage.Queue.LinkedQueue;

public class JUnitTesting {

    private Graph<String> graph;

    @Before
    public void setUp() {
        // Initialize the graph before each test
        graph = new Graph<>();

        // Add vertices
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");

        // Add edges
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "D");
        graph.addEdge("C", "E");
        graph.addEdge("D", "E");
    }

    @Test
    public void testAddVertex() {
        assertTrue(graph.addVertex("F")); // Add new vertex
        assertFalse(graph.addVertex("A")); // Adding duplicate vertex should fail
    }

    @Test
    public void testAddEdge() {
        assertTrue(graph.addEdge("A", "D"));
        assertFalse(graph.addEdge("A", "Z")); // Vertex Z does not exist
    }

    @Test
    public void testHasEdge() {
        assertTrue(graph.hasEdge("A", "B"));
        assertFalse(graph.hasEdge("B", "C"));
    }

    @Test
    public void testIsEmpty() {
        assertFalse(graph.isEmpty());
        graph.clear();
        assertTrue(graph.isEmpty());
    }

    @Test
    public void testGetNumberOfVertices() {
        assertEquals(5, graph.getNumberOfVertices());
    }

    @Test
    public void testGetNumberOfEdges() {
        assertEquals(5, graph.getNumberOfEdges());
    }

    @Test
    public void testBreadthFirstTraversal() {
        // Perform BFS starting from A
        LinkedQueue<String> bfsResult = (LinkedQueue<String>) graph.getBreadthFirstTraversal("A");

        // Expected traversal order: A -> B -> C -> D -> E
        String[] expectedOrder = {"A", "B", "C", "D", "E"};
        for (String expected : expectedOrder) {
            assertEquals(expected, bfsResult.dequeue());
        }
    }
}
