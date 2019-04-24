import Graph.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class EdgeTest {

    @Test
    public void testConstructorBasic() {

        Node<String, String> parent = new Node<String, String>("Parent");
        Node<String, String> child = new Node<String, String>("Child");

        Edge<String, String> e = new Edge<String, String>(parent, "edge", child);
        parent.addEdge(e);
        child.addEdge(e);

        assert(e.getParent() == parent);
        assert(e.getLabels().contains("edge"));
        assert(e.getChild() == child);
    }

    @Test
    public void testConstructorSameName() {

        Node<String, String> parent = new Node<String, String>("");
        Node<String, String> child = new Node<String, String>("");

        Edge<String, String> e = new Edge<String, String>(parent, "", child);
        parent.addEdge(e);
        child.addEdge(e);

        assert(e.getParent() == parent);
        assert(e.getLabels().contains(""));
        assert(e.getChild() == child);
        assert(e.getParent() != child);
        assert(e.getChild() != parent);
    }

    @Test
    public void testConstructorReflexiveEdge() {

        Node<String, String> n = new Node<String, String>("A");

        Edge<String, String> e = new Edge<String, String>(n, "A", n);
        n.addEdge(e);

        assert(e.getLabels().contains("A"));
        assert(e.getParent() == n);
        assert(e.getChild() == n);
    }

}