/*
  http://stackoverflow.com/questions/14133115/implementation-of-an-adjacency-list-graph-representation
  http://stackoverflow.com/questions/1112531/what-is-the-best-way-to-use-two-keys-with-a-stdmap
*/

#include <assert.h>
#include <iostream>
#include <limits>
#include <map>
#include <queue>
#include <set>

class Graph {

public:
  Graph(int vertices)
  : vertices(vertices),
    edges(0)
  {
  }

  // Returns the number of vertices in the graph.
  int V() {
    return vertices;
  }

  // Returns the number of edges in the graph.
  int E() {
    return edges;
  }

  // Tests whether there is an edge from node x to node y.
  bool adjacent(int x, int y) {
    assert(x < vertices);
    assert(y < vertices);
    return costs.find(std::make_pair(x, y)) != costs.end();
  }

  // Set nodes to be set of nodes y such than there is an edge from x to y.
  void neighbors(int x, std::set<int>& nodes) {
    nodes.clear();

    for (std::set<int>::const_iterator i = graph[x].begin(); i != graph[x].end(); i++) {
      nodes.insert(*i);
    }
  }

  // Add the edge from x to y, and vice versa.
  void add(int x, int y) {
    assert(x < vertices);
    assert(y < vertices);
    graph[x].insert(y);
    graph[y].insert(x);
  }

  // Returns the value associated to the edge (x, y).
  int get_edge_value(int x, int y) {
    assert(x < vertices);
    assert(y < vertices);

    if (x == y) {
      return 0;
    } else if (costs.find(std::make_pair(x, y)) == costs.end()) {
      return std::numeric_limits<int>::max();
    } else {
      return costs[std::make_pair(x, y)];
    }
  }

  // Sets the value associated to the edge (x, y) to v.
  void set_edge_value(int x, int y, int v) {
    assert(x < vertices);
    assert(y < vertices);
    edges++;
    costs[std::make_pair(x, y)] = v;
    costs[std::make_pair(y, x)] = v;
  }

  void print() {
    for (std::map<int, std::set<int> >::const_iterator iter = graph.begin(); iter != graph.end(); iter++) {
      // std::cout << "print" << std::endl;
      std::cout << iter->first;

      for (std::set<int>::const_iterator iter2 = iter->second.begin(); iter2 != iter->second.end(); iter2++) {
        std::cout << " " << *iter2;
      }
      std::cout << std::endl;
    }
  }

  int vertices;
  int edges;

  // adjacency list
  std::map<int, std::set<int> > graph;

  std::map<std::pair<int, int>, int> costs;

};


class PriorityQueue {

  void chgPriority(int priority) {
    assert(false);
  }

  int minPriority() {
    return queue.top();
  }

  bool contains(int queue_element);


  void insert(int queue_element) {
    queue.push(queue_element);
  }

  int top() {
    return queue.top();
  }

  int size() {
    return queue.size();
  }

  std::priority_queue<int> queue;
};


class ShortestPath {

public:
  ShortestPath(Graph& graph)
  : graph(graph)
  {
  }

