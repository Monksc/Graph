package Graph;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.ToDoubleFunction;

/**
 * Graph represent a directed multigraph with nodes and edges and reflexive edges
 * Each node has a N as a label and
 * Each edge has a E as a label
 *
 * @param <N> where N is the label of the Node
 * @param <E> where E is the label of the Edge
 * @author cameronmonks
 */
public class Graph<N, E> {

    private HashMap<N, Node<N, E>> nodes;
    private HashSet<Edge<N, E>> edges;

    // Abstraction Function:
    // Graph, g, represents the Graph
    //
    // Representation Invariant for every Graph g:
    // nodes != null && edges != null &&
    // foreach Node i,j in nodes: i != j i.label != j.label
    // foreach Edge e in edges: e.parent is an element in nodes && e.child is an element in nodes
    // foreach Edge i,j in edges: (i != j && (i.parent != j.parent || i.child != j.child))
    //
    // In other words:
    // nodes and edges cannot be null
    // Every node in nodes has a unique label
    // All edges' parent/child node must be in this.nodes
    // Every edge must be unique based on their parent and child

    /**
     * @effects Constructs a new Graph with value no nodes or edges
     */
    public Graph() {

        nodes = new HashMap<N, Node<N, E>>();
        edges = new HashSet<Edge<N, E>>();

        checkRep();
    }

    /**
     * Checks that the representation invariant holds (if any).
     **/
    // Throws a RuntimeException if the rep invariant is violated.
    private void checkRep() throws RuntimeException {
    	/*
    	if (nodes == null) {
    		throw new RuntimeException("Nodes is null");
    	}

    	if (edges == null) {
    		throw new RuntimeException("edges is null");
    	}

    	for (Entry<N, Node<N, E>> pair : nodes.entrySet()) {

    		if (!pair.getKey().equals(pair.getValue().getLabel())) {
    			throw new RuntimeException("nodes key is not the same as value label");
    		}
        }

    	for (Edge<N, E> e : edges) {
            if (!nodes.containsKey(e.getParent().getLabel())) {
            	throw new RuntimeException("Edge contains a parent node thats not in graph");
            }
            if (!nodes.containsKey(e.getChild().getLabel())) {
            	throw new RuntimeException("Edge contains a child node thats not in graph");
            }
        }
        */

    }


    /**
     * @modifies this.graph
     * @effects adds a node with nodeData to graph if its not already in their
     * @param nodeData
     * @return true if nodeData is added else false
     */
    public boolean addNode(N nodeData) {

        if (!nodes.containsKey(nodeData)) {
            Node<N, E> n = new Node<N, E>(nodeData);
            nodes.put(nodeData, n);
            checkRep();
            return true;
        }

        checkRep();
        return false;
    }

    /**
     *
     * @param node
     * @return true if node is in the set nodes else false
     */
    public boolean hasNode(N node) {

        return nodes.containsKey(node);
    }

    /**
     *
     * @param node
     * @return returns a HashSet<N> of the label of  the nodes that
     * 			are connected from @param node to the new node. If node doesn't
     * 			exist it returns empty HashSet<N>
     */
    public HashSet<N> getNodesConnectedTo(N node) {

        Node<N, E> n = nodes.get(node);
        if (n == null) return new HashSet<N>();

        return n.getNextStringLabels();
    }

    /**
     *
     * @param node
     * @return returns a HashSet<N> of the label of  the nodes that
     * 			are connected from @param node to the new node. If node doesn't
     * 			exist it returns empty HashSet<N>
     */
    public HashSet<N> getNodesGoingIntoNode(N node) {

        Node<N, E> n = nodes.get(node);
        if (n == null) return new HashSet<N>();

        return n.getPrevStringLabels();
    }


    /**
     *
     * @param fromNode
     * @param toNode
     * @return a HashSet<N> of the labels between the nodes, fromNode to toNode
     * 		if their doesn't exist an edge between them for any reason it returns
     * 		the empty set
     */
    public HashSet<E> getEdgesBetweenNodes(N fromNode, N toNode) {

        Node<N, E> n = nodes.get(fromNode);
        if (n == null) return new HashSet<E>();

        return n.getLabelsGoingIntoNode(toNode);
    }

    /**
     * @require parentNode != null && childNode != null && edgeLabel != null
     * @modifies this.graph
     * @effects adds an edge going from parent node to
     * 		childNode with edgeLabel as the label if parentNode and childNode exist
     * @param parentNode
     * @param childNode
     * @param edgeLabel
     * @return true if edge is added else false
     */
    public boolean addEdge(N parentNode, N childNode, E edgeLabel) {

        Node<N, E> parent = nodes.get(parentNode);
        Node<N, E> child  = nodes.get(childNode);

        if (parent == null || child == null) {
            checkRep();
            return false;
        }

        Edge<N, E> e = parent.getEdgeToNextNode(child.getLabel());

        if (e == null) {
            Edge<N, E> newEdge = new Edge<N, E>(parent, edgeLabel, child);
            edges.add(newEdge);
            parent.addEdge(newEdge);
            child.addEdge(newEdge);
            checkRep();
            return true;
        }


        boolean didAdd = e.addLabel(edgeLabel);
        checkRep();
        return didAdd;
    }


    /**
     *
     * @return an ArrayList<N> of the nodes in the graph
     */
    public ArrayList<N> getNodes() {

        ArrayList<N> nodesList = new ArrayList<N>();

        for (Entry<N, Node<N, E>> entry : nodes.entrySet()) {

            nodesList.add(entry.getKey());
        }

        return nodesList;
    }


