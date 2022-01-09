#include "elektronska_poruka.h"

ostream& operator<<(ostream& os, const ElektronskaPoruka& ep)
{
	ep.pisi(os);
	return os;
}

void ElektronskaPoruka::pisi(ostream& os) const
{
	os << naslov << endl;
	os << posiljalac << endl;
	os << primalac << endl;
}

ElektronskaPoruka::ElektronskaPoruka(Korisnik& pos,  Korisnik& prim, const string& nasl)
	:posiljalac(pos), primalac(prim), naslov(nasl)
{
}

string ElektronskaPoruka::getNaslov() const
{
	return naslov;
}

Korisnik& ElektronskaPoruka::getPrimalac() const
{
	return primalac;
}

Korisnik& ElektronskaPoruka::getPosiljalac() const
{
	return posiljalac;
}

ElektronskaPoruka::~ElektronskaPoruka()
{
	
}
