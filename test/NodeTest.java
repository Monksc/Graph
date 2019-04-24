import Graph.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class NodeTest {


    @Test
    public void testConstructor() {

        Node<String, String> n1 = new Node<String, String>("");
        assert(n1.getLabel().equals(""));

        // test to see if you can change label
        String label = "Hello World";
        Node<String, String> n2 = new Node<String, String>(label);
        label = "Goodbye World";
        assert(n2.getLabel().equals("Hello World"));

        // test to see if you can change label
        Node<String, String> n3 = new Node<String, String>("Test3");
        String label3 = n3.getLabel();
        label3 = "TEST";
        assert(n3.getLabel().equals("Test3"));
    }


    @Test
    public void testingAddingEdgesBasics() {

        Node<String, String> n1 = new Node<String, String>("Node 1");
        Node<String, String> n2 = new Node<String, String>("Node 2");

        Edge<String, String> n1ToN2 = new Edge<String, String>(n1, "EDGE", n2);

        n1.addEdge(n1ToN2);
        n2.addEdge(n1ToN2);

        assert(n1.getEdgeToNextNode("Node 1") == null);
        assert(n1.getEdgeToNextNode("Node 2") == n1ToN2);
        assert(n1.getEdgeToNextNode("EDGE") == null);

        assert(n1.getEdgeToPrevNode("Node 1") == null);
        assert(n1.getEdgeToPrevNode("Node 2") == null);
        assert(n1.getEdgeToPrevNode("EDGE") == null);


        assert(n2.getEdgeToNextNode("Node 1") == null);
        assert(n2.getEdgeToNextNode("Node 2") == null);
        assert(n2.getEdgeToNextNode("EDGE") == null);

        assert(n2.getEdgeToPrevNode("Node 1") == n1ToN2);
        assert(n2.getEdgeToPrevNode("Node 2") == null);
        assert(n2.getEdgeToPrevNode("EDGE") == null);

    }


    @Test
    public void testAddingEdgesLine() {

        Node<String, String> n1 = new Node<String, String>("Head");
        Node<String, String> n2 = new Node<String, String>("Node 2");
        Node<String, String> n3 = new Node<String, String>("Node 3");
        Node<String, String> n4 = new Node<String, String>("Tail");

        Edge<String, String> e1to2 = new Edge<String, String>(n1, "e1to2", n2);
        n1.addEdge(e1to2);
        n2.addEdge(e1to2);

        Edge<String, String> e2to3 = new Edge<String, String>(n2, "e2to3", n3);
        n2.addEdge(e2to3);
        n3.addEdge(e2to3);

        Edge<String, String> e3to4 = new Edge<String, String>(n3, "e3to4", n4);
        n3.addEdge(e3to4);
        n4.addEdge(e3to4);

        // forward pass
        assert(n1.getEdgeToNextNode("Node 2").getParent() == n1);
        assert(n1.getEdgeToNextNode("Node 2").getChild() == n2);

        assert(n2.getEdgeToNextNode("Node 3").getParent() == n2);
        assert(n2.getEdgeToNextNode("Node 3").getChild() == n3);

        assert(n3.getEdgeToNextNode("Tail").getParent() == n3);
        assert(n3.getEdgeToNextNode("Tail").getChild() == n4);

        // previous pass
        assert(n4.getEdgeToPrevNode("Node 3").getParent() == n3);
        assert(n4.getEdgeToPrevNode("Node 3").getChild() == n4);

        assert(n3.getEdgeToPrevNode("Node 2").getParent() == n2);
        assert(n3.getEdgeToPrevNode("Node 2").getChild() == n3);

        assert(n2.getEdgeToPrevNode("Head").getParent() == n1);
        assert(n2.getEdgeToPrevNode("Head").getChild() == n2);


        // Testing for if the node doesn't exist
        assert(n1.getEdgeToNextNode("Head") == null);
        assert(n1.getEdgeToNextNode("Node 3") == null);
        assert(n1.getEdgeToNextNode("Tail") == null);
        assert(n1.getEdgeToNextNode("") == null);

        assert(n1.getEdgeToPrevNode("Head") == null);
        assert(n1.getEdgeToPrevNode("Node 2") == null);
        assert(n1.getEdgeToPrevNode("Node 3") == null);
        assert(n1.getEdgeToPrevNode("Tail") == null);
        assert(n1.getEdgeToPrevNode("") == null);

    }


    @Test
    public void testAddingMultipleEdgesToNode() {

        Node<String, String> head = new Node<String, String>("HEAD");

        Node<String, String> n1 = new Node<String, String>("N1");
        Node<String, String> n2 = new Node<String, String>("N2");
        Node<String, String> n3 = new Node<String, String>("N3");
        Node<String, String> n4 = new Node<String, String>("N4");

        Edge<String, String> e1 = new Edge<String, String>(head, "e1", n1);
        Edge<String, String> e2 = new Edge<String, String>(head, "e2", n2);
        Edge<String, String> e3 = new Edge<String, String>(head, "e3", n3);
        Edge<String, String> e4 = new Edge<String, String>(head, "e4", n4);

        head.addEdge(e1);
        head.addEdge(e2);
        head.addEdge(e3);
        head.addEdge(e4);

        n1.addEdge(e1);
        n2.addEdge(e2);
        n3.addEdge(e3);
        n4.addEdge(e4);

        assert(head.getEdgeToNextNode("N1") == e1);
        assert(head.getEdgeToNextNode("N2") == e2);
        assert(head.getEdgeToNextNode("N3") == e3);
        assert(head.getEdgeToNextNode("N4") == e4);

        assert(n1.getEdgeToPrevNode("HEAD") == e1);
        assert(n2.getEdgeToPrevNode("HEAD") == e2);
        assert(n3.getEdgeToPrevNode("HEAD") == e3);
        assert(n4.getEdgeToPrevNode("HEAD") == e4);
    }

    @Test
    public void testReflexiveEdgeSameName() {

        Node<String, String> n = new Node<String, String>("");

        Edge<String, String> e = new Edge<String, String>(n, "", n);

        n.addEdge(e);

        assert(n.getEdgeToNextNode("") == e);
        assert(n.getEdgeToPrevNode("") == e);
    }

}