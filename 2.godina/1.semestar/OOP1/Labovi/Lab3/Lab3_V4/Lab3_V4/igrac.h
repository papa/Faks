#ifndef _igrac_h_
#define _igrac_h_

#include <iostream>
#include <string>

using namespace std;

class Igrac
{
	string ime;
	double vrednost;

	void pisi(ostream& os) const;
public:
	Igrac(const string& i, double v = 1000.0);

	double getVrednost() const;

	void smanjiVrednost(double procenat);
	void povecajVrednost(double procenat);

	friend bool operator == (const Igrac& i1, const Igrac& i2);
	friend bool operator != (const Igrac& i1, const Igrac& i2);

	friend ostream& operator << (ostream& os, const Igrac& i);
};

#endif

