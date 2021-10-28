#include <iostream>
#include "tacka.h"

using namespace std;

int main()
{
	Point p1; // object
	//double x, y;
	//cin >> x >> y;
	//p1.set_coordinates(x, y);
	p1.read_coordinates();
	cout << "Point 1";
	p1.print_coordinates();

	Point p2;
	p2.read_coordinates();
	cout << "Point 2";
	p2.print_coordinates();

	double dist = p1.distance(p2);
	cout << "Distance " << dist << endl;

	return 0;
}

//testing Point class