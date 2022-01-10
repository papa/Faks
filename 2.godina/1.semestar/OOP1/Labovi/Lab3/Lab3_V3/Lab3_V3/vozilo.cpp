#include "vozilo.h"

double Vozilo::cenaJed = 0.1;
void Vozilo::pisi(ostream& os) const
{
	os << naziv;
}

Vozilo::Vozilo(const string& naz)
	:naziv(naz)
{
}

double Vozilo::cenaPuta(const Put& p) const
{
	return p.duzinaPuta() * cenaJed + startnaCena();
}

Vozilo::~Vozilo()
{
}

ostream& operator<<(ostream& os, const Vozilo& v)
{
	v.pisi(os);
	return os;
}
