#include "put.h"

ostream& operator<<(ostream& os, const Put& p)
{
	p.pisi(os);
	return os;
}

void Put::pisi(ostream& os) const
{
	int len = tacke.getBrElem();
	for (int i = 0; i < len; i++)
		os << *tacke[i] << endl;
}

Put& Put::operator+=(const Tacka* t)
{
	int len = tacke.getBrElem();
	for (int i = 0; i < len; i++)
	{
		if (*tacke[i] == *t)
			throw GIsteTacke();
	}
	tacke += t;
}

double Put::duzinaPuta() const
{
	double sum = 0;
	int len = tacke.getBrElem();
	for (int i = 1; i < len; i++)
		sum += dist(*tacke[i - 1], *tacke[i]);
	return sum;
}
