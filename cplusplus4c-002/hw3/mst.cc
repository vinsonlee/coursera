#include <cassert>
#include <fstream>
#include <iostream>
#include <limits>
#include <map>
#include <set>
#include <sstream>
#include <vector>

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

      this->vertices = v[0];

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

  void add(const int x, const int y) {
    assert(x >=0 && x < this->vertices);
    assert(y >=0 && y < this->vertices);

    this->adjacency_lists[x].insert(y);
  }


  // Returns the value associated to the edge (x, y).
  double get_edge_value(int x, int y) {
    assert(x >=0 && x < this->vertices);
    assert(y >=0 && y < this->vertices);

    if (x == y) {
      return 0;
    } else if (this->costs.find(std::make_pair(x, y)) == this->costs.end()) {
      return std::numeric_limits<double>::infinity();
    } else {
      return this->costs[std::make_pair(x, y)];
    }
  }

  void set_edge_value(int x, int y, double v) {
    assert(x >=0 && x < this->vertices);
    assert(y >=0 && y < this->vertices);
    assert(v > 0 && v < std::numeric_limits<double>::infinity());
    this->costs[std::make_pair(x, y)] = v;
  }


  // Tests whether there is an edge from node x to node y.
  bool adjacent (const int x, const int y) const {
    assert(x >=0 && x < this->vertices);
    assert(y >=0 && y < this->vertices);
    return this->costs.find(std::make_pair(x, y)) != this->costs.end();
  }

  void print() {
    for (std::map<int, std::set<int> >::const_iterator iter = this->adjacency_lists.begin();
         iter != this->adjacency_lists.end();
         iter++) {
      std::cout << iter->first << ":";

      for (std::set<int>::const_iterator iter2 = iter->second.begin(); iter2 != iter->second.end(); iter2++) {
        std::cout << " " << *iter2;
      }
      std::cout << std::endl;
    }

    for (int i = 0; i < this->vertices; i++) {
      for (int j = 0; j < this->vertices; j++) {
        if (this->adjacent(i, j)) {
          std::cout << i << "->" << j << ": " << this->get_edge_value(i, j) << std::endl;
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
            //std::cout << i << " " << j << " " << std::endl;
            min_cost = costs[std::make_pair(i, j)];
            min_i = i;
            min_j = j;
          }
        }
      }

      assert(min_cost != std::numeric_limits<double>::infinity());
      total_cost += min_cost;
      visited.insert(min_j);

      std::cout << "Edge:" << min_i << "->" << min_j << " Value:" << min_cost << std::endl;
    }

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

  return 0;
}
