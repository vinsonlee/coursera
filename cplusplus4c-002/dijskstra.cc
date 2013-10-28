/*
http://stackoverflow.com/questions/14133115/implementation-of-an-adjacency-list-graph-representation
http://stackoverflow.com/questions/1112531/what-is-the-best-way-to-use-two-keys-with-a-stdmap
*/

#include <assert.h>
#include <iostream>
#include <map>
#include <set>

class Graph {

public:
  Graph(int vertices) : vertices(vertices), edges(0) {
  }

  // Returns the number of vertices in the graph.
  int V() {
    return vertices;
  }

  // Returns the number of edges in the graph.
  int E() {
    assert(false);
    return edges;
  }

  // Tests whether there is an edge from node x to node y.
  bool adjacent(int x, int y) {
    assert(false);
    return false;
  }

  // Lists all nodes y such than there is an edge from x to y.
  void neighbors(int x) {
    assert(false);
    return;
  }

  void add(int x, int y) {
    graph[x].insert(y);
    graph[y].insert(x);
  }

  void remove(int x, int y) {
    assert(false);
    return;
  }

  int get_edge_value(int x, int y) {
    if (costs.find(std::make_pair(x, y)) == costs.end()) {
      // not found
      return 1000;
    } else {
      return costs[std::make_pair(x, y)];
    }
  }

  void set_edge_value(int x, int y, int v) {
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

  //private:
  int vertices;
  int edges;
  std::map<int, std::set<int> > graph;
  std::map<std::pair<int, int>, int> costs;

};

int main() {
  std::cout << "Hello\n";

  Graph G(8);

  std::cout << G.V() << std::endl;

  // create graph from class
  G.add(0, 2);
  G.add(0, 7);
  G.add(0, 3);

  G.add(1, 7);
  G.add(1, 3);

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
  std::cout << G.get_edge_value(0, 2) << std::endl;
  std::cout << G.get_edge_value(1, 200) << std::endl;



  //std::cout << G.graph[0].find(2) << std::endl;



  return 0;
}
