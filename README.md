# Graph
This is my implementation of a generic Graph represent a directed multigraph with nodes and edges and reflexive edges.

It can run Dijkstra and find the shortest path using Diajkstra. You can also easily see all the nodes tht are connected
from/to each node so writing your own algorithms like A* or BFS is easy.

Below is an example of how to find the path. Path contains all of the edges and the distances between each nodes along the way.

```
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
```
