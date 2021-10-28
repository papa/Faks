#include "tacka.h"
#include <cmath>

using namespace std;

//calculates distance to another point
double Point::distance(Point p) const
{
	return sqrt(pow(this->x - p.x, 2) + pow(this->y - p.y, 2));
}
