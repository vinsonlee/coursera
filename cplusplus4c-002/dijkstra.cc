/*
  http://stackoverflow.com/questions/14133115/implementation-of-an-adjacency-list-graph-representation
  http://stackoverflow.com/questions/1112531/what-is-the-best-way-to-use-two-keys-with-a-stdmap
*/

#include <cassert>
#include <cstdlib>
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

  // Create a random graph.
  Graph(int vertices, double edge_density, double range_start, double range_end)
    : vertices(vertices),
      edges(0)
  {
    assert(vertices > 0);

    for (int i = 0; i < vertices; i++) {
      for (int j = i + 1; j < vertices; j++) {
        // http://stackoverflow.com/questions/686353/c-random-float-number-generation
        // http://stackoverflow.com/questions/1340729/how-do-you-generate-a-random-double-uniformly-distributed-between-0-and-1-from-c
        double r = static_cast<double>(rand()) / static_cast<double>(RAND_MAX);

        if (r < edge_density) {
          this->add(i, j);
          double v = range_start + static_cast<double>(rand()) / (static_cast<double>(RAND_MAX) / (range_end - range_start));
          set_edge_value(i, j, v);
        }
      }
    }
  }

  // Returns the number of vertices in the graph.
  int V() const {
    return vertices;
  }

  // Returns the number of edges in the graph.
  int E() const {
    return edges;
  }

  // Tests whether there is an edge from node x to node y.
  bool adjacent (const int x, const int y) const {
    assert(x >=0 && x < vertices);
    assert(y >=0 && y < vertices);
    return costs.find(std::make_pair(x, y)) != costs.end();
  }

  // Set nodes to be set of y such than there is an edge from x to y.
  void neighbors(const int x, std::set<int>& nodes) {
    assert(x >=0 && x < vertices);

    nodes.clear();

    for (std::set<int>::const_iterator i = graph[x].begin(); i != graph[x].end(); i++) {
      nodes.insert(*i);
    }
  }

  // Add the edge from x to y, and vice versa.
  void add(const int x, const int y) {
    assert(x >=0 && x < vertices);
    assert(y >=0 && y < vertices);

    graph[x].insert(y);
    graph[y].insert(x);
  }

  // Returns the value associated to the edge (x, y).
  double get_edge_value(int x, int y) {
    assert(x >=0 && x < vertices);
    assert(y >=0 && y < vertices);

    if (x == y) {
      return 0;
    } else if (costs.find(std::make_pair(x, y)) == costs.end()) {
      return std::numeric_limits<double>::infinity();
    } else {
      return costs[std::make_pair(x, y)];
    }
  }

  // Sets the value associated to the edge (x, y) to v.
  void set_edge_value(int x, int y, double v) {
    assert(x >=0 && x < vertices);
    assert(y >=0 && y < vertices);
    assert(v > 0 && v < std::numeric_limits<double>::infinity());
    edges++;
    costs[std::make_pair(x, y)] = v;
    costs[std::make_pair(y, x)] = v;
  }

  void print() {
    for (std::map<int, std::set<int> >::const_iterator iter = graph.begin(); iter != graph.end(); iter++) {
      std::cout << iter->first;

      for (std::set<int>::const_iterator iter2 = iter->second.begin(); iter2 != iter->second.end(); iter2++) {
        std::cout << " " << *iter2;
      }
      std::cout << std::endl;
    }

    for (int i = 0; i < vertices; i++) {
      for (int j = i; j < vertices; j++) {
        if (adjacent(i, j)) {
          std::cout << i << "->" << j << ": " << get_edge_value(i, j) << std::endl;
        }
      }
    }

    std::cout << "edges: " << edges << std::endl;
    std::cout << "possible edges: " << vertices * (vertices - 1) / 2 << std::endl;
    std::cout << "edge density: " << static_cast<double>(edges) / (vertices * (vertices - 1) / 2) << std::endl;
  }

  int vertices;
  int edges;

  // adjacency list
  std::map<int, std::set<int> > graph;

  // edge values
  std::map<std::pair<int, int>, double> costs;
};


class ShortestPath {
public:
  ShortestPath(Graph& graph)
  : graph(graph)
  {
  }


