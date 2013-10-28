//  Convert this program to C++
//  change to C++ io
//  change to one line comments
//  change defines of constants to const
//  change array to vector<>
//  inline any short function
//

#include <iostream>
#include <vector>

const int N = 40;

// sum the first n elements of vector.
inline void sum(int &p, int n, std::vector<int> &d)
{
  p = 0;

  for (int i = 0; i < n; ++i) {
    p = p + d[i];
  }
}

int main()
{
  std::vector<int> data(N);

  for (int i = 0; i < N; ++i) {
    data[i] = i;
  }

  int accum = 0;
  sum(accum, N, data);
  std::cout << "sum is " << accum << std::endl;

  return 0;
}