  // http://en.wikipedia.org/wiki/Dijkstra's_algorithm
  void dijkstra(int source, int target) {
    // check if already in shortest path

    std::map<int, int> dist;
    std::map<int, int> previous;

    for (int v = 0; v < graph.V(); v++) {
      dist[v] = std::numeric_limits<int>::max();
      previous[v] = -1;
    }

    dist[source] = 0;

    // Q is set of all nodes in graph.
    std::set<int> Q;
    for (int v = 0; v < graph.V(); v++) {
      Q.insert(v);
    }

    while (!Q.empty()) {
      std::cout << "Q: ";
      for (std::set<int>::iterator iter = Q.begin(); iter != Q.end(); iter++) {
        std::cout << *iter << " ";
      }
      std::cout << "\n";

      // Remove u with smallest distance in dist[]
      int u = -1;
      int d = std::numeric_limits<int>::max();
      for (std::set<int>::iterator iter = Q.begin(); iter != Q.end(); iter++) {
        if (dist[*iter] < d) {
          u = *iter;
          d = dist[*iter];
        }
      }
      assert(d != std::numeric_limits<int>::max());
      assert(u != -1);

      // fixme
      // terminate at target
      //if (target == u) {
      //  break;
      //}

      // remove u from Q
      std::cout << "Remove node " << u << std::endl;
      Q.erase(u);

      if (dist[u] == std::numeric_limits<int>::max()) {
        // std::cout << "inaccessible\n";
        break;
      }

      // for each neighbor v of u, that is still in Q
      std::set<int> neighbors;
      graph.neighbors(u, neighbors);

      std::cout << "Neighbors of node " << u << ": ";
      for (std::set<int>::const_iterator v = neighbors.begin(); v != neighbors.end(); v++) {
        if (Q.find(*v) == Q.end()) {
          continue;
        }
        std::cout << *v << " ";
      }
      std::cout << std::endl;

      for (std::set<int>::const_iterator v = neighbors.begin(); v != neighbors.end(); v++) {
        if (Q.find(*v) == Q.end()) {
          continue;
        }

        //std::cout << "neighbor: " << *v << std::endl;

        int alt = dist[u] + graph.get_edge_value(u, *v);
        //std::cout << "alt: " << alt << std::endl;

        if (alt < dist[*v]) {
          dist[*v] = alt;
          previous[*v] = u;
        }
      }

      // print out all nodes in Q
      //for (std::set<int>::iterator iter = Q.begin(); iter != Q.end(); iter++) {
      //    std::cout << *iter << " ";
      //}
      //std::cout << "\n";

      //assert(false);
      std::cout << "Distance from " << source << std::endl;
      for (std::map<int, int>::iterator i = dist.begin(); i != dist.end(); i++) {
        std::cout << source << "->" << i->first << " : " << i->second << std::endl;
      }
    }

    std::cout << "Distance from " << source << std::endl;
    for (std::map<int, int>::iterator i = dist.begin(); i != dist.end(); i++) {
      std::cout << source << "->" << i->first << " : " << i->second << std::endl;
    }


    // store everything in shortests_paths
  }

  Graph& graph;
  //PriorityQueue& queue;
  std::map<std::pair<int, int>, int> shortest_paths;

};


int main() {
  std::cout << "Hello\n";

  Graph G(9);

  std::cout << G.V() << std::endl;

  // create graph from class
  G.add(0, 2);
  G.add(0, 3);
  G.add(0, 7);
  G.add(1, 3);
  G.add(1, 7);
  G.add(2, 3);
  G.add(2, 4);
  G.add(3, 4);
  G.add(3, 5);
  G.add(3, 7);
  G.add(3, 8);
  G.add(4, 6);
  G.add(4, 8);
  G.add(5, 8);
  G.add(6, 8);

  G.print();

  G.set_edge_value(0, 2, 1);
  G.set_edge_value(0, 3, 3);
  G.set_edge_value(0, 7, 4);
  G.set_edge_value(1, 3, 4);
  G.set_edge_value(1, 7, 3);
  G.set_edge_value(2, 3, 3);
  G.set_edge_value(2, 4, 1);
  G.set_edge_value(3, 4, 1);
  G.set_edge_value(3, 5, 5);
  G.set_edge_value(3, 7, 7);
  G.set_edge_value(3, 8, 3);
  G.set_edge_value(4, 6, 2);
  G.set_edge_value(4, 8, 4);
  G.set_edge_value(5, 8, 5);
  G.set_edge_value(6, 8, 3);


  std::cout << G.get_edge_value(0, 2) << std::endl;
  std::cout << G.get_edge_value(1, 5) << std::endl;

  std::cout << G.adjacent(0, 2) << std::endl;
  std::cout << G.adjacent(2, 0) << std::endl;
  std::cout << G.adjacent(4, 5) << std::endl;

  std::cout << "neighbors of node 5\n";
  std::set<int> S;
  G.neighbors(5, S);
  for (std::set<int>::const_iterator i = S.begin(); i != S.end(); i++) {
    std::cout << *i << " ";
  }
  std::cout << std::endl;

  std::cout << "DDDDD\n\n" << std::endl;

  std::cout << "shortest path from 7 to 6" << std::endl;
  ShortestPath path = ShortestPath(G);
  path.dijkstra(7, 6);

  //path.dijkstra(6, 7);

  return 0;
}
