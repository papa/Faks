#ifndef _angle_h
#define _angle_h
#include <iostream>

using namespace std;

const double fact = 3.14 / 180;

class Angle
{
	double radians;
public:
	//constructor
	//radians = 0.0, 0 is value if not said different
	Angle(double radians = 0.0)
	{
		this->radians = radians;
	}
	
	Angle(double degrees, double minutes, double seconds)
	{
		this->radians = ((seconds / 60 + minutes) / 60 + degrees)*fact;
	}

	double get_radians() const { return radians;}

	int get_degrees() const { return radians / fact; }

	int get_minutes() const { return int(radians / fact * 60) % 60; }

	int get_seconds() const { return int(radians / fact * 3600) % 60; }

	void get_degs_mins_secs(int& degs, int& mins, int& secs)
	{
		degs = get_degrees();
		mins = get_minutes();
		secs = get_seconds();
	}

	//if the return value was Angle, there would be a copy
	//of object refered to by this
	Angle& add(Angle ang)
	{
		this->radians += ang.radians;
		return *this;
	}

	Angle& multiple(double a)
	{
		this->radians *= a;
		return *this;
	}

	void read_angle() { cin >> this->radians; }

	void read_degs()
	{
		int deg, min, sec;
		cin >> deg >> min >> sec;
		*this = Angle(deg, min, sec); // using constructor to make a degree
	}

	void print() const { cout << radians << endl; }

	void print_deg_min_sec() const;
};

#endif // !_angle_h

