#include <cassert>
#include <fstream>
#include <iostream>
#include <limits>
#include <map>
#include <queue>
#include <set>
#include <sstream>
#include <vector>

class Edge {
public:
  Edge(int i, int j, double cost)
    : i(i), j(j), cost(cost)
  {};

  bool operator<(const Edge& rhs) const {
    return cost > rhs.cost;
  }

  int i;
  int j;
  double cost;
};

class Graph {
public:
  // Constructor that reads in a graph from a file.
  Graph(std::string filename) {
    // http://stackoverflow.com/questions/8116808/read-integers-from-a-text-file-with-c-ifstream

    std::ifstream infile(filename.c_str());
    std::string line;

    if (infile.is_open()) {
      std::getline(infile, line);
      std::istringstream iss(line);
      int n;
      std::vector<int> v;

      while (iss >> n) {
        v.push_back(n);
      }

      vertices = v[0];

      while (std::getline(infile, line)) {
        std::istringstream iss(line);
        double d;
        std::vector<double> v;

        while (iss >> d) {
          v.push_back(d);
        }

        int i = static_cast<int>(v[0]);
        int j = static_cast<int>(v[1]);
        double cost = v[2];
        add(i, j);
        set_edge_value(i, j, cost);
      }

      infile.close();
    }
  }

  // Add edge x->y to graph.
  void add(int x, int y) {
    assert(x >=0 && x < vertices);
    assert(y >=0 && y < vertices);

    adjacency_lists[x].insert(y);
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

  // Sets the value of the edge x->y with value v.
  void set_edge_value(int x, int y, double v) {
    assert(x >=0 && x < vertices);
    assert(y >=0 && y < vertices);
    assert(v > 0 && v < std::numeric_limits<double>::infinity());
    costs[std::make_pair(x, y)] = v;
  }


  // Tests whether there is an edge from node x to node y.
  bool adjacent (const int x, const int y) const {
    assert(x >=0 && x < vertices);
    assert(y >=0 && y < vertices);
    return costs.find(std::make_pair(x, y)) != costs.end();
  }

  void print() {
    for (std::map<int, std::set<int> >::const_iterator iter = adjacency_lists.begin(); iter != adjacency_lists.end(); iter++) {
      std::cout << iter->first << ":";

      for (std::set<int>::const_iterator iter2 = iter->second.begin(); iter2 != iter->second.end(); iter2++) {
        std::cout << " " << *iter2;
      }
      std::cout << std::endl;
    }

    for (int i = 0; i < vertices; i++) {
      for (int j = 0; j < vertices; j++) {
        if (adjacent(i, j)) {
          std::cout << i << "->" << j << ": " << get_edge_value(i, j) << std::endl;
        }
      }
    }
  }

  // Prim's algorithm
  void prim() {
    std::cout << "Prim's algorithm\n";

    std::set<int> visited;
    double total_cost = 0;

    visited.insert(0);

    while (static_cast<int>(visited.size()) < vertices) {
      double min_cost = std::numeric_limits<double>::infinity();
      int min_i = -1;
      int min_j = -1;

      // Find next shortest edge.
      for (int i = 0; i < vertices; i++) {
        for (int j = 0; j < vertices; j++) {
          if (i == j) {
            continue;
          }

          // i must have has been visited.
          if (visited.find(i) == visited.end()) {
            continue;
          }

          // j must not have been visited.
          if (visited.find(j) != visited.end()) {
            continue;
          }

          // There must be an edge.
          if (costs.find(std::make_pair(i, j)) == costs.end()) {
            continue;
          }

          if (costs[std::make_pair(i, j)] < min_cost) {
            min_cost = costs[std::make_pair(i, j)];
            min_i = i;
            min_j = j;
          }
        }
      }

      assert(min_cost < std::numeric_limits<double>::infinity());
      total_cost += min_cost;
      visited.insert(min_j);

      std::cout << "Edge:" << min_i << "->" << min_j << " Value:" << min_cost << std::endl;
    }

    std::cout << "minimum spanning tree: " << total_cost << std::endl;
  }

  void kruskal() {
    std::cout << "Kruskal's algorithm\n";

    // F is the forest, set of trees.
    std::vector<std::set<int> > F;

    for (int i = 0; i < vertices; i++) {
      std::set<int> set;
      set.insert(i);
      F.push_back(set);
    }

    // S is set of all edges in graph
    std::priority_queue<Edge> S;

    for (int i = 0; i < vertices; i++) {
      for (int j = i + 1; j < vertices; j++) {
        if (costs.find(std::make_pair(i, j)) != costs.end()) {
          double cost = costs[std::make_pair(i, j)];

          (void) cost;
          S.push(Edge(i, j, cost));
        }
      }
    }

    double total_cost = 0;

    while (!S.empty()) {
      Edge edge = S.top();
      S.pop();

      // Find the forest set for vertice i and vertice j.
      int set_i = -1;
      int set_j = -1;

      for (std::vector<std::set<int> >::iterator it = F.begin(); it != F.end(); it++) {
        std::set<int> set = *it;

        if (set.find(edge.i) != set.end()) {
          assert(set_i == -1);
          set_i = std::distance(F.begin(), it);
        }

        if (set.find(edge.j) != set.end()) {
          assert(set_j == -1);
          set_j = std::distance(F.begin(), it);
        }
      }

      assert(set_i != -1);
      assert(set_j != -1);

      // Ignore this edge if i and j are already in the same forest set.
      if (set_i == set_j) {
        continue;
      }

      // Join the two sets.
      //
      // Merge the set that contains j into the set that contain i.
      // Then delete set j.
      F[set_i].insert(F[set_j].begin(), F[set_j].end());
      F.erase(F.begin() + set_j);

      std::cout << "Edge:" << edge.i << "->" << edge.j << " Value:" << edge.cost << std::endl;
      total_cost += edge.cost;
    }

    assert(F.size() == 1);
    std::cout << "minimum spanning tree: " << total_cost << std::endl;
  }

private:
  int vertices;
  std::map<int, std::set<int> > adjacency_lists;
  std::map<std::pair<int, int>, double> costs;

};

int main() {
  Graph graph = Graph("cplusplus4c-homeworks-Homework3_SampleTestData_mst_data.txt");
  //Graph graph = Graph("tinyEWG.txt");
  //graph.print();

  graph.prim();
  graph.kruskal();

  return 0;
}
