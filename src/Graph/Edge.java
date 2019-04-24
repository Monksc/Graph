package Graph;

import java.util.HashSet;

/**
 * Edge presents an Edge in Graph going from
 * Node parent to Node child
 * N is the node labels and E is for edge Labels
 *
 * @param <N> where N is the label of the Node
 * @param <E> where E is the label of the Edge
 * @author cameronmonks
 */
public class Edge<N, E> {

    private final HashSet<E> labels;
    private final Node<N, E> parent;
    private final Node<N, E> child;

    //
    // Abstraction Function:
    // Edge, e, represents the Edge
    //
    // Representation Invariant for every Edge e:
    // e.label != null && e.parent != null && e.child != null  && labels.size() > 0
    //

    /**
     * @effects Constructs a new Edge with values given
     * @param parent
     * @param label
     * @param child
     */
    public Edge(Node<N, E> parent, E label, Node<N, E> child) {
        labels = new HashSet<E>();
        labels.add(label);
        this.parent = parent;
        this.child = child;

        checkRep();
    }


    /**
     * Checks that the representation invariant holds (if any).
     **/
    // Throws a RuntimeException if the rep invariant is violated.
    private void checkRep() throws RuntimeException {
    	/*
    	if (labels == null || parent == null || child == null) {
    		throw new RuntimeException("Label or parent or child is null");
    	}

    	if (labels.size() == 0) {
    		throw new RuntimeException("Labels.size() = 0");
    	}
    	*/
    }


    /**
     * @modifies labels
     * @effects label is added to labels if labels doesn't contain label
     * @param label
     * @return true if label was added else false
     */
    public boolean addLabel(E label) {

        boolean r = labels.add(label);
        checkRep();
        return r;
    }

    /**
     * @modifies parent
     * @effect whatever the client changes it does to parent
     * @return parent
     */
    public Node<N, E> getParent() {
        return parent;
    }


    /**
     * @modifies child
     * @effect whatever the client changes it does to child
     * @return child
     */
    public Node<N, E> getChild() {
        return child;
    }

    /**
     *
     * @return  a copy of the labels
     */
    @SuppressWarnings("unchecked")
    public HashSet<E> getLabels() {
        return (HashSet<E>) labels.clone();
    }


    /**
     *
     * @return the amount of labels
     */
    public int getLabelsCount() {
        return labels.size();
    }

}
