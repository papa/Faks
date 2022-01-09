#include "igrac.h"

Igrac::Igrac(const string& i, double v)
	:ime(i), vrednost(v)
{
}

void Igrac::pisi(ostream& os) const
{
	os << ime << "#" << vrednost;
}

double Igrac::getVrednost() const
{
	return vrednost;
}

void Igrac::smanjiVrednost(double procenat)
{
	vrednost = (100.0 - procenat) / 100.0 * vrednost;
}

void Igrac::povecajVrednost(double procenat)
{
	vrednost = (100 + procenat) / 100.0 * vrednost;
}

bool operator==(const Igrac& i1, const Igrac& i2)
{
	return i1.ime == i2.ime && i1.vrednost == i2.vrednost;
}

bool operator != (const Igrac& i1, const Igrac& i2)
{
	return !(i1 == i2);
}

ostream& operator<<(ostream& os, const Igrac& i)
{
	i.pisi(os);
	return os;
}
