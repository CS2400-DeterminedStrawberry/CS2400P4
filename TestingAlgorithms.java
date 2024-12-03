import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class TestingAlgorithms {
        private Map<String, List<String>> adjacencyList;

    public TestingAlgorithms() {
        adjacencyList = new HashMap<>();
    }

    //add vertex
    public void addVertex(String label) {
        adjacencyList.putIfAbsent(label, new ArrayList<>());
    }

    //add edge
    public void addEdge(String start, String end) {
        adjacencyList.get(start).add(end);
        adjacencyList.get(end).add(start); // For undirected graph
    }

    //Breadth-First Search
    public List<String> breadthFirstTraversal(String start) {
        List<String> visited = new ArrayList<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (!visited.contains(current)) {
                visited.add(current);
                for (String neighbor : adjacencyList.get(current)) {
                    if (!visited.contains(neighbor)) {
                        queue.add(neighbor);
                    }
                }
            }
        }
        return visited;
    }

    //Depth-First Search 
    public List<String> depthFirstTraversal(String start) {
        List<String> visited = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            String current = stack.pop();
            if (!visited.contains(current)) {
                visited.add(current);
                for (String neighbor : adjacencyList.get(current)) {
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }
        return visited;
    }

    //BFS Tree
    public Map<String, List<String>> generateBreadthFirstTree(String start) {
        Map<String, List<String>> bfsTree = new HashMap<>();
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (!visited.contains(current)) {
                visited.add(current);
                bfsTree.putIfAbsent(current, new ArrayList<>());
                for (String neighbor : adjacencyList.get(current)) {
                    if (!visited.contains(neighbor)) {
                        queue.add(neighbor);
                        bfsTree.get(current).add(neighbor);
                    }
                }
            }
        }
        return bfsTree;
    }

    //DFS Tree
    public Map<String, List<String>> generateDepthFirstTree(String start) {
        Map<String, List<String>> dfsTree = new HashMap<>();
        Stack<String> stack = new Stack<>();
        Set<String> visited = new HashSet<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            String current = stack.pop();
            if (!visited.contains(current)) {
                visited.add(current);
                dfsTree.putIfAbsent(current, new ArrayList<>());
                for (String neighbor : adjacencyList.get(current)) {
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                        dfsTree.get(current).add(neighbor);
                    }
                }
            }
        }
        return dfsTree;
    }

    //Traverse BFS
    public List<String> traverseTree(Map<String, List<String>> tree, String start) {
        List<String> visited = new ArrayList<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (!visited.contains(current)) {
                visited.add(current);
                List<String> neighbors = tree.getOrDefault(current, new ArrayList<>());
                queue.addAll(neighbors);
            }
        }
        return visited;
    }

    public static void main(String[] args) {
        TestingAlgorithms graph = new TestingAlgorithms();

        //Create the graph
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");
        graph.addVertex("F");
        graph.addVertex("G");
        graph.addVertex("H");
        graph.addVertex("I");

        graph.addEdge("A", "B");
        graph.addEdge("A", "D");
        graph.addEdge("A", "E");
        graph.addEdge("B", "E");
        graph.addEdge("D", "G");
        graph.addEdge("E", "F");
        graph.addEdge("E", "H");
        graph.addEdge("G", "H");
        graph.addEdge("F", "C");
        graph.addEdge("F", "H");
        graph.addEdge("H", "I");
        graph.addEdge("C", "B");
        graph.addEdge("I", "F");
        
        System.out.println("Task 1:");
        //BFS
        System.out.println("Breadth-First Traversal:");
        System.out.println(graph.breadthFirstTraversal("A"));

        System.out.println();
        System.out.print("Task 2:");
        //DFS
        System.out.println("\nDepth-First Traversal:");
        System.out.println(graph.depthFirstTraversal("A"));

        System.out.println();
        System.out.print("Task 3:");
        //BFS Tree
        Map<String, List<String>> bfsTree = graph.generateBreadthFirstTree("A");
        System.out.println("\nBFS Tree Traversal:");
        System.out.println(graph.traverseTree(bfsTree, "A"));

        //DFS Tree
        Map<String, List<String>> dfsTree = graph.generateDepthFirstTree("A");
        System.out.println("\nDFS Tree Traversal:");
        System.out.println(graph.traverseTree(dfsTree, "A"));
    }
}
