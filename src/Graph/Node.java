package Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;


/**
 * Node represents a node in a graph
 * N is the node labels and E is for edge Labels
 *
 * @param <N> where N is the label of the Node
 * @param <E> where E is the label of the Edge
 * @author cameronmonks
 */
public class Node<N, E> {

    private final N label;

    // A Hashmap that has a key of next node label
    // and a value of an edge that connects them
    private HashMap<N, Edge<N, E>> nextNodeLabelToEdgeMap;

    // A Hashmap that has a key of previous node label
    // and a value of an edge that connects them
    private HashMap<N, Edge<N, E>> prevNodeLabelToEdgeMap;

    // Abstraction Function:
    // Node, n, represents the Node
    //
    // Representation Invariant for every Graph g:
    //
    // foreach E s, Edge e in n.nextNodeLabelToEdgeMap: e.parent == n && e.child.label == s
    // foreach E s, Edge e in n.prevNodeLabelToEdgeMap: e.child == n && e.parent.label == s
    //
    // In other words:
    // nextNodeLabelToEdgeMap, prevNodeLabelToEdgeMap and label cannot be null
    // All N in nextNodeLabelToEdgeMap must be equal to the edge's child.label
    // All Edge e in nextNodeLabelToEdgeMap, e.parent must be this
    // All N in prevNodeLabelToEdgeMap must be equal to the edge's parent.label
    // All Edge e in nextNodeLabelToEdgeMap, e.child must be this

    /**
     * @require label != null
     * @effects Constructs a Node with the label given
     * @param label
     */
    public Node(N label) {
        this.label = label;
        nextNodeLabelToEdgeMap = new HashMap<N, Edge<N, E>>();
        prevNodeLabelToEdgeMap = new HashMap<N, Edge<N, E>>();
        checkRep();
    }


    /**
     * Checks that the representation invariant holds (if any).
     **/
    // Throws a RuntimeException if the rep invariant is violated.
    private void checkRep() throws RuntimeException {
    	/*
    	if (label == null || nextNodeLabelToEdgeMap == null || prevNodeLabelToEdgeMap == null) {
    		throw new RuntimeException("Label = null or nextNodeLabelToEdgeMap = null or prevNodeLabelToEdgeMap = null");
    	}



    	for (Entry<N, Edge<N, E>> pair : nextNodeLabelToEdgeMap.entrySet()){

    		if (!pair.getKey().equals(pair.getValue().getChild().getLabel())) {
    			throw new RuntimeException("Key doesnt match nextNodeLabelToEdgeMap child node label");
    		}

    		if (this == pair.getValue().getParent()) {
    			throw new RuntimeException("this.label doesnt match nextNodeLabelToEdgeMap parent.node.label");
    		}
        }

    	for (Entry<N, Edge<N, E>> pair : prevNodeLabelToEdgeMap.entrySet()){

    		if (!pair.getKey().equals(pair.getValue().getParent().getLabel())) {
    			throw new RuntimeException("Key doesnt match prevNodeLabelToEdgeMap parent node label");
    		}

    		if (this == pair.getValue().getChild()) {
    			throw new RuntimeException("this.label doesnt match prevNodeLabelToEdgeMap child.node.label");
    		}
        }
        */
    }


    /**
     *
     * @return label
     */
    public N getLabel() {
        return label;
    }

    /**
     * @require N node != null
     * @param N node
     * @return returns Edge e in nextNodeLabelToEdgeMap where e.child.label = node if it exist
     * 			else null
     */
    public Edge<N, E> getEdgeToNextNode(N node) {
        return nextNodeLabelToEdgeMap.get(node);
    }


    /**
     * @require N node != null
     * @param N node
     * @return returns Edge e in nextNodeLabelToEdgeMap where e.parent.label = node if it exist
     * 			else null
     */
    public Edge<N, E> getEdgeToPrevNode(N node) {
        return prevNodeLabelToEdgeMap.get(node);
    }



    /**
     * If e.parent == this then it adds edge to nextNodeLabelToEdgeMap with key edge.child.label
     * If e.child == this then it adds edge to prevNodeLabelToEdgeMap with key edge.parent.label
     *
     * @require Edge e != null
     * @param e
     */
    public void addEdge(Edge<N, E> e) {

        if (e.getParent() == this) {
            nextNodeLabelToEdgeMap.put(e.getChild().getLabel(), e);
        }

        if (e.getChild() == this) {
            prevNodeLabelToEdgeMap.put(e.getParent().getLabel(), e);
        }
    }


    /**
     * Returns a HashSet of the labels of the nodes connected to it
     * @return a HashSet of the labels of the nodes connected to it
     */
    public HashSet<N> getNextStringLabels() {

        HashSet<N> nextNodes = new HashSet<N>();
        for (Entry<N, Edge<N, E>> entry : nextNodeLabelToEdgeMap.entrySet()) {
            nextNodes.add(entry.getKey());
        }

        return nextNodes;
    }

    /**
     * Returns a HashSet of the labels of the nodes going into it
     * @return a HashSet of the labels of the nodes going into it
     */
    public HashSet<N> getPrevStringLabels() {

        HashSet<N> prevNodes = new HashSet<N>();
        for (Entry<N, Edge<N, E>> entry : prevNodeLabelToEdgeMap.entrySet()) {
            prevNodes.add(entry.getKey());
        }

        return prevNodes;
    }

    /**
     * @param node the node that is connected to
     * @return a HashSet of E labels that goes from this into node
     * 		if their exist an edge else returns empty set
     */
    public HashSet<E> getLabelsGoingIntoNode(N node) {

        Edge<N, E> e = nextNodeLabelToEdgeMap.get(node);
        if (e == null) return new HashSet<E>();
        return e.getLabels();
    }


    /**
     *
     * @return amount of edges going into this node
     */
    public int getIncomingEdgeCount() {

        int count = 0;

        for (Entry<N, Edge<N, E>> pair : prevNodeLabelToEdgeMap.entrySet()) {

            count += pair.getValue().getLabelsCount();
        }

        return count;
    }

    /**
     *
     * @return amount of edges going out of this node
     */
    public int getOutgoingEdgeCount() {


        int count = 0;

        for (Entry<N, Edge<N, E>> pair : nextNodeLabelToEdgeMap.entrySet()) {

            count += pair.getValue().getLabelsCount();
        }

        return count;
    }

}
