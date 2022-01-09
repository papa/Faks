#include "posiljka.h"
#include "rukovalac.h"

int Posiljka::ID = 0;

void Posiljka::pisi(ostream& os) const
{
	os << "Posiljka[" << idPos << ", " << artikal.getNaziv() << "]";
}


Posiljka::Posiljka(const Artikal& a)
	: artikal(a), detalji(0, 0)
{
}

Posiljka& Posiljka::operator+=(Rukovalac* r)
{
	if (izracunaoDetalje) throw GDodavanjeRukovaoca();
	rukovaoci += r;
	return *this;
}

void Posiljka::izracunajDetaljePosiljke()
{
	detalji.cena = 0;
	detalji.dani = 0;
	int len = rukovaoci.getBrElem();
	for (int i = 0; i < len; i++)
	{
		rukovaoci[i]->obradiPosiljku(*this);
	}
	izracunaoDetalje = true;
}

int Posiljka::getID() const
{
	return idPos;
}

const Artikal& Posiljka::getArtikal() const
{
	return artikal;
}

int Posiljka::getBrDana() const
{
	if (!izracunaoDetalje) throw GNeizracunatiDetalji();
	return detalji.dani;
}

double Posiljka::getCena() const
{
	if (!izracunaoDetalje) throw GNeizracunatiDetalji();
	return detalji.cena;
}

ostream& operator<<(ostream& os, const Posiljka& p)
{
	p.pisi(os);
	return os;
}