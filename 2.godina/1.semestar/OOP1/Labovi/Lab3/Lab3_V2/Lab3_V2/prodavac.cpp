#include "prodavac.h"
#include "posiljka.h"

Prodavac::Prodavac(const string& naz)
	:naziv(naz) { }

void Prodavac::dodajUKatalog(tuple<Artikal*, double, int> t)
{
	katalog += t;
}

void Prodavac::obradiPosiljku(Posiljka& p)
{
	double sCena = 0;
	int sDani = 0;
	int len = katalog.getBrElem();
	for (int i = 0; i < len; i++)
	{
		Artikal* a = nullptr;
		double cenaTuple;
		int daniTuple;
		tie(a, cenaTuple, daniTuple) = katalog[i];
		if (*a == p.getArtikal())
		{
			sDani += daniTuple;
			sCena += a->getCena() * cenaTuple;
			break;
		}
	}
	Rukovalac::rukujPosiljkom(p, sDani, sCena);
}

string Prodavac::getNaziv() const
{
	return naziv;
}
