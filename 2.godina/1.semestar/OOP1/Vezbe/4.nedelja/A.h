#ifndef _a_h_
#define _a_h_

#include <iostream>

using namespace std;

class A
{
public:
	void print_kvadar(const Kvadar& k)
	{
		cout << k.a << " " << k.b << " " << k.c << endl;
	}
};

#endif _a_h_