  // http://en.wikipedia.org/wiki/Dijkstra's_algorithm
  void dijkstra(int source) {
    std::map<int, double> dist;
    std::map<int, int> previous;

    for (int v = 0; v < graph.V(); v++) {
      dist[v] = std::numeric_limits<double>::infinity();
      previous[v] = -1;
    }

    dist[source] = 0;

    // Q is set of all nodes in graph.
    std::set<int> Q;
    for (int v = 0; v < graph.V(); v++) {
      Q.insert(v);
    }

    while (!Q.empty()) {
      // Remove u with smallest distance in dist[]
      int u;
      double d = std::numeric_limits<double>::infinity();
      for (std::set<int>::const_iterator iter = Q.begin(); iter != Q.end(); iter++) {
        if (dist[*iter] <= d) {
          u = *iter;
          d = dist[*iter];
        }
      }

      // remove u from Q
      Q.erase(u);

      if (dist[u] == std::numeric_limits<double>::infinity()) {
        break;
      }

      // for each neighbor v of u, that is still in Q
      std::set<int> neighbors;
      graph.neighbors(u, neighbors);
      for (std::set<int>::const_iterator v = neighbors.begin(); v != neighbors.end(); v++) {
        if (Q.find(*v) == Q.end()) {
          continue;
        }

        double alt = dist[u] + graph.get_edge_value(u, *v);

        if (alt < dist[*v]) {
          dist[*v] = alt;
          previous[*v] = u;
        }
      }
    }

    // store everything in shortests_paths
    for (std::map<int, double>::const_iterator i = dist.begin(); i != dist.end(); i++) {
      shortest_path_values[std::make_pair(source, i->first)] = i->second;
    }
  }


  double get_shortest_path_value(int x, int y) {
    if (shortest_path_values.find(std::make_pair(x, y)) == shortest_path_values.end()) {
      this->dijkstra(x);
    }

    return shortest_path_values[std::make_pair(x, y)];
  }


  Graph& graph;
  std::map<std::pair<int, int>, double> shortest_path_values;
};


int main() {
#if 0
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

  G.set_edge_value(0, 2, 1.0);
  G.set_edge_value(0, 3, 3.0);
  G.set_edge_value(0, 7, 4.0);
  G.set_edge_value(1, 3, 4.0);
  G.set_edge_value(1, 7, 3.0);
  G.set_edge_value(2, 3, 3.0);
  G.set_edge_value(2, 4, 1.0);
  G.set_edge_value(3, 4, 1.0);
  G.set_edge_value(3, 5, 5.0);
  G.set_edge_value(3, 7, 7.0);
  G.set_edge_value(3, 8, 3.0);
  G.set_edge_value(4, 6, 2.0);
  G.set_edge_value(4, 8, 4.0);
  G.set_edge_value(5, 8, 5.0);
  G.set_edge_value(6, 8, 3.0);


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

  std::cout << "shortest path from 7 to 6" << std::endl;
  ShortestPath path = ShortestPath(G);
  std::cout << path.get_shortest_path_value(7, 6) << std::endl;
  std::cout << path.get_shortest_path_value(7, 6) << std::endl;

  std::cout << "create random graph\n";
  //Graph graph2 = Graph(50, .4, 1.0, 10.0);
  //graph2.print();
#endif

  std::cout << "Monte Carlo simulations\n";
  const int simulations = 10000;
  std::cout << "simulations: " << simulations << std::endl;

  std::set<double> densities;
  densities.insert(0.2);
  densities.insert(0.4);

  for (std::set<double>::const_iterator density = densities.begin(); density != densities.end(); density++) {
    const int vertices = 50;
    const double start = 1.0;
    const double end = 10.0;

    double average_path_total = 0;

    for (int i = 0; i < simulations; i++) {
      Graph graph = Graph(vertices, *density, start, end);
      ShortestPath shortest_path = ShortestPath(graph);
      double total = 0;
      int no_paths = 0;

      for (int y = 1; y < 50; y++) {
        double value = shortest_path.get_shortest_path_value(0, y);
        assert(value > 0);

        if (value == std::numeric_limits<double>::infinity()) {
          no_paths += 1;
          continue;
        }

        total += value;
      }
      double average_path = total / (49 - no_paths);
      average_path_total += average_path;
    }

    double average = average_path_total / simulations;
    std::cout << "density " << *density << ": " << average << std::endl;
  }

  return 0;
}
