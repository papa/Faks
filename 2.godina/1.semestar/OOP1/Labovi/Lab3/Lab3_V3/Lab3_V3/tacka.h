#ifndef _tacka_h_
#define _tacka_h_

#include <iostream>
#include <cmath>

using namespace std;

class Tacka
{
	int x, y;

	void pisi(ostream& os) const;
public:
	Tacka(int xx, int yy);

	friend double dist(const Tacka& t1, const Tacka& t2);

	friend bool operator == (const Tacka& t1, const Tacka& t2);
	friend bool operator != (const Tacka& t1, const Tacka& t2);

	friend ostream& operator << (ostream& os, const Tacka& t);
};

#endif
