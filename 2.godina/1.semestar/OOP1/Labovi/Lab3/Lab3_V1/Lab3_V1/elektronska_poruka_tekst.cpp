#include "elektronska_poruka_tekst.h"

void ElektronskaPorukaSaTekstom::pisi(ostream& os) const
{
	ElektronskaPoruka::pisi(os);
	os << tekst << endl;
}

ElektronskaPorukaSaTekstom::ElektronskaPorukaSaTekstom(Korisnik& pos, Korisnik& prim, const string& nasl)
	:ElektronskaPoruka(pos, prim, nasl)
{
}

void ElektronskaPorukaSaTekstom::posaljiPoruku()
{
	stanjePoruke = stanje::POSLATA;
}

ElektronskaPorukaSaTekstom* ElektronskaPorukaSaTekstom::kopiraj() const
{
	return new ElektronskaPorukaSaTekstom(*this);
}

void ElektronskaPorukaSaTekstom::postaviTekst(const string& tekst)
{
	if (stanjePoruke == stanje::POSLATA) throw GPoslataPoruka();
	this->tekst = tekst;
}

ElektronskaPorukaSaTekstom::~ElektronskaPorukaSaTekstom()
{
}
