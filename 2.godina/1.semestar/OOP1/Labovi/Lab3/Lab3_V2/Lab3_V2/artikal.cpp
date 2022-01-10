#include "artikal.h"

Artikal::Artikal(const string& naz,int b, double c)
	:barkod(b), cena(c), naziv(naz)
{
}

int Artikal::getBarkod() const
{
	return barkod;
}

double Artikal::getCena() const
{
	return cena;
}

string Artikal::getNaziv() const
{
	return naziv;
}

bool operator==(const Artikal& a1, const Artikal& a2)
{
	return a1.barkod == a2.barkod;
}

bool operator!=(const Artikal& a1, const Artikal& a2)
{
	return !(a1 == a2);
}
