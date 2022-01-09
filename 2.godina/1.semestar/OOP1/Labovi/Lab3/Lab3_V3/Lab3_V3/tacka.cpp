#include "tacka.h"

void Tacka::pisi(ostream& os) const
{
	os << "(" << x << "," << y << ")";
}

Tacka::Tacka(int xx, int yy)
	:x(xx), y(yy)
{
}

double dist(const Tacka& t1, const Tacka& t2)
{
	return sqrt((t1.x - t2.x) * (t1.x - t2.x) + (t1.y - t2.y) * (t1.y - t2.y));
}

bool operator==(const Tacka& t1, const Tacka& t2)
{
	return t1.x == t2.x && t1.y == t2.y;
}

bool operator!=(const Tacka& t1, const Tacka& t2)
{
	return !(t1 == t2);
}

ostream& operator<<(ostream& os, const Tacka& t)
{
	t.pisi(os);
	return os;
}