    /**
     *
     * @return the amount of nodes in the graph
     */
    public int nodeCount() {
        return nodes.size();
    }

    /**
     *
     * @return the amount of edges in the graph
     */
    public int edgeCount() {
        return edges.size();
    }


    /**
     * @required N headNode is a node in the graph
     * @required this.graph != null
     * @param headNode the start of the node in the dijkstra
     * @param reverseOrder if you want to the edges direction to be flipped
     * @param toDouble should convert the edges to a double
     * @return Map<N, Double> where each key is the label of the nodes
     * 		connected to headNode and the value is the distance from headNode
     */
    public Map<N, Double> dijkstra(N headNode, boolean reverseOrder, ToDoubleFunction<E> toDouble) {

        // a map from the nodes in graph that are connected to headNode and the distances from headNode to the node
        Map<N, Double> nodesAndDistances = new HashMap<>();

        // The queue will be sorted by the nodes closest to headNode
        PriorityQueue<Entry<N, Double>> heap = new PriorityQueue<Entry<N, Double>>(new Comparator<Entry<N, Double>>() {
            @Override
            public int compare(Entry<N, Double> o1, Entry<N, Double> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        heap.add(new AbstractMap.SimpleEntry<N, Double>(headNode, 0.0));

        while (!heap.isEmpty()) {

            Entry<N, Double> firstNodePair = heap.poll();
            if (nodesAndDistances.containsKey(firstNodePair.getKey())) {
                continue;
            }

            // update all the nodes connected to firstNodePair that havn't been seen yet
            HashSet<N> possibleNodes = reverseOrder ? getNodesGoingIntoNode(firstNodePair.getKey()) : getNodesConnectedTo(firstNodePair.getKey());
            for (N nextNode : possibleNodes) {

                if (!nodesAndDistances.containsKey(nextNode)) {

                    ArrayList<E> possibleEdges;
                    if (reverseOrder) {
                        possibleEdges = new ArrayList<E>(getEdgesBetweenNodes(nextNode, firstNodePair.getKey()));
                    } else {
                        possibleEdges = new ArrayList<E>(getEdgesBetweenNodes(firstNodePair.getKey(), nextNode));
                    }

                    E distanceAsN = Collections.min(possibleEdges,
                            new Comparator<E>() {

                                @Override
                                public int compare(E e1, E e2) {

                                    Double v1 = toDouble.applyAsDouble(e1);
                                    Double v2 = toDouble.applyAsDouble(e2);
                                    return v1.compareTo(v2);
                                }
                            });

                    double distance = toDouble.applyAsDouble(distanceAsN);

                    heap.add(new AbstractMap.SimpleEntry<N, Double>(nextNode, firstNodePair.getValue() + distance));
                }
            }

            nodesAndDistances.put(firstNodePair.getKey(), firstNodePair.getValue());
        }

        return nodesAndDistances;
    }


    /**
     *
     * @param startNode
     * @param toNode
     * @return an ArrayList of the path. At each element is a Entry<Entry<node1, node2>, edge> where
     * 		the edge is the next path to take to get close to toNode from startNode and node1 and node2
     * 		are connected through edge. If their is not a path then it returns null
     */
    public ArrayList<Entry<Entry<N,N>, E>> findPath(N startNode, N toNode, ToDoubleFunction<E> toDouble) {

        ArrayList<Entry<Entry<N,N>, E>> path = new ArrayList<>();

        Map<N, Double> shortestDistances = dijkstra(toNode, true, toDouble);

        if (!shortestDistances.containsKey(startNode)) {
            return null;
        }

        N lastNode = startNode;
        double lastLength = shortestDistances.get(lastNode);

        while (!lastNode.equals(toNode)) {


            // Get Next Best Node
            N nextNode = null;
            double newLength = lastLength;

            for (N nextPosibleNode : getNodesConnectedTo(lastNode)) {

                double newLastLength = shortestDistances.get(nextPosibleNode).doubleValue();
                E lengthOfPathAsE = Collections.min(this.getEdgesBetweenNodes(lastNode, nextPosibleNode), new Comparator<E>() {
                    @Override
                    public int compare(E e1, E e2) {

                        Double v1 = toDouble.applyAsDouble(e1);
                        Double v2 = toDouble.applyAsDouble(e2);
                        return v1.compareTo(v2);
                    }
                });

                double lengthOfPath = toDouble.applyAsDouble(lengthOfPathAsE);

                if (newLastLength + lengthOfPath <= newLength) {

                    nextNode = nextPosibleNode;
                    newLength = newLastLength;
                }
            }

            E bestEdge = Collections.min(new ArrayList<E>(getEdgesBetweenNodes(lastNode, nextNode)),
                    new Comparator<E>() {

                        @Override
                        public int compare(E e1, E e2) {

                            double v1 = toDouble.applyAsDouble(e1);
                            double v2 = toDouble.applyAsDouble(e2);

                            if (v1 < v2) {
                                return -1;
                            } else if (v2 > v1) {
                                return 1;
                            }
                            return 0;
                        }
                    });

            // update the path for the new node
            path.add(new AbstractMap.SimpleEntry<Entry<N,N>, E>(new AbstractMap.SimpleEntry<N, N>(lastNode, nextNode), bestEdge));

            lastNode = nextNode;
            lastLength = newLength;
        }

        return path;
    }
}
