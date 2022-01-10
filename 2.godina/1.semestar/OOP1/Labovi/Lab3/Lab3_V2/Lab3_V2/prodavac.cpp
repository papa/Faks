#include "prodavac.h"
#include "posiljka.h"

Prodavac::Prodavac(const string& naz)
	:naziv(naz) { }

void Prodavac::dodajUKatalog(const Artikal& a, double m, int d)
{
	Trojka tr(a, m, d);
	katalog += tr;
}

void Prodavac::obradiPosiljku(Posiljka& p)
{
	int len = katalog.getBrElem();
	for (int i = 0; i < len; i++)
	{	
		Trojka& tr = katalog[i];
		if (tr.art == p.getArtikal())
		{
			p.detalji.dani += tr.dani;
			p.detalji.cena += tr.art.getCena() * tr.marza;
			break;
		}
	}
}

string Prodavac::getNaziv() const
{
	return naziv;
}
