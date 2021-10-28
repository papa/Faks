#ifndef _tacka_h_
#define _tacka_h_

#include <iostream>

using namespace std;

class Point
{
	//private, if not defined differently
	double x, y;
public:

	//methods which are implemented in class, not 
	//implemented through cpp file
	//are inline methods

	//this pointer hidden as parameter
	void set_coordinates(double a, double b)
	{
		this->x = a; // x = a;
		this->y = b; // y = b;
	}

	//const methods don't change parameters of the object
	//can be called for const and not const objects
	double get_x_coordinate() const { return this->x; }
	double get_y_coordinate() const { return this->y; }

	//will be implemented in cpp file
	//in header file there should be only some simple functions
	//getting, setting parameters...
	double distance(Point p) const;

	void read_coordinates()
	{
		cin >> this->x >> this->y;
	}

	void print_coordinates() const
	{
		cout << "(" << this->x << "," << this->y << ")" << endl;
	}
};


#endif // !_tacka_h_

