import Graph.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import static org.junit.Assert.*;

public class GraphTest {


    @Test
    public void testConstructor() {

        Graph<String, String> g = new Graph<String, String>();

        assert(g.getNodes().size() == 0);
    }


    @Test
    public void testAddingNodeWithSameNameTwice() {

        Graph<String, String> g = new Graph<String, String>();

        assert(g.addNode("A"));
        assert(!g.addNode("A"));

        assert(g.hasNode("A"));
        assert(g.getNodes().size() == 1);
        assert(g.getNodesConnectedTo("A").size() == 0);
    }


    @Test
    public void testAddingEdgeWithNotAValidNode() {

        Graph<String, String> g = new Graph<String, String>();


        assert(g.addNode("A"));
        assert(!g.addEdge("A", "B", "ab"));
        assert(g.addNode("B"));


        assert(g.hasNode("A"));
        assert(g.hasNode("B"));
        assert(g.getNodes().size() == 2);

        assert(g.getNodesConnectedTo("not a node").size() == 0);
        assert(g.getNodesGoingIntoNode("not a node").size() == 0);
        assert(g.getNodesConnectedTo("A").size() == 0);
        assert(g.getNodesConnectedTo("B").size() == 0);
    }


    @Test
    public void testAddingNodeAndEdgeOfAllWays() {

        Graph<String, String> g = new Graph<String, String>();

        assert(g.addNode("A"));
        assert(!g.addEdge("A", "B", "ab"));
        assert(!g.addEdge("B", "A", "ba"));

        assert(g.addNode("B"));
        assert(g.addEdge("A", "B", "ab"));
        assert(!g.addEdge("A", "B", "ab"));
    }

    @Test
    public void testNodeCountAndEdgeCount() {

        Graph<String, String> g = new Graph<String, String>();

        assert(g.addNode("A"));
        assert(g.addNode("B"));

        assert(!g.addNode("A"));
        assert(!g.addNode("B"));

        assert(g.addNode("C"));
        assert(g.addNode("D"));

        assert(!g.addNode("C"));
        assert(!g.addNode("D"));


        assert(g.addEdge("A", "B", "AB"));

        assert(g.addEdge("A", "C", "AC"));
        assert(!g.addEdge("A", "C", "AC"));

        assert(g.addEdge("A", "D", "AD"));

        assert(g.edgeCount() == 3);
        assert(g.nodeCount() == 4);
    }

    @Test
    public void testMethodHasNode() {

        Graph<String, String> g = new Graph<String, String>();

        assert(g.addNode("A"));
        assert(g.addNode("B"));

        assert(!g.addNode("A"));
        assert(!g.addNode("B"));

        assert(g.addNode("C"));
        assert(g.addNode("D"));

        assert(!g.addNode("C"));
        assert(!g.addNode("D"));

        assert(g.hasNode("A"));
        assert(g.hasNode("B"));
        assert(g.hasNode("C"));
        assert(g.hasNode("D"));

        assert(!g.hasNode(""));
        assert(!g.hasNode(null));
        assert(!g.hasNode("AA"));
        assert(!g.hasNode("E"));
    }

    @Test
    public void testNodesConnectedTo() {

        Graph<String, String> g = new Graph<String, String>();
        assert(g.addNode("A"));
        assert(g.addNode("B"));
        assert(g.addNode("C"));
        assert(g.addNode("D"));

        assert(g.addEdge("A", "B", "AB"));
        assert(g.addEdge("B", "A", "BA"));
        assert(g.addEdge("A", "C", "AC"));

        HashSet<String> s = g.getNodesConnectedTo("A");
        assert(s.contains("B"));
        assert(s.contains("C"));
        assert(s.size() == 2);

        s = g.getNodesConnectedTo("B");
        assert(s.contains("A"));
        assert(s.size() == 1);

        s = g.getNodesConnectedTo("C");
        assert(s.size() == 0);

        s = g.getNodesConnectedTo("D");
        assert(s.size() == 0);

        s = g.getNodesConnectedTo("");
        assert(s.size() == 0);

        s = g.getNodesConnectedTo(null);
        assert(s.size() == 0);
    }

