#ifndef _tacka_h_
#define _tacka_h_

#include <iostream>

using namespace std;

class Tacka
{
	int x, y;

	void pisi(ostream& os) const;
public:
	Tacka(int xx, int yy);

	Tacka(const Tacka& t) = default;
	Tacka& operator = (const Tacka& t) = default;

	friend double dist(const Tacka& t1, const Tacka& t2);

	friend bool operator == (const Tacka& t1, const Tacka& t2);
	friend bool operator != (const Tacka& t1, const Tacka& t2);

	friend ostream& operator << (ostream& os, const Tacka& t);
};

#endif
