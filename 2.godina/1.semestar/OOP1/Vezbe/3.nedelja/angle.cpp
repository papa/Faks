#include "angle.h"

using namespace std;

void Angle::print_deg_min_sec () const
{
	cout << "(" << 
		get_degrees() << ":" <<
		get_minutes() << ":" <<
		get_seconds() << ")" << endl;
}
