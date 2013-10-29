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
  Graph(int vertices) : vertices(vertices), edges(0) {
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

  // Lists all nodes y such than there is an edge from x to y.
  void neighbors(int x) {
    assert(false);
    return;
  }

  void add(int x, int y) {
    assert(x < vertices);
    assert(y < vertices);
    graph[x].insert(y);
    graph[y].insert(x);
  }

  void remove(int x, int y) {
    assert(false);
    return;
  }

  int get_edge_value(int x, int y) {
    assert(x < vertices);
    assert(y < vertices);
      if (x == y) {
          return 0;
      }
      
    else if (costs.find(std::make_pair(x, y)) == costs.end()) {
      return std::numeric_limits<int>::max();
    } else {
      return costs[std::make_pair(x, y)];
    }
  }

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

  //private:
  int vertices;
  int edges;
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
  // vertices
  // path
  // path_size

  // http://en.wikipedia.org/wiki/Dijkstra's_algorithm
  void dijkstra(int source, int y) {
      std::map<int, int> dist;
      std::map<int, int> previous;
      
    for (int v = 0; v < graph.V(); v++) {
      std::cout << v << std::endl;
        
        dist[v] = std::numeric_limits<int>::max();
        previous[v] = -1;
    }
      
      dist[source] = 0;
      
      std::set<int> Q;
      for (int v = 0; v < graph.V(); v++ ){
          Q.insert(v);
      }
      
      std::cout << "foo\n";
      
      while (!Q.empty()) {
          // print out all nodes in Q
          for (std::set<int>::iterator iter = Q.begin(); iter != Q.end(); iter++) {
              std::cout << *iter << " ";
          }
          std::cout << "\n";
          
          
          // u := vertex in Q with smallest distance in dist[]
          int u = -1;
          int d = std::numeric_limits<int>::max();
          for (std::set<int>::iterator iter = Q.begin(); iter != Q.end(); iter++) {
              if (dist[*iter] < d) {
                  u = *iter; //???
              }
          }
          
          assert(u != -1);
          // remove u from Q
          std::cout << "remove node " << u << std::endl;
          Q.erase(u);

          if (dist[u] == std::numeric_limits<int>::max()) {
              break;
          }
          
          // print out all nodes in Q
          //for (std::set<int>::iterator iter = Q.begin(); iter != Q.end(); iter++) {
          //    std::cout << *iter << " ";
          //}
          //std::cout << "\n";

          
          //assert(false);
      }
      
  }

  Graph& graph;
  //PriorityQueue& queue;

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

  std::cout << "DDDDD" << std::endl;

  ShortestPath path = ShortestPath(G);
  path.dijkstra(7, 6);

  return 0;
}