    @Test
    public void testEdgesBetweenNodes() {

        Graph<String, String> g = new Graph<String, String>();
        assert(g.addNode("A"));
        assert(g.addNode("B"));
        assert(g.addNode("C"));
        assert(g.addNode("D"));

        assert(g.addEdge("A", "B", "AB1"));
        assert(g.addEdge("A", "B", "AB2"));
        assert(g.addEdge("B", "A", "BA"));
        assert(g.addEdge("A", "C", "AC"));

        HashSet<String> s = g.getEdgesBetweenNodes("A", "B");
        assert(s.contains("AB1"));
        assert(s.contains("AB2"));
        assert(s.size() == 2);

        s = g.getEdgesBetweenNodes("B", "A");
        assert(s.contains("BA"));
        assert(s.size() == 1);

        s = g.getEdgesBetweenNodes("A", "C");
        assert(s.contains("AC"));
        assert(s.size() == 1);

        s = g.getEdgesBetweenNodes("C", "A");
        assert(s.size() == 0);

        s = g.getEdgesBetweenNodes("A", "A");
        assert(s.size() == 0);

        s = g.getEdgesBetweenNodes("D", "A");
        assert(s.size() == 0);

        s = g.getEdgesBetweenNodes("A", "D");
        assert(s.size() == 0);

        s = g.getEdgesBetweenNodes("A", "D");
        assert(s.size() == 0);

        s = g.getEdgesBetweenNodes(null, "D");
        assert(s.size() == 0);

        s = g.getEdgesBetweenNodes("D", null);
        assert(s.size() == 0);


        s = g.getEdgesBetweenNodes(null, null);
        assert(s.size() == 0);

        s = g.getEdgesBetweenNodes("Z", "D");
        assert(s.size() == 0);

        s = g.getEdgesBetweenNodes("D", "Z");
        assert(s.size() == 0);


        s = g.getEdgesBetweenNodes("Z", "Q");
        assert(s.size() == 0);

    }

    @Test
    public void testDijkstra() {

        Graph<String, Double> g = new Graph<String,Double>();

        g.addNode("A");
        g.addNode("B");
        g.addNode("C");
        g.addNode("D");

        g.addEdge("A", "D", 10.0);
        g.addEdge("A", "D", 9.0);
        g.addEdge("A", "B", 10.0);
        g.addEdge("A", "B", 9.0);
        g.addEdge("B", "D", 0.0);
        g.addEdge("A", "C", 5.0);
        g.addEdge("C", "D", 1.0);


        Map<String, Double> distancesFromA = g.dijkstra("A", false, (Double d) -> d);


        assert(distancesFromA.size() == 4);
        assert(distancesFromA.get("A").equals(0.0));
        assert(distancesFromA.get("B").equals(9.0));
        assert(distancesFromA.get("C").equals(5.0));
        assert(distancesFromA.get("D").equals(6.0));



        // Reverse
        distancesFromA = g.dijkstra("D", true, (Double d) -> d);


        assert(distancesFromA.size() == 4);
        assert(distancesFromA.get("A").equals(6.0));
        assert(distancesFromA.get("B").equals(0.0));
        assert(distancesFromA.get("C").equals(1.0));
        assert(distancesFromA.get("D").equals(0.0));

    }

    @Test
    public void testFindShortestPath() {

        Graph<String, Double> g = new Graph<String,Double>();

        g.addNode("A");
        g.addNode("B");
        g.addNode("C");
        g.addNode("D");

        g.addEdge("A", "D", 10.0);
        g.addEdge("A", "D", 9.5);
        g.addEdge("A", "B", 10.0);
        g.addEdge("A", "B", 9.0);
        g.addEdge("B", "D", 0.0);
        g.addEdge("A", "C", 5.0);
        g.addEdge("C", "D", 6.0);

        ArrayList<Map.Entry<Map.Entry<String, String>, Double>> path = g.findPath("A", "D", (Double d) -> d);


        assert(path.size() == 2);

        assert(path.get(0).getKey().getKey().equals("A"));
        assert(path.get(0).getKey().getValue().equals("B"));
        assert(path.get(0).getValue().equals(9.0));

        assert(path.get(1).getKey().getKey().equals("B"));
        assert(path.get(1).getKey().getValue().equals("D"));
        assert(path.get(1).getValue().equals(0.0));

    }

